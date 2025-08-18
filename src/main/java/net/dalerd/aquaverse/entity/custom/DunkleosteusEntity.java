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
    public AnimationState attackAnimationState;
    public AnimationState waterIdleAnimationState;
    private int idleAnimationTimeout = 0;

    public DunkleosteusEntity(EntityType<? extends WaterCreatureEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        // Swimming behavior
        this.goalSelector.add(0, new SwimGoal(this));

        // Melee attack
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.3D, true));

        // Attack everything that lives (players + mobs + its own kind)
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, LivingEntity.class, true));

        // Wander & idle behavior
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(3, new LookAroundGoal(this));
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 4.0F));
    }

    public static DefaultAttributeContainer.Builder createAttributes(){
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

        // Suffocation logic when out of water
        if (!this.isTouchingWater() && this.isAlive()) {
            int air = this.getAir() - 1;
            this.setAir(air);

            if (air <= -20) { // once air runs out, damage every tick
                this.setAir(0);
                this.damage(this.getDamageSources().drown(), 2.0F);
            }
        } else {
            // reset air when in water
            this.setAir(this.getMaxAir());
        }
    }
}

