package net.dalerd.aquaverse.block.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;

@Environment(EnvType.CLIENT)
public class DunkleosteusSkullRenderer implements BlockEntityRenderer<DunkleosteusSkullBlockEntity> {

    public DunkleosteusSkullRenderer(BlockEntityRendererFactory.Context ctx) {}

    @Override
    public void render(DunkleosteusSkullBlockEntity entity, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light, int overlay) {

        if (entity == null || entity.getWorld() == null) return;

        var world = entity.getWorld();
        var state = entity.getCachedState();
        var pos = entity.getPos();

        BlockRenderManager brm = MinecraftClient.getInstance().getBlockRenderManager();
        RenderLayer layer = RenderLayer.getCutout();
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(layer);

        // Correct render method signature for 1.21.x
        brm.renderBlock(state, pos, world, matrices, vertexConsumer, true, world.getRandom());
    }
}








