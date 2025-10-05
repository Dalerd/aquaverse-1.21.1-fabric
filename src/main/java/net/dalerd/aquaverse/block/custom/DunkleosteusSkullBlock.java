package net.dalerd.aquaverse.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;

public class DunkleosteusSkullBlock extends Block {

    public static final BooleanProperty OPEN = Properties.OPEN;

    public DunkleosteusSkullBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(HorizontalFacingBlock.FACING, net.minecraft.util.math.Direction.NORTH)
                .with(OPEN, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(HorizontalFacingBlock.FACING, OPEN);
    }
}






