package net.dalerd.aquaverse.block;

import net.dalerd.aquaverse.AquaVerse;
import net.dalerd.aquaverse.block.custom.DunkleosteusSkullBlock;
import net.dalerd.aquaverse.block.custom.DunkleosteusSkullBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {

    // ✅ Declare block and block entity
    public static final DunkleosteusSkullBlock DUNKLEOSTEUS_SKULL =
            new DunkleosteusSkullBlock(Block.Settings.copy(Blocks.STONE));

    public static BlockEntityType<DunkleosteusSkullBlockEntity> DUNKLEOSTEUS_SKULL_ENTITY;

    public static void registerModBlocks() {
        // ✅ Use Identifier.of(...) instead of private constructor
        Identifier id = Identifier.of(AquaVerse.MOD_ID, "dunkleosteus_skull");

        // Register block
        Registry.register(Registries.BLOCK, id, DUNKLEOSTEUS_SKULL);

        // Register block item (no .group() in 1.21)
        Registry.register(
                Registries.ITEM,
                id,
                new BlockItem(DUNKLEOSTEUS_SKULL, new Item.Settings())
        );

        // Register block entity type
        DUNKLEOSTEUS_SKULL_ENTITY = Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                id,
                BlockEntityType.Builder.create(DunkleosteusSkullBlockEntity::new, DUNKLEOSTEUS_SKULL).build(null)
        );

        AquaVerse.LOGGER.info("Registered Dunkleosteus Skull Block and BlockEntity!");
    }
}












