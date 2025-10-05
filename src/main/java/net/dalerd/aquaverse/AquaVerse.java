package net.dalerd.aquaverse;

import net.dalerd.aquaverse.block.ModBlocks;
import net.dalerd.aquaverse.entity.ModEntities;
import net.dalerd.aquaverse.entity.custom.DunkleosteusEntity;
import net.dalerd.aquaverse.item.custom.ModItems;
import net.dalerd.aquaverse.item.custom.ModItemGroups;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnLocationTypes;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.entity.SpawnRestriction;

import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.dalerd.aquaverse.block.custom.DunkleosteusSkullRenderer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AquaVerse implements ModInitializer {
	public static final String MOD_ID = "aquaverse";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// Register everything in proper order
		ModBlocks.registerModBlocks();
		ModItems.registerModItems();
		ModItemGroups.registerItemGroups();
		ModEntities.registerModEntities();

		// Register entity attributes
		FabricDefaultAttributeRegistry.register(
				ModEntities.DUNKLEOSTEUS,
				DunkleosteusEntity.createAttributes()
		);

		// Spawn restrictions
		SpawnRestriction.register(
				ModEntities.DUNKLEOSTEUS,
				SpawnLocationTypes.IN_WATER,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				WaterCreatureEntity::canSpawn
		);

		// Add spawn to biomes
		BiomeModifications.addSpawn(
				BiomeSelectors.includeByKey(
						BiomeKeys.DEEP_OCEAN,
						BiomeKeys.DEEP_COLD_OCEAN,
						BiomeKeys.DEEP_LUKEWARM_OCEAN,
						BiomeKeys.WARM_OCEAN,
						BiomeKeys.LUKEWARM_OCEAN
				),
				SpawnGroup.WATER_CREATURE,
				ModEntities.DUNKLEOSTEUS,
				5, 1, 1
		);

		// ✅ Register Dunkleosteus Skull BlockEntity Renderer (CLIENT-SIDE)
		BlockEntityRendererRegistry.register(ModBlocks.DUNKLEOSTEUS_SKULL_ENTITY, DunkleosteusSkullRenderer::new);

		LOGGER.info("AquaVerse initialized successfully with Dunkleosteus and Skull Block!");
	}
}




