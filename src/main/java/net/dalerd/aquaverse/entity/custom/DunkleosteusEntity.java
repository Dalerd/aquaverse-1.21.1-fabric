package net.dalerd.aquaverse.entity.custom;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.*;
import net.minecraft.entity.attribute.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.*;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.animation.AnimationState;

import java.util.EnumSet;

public class DunkleosteusEntity extends WaterCreatureEntity implements GeoEntity {

    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    private boolean crawlMode = false;

    private int biteCooldown = 0;

    private boolean biteTriggered = false;
    private int biteTick = 0;

    public DunkleosteusEntity(EntityType<? extends WaterCreatureEntity> type, World world) {
        super(type, world);
        this.moveControl = new MoveControl(this);
    }

    // ================= ATTRIBUTES =================

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 100)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 12)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.07)
                .add(EntityAttributes.GENERIC_ARMOR, 20)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 40);
    }

    // ================= NAVIGATION =================

    @Override
    protected EntityNavigation createNavigation(World world) {
        return new SwimNavigation(this, world);
    }

    private boolean touchingGround() {
        return this.isOnGround() && !this.isTouchingWater();
    }

    private void updateMovementMode() {
        boolean newMode = touchingGround();

        if (newMode != crawlMode) {
            crawlMode = newMode;

            if (crawlMode)
                this.navigation = new MobNavigation(this, this.getWorld());
            else
                this.navigation = new SwimNavigation(this, this.getWorld());
        }
    }

    // ================= GOALS =================

    @Override
    protected void initGoals() {

        this.goalSelector.add(0, new ReturnToWaterGoal(this, 1.1));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.4, false));
        this.goalSelector.add(2, new WanderAroundGoal(this, 0.7));
        this.goalSelector.add(3, new LookAroundGoal(this));

        // target players
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));

        // attack anything that hurts it
        this.targetSelector.add(2, new RevengeGoal(this).setGroupRevenge());
    }

    // ================= MOVEMENT =================

    @Override
    public void tickMovement() {
        super.tickMovement();
        updateMovementMode();

        if (this.isTouchingWater() && !crawlMode) {

            LivingEntity target = this.getTarget();
            Vec3d velocity = this.getVelocity();

            if (target != null) {
                double dy = target.getY() - this.getY();
                double verticalForce = MathHelper.clamp(dy * 0.015, -0.08, 0.08);
                velocity = velocity.add(0, verticalForce, 0);
            }

            BlockPos above = this.getBlockPos().up();
            if (!this.getWorld().getFluidState(above).isIn(FluidTags.WATER)) {
                velocity = velocity.add(0, -0.05, 0);
            }

            velocity = new Vec3d(velocity.x, velocity.y * 0.9, velocity.z);
            this.setVelocity(velocity);
        }

        // ===== BITE TIMING =====
        if (biteTriggered) {
            biteTick++;

            if (biteTick == 6) {
                LivingEntity target = this.getTarget();

                if (target != null && this.squaredDistanceTo(target) < 6) {
                    damageTarget(target);
                }
            }

            if (biteTick > 12) {
                biteTriggered = false;
            }
        }

        if (biteCooldown > 0) biteCooldown--;
    }

    @Override
    public void travel(Vec3d input) {

        if (this.isTouchingWater() && !crawlMode) {

            float swimSpeed = 0.15F;

            this.updateVelocity(swimSpeed, input);
            this.move(MovementType.SELF, this.getVelocity());

            this.setVelocity(this.getVelocity().multiply(0.9));

        } else {
            super.travel(input);
        }
    }

    // ================= BOAT DESTRUCTION =================

    private void breakBoat(BoatEntity boat) {

        if (!this.getWorld().isClient) {

            boat.removeAllPassengers();

            ItemScatterer.spawn(
                    this.getWorld(),
                    boat.getX(), boat.getY(), boat.getZ(),
                    boat.asItem().getDefaultStack()
            );

            boat.discard();
        }
    }

    // ================= DAMAGE HELPERS =================

    private void damageTarget(LivingEntity target) {

        target.damage(
                this.getDamageSources().mobAttack(this),
                (float)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE)
        );

        target.takeKnockback(1.4F,
                this.getX() - target.getX(),
                this.getZ() - target.getZ());
    }

    // ================= ATTACK =================

    @Override
    public boolean tryAttack(Entity target) {

        if (biteCooldown > 0) return false;

        biteCooldown = 20;

        // break boats instead of pushing
        if (target instanceof BoatEntity boat) {
            breakBoat(boat);
            return true;
        }

        if (target instanceof LivingEntity) {
            biteTriggered = true;
            biteTick = 0;
            return true;
        }

        return false;
    }

    // ================= RETALIATE WHEN HIT =================

    @Override
    public boolean damage(DamageSource source, float amount) {
        boolean result = super.damage(source, amount);

        Entity attacker = source.getAttacker();

        if (attacker instanceof LivingEntity living && living != this) {
            this.setTarget(living);
        }

        return result;
    }

    // ================= RETURN TO WATER =================

    static class ReturnToWaterGoal extends Goal {
        private final DunkleosteusEntity mob;
        private final double speed;

        ReturnToWaterGoal(DunkleosteusEntity mob, double speed) {
            this.mob = mob;
            this.speed = speed;
            this.setControls(EnumSet.of(Control.MOVE));
        }

        @Override
        public boolean canStart() {
            return mob.crawlMode;
        }

        @Override
        public void tick() {
            BlockPos water = findWater();
            if (water != null) {
                mob.getNavigation().startMovingTo(
                        water.getX() + 0.5,
                        water.getY(),
                        water.getZ() + 0.5,
                        speed
                );
            }
        }

        private BlockPos findWater() {
            BlockPos origin = mob.getBlockPos();

            for (BlockPos pos : BlockPos.iterateOutwards(origin, 10, 4, 10)) {
                if (mob.getWorld().getFluidState(pos).isIn(FluidTags.WATER)
                        && mob.getWorld().getFluidState(pos.down()).isIn(FluidTags.WATER)) {
                    return pos;
                }
            }
            return null;
        }
    }

    // ================= ANIMATIONS =================

    private PlayState movement(AnimationState<DunkleosteusEntity> state) {

        if (crawlMode) {
            state.getController().setAnimation(
                    RawAnimation.begin().thenLoop(
                            state.isMoving()
                                    ? "animation.dunkleosteus.ground_walk"
                                    : "animation.dunkleosteus.ground_idle"));
            return PlayState.CONTINUE;
        }

        state.getController().setAnimation(
                RawAnimation.begin().thenLoop(
                        state.isMoving()
                                ? "animation.dunkleosteus.water_swim"
                                : "animation.dunkleosteus.water_idle"));
        return PlayState.CONTINUE;
    }

    private PlayState bite(AnimationState<DunkleosteusEntity> state) {

        if (biteTriggered) {
            state.getController().forceAnimationReset();
            state.getController().setAnimation(
                    RawAnimation.begin().thenPlay("animation.dunkleosteus.bite_attack"));
            return PlayState.CONTINUE;
        }

        return PlayState.STOP;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "move", 5, this::movement));
        controllers.add(new AnimationController<>(this, "bite", 0, this::bite));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}








