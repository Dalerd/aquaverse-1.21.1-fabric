package net.dalerd.aquaverse.entity.client;

import net.dalerd.aquaverse.entity.custom.DunkleosteusEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class DunkleosteusModel extends SinglePartEntityModel<DunkleosteusEntity> {
    private final ModelPart root;

    public DunkleosteusModel(ModelPart root) {
        this.root = root.getChild("root");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        // ✅ Shift root pivot upward so Dunkleosteus sits correctly in hitbox
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(),
                ModelTransform.pivot(0.0F, 24.0F, 0.0F)); // was 24.0F

        ModelPartData dunkleosteus = root.addChild("dunkleosteus",
                ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        // === Torso ===
        ModelPartData torso = dunkleosteus.addChild("torso",
                ModelPartBuilder.create().uv(0, 0).cuboid(-10.0F, -20.0F, 1.0F, 20.0F, 22.0F, 28.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        // === Tail ===
        ModelPartData tail = torso.addChild("tail",
                ModelPartBuilder.create().uv(0, 50).cuboid(-7.0F, -9.0F, -5.0F, 14.0F, 16.0F, 21.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, -7.0F, 29.0F));

        ModelPartData tail_1 = tail.addChild("tail_1",
                ModelPartBuilder.create().uv(96, 19).cuboid(-4.0F, -5.0F, -2.0F, 8.0F, 9.0F, 19.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, 0.0F, 15.0F));

        ModelPartData tail_fin = tail_1.addChild("tail_fin",
                ModelPartBuilder.create().uv(56, 138).cuboid(-0.5F, -7.0F, -2.0F, 1.0F, 13.0F, 5.0F, new Dilation(0.0F))
                        .uv(0, 106).cuboid(0.0F, -13.0F, -1.0F, 0.0F, 25.0F, 17.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, 0.0F, 16.0F));

        torso.addChild("right_fin_back",
                ModelPartBuilder.create().uv(128, 130).cuboid(-8.0F, -1.0F, -1.0F, 8.0F, 1.0F, 7.0F, new Dilation(0.0F))
                        .uv(88, 117).cuboid(-14.0F, -0.5F, -1.0F, 14.0F, 0.0F, 13.0F, new Dilation(0.0F)),
                ModelTransform.pivot(-10.0F, 0.0F, 23.0F));

        ModelPartData fin_top = torso.addChild("fin_top", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -20.0F, 19.0F));
        fin_top.addChild("bb_main_r1",
                ModelPartBuilder.create().uv(34, 130).cuboid(-6.0F, -8.0F, 3.0F, 1.0F, 15.0F, 10.0F, new Dilation(0.0F)),
                ModelTransform.of(5.5F, -5.0F, -1.0F, -0.5236F, 0.0F, 0.0F));

        ModelPartData left_fin_front = torso.addChild("left_fin_front",
                ModelPartBuilder.create().uv(56, 130).cuboid(0.0F, -1.0F, -3.0F, 11.0F, 1.0F, 7.0F, new Dilation(0.0F)),
                ModelTransform.pivot(10.0F, 2.0F, 4.0F));
        left_fin_front.addChild("bb_main_r2",
                ModelPartBuilder.create().uv(68, 104).cuboid(-29.0F, 1.5F, 1.0F, 19.0F, 0.0F, 13.0F, new Dilation(0.0F)),
                ModelTransform.of(-10.0F, 1.0F, -4.0F, 0.0F, 0.0F, -3.1416F));

        torso.addChild("right_fin_front",
                ModelPartBuilder.create().uv(92, 130).cuboid(-11.0F, -1.0F, -3.0F, 11.0F, 1.0F, 7.0F, new Dilation(0.0F))
                        .uv(68, 104).cuboid(-19.0F, -0.5F, -3.0F, 19.0F, 0.0F, 13.0F, new Dilation(0.0F)),
                ModelTransform.pivot(-10.0F, 2.0F, 4.0F));

        torso.addChild("left_fin_back",
                ModelPartBuilder.create().uv(34, 106).cuboid(0.0F, -1.0F, -1.0F, 8.0F, 1.0F, 7.0F, new Dilation(0.0F))
                        .uv(34, 117).cuboid(0.0F, -0.5F, -1.0F, 14.0F, 0.0F, 13.0F, new Dilation(0.0F)),
                ModelTransform.pivot(10.0F, 0.0F, 23.0F));

        // === Head ===
        ModelPartData head = dunkleosteus.addChild("head", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -8.0F, 0.0F));
        head.addChild("upper_jaw",
                ModelPartBuilder.create().uv(70, 50).cuboid(-9.0F, -11.0F, -15.0F, 18.0F, 11.0F, 19.0F, new Dilation(0.01F))
                        .uv(96, 0).cuboid(-9.0F, -1.0F, -15.0F, 18.0F, 3.0F, 16.0F, new Dilation(-0.01F)),
                ModelTransform.pivot(0.0F, 3.0F, 0.0F));

        head.addChild("lower_jaw",
                ModelPartBuilder.create().uv(70, 80).cuboid(-9.0F, 0.0F, -15.0F, 18.0F, 5.0F, 19.0F, new Dilation(0.0F))
                        .uv(0, 87).cuboid(-9.0F, -2.0F, -15.0F, 18.0F, 3.0F, 16.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, 3.0F, 0.0F));

        return TexturedModelData.of(modelData, 256, 256);
    }

    @Override
    public void setAngles(DunkleosteusEntity entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {

        this.getPart().traverse().forEach(ModelPart::resetTransform);

        if (entity.isTouchingWater()) {
            this.animateMovement(DunkleosteusAnimations.DUNK_WATER_SWIM, limbSwing, limbSwingAmount, 2.0f, 2.5f);
            this.updateAnimation(entity.waterIdleAnimationState, DunkleosteusAnimations.DUNK_WATER_IDLE, ageInTicks, 1.0f);
        } else {
            this.animateMovement(DunkleosteusAnimations.DUNK_GROUND_WALK, limbSwing, limbSwingAmount, 2.0f, 2.5f);
            this.updateAnimation(entity.idleAnimationState, DunkleosteusAnimations.DUNK_GROUND_IDLE, ageInTicks, 1.0f);
        }

        this.updateAnimation(entity.attackAnimationState, DunkleosteusAnimations.DUNK_ATTACK, ageInTicks, 1.0f);
    }

    @Override
    public ModelPart getPart() {
        return root;
    }
}




