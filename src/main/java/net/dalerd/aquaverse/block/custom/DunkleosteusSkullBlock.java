package net.dalerd.aquaverse.block.custom;

import com.mojang.serialization.MapCodec;
import net.dalerd.aquaverse.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class DunkleosteusSkullBlock extends HorizontalFacingBlock {

    public static final BooleanProperty OPEN = Properties.OPEN;
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;

    public static final MapCodec<DunkleosteusSkullBlock> CODEC = createCodec(DunkleosteusSkullBlock::new);

    protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
        return CODEC;
    }

    public DunkleosteusSkullBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(FACING, Direction.NORTH)
                .with(OPEN, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, OPEN);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState()
                .with(FACING, ctx.getHorizontalPlayerFacing().getOpposite())
                .with(OPEN, false);
    }

    // --- 🦴 Interactions ---

    public ActionResult onUse(BlockState state, World world, BlockPos pos,
                              net.minecraft.entity.player.PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            boolean open = state.get(OPEN);
            world.setBlockState(pos, state.with(OPEN, !open), Block.NOTIFY_ALL);
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block,
                               BlockPos fromPos, boolean notify) {
        if (!world.isClient) {
            boolean powered = world.isReceivingRedstonePower(pos);
            if (powered != state.get(OPEN)) {
                world.setBlockState(pos, state.with(OPEN, powered), Block.NOTIFY_ALL);
            }
        }
    }

    // --- 📦 Hitboxes ---
    private static final VoxelShape CLOSED_SHAPE = Block.createCuboidShape(
            0, 0, 0, 16, 14, 16
    );

    private static final VoxelShape OPEN_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0, 0, 0, 16, 8, 16),  // base
            Block.createCuboidShape(0, 8, 0, 16, 12, 16)  // slab-like jaw
    );

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos,
                                      net.minecraft.block.ShapeContext context) {
        return state.get(OPEN) ? OPEN_SHAPE : CLOSED_SHAPE;
    }

    public boolean isOpaque(BlockState state) {
        return false;
    }

    public boolean isFullCube(BlockState state) {
        return false;
    }

    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }
}













