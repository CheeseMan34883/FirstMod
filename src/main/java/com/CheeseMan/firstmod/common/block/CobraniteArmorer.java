package com.CheeseMan.firstmod.common.block;

import com.CheeseMan.firstmod.common.te.CobraniteArmorerTileEntity;
import com.CheeseMan.firstmod.core.init.TileEntityTypesInit;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;

public class CobraniteArmorer extends Block {

	public CobraniteArmorer() {
		super(AbstractBlock.Properties.of(Material.METAL, MaterialColor.COLOR_GRAY).strength(15f, 9f)
				.sound(SoundType.METAL).harvestTool(ToolType.AXE).harvestLevel(0).sound(SoundType.METAL)
				.requiresCorrectToolForDrops());
		this.registerDefaultState(this.stateDefinition.any().setValue(BlockStateProperties.POWERED, false));

	}
	@Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        return state.getValue(BlockStateProperties.POWERED) ? 13 : 0;
	}
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return TileEntityTypesInit.COBRANITE_ARMORER_TILE_ENTITY_TYPE.get().create();

	}
	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult hit) {
		if (!player.isCrouching() && !worldIn.isClientSide) {
			TileEntity te = worldIn.getBlockEntity(pos);
			if (te instanceof CobraniteArmorerTileEntity) {
				NetworkHooks.openGui((ServerPlayerEntity) player, (CobraniteArmorerTileEntity) te, pos);
			}
		}
		return ActionResultType.SUCCESS;
	}
	@Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BlockStateProperties.POWERED);
    }
	@Override
	public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState oldState,
			boolean isMoving) {
		// code that drops items upon breaking TE
		if(!state.is(oldState.getBlock())) {
            TileEntity entity = worldIn.getBlockEntity(pos);
            if(entity instanceof CobraniteArmorerTileEntity) {
                CobraniteArmorerTileEntity tileEntity = (CobraniteArmorerTileEntity) entity;
                InventoryHelper.dropContents(worldIn, pos, tileEntity);
                worldIn.updateNeighbourForOutputSignal(pos, this);
            }
            
        }
		//makes sure the TE drops on breaking
		super.onRemove(state, worldIn, pos, oldState, isMoving);
		
	}
	

}
