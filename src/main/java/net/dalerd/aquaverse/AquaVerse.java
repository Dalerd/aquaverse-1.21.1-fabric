package net.dalerd.aquaverse;

import net.dalerd.aquaverse.block.ModBlocks;
import net.dalerd.aquaverse.entity.ModEntities;
import net.dalerd.aquaverse.entity.custom.DunkleosteusEntity;
import net.dalerd.aquaverse.item.custom.ModItems;
import net.dalerd.aquaverse.item.custom.ModItemGroups;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.dalerd.aquaverse.block.custom.DunkleosteusSkullRenderer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AquaVerse implements ModInitializer {
	public static final String MOD_ID = "aquaverse";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		ModBlocks.registerModBlocks();
		ModItems.registerModItems();
		ModItemGroups.registerItemGroups();
		ModEntities.registerModEntities();

		// ✅ Entity attributes (this is correct here)
		FabricDefaultAttributeRegistry.register(
				ModEntities.DUNKLEOSTEUS,
				DunkleosteusEntity.createAttributes()
		);

		// ⚠️ NOTE:
		// BlockEntityRendererRegistry should be CLIENT ONLY.
		// This should eventually move to a ClientModInitializer.
		BlockEntityRendererRegistry.register(
				ModBlocks.DUNKLEOSTEUS_SKULL_ENTITY,
				DunkleosteusSkullRenderer::new
		);

		LOGGER.info("AquaVerse initialized successfully!");
	}
}




