package net.dalerd.aquaverse.entity.custom;

import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class DunkleosteusEntity extends WaterCreatureEntity {
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public DunkleosteusEntity(EntityType<? extends WaterCreatureEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        // Swimming
        this.goalSelector.add(0, new SwimGoal(this));

        // Melee attack
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.3D, true));

        // Target all living
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, LivingEntity.class, true));

        // Wander & look
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(3, new LookAroundGoal(this));
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 4.0F));
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 40)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 80)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.9)
                .add(EntityAttributes.GENERIC_WATER_MOVEMENT_EFFICIENCY, 0.6)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 0.5)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 30F)
                .add(EntityAttributes.GENERIC_SAFE_FALL_DISTANCE, 3.0F)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 0.7);
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 40;
            this.idleAnimationState.start(this.age);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getWorld().isClient()) {
            this.setupAnimationStates();
        }
    }

    @Override
    public void baseTick() {
        super.baseTick();

        // Suffocation if out of water
        if (!this.isTouchingWater() && this.isAlive()) {
            int air = this.getAir() - 1;
            this.setAir(air);

            if (air <= -20) {
                this.setAir(0);
                this.damage(this.getDamageSources().drown(), 2.0F);
            }
        } else {
            this.setAir(this.getMaxAir());
        }
    }

    // ---------------------------
    // Attack Animation Trigger
    // ---------------------------
    @Override
    public boolean tryAttack(net.minecraft.entity.Entity target) {
        boolean success = super.tryAttack(target);
        if (success) {
            this.handSwingTicks = 10; // keeps isAttacking() true for ~10 ticks
        }
        return success;
    }

    @Override
    public boolean isAttacking() {
        return this.handSwingTicks > 0;
    }
}
