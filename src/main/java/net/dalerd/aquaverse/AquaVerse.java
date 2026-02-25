package net.dalerd.aquaverse;


import net.dalerd.aquaverse.block.ModBlocks;
import net.dalerd.aquaverse.entity.ModEntities;
import net.dalerd.aquaverse.entity.custom.DunkleosteusEntity;
import net.dalerd.aquaverse.item.custom.ModItems;
import net.dalerd.aquaverse.item.custom.ModItemGroups;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib.GeckoLib;


public class AquaVerse implements ModInitializer {


	public static final String MOD_ID = "aquaverse";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


	@Override
	public void onInitialize() {

		ModBlocks.registerModBlocks();
		ModItems.registerModItems();
		ModItemGroups.registerItemGroups();
		ModEntities.registerModEntities();


		FabricDefaultAttributeRegistry.register(
				ModEntities.DUNKLEOSTEUS,
				DunkleosteusEntity.createAttributes()
		);


		LOGGER.info("AquaVerse initialized successfully!");
	}
}




