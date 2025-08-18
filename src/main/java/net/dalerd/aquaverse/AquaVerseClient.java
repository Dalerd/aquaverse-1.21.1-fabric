package net.dalerd.aquaverse;

import net.dalerd.aquaverse.entity.ModEntities;
import net.dalerd.aquaverse.entity.client.DunkleosteusModel;
import net.dalerd.aquaverse.entity.client.DunkleosteusRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class AquaVerseClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        EntityModelLayerRegistry.registerModelLayer(
                DunkleosteusModel.LAYER_LOCATION,
                DunkleosteusModel::getTexturedModelData
        );

        EntityRendererRegistry.register(ModEntities.DUNKLEOSTEUS, DunkleosteusRenderer::new);
    }
}
