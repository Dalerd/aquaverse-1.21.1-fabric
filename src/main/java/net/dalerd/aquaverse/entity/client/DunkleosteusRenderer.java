package net.dalerd.aquaverse.entity.client;

import net.dalerd.aquaverse.entity.custom.DunkleosteusEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class DunkleosteusRenderer extends GeoEntityRenderer<DunkleosteusEntity> {

    public DunkleosteusRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new DunkleosteusModel());
        this.shadowRadius = 1.2f;
    }
}








