package net.dalerd.aquaverse;

import net.dalerd.aquaverse.block.ModBlocks;
import net.dalerd.aquaverse.block.custom.DunkleosteusSkullRenderer;
import net.dalerd.aquaverse.entity.ModEntities;
import net.dalerd.aquaverse.entity.client.DunkleosteusModel;
import net.dalerd.aquaverse.entity.client.DunkleosteusRenderer;
import net.dalerd.aquaverse.entity.client.ModModelLayers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;

public class AquaVerseClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // ✅ Register the model layer for the Dunkleosteus
        EntityModelLayerRegistry.registerModelLayer(
                ModModelLayers.DUNKLEOSTEUS,
                DunkleosteusModel::getTexturedModelData
        );

        // ✅ Register the renderer for the Dunkleosteus
        EntityRendererRegistry.register(
                ModEntities.DUNKLEOSTEUS,
                DunkleosteusRenderer::new
        );
        // ✅ Register the custom renderer for the Dunkleosteus Skull
        BlockEntityRendererRegistry.register(ModBlocks.DUNKLEOSTEUS_SKULL_ENTITY, DunkleosteusSkullRenderer::new);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DUNKLEOSTEUS_SKULL, RenderLayer.getCutout());


        AquaVerse.LOGGER.info("AquaVerse client initialized — custom renderers loaded!");
    }
}


