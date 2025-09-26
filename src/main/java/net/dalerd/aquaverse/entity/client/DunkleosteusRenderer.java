package net.dalerd.aquaverse.entity.client;

import net.dalerd.aquaverse.AquaVerse;
import net.dalerd.aquaverse.entity.custom.DunkleosteusEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class DunkleosteusRenderer extends MobEntityRenderer<DunkleosteusEntity, DunkleosteusModel> {

    // Use Identifier.of() in 1.21+
    private static final Identifier TEXTURE = Identifier.of(AquaVerse.MOD_ID, "textures/entity/dunkleosteus/dunkleosteus.png");

    public DunkleosteusRenderer(EntityRendererFactory.Context context) {
        super(context, new DunkleosteusModel(context.getPart(ModModelLayers.DUNKLEOSTEUS)), 2.2f);
    }

    @Override
    public Identifier getTexture(DunkleosteusEntity entity) {
        return TEXTURE;
    }

    @Override
    protected void scale(DunkleosteusEntity entity, MatrixStack matrices, float amount) {
        // Keep model scaling, but remove extra translation that caused hovering
        float scale = 1.0F;
        matrices.scale(scale, scale, scale);

        super.scale(entity, matrices, amount);
    }

    @Override
    public void render(DunkleosteusEntity mobEntity, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}







