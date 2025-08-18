package net.dalerd.aquaverse.item.custom;

import net.dalerd.aquaverse.AquaVerse;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroups;

public class ModItemGroups {

    public static void registerItemGroups() {
        AquaVerse.LOGGER.info("Registering Item Groups for " + AquaVerse.MOD_ID);

        // Add Dunkleosteus spawn egg to the SPAWN_EGGS tab
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(entries -> {
            entries.add(ModItems.DUNKLEOSTEUS_SPAWN_EGG);
        });
    }
}
