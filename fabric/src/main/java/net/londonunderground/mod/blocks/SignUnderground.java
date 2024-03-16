package net.londonunderground.mod.blocks;

import net.londonunderground.mod.BlockEntityTypes;
import org.mtr.mapping.holder.*;
import org.mtr.mapping.mapper.BlockEntityExtension;
import org.mtr.mapping.mapper.BlockHelper;
import org.mtr.mapping.tool.HolderBase;
import org.mtr.mod.block.IBlock;

import javax.annotation.Nonnull;
import java.util.List;

public class SignUnderground extends BlockRoundelBase {

	public SignUnderground(BlockSettings settings) {
		super(settings);
	}

	@Override
	public BlockState getPlacementState2(ItemPlacementContext ctx) {
		return getDefaultState2().with(new Property<>(FACING.data), ctx.getPlayerFacing().data);
	}

	@Nonnull
	@Override
	public VoxelShape getOutlineShape2(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		final Direction facing = IBlock.getStatePropertySafe(state, FACING);
		return BlockHelper.shapeUnion(
				IBlock.getVoxelShapeByDirection(-1.5, 8.66667, 7.575, 17.5, 27.5, 8.425, facing),
				IBlock.getVoxelShapeByDirection(-1.65, 0, 7.125, -0.85, 27.5, 8.875, facing),
				IBlock.getVoxelShapeByDirection(16.85, 0, 7.125, 17.65, 27.5, 8.875, facing)
		);
	}

	@Nonnull
	@Override
	public BlockEntityExtension createBlockEntity(BlockPos pos, BlockState state) {
		return new SignUnderground.TileEntitySignUnderground(pos, state);
	}

	@Override
	public void addBlockProperties(List<HolderBase<?>> builder) {
		builder.add(FACING);
	}

	public static class TileEntitySignUnderground extends TileEntityBlockRoundelBase {

		public TileEntitySignUnderground(BlockPos pos, BlockState state) {
			super(BlockEntityTypes.SIGN_UNDERGROUND_TILE_ENTITY.get(), pos, state);
		}

		@Override
		public boolean shouldRender() {
			return true;
		}
	}
}
