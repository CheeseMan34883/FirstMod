package com.CheeseMan.firstmod.core.init;

import com.CheeseMan.firstmod.FirstMod;
import com.CheeseMan.firstmod.common.block.CobraniteArmorer;
import com.CheeseMan.firstmod.common.block.CryogenusOre;
import com.CheeseMan.firstmod.common.block.UglymantaniteOre;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.OreBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockInit {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
			FirstMod.MOD_ID);

	public static final RegistryObject<Block> UGLYMANTANITE_BLOCK = BLOCKS.register("uglymantanite_block",
			() -> new Block(AbstractBlock.Properties.of(Material.METAL, MaterialColor.COLOR_GRAY).strength(35f, 1200f)
					.harvestTool(ToolType.PICKAXE).harvestLevel(4).sound(SoundType.METAL)
					.requiresCorrectToolForDrops()));

	public static final RegistryObject<Block> COBRANITE = BLOCKS.register("cobranite",
			() -> new Block(AbstractBlock.Properties.of(Material.METAL, MaterialColor.COLOR_GRAY).strength(22.5f, 9f)
					.harvestTool(ToolType.PICKAXE).harvestLevel(2).sound(SoundType.METAL)
					.requiresCorrectToolForDrops()));

	public static final RegistryObject<Block> COBRANITE_ARMORER = BLOCKS.register("cobranite_armorer",
			() -> new CobraniteArmorer());

	public static final RegistryObject<Block> UGLYMANTANITE_ORE = BLOCKS.register("uglymantanite_ore",
			() -> new UglymantaniteOre(8, 12, 4));
	
	public static final RegistryObject<Block> CRYOGENUS_ORE = BLOCKS.register("cryogenus_ore",
			() -> new CryogenusOre(3, 4, 4));
	
	public static final RegistryObject<Block> CRYOGENUS_BLOCK = BLOCKS.register("cryogenus_block",
			() -> new Block(AbstractBlock.Properties.of(Material.METAL, MaterialColor.COLOR_GRAY).strength(10f, 1200f)
					.harvestTool(ToolType.PICKAXE).harvestLevel(2).sound(SoundType.METAL)
					.requiresCorrectToolForDrops()));

}
