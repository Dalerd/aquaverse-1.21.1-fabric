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

import java.util.Random;

@Environment(EnvType.CLIENT)
public class DunkleosteusSkullRenderer implements BlockEntityRenderer<DunkleosteusSkullBlockEntity> {

    private final Random random = new Random();

    public DunkleosteusSkullRenderer(BlockEntityRendererFactory.Context ctx) {}

    @Override
    public void render(DunkleosteusSkullBlockEntity entity, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light, int overlay) {

        if (entity.getWorld() != null) {
            BlockRenderManager brm = MinecraftClient.getInstance().getBlockRenderManager();
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getCutout());
            brm.renderBlock(entity.getCachedState(), entity.getPos(), entity.getWorld(), matrices, vertexConsumer, true, random);
        }
    }
}







