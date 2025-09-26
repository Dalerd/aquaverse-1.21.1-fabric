package net.dalerd.aquaverse;

import net.dalerd.aquaverse.entity.ModEntities;
import net.dalerd.aquaverse.entity.client.DunkleosteusModel;
import net.dalerd.aquaverse.entity.client.DunkleosteusRenderer;
import net.dalerd.aquaverse.entity.client.ModModelLayers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

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
    }
}


