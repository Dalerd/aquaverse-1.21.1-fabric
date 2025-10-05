package net.dalerd.aquaverse.block;

import net.dalerd.aquaverse.AquaVerse;
import net.dalerd.aquaverse.block.custom.DunkleosteusSkullBlock;
import net.dalerd.aquaverse.block.custom.DunkleosteusSkullBlockEntity;
import net.dalerd.aquaverse.item.custom.ModItemGroups;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final DunkleosteusSkullBlock DUNKLEOSTEUS_SKULL = new DunkleosteusSkullBlock(Block.Settings.copy(Blocks.STONE));
    public static BlockEntityType<DunkleosteusSkullBlockEntity> DUNKLEOSTEUS_SKULL_ENTITY;

    public static void registerModBlocks() {
        Registry.register(Registries.BLOCK, new Identifier(AquaVerse.MOD_ID, "dunkleosteus_skull"), DUNKLEOSTEUS_SKULL);
        Registry.register(Registries.ITEM, new Identifier(AquaVerse.MOD_ID, "dunkleosteus_skull"),
                new BlockItem(DUNKLEOSTEUS_SKULL, new Item.Settings().group(ModItemGroups.AQUAVERSE_GROUP))
        );

        DUNKLEOSTEUS_SKULL_ENTITY = Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                new Identifier(AquaVerse.MOD_ID, "dunkleosteus_skull"),
                BlockEntityType.Builder.create(DunkleosteusSkullBlockEntity::new, DUNKLEOSTEUS_SKULL).build(null)
        );
    }
}











