package net.dalerd.aquaverse.entity.client;

import net.dalerd.aquaverse.AquaVerse;
import net.dalerd.aquaverse.entity.custom.DunkleosteusEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class DunkleosteusModel extends GeoModel<DunkleosteusEntity> {

    @Override
    public Identifier getModelResource(DunkleosteusEntity animatable) {
        return Identifier.of(AquaVerse.MOD_ID, "geo/dunkleosteus.geo.json");
    }

    @Override
    public Identifier getTextureResource(DunkleosteusEntity animatable) {
        return Identifier.of(AquaVerse.MOD_ID, "textures/entity/dunkleosteus/dunkleosteus.png");
    }

    @Override
    public Identifier getAnimationResource(DunkleosteusEntity animatable) {
        return Identifier.of(AquaVerse.MOD_ID, "animations/dunkleosteus.animation.json");
    }
}





