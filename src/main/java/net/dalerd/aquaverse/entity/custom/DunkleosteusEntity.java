package net.dalerd.aquaverse.entity.custom;

import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.SwimNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.Map;
import java.util.WeakHashMap;

public class DunkleosteusEntity extends WaterCreatureEntity {

    // === Animation states ===
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState walkAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState waterIdleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    // Track last attack times for non-player targets
    private final Map<LivingEntity, Long> lastAttackTime = new WeakHashMap<>();
    private static final long NON_PLAYER_ATTACK_COOLDOWN = 10 * 60 * 20L; // 10 minutes in ticks

    public DunkleosteusEntity(EntityType<? extends WaterCreatureEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new DunkleosteusMoveControl(this);
    }

    @Override
    public net.minecraft.registry.RegistryKey<net.minecraft.loot.LootTable> getLootTableId() {
        return net.minecraft.registry.RegistryKey.of(
                net.minecraft.registry.RegistryKeys.LOOT_TABLE,
                net.minecraft.util.Identifier.of("aquaverse", "entities/dunkleosteus")
        );
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        return new SwimNavigation(this, world);
    }

    @Override
    protected void initGoals() {
        // Hunting
        this.goalSelector.add(0, new ReturnToWaterGoal.DunkleosteusAttackGoal(this, 1.3));

        // Swimming / idle behavior
        this.goalSelector.add(0, new ReturnToWaterGoal.DunkleosteusSwimGoal(this, 1.0)); // patrol swimming
        this.goalSelector.add(2, new SwimAroundGoal(this, 1.0, 10));
        this.goalSelector.add(3, new WanderAroundGoal(this, 1.0));

        // Looking
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 12.0f));
        this.goalSelector.add(5, new LookAroundGoal(this));
        this.goalSelector.add(0, new ReturnToWaterGoal(this, 1.2));

        // Boat attack
        this.goalSelector.add(3, new ReturnToWaterGoal.AttackBoatGoal(this, 1.2));

        // Targeting
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true)); // always attack players
        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(0, new ReturnToWaterGoal.ConditionalWaterMobTargetGoal<>(this, WaterCreatureEntity.class, NON_PLAYER_ATTACK_COOLDOWN));
        this.targetSelector.add(0, new ReturnToWaterGoal.ConditionalWaterMobTargetGoal<>(this, HostileEntity.class, NON_PLAYER_ATTACK_COOLDOWN));
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 100.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.35D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 10.0D) // reduced from 80 -> 20
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 40.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_ARMOR, 20.0D);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getWorld().isClient) {
            setupAnimationStates();
        }
    }

    private void setupAnimationStates() {
        if (this.isTouchingWater()) {
            if (!this.waterIdleAnimationState.isRunning()) {
                this.waterIdleAnimationState.start(this.age);
            }
            this.walkAnimationState.stop();
            this.idleAnimationState.stop();
        } else {
            if (this.getVelocity().horizontalLengthSquared() > 0.001) {
                if (!this.walkAnimationState.isRunning()) this.walkAnimationState.start(this.age);
                this.idleAnimationState.stop();
            } else {
                if (!this.idleAnimationState.isRunning()) this.idleAnimationState.start(this.age);
                this.walkAnimationState.stop();
            }
            this.waterIdleAnimationState.stop();
        }

        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 40;
            this.idleAnimationState.start(this.age);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    @Override
    public boolean tryAttack(net.minecraft.entity.Entity target) {
        if (super.tryAttack(target)) {
            this.attackAnimationState.start(this.age);
            return true;
        }
        return false;
    }

    // ===============================================
    // ReturnToWaterGoal + AI nested classes
    // ===============================================
    static class ReturnToWaterGoal extends Goal {
        private final DunkleosteusEntity mob;
        private final double crawlSpeed;
        private BlockPos targetWater; // center of target water block

        public ReturnToWaterGoal(DunkleosteusEntity mob, double crawlSpeed) {
            this.mob = mob;
            this.crawlSpeed = crawlSpeed;
            this.setControls(EnumSet.of(Control.MOVE));
        }

        @Override
        public boolean canStart() {
            if (mob.isTouchingWater()) return false;
            targetWater = findNearestSurfaceWater();
            return targetWater != null;
        }

        @Override
        public void start() {
            if (targetWater != null) {
                mob.getNavigation().startMovingTo(
                        targetWater.getX() + 0.5,
                        targetWater.getY() + 0.5,
                        targetWater.getZ() + 0.5,
                        crawlSpeed
                );
            }
        }

        @Override
        public void stop() {
            targetWater = null;
            mob.getNavigation().stop();
        }

        @Override
        public void tick() {
            if (targetWater == null) return;
            if (mob.getWorld().getFluidState(mob.getBlockPos()).isIn(FluidTags.WATER)) {
                targetWater = null;
                return;
            }

            if (!mob.getNavigation().isFollowingPath()) {
                double dx = (targetWater.getX() + 0.5) - mob.getX();
                double dz = (targetWater.getZ() + 0.5) - mob.getZ();
                double horizontalSq = dx * dx + dz * dz;

                if (horizontalSq < 2.5D) {
                    Vec3d dir = new Vec3d(dx, (targetWater.getY() + 0.5) - mob.getY(), dz).normalize();
                    mob.setVelocity(mob.getVelocity().multiply(0.5).add(dir.multiply(0.12)));
                    mob.velocityDirty = true;
                } else {
                    Vec3d dir = new Vec3d(dx, 0, dz).normalize();
                    mob.setVelocity(mob.getVelocity().multiply(0.6).add(dir.multiply(0.06)));
                    mob.velocityDirty = true;
                }
            }

            double dx = (targetWater.getX() + 0.5) - mob.getX();
            double dz = (targetWater.getZ() + 0.5) - mob.getZ();
            float targetYaw = (float) (MathHelper.atan2(dx, dz) * (180F / Math.PI));
            mob.setYaw(targetYaw);
            mob.bodyYaw = targetYaw;
        }

        private BlockPos findNearestSurfaceWater() {
            BlockPos mobPos = mob.getBlockPos();
            int radius = 8;

            for (int y = 2; y >= -2; y--) {
                for (BlockPos checkPos : BlockPos.iterateOutwards(mobPos.add(0, y, 0), radius, 2, radius)) {
                    if (mob.getWorld().getFluidState(checkPos).isIn(FluidTags.WATER) && is2x2Water(checkPos)) {
                        return checkPos;
                    }
                }
            }
            return null;
        }

        private boolean is2x2Water(BlockPos pos) {
            World world = mob.getWorld();
            for (int dx = 0; dx < 2; dx++) {
                for (int dz = 0; dz < 2; dz++) {
                    BlockPos check = pos.add(dx, 0, dz);
                    if (!world.getFluidState(check).isIn(FluidTags.WATER)) return false;
                    if (!world.getFluidState(check.up()).isIn(FluidTags.WATER) && !world.isAir(check.up()))
                        return false;
                }
            }
            return true;
        }

        // ------------------- Custom target goal for non-player water mobs -------------------
        static class ConditionalWaterMobTargetGoal<T extends LivingEntity> extends ActiveTargetGoal<T> {

            private final DunkleosteusEntity dunk;
            private final long cooldown;

            public ConditionalWaterMobTargetGoal(DunkleosteusEntity mob, Class<T> targetClass, long cooldown) {
                super(mob, targetClass, true);
                this.dunk = mob;
                this.cooldown = cooldown;
            }

            @Override
            public boolean canStart() {
                T target = this.mob.getWorld().getClosestEntity(
                        this.targetClass,
                        this.targetPredicate,
                        this.mob,
                        this.mob.getX(), this.mob.getY(), this.mob.getZ(),
                        this.mob.getBoundingBox().expand(20.0D)
                );
                if (target == null) return false;
                if (target instanceof PlayerEntity) return true;

                long lastAttack = dunk.lastAttackTime.getOrDefault(target, 0L);
                if (dunk.getHealth() <= dunk.getMaxHealth() / 2.0 || dunk.getWorld().getTime() - lastAttack >= cooldown) {
                    dunk.lastAttackTime.put(target, dunk.getWorld().getTime());
                    this.target = target;
                    return true;
                }
                return false;
            }
        }

        // ------------------- Attack goal -------------------
        static class DunkleosteusAttackGoal extends Goal {
            private final DunkleosteusEntity dunk;
            private final double speed;
            private int biteDelay = -1;
            private int lungeCooldown = 0;

            public DunkleosteusAttackGoal(DunkleosteusEntity dunk, double speed) {
                this.dunk = dunk;
                this.speed = speed;
            }

            @Override
            public boolean canStart() {
                return dunk.getTarget() != null && dunk.getTarget().isAlive();
            }

            @Override
            public boolean shouldContinue() {
                return dunk.getTarget() != null && dunk.getTarget().isAlive();
            }

            @Override
            public void stop() {
                dunk.getNavigation().stop();
                biteDelay = -1;
            }

            @Override
            public void tick() {
                LivingEntity target = dunk.getTarget();
                if (target == null) return;

                double dx = target.getX() - dunk.getX();
                double dy = target.getY() - dunk.getY();
                double dz = target.getZ() - dunk.getZ();
                double distSq = dx * dx + dy * dy + dz * dz;

                dunk.getNavigation().startMovingTo(target, speed);
                dunk.getLookControl().lookAt(target, 45.0F, 45.0F);
                dunk.setYaw(-((float) MathHelper.atan2(dx, dz)) * (180F / (float) Math.PI));
                dunk.bodyYaw = dunk.getYaw();

                if (biteDelay > 0) {
                    biteDelay--;
                    if (biteDelay == 0 && distSq < getSquaredMaxAttackDistance(target) + 1.5D) {
                        dunk.tryAttack(target);
                        dunk.swingHand(dunk.getActiveHand());
                    }
                }

                double reach = getSquaredMaxAttackDistance(target);
                if (distSq <= (reach + 4.0D) && distSq > reach && lungeCooldown <= 0) {
                    Vec3d dir = new Vec3d(dx, dy, dz).normalize();
                    dunk.setVelocity(dunk.getVelocity().add(dir.multiply(1.0D)));
                    dunk.attackAnimationState.start(dunk.age);
                    biteDelay = 10;
                    lungeCooldown = 25;
                } else if (lungeCooldown > 0) {
                    lungeCooldown--;
                }
            }

            protected double getSquaredMaxAttackDistance(LivingEntity target) {
                double width = dunk.getWidth();
                return width * 2.0 * width * 2.0 + target.getWidth();
            }
        }

        // ------------------- Swim goal -------------------
        static class DunkleosteusSwimGoal extends Goal {
            private final DunkleosteusEntity dunk;
            private final double speed;
            private int cooldown;

            public DunkleosteusSwimGoal(DunkleosteusEntity dunk, double speed) {
                this.dunk = dunk;
                this.speed = speed;
            }

            @Override
            public boolean canStart() {
                return dunk.getTarget() == null || !dunk.getTarget().isAlive();
            }

            @Override
            public void tick() {
                if (cooldown > 0) {
                    cooldown--;
                    return;
                }

                double offsetX = (dunk.getRandom().nextDouble() - 0.5D) * 16.0D;
                double offsetY = (dunk.getRandom().nextDouble() - 0.5D) * 6.0D;
                double offsetZ = (dunk.getRandom().nextDouble() - 0.5D) * 16.0D;

                double targetX = dunk.getX() + offsetX;
                double targetY = dunk.getY() + offsetY;
                double targetZ = dunk.getZ() + offsetZ;

                if (dunk.getWorld().getFluidState(dunk.getBlockPos()).isIn(FluidTags.WATER)) {
                    dunk.getNavigation().startMovingTo(targetX, targetY, targetZ, speed);
                }

                cooldown = 40 + dunk.getRandom().nextInt(60);
            }
        }

        // ------------------- Boat attack -------------------
        static class AttackBoatGoal extends Goal {
            private final DunkleosteusEntity mob;
            private final double speed;

            public AttackBoatGoal(DunkleosteusEntity mob, double speed) {
                this.mob = mob;
                this.speed = speed;
            }

            @Override
            public boolean canStart() {
                return getClosestBoat(6.0) != null;
            }

            @Override
            public void tick() {
                BoatEntity boat = getClosestBoat(3.0);
                if (boat != null) {
                    mob.getNavigation().startMovingTo(boat.getX(), boat.getY(), boat.getZ(), speed);

                    if (mob.squaredDistanceTo(boat) < 4.0) {
                        boat.damage(mob.getDamageSources().mobAttack(mob), 20.0f); // big bite
                    }
                }
            }

            private BoatEntity getClosestBoat(double range) {
                return mob.getWorld()
                        .getOtherEntities(mob, mob.getBoundingBox().expand(range), e -> e instanceof BoatEntity)
                        .stream()
                        .map(e -> (BoatEntity) e)
                        .min((a, b) -> Double.compare(mob.squaredDistanceTo(a), mob.squaredDistanceTo(b)))
                        .orElse(null);
            }
        }
    }
}








