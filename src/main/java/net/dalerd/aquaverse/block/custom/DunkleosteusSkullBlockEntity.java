package net.dalerd.aquaverse.block.custom;

import net.dalerd.aquaverse.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class DunkleosteusSkullBlockEntity extends BlockEntity {

    // ✅ Correct constructor for Fabric 1.21.1
    public DunkleosteusSkullBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.DUNKLEOSTEUS_SKULL_ENTITY, pos, state);
    }

    // ✅ You can add animation, open/close state, or tick() logic here later
    // For example:
    // public static void tick(World world, BlockPos pos, BlockState state, DunkleosteusSkullBlockEntity be) { ... }
}






