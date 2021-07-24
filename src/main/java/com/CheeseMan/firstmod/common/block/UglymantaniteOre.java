package com.CheeseMan.firstmod.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;

public class UglymantaniteOre extends Block {

	private final int minXP;
	private final int maxXP;

	public UglymantaniteOre(int minXP, int maxXP, int harvestLevel) {
		super(AbstractBlock.Properties.of(Material.METAL, MaterialColor.COLOR_GRAY).strength(35f, 1200f)
				.harvestTool(ToolType.PICKAXE).harvestLevel(harvestLevel).sound(SoundType.METAL)
				.requiresCorrectToolForDrops());
		this.minXP = maxXP;
		this.maxXP = maxXP;
		
				
		
	}
	private int getExp(ServerWorld serverWorld){
		return MathHelper.nextInt(serverWorld.random, this.minXP, this.maxXP );
		
	}
	@Override
    public int getExpDrop( BlockState state , IWorldReader world , BlockPos pos , int fortune , int silktouch ){
        return silktouch == 0 ? this.getExp((ServerWorld) world) : 0;

	}
	
}

