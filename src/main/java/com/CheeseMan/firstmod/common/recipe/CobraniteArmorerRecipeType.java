package com.CheeseMan.firstmod.common.recipe;

import com.CheeseMan.firstmod.FirstMod;

import net.minecraft.item.crafting.IRecipeType;

public class CobraniteArmorerRecipeType implements IRecipeType<CobraniteArmorerRecipe>{
	@Override
	public String toString() {
		return FirstMod.MOD_ID + ":cobranite_armorer_recipe";
	}
}
