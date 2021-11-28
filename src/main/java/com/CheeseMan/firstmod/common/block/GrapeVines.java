package com.CheeseMan.firstmod.common.block;

import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMap;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.server.ServerWorld;

public class GrapeVines extends Block {
	protected static final BooleanProperty DOWN = BlockStateProperties.DOWN;
	protected static final BooleanProperty NORTH = BlockStateProperties.NORTH;
	protected static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
	protected static final BooleanProperty EAST = BlockStateProperties.EAST;
	protected static final BooleanProperty WEST = BlockStateProperties.WEST;
	protected final Map<BlockState, VoxelShape> stateToShape;

	public GrapeVines(Properties builder) {
		super(builder); 
		this.registerDefaultState(this.stateDefinition.any().setValue(DOWN, Boolean.valueOf(false)));
		this.stateToShape = ImmutableMap.copyOf(this.stateDefinition.getPossibleStates().stream().collect(Collectors.toMap(Function.identity(), GrapeVines::calculateShape)));
		 
		
	}

	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {

	}

	@Override
	public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
		return super.canSurvive(state, worldIn, pos);
	}

	public static boolean canAttachTo(IBlockReader blockReader, BlockPos pos, Direction neighborPos) {
		BlockState blockstate = blockReader.getBlockState(pos);
		return Block.isFaceFull(blockstate.getCollisionShape(blockReader, pos), neighborPos.getOpposite());
	}

	private static final VoxelShape calculateShape(BlockState state) {
		VoxelShape voxelshape = VoxelShapes.empty();
		if (state.getValue(NORTH)) {
			voxelshape = Block.box(0.0D, 0.0D, 15.0D, 16.0D, 16.0D, 16.0D);
		}
		if (state.getValue(SOUTH)) {
			voxelshape = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
		}
		if (state.getValue(EAST)) {
			voxelshape = Block.box(0.0D, 0.0D, 0.0D, 1.0D, 16.0D, 16.0D);
		}
		if (state.getValue(WEST)) {
			voxelshape = Block.box(15.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
		}
		return voxelshape;

	}

}
