package net.dalerd.aquaverse.entity.custom;

import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.util.math.MathHelper;

public class DunkleosteusMoveControl extends MoveControl {
    private final DunkleosteusEntity dunk;

    public DunkleosteusMoveControl(DunkleosteusEntity dunkleosteusEntity) {
        super(dunkleosteusEntity); // ✅ pass entity to MoveControl
        this.dunk = dunkleosteusEntity;
    }

    @Override
    public void tick() {
        if (this.state == State.MOVE_TO && !this.dunk.getNavigation().isIdle()) {
            double dx = this.targetX - this.dunk.getX();
            double dy = this.targetY - this.dunk.getY();
            double dz = this.targetZ - this.dunk.getZ();
            double dist = Math.sqrt(dx * dx + dy * dy + dz * dz);

            if (dist < this.dunk.getBoundingBox().getAverageSideLength()) {
                this.state = State.WAIT;
                this.dunk.setVelocity(this.dunk.getVelocity().multiply(0.5D));
            } else {
                // 🐟 Slightly faster swimming
                float speed = (float) (this.speed *
                        this.dunk.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED) *
                        1.3F);

                // 🐟 Apply full 3D steering (up/down included)
                this.dunk.setVelocity(this.dunk.getVelocity().add(
                        dx / dist * 0.08D * speed,
                        dy / dist * 0.08D * speed,
                        dz / dist * 0.08D * speed
                ));

                // 🐟 Rotate towards target or movement direction
                if (this.dunk.getTarget() == null) {
                    this.dunk.setYaw(-((float) MathHelper.atan2(
                            this.dunk.getVelocity().x,
                            this.dunk.getVelocity().z)) * (180F / (float) Math.PI));
                } else {
                    double tx = this.dunk.getTarget().getX() - this.dunk.getX();
                    double tz = this.dunk.getTarget().getZ() - this.dunk.getZ();
                    this.dunk.setYaw(-((float) MathHelper.atan2(tx, tz)) * (180F / (float) Math.PI));
                }
                this.dunk.bodyYaw = this.dunk.getYaw();
            }
        } else {
            // 🐟 Gentle neutral buoyancy when idle
            if (this.dunk.isSubmergedInWater()) {
                this.dunk.setVelocity(this.dunk.getVelocity().add(0.0D, -0.0025D, 0.0D));
            }
        }
    }
}


