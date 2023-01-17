package net.londonunderground.blocks;

import mtr.block.IBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class StationA4 extends HorizontalDirectionalBlock {
	public StationA4(Properties settings) {
		super(settings);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		return defaultBlockState().setValue(FACING, ctx.getHorizontalDirection());
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext collisionContext) {
		final Direction facing = IBlock.getStatePropertySafe(state, FACING);
		return Shapes.or(
				IBlock.getVoxelShapeByDirection(0,0,0,16, 4, 9, facing),
				IBlock.getVoxelShapeByDirection(0, 4, 0,16, 7, 10, facing),
				IBlock.getVoxelShapeByDirection(0, 7, 0,16, 9, 11, facing),
				IBlock.getVoxelShapeByDirection(0,9,0,16, 11, 12, facing),
				IBlock.getVoxelShapeByDirection(0, 11, 0,16, 13, 13, facing),
				IBlock.getVoxelShapeByDirection(0, 13, 0,16, 15, 14, facing),
				IBlock.getVoxelShapeByDirection(0,15,0,16, 16, 15, facing)
		);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}


}
