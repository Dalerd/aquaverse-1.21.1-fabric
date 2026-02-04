package net.dalerd.aquaverse.item.custom;

import net.dalerd.aquaverse.AquaVerse;
import net.dalerd.aquaverse.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroups;

public class ModItemGroups {

    public static void registerItemGroups() {
        AquaVerse.LOGGER.info("Registering Item Groups for " + AquaVerse.MOD_ID);

        // 🐟 Add Dunkleosteus Spawn Egg to Spawn Eggs tab
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(entries -> {
            entries.add(ModItems.DUNKLEOSTEUS_SPAWN_EGG);
        });

        // 🪨 Add Dunkleosteus Skull block to the Natural Blocks tab
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> {
            entries.add(ModBlocks.DUNKLEOSTEUS_SKULL);
        });
    }
}

