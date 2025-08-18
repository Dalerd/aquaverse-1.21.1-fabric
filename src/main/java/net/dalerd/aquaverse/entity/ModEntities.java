package net.dalerd.aquaverse.entity;

import net.dalerd.aquaverse.AquaVerse;
import net.dalerd.aquaverse.entity.custom.DunkleosteusEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {

    public static final EntityType<DunkleosteusEntity> DUNKLEOSTEUS = Registry.register(Registries.ENTITY_TYPE, Identifier.of(AquaVerse.MOD_ID, "dunkleosteus"),
            EntityType.Builder.create(DunkleosteusEntity::new, SpawnGroup.WATER_CREATURE)
                    .dimensions(1.5f, 1.5f).build());

    public static void registerModEntities() {
        AquaVerse.LOGGER.info("Registering Mod Entities for " + AquaVerse.MOD_ID);
    }
}
