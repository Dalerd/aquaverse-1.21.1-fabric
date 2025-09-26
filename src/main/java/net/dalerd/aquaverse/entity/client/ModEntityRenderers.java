package net.dalerd.aquaverse.entity.client;

import net.dalerd.aquaverse.entity.ModEntities;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EntityRendererFactory;

public class ModEntityRenderers {
    public static void registerEntityRenderers() {
        EntityRendererRegistry.register(ModEntities.DUNKLEOSTEUS, DunkleosteusRenderer::new);
    }
}
