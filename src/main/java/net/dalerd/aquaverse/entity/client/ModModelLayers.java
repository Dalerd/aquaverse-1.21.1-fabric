package net.dalerd.aquaverse.entity.client;

import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.dalerd.aquaverse.AquaVerse;

public class ModModelLayers {

    // ✅ The model layer key must match between Renderer + Model registration
    public static final EntityModelLayer DUNKLEOSTEUS =
            new EntityModelLayer(
                    Identifier.of(AquaVerse.MOD_ID, "dunkleosteus"), // <modid>:dunkleosteus
                    "main" // always "main" unless you add extra parts
            );
}



