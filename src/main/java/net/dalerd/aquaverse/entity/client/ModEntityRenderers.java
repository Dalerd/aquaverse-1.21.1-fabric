package net.dalerd.aquaverse.entity.client;


import net.dalerd.aquaverse.entity.ModEntities;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;


public class ModEntityRenderers {


    public static void register() {
        EntityRendererRegistry.register(
                ModEntities.DUNKLEOSTEUS,
                DunkleosteusRenderer::new
        );
    }
}

