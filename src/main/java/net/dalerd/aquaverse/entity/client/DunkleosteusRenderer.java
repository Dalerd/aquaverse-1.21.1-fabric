package net.dalerd.aquaverse.entity.client;

import net.dalerd.aquaverse.AquaVerse;
import net.dalerd.aquaverse.entity.custom.DunkleosteusEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class DunkleosteusRenderer extends MobEntityRenderer<DunkleosteusEntity, DunkleosteusModel<DunkleosteusEntity>> {

    public DunkleosteusRenderer(EntityRendererFactory.Context context) {

        super(context, new DunkleosteusModel<>(context.getPart(DunkleosteusModel.LAYER_LOCATION)), 0.8f);
    }

    @Override
    public Identifier getTexture(DunkleosteusEntity entity) {

        return Identifier.of(AquaVerse.MOD_ID, "textures/entity/dunkleosteus/dunkleosteus.png");
    }
}
