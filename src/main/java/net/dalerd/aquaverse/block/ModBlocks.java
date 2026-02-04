package net.dalerd.aquaverse.block;

import net.dalerd.aquaverse.AquaVerse;
import net.dalerd.aquaverse.block.custom.DunkleosteusSkullBlock;
import net.dalerd.aquaverse.block.custom.DunkleosteusSkullBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final DunkleosteusSkullBlock DUNKLEOSTEUS_SKULL =
            new DunkleosteusSkullBlock(
                    Block.Settings.create()
                            .mapColor(MapColor.OFF_WHITE)
                            .sounds(BlockSoundGroup.BONE)
                            .strength(1.5f, 3.0f)
                            .nonOpaque()
                            .requiresTool()
            );


    public static BlockEntityType<DunkleosteusSkullBlockEntity> DUNKLEOSTEUS_SKULL_ENTITY;

    public static void registerModBlocks() {
        Identifier id = Identifier.of(AquaVerse.MOD_ID, "dunkleosteus_skull");

        // Register block
        Registry.register(Registries.BLOCK, id, DUNKLEOSTEUS_SKULL);

        // Register block item
        Registry.register(Registries.ITEM, id, new BlockItem(DUNKLEOSTEUS_SKULL, new Item.Settings()));

        // Register block entity
        DUNKLEOSTEUS_SKULL_ENTITY = Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                id,
                BlockEntityType.Builder.create(DunkleosteusSkullBlockEntity::new, DUNKLEOSTEUS_SKULL).build(null)
        );

        AquaVerse.LOGGER.info("✅ Registered Dunkleosteus Skull Block and BlockEntity!");
    }
}















