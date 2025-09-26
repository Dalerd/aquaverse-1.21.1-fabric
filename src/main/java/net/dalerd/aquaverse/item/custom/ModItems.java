package net.dalerd.aquaverse.item.custom;

import net.dalerd.aquaverse.AquaVerse;
import net.dalerd.aquaverse.entity.ModEntities;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item DUNKLEOSTEUS_SPAWN_EGG = registerItem("dunkleosteus_spawn_egg",
            new SpawnEggItem(ModEntities.DUNKLEOSTEUS, 0x2F2F2F, 0xC2B280, new Item.Settings()));


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(AquaVerse.MOD_ID, name), item);
    }

    public static void registerModItems() {
        AquaVerse.LOGGER.info("Registering Mod Items for " + AquaVerse.MOD_ID);
    }
}
