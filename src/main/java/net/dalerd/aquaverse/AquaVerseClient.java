package net.dalerd.aquaverse;

import net.dalerd.aquaverse.block.ModBlocks;
import net.dalerd.aquaverse.block.custom.DunkleosteusSkullRenderer;
import net.dalerd.aquaverse.entity.client.ModEntityRenderers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import net.dalerd.aquaverse.entity.client.ModEntityRenderers;

public class AquaVerseClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        // ✅ Register GeckoLib entity renderers
        ModEntityRenderers.register();

        // ✅ Register skull block renderer (client-only)
        BlockEntityRendererRegistry.register(
                ModBlocks.DUNKLEOSTEUS_SKULL_ENTITY,
                DunkleosteusSkullRenderer::new
        );

        // ✅ Render layer for skull transparency
        BlockRenderLayerMap.INSTANCE.putBlock(
                ModBlocks.DUNKLEOSTEUS_SKULL,
                RenderLayer.getCutout()
        );

        AquaVerse.LOGGER.info("AquaVerse client initialized — GeckoLib renderer active!");
    }
}


