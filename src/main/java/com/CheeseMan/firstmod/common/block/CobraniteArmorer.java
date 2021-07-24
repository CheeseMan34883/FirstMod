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

}
