package net.londonunderground.blocks;

import mtr.block.IBlock;
import mtr.mappings.BlockEntityMapper;
import net.londonunderground.MyBlockEntityTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class BritishRailUnderground extends BlockRoundelBase {

	public BritishRailUnderground(Properties settings) {
		super(settings);
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand interactionHand, BlockHitResult hit) {
		return IBlock.checkHoldingBrush(world, player, () -> world.setBlockAndUpdate(pos, state.cycle(COLOR)));
	}

	@Override
	public void appendHoverText(ItemStack stack, BlockGetter blockGetter, List<Component> tooltip, TooltipFlag tooltipFlag) {
		tooltip.add(new TranslatableComponent("tooltip.londonunderground.block_roundel").setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
		final Direction facing = IBlock.getStatePropertySafe(state, FACING);
		return world.getBlockState(pos.relative(facing)).isFaceSturdy(world, pos.relative(facing), facing.getOpposite());
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		final Direction side = ctx.getClickedFace();
		if (side != Direction.UP && side != Direction.DOWN) {
			return defaultBlockState().setValue(FACING, side.getOpposite());
		} else {
			return null;
		}
	}

	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState newState, LevelAccessor world, BlockPos pos, BlockPos posFrom) {
		if (direction.getOpposite() == IBlock.getStatePropertySafe(state, FACING).getOpposite() && !state.canSurvive(world, pos)) {
			return Blocks.AIR.defaultBlockState();
		} else {
			return state;
		}
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext collisionContext) {
		return IBlock.getVoxelShapeByDirection(0, 0, 0, 16, 16, 1, IBlock.getStatePropertySafe(state, FACING));
	}

	@Override
	public VoxelShape getCollisionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
		return Shapes.empty();
	}

	@Override
	public BlockEntityMapper createBlockEntity(BlockPos pos, BlockState state) {
		return new BritishRailUnderground.TileEntityBritishRailUnderground(pos, state);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(COLOR, FACING);
	}

	public static class TileEntityBritishRailUnderground extends TileEntityBlockRoundelBase {

		public TileEntityBritishRailUnderground(BlockPos pos, BlockState state) {
			super(MyBlockEntityTypes.BRITISH_RAIL_UNDERGROUND_TILE_ENTITY.get(), pos, state);
		}

		@Override
		public boolean shouldRender() {
			return true;
		}
	}
}
