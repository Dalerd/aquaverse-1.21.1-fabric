package net.dalerd.aquaverse.entity.client;

import net.dalerd.aquaverse.entity.custom.DunkleosteusEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class DunkleosteusModel<T extends DunkleosteusEntity> extends SinglePartEntityModel<T> {
    private final ModelPart root;

    public static final EntityModelLayer LAYER_LOCATION =
            new EntityModelLayer(Identifier.of("aquaverse", "dunkleosteus"), "main");

    public DunkleosteusModel(ModelPart root) {
        this.root = root;
    }


    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData dunkleosteus = root.addChild("dunkleosteus", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData torso = dunkleosteus.addChild("torso",
                ModelPartBuilder.create().uv(0, 0).cuboid(-10.0F, -20.0F, 1.0F, 20.0F, 22.0F, 28.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

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
    public void setAngles(T entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        // --- Water vs ground animations ---
        if (entity.isTouchingWater()) {
            if (limbSwingAmount > 0.1F) {
                this.animateMovement(DunkleosteusAnimations.DUNK_WATER_SWIM,
                        limbSwing, limbSwingAmount, 2.0f, 2.5f);
            } else {
                this.animate(DunkleosteusAnimations.DUNK_WATER_IDLE,
                        ageInTicks, 1.0f);
            }
        } else {
            if (limbSwingAmount > 0.1F) {
                this.animateMovement(DunkleosteusAnimations.DUNK_GROUND_WALK,
                        limbSwing, limbSwingAmount, 1.0f, 1.0f);
            } else {
                this.animate(DunkleosteusAnimations.DUNK_GROUND_IDLE,
                        ageInTicks, 1.0f);
            }
        }

        // --- Attack animation ---
        if (entity.isAttacking()) {
            this.animate(DunkleosteusAnimations.DUNK_ATTACK, ageInTicks, 1.0f);
        }
    }

    private void animate(Animation dunkWaterIdle, float ageInTicks, float v) {
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices,
                       int light, int overlay, int color) {
        root.render(matrices, vertices, light, overlay, color);
    }

    @Override
    public ModelPart getPart() {
        return root;
    }
}

