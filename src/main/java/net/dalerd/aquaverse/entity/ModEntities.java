package net.dalerd.aquaverse.entity;

import net.dalerd.aquaverse.AquaVerse;
import net.dalerd.aquaverse.entity.custom.DunkleosteusEntity;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnLocationTypes;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.util.Identifier;
import net.minecraft.world.Heightmap;

public class ModEntities {

    public static final EntityType<DunkleosteusEntity> DUNKLEOSTEUS =
            Registry.register(
                    Registries.ENTITY_TYPE,
                    Identifier.of(AquaVerse.MOD_ID, "dunkleosteus"),
                    EntityType.Builder.create(DunkleosteusEntity::new, SpawnGroup.WATER_CREATURE)
                            .dimensions(1.5f, 1.5f)
                            .build()
            );

    public static void registerModEntities() {
        AquaVerse.LOGGER.info("Registering Mod Entities for " + AquaVerse.MOD_ID);

        // ✅ Spawn restriction (ONLY ONCE)
        SpawnRestriction.register(
                DUNKLEOSTEUS,
                SpawnLocationTypes.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                WaterCreatureEntity::canSpawn
        );

        // ✅ Biome spawning
        BiomeModifications.addSpawn(
                BiomeSelectors.tag(BiomeTags.IS_DEEP_OCEAN),
                SpawnGroup.WATER_CREATURE,
                DUNKLEOSTEUS,
                5,
                1,
                1
        );
    }
}

