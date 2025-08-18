package net.dalerd.aquaverse.entity.client;

import net.dalerd.aquaverse.entity.custom.DunkleosteusEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class DunkleosteusModel<T extends DunkleosteusEntity> extends SinglePartEntityModel<T> {
    private final ModelPart dunkleosteus;
    private final ModelPart head;

    // ✅ Use Identifier.of instead of "new Identifier"
    public static final EntityModelLayer LAYER_LOCATION =
            new EntityModelLayer(Identifier.of("aquaverse", "dunkleosteus"), "main");

    public DunkleosteusModel(ModelPart root) {
        // ✅ Fix: Blockbench export adds a "root" container
        ModelPart rootPart = root.getChild("root");
        this.dunkleosteus = rootPart.getChild("dunkleosteus");
        this.head = this.dunkleosteus.getChild("head");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData root = modelData.getRoot();

        // ✅ Wrap in "root" to match Blockbench export
        ModelPartData rootPart = root.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData dunkleosteus = rootPart.addChild("dunkleosteus",
                ModelPartBuilder.create().uv(0, 0)
                        .cuboid(-4.0F, -4.0F, -8.0F, 8.0F, 8.0F, 16.0F),
                ModelTransform.pivot(0.0F, -4.0F, 0.0F));

        dunkleosteus.addChild("head",
                ModelPartBuilder.create().uv(0, 24)
                        .cuboid(-3.0F, -3.0F, -6.0F, 6.0F, 6.0F, 6.0F),
                ModelTransform.pivot(0.0F, 0.0F, -8.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        // TODO: add your animations here
        this.head.yaw = netHeadYaw * ((float)Math.PI / 180F);
        this.head.pitch = headPitch * ((float)Math.PI / 180F);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay,
                       int color) {
        dunkleosteus.render(matrices, vertices, light, overlay, color);
    }

    @Override
    public ModelPart getPart() {
        return dunkleosteus;
    }
}
