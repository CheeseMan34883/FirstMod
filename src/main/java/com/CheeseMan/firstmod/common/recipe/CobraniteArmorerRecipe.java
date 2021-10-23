package com.CheeseMan.firstmod.common.recipe;

import com.CheeseMan.firstmod.FirstMod;
import com.CheeseMan.firstmod.common.te.CobraniteArmorerTileEntity;
import com.CheeseMan.firstmod.core.init.ItemInit;
import com.CheeseMan.firstmod.core.init.RecipeInit;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class CobraniteArmorerRecipe implements IRecipe<CobraniteArmorerTileEntity> {

	public static final Serializer SERIALIZER = new Serializer();

	private final Ingredient input;
	private final Ingredient input1;
	private final Ingredient input2;
	private final Ingredient input3;
	private final ItemStack output;
	private final ResourceLocation id;

	public CobraniteArmorerRecipe(Ingredient input, Ingredient input1, Ingredient input2, Ingredient input3,
			ItemStack output, ResourceLocation id) {
		this.input = input;
		this.input1 = input1;
		this.input2 = input2;
		this.input3 = input3;
		this.output = output;
		this.id = id;
	}

	@Override
	public boolean matches(CobraniteArmorerTileEntity inv, World world) {
		if (inv.getContainerSize() == 6) {
			return testInputs(inv, input) && testInputs(inv, input1) && testInputs(inv, input2)
					&& testInputs(inv, input3);
		}
		return false;
	}

	@Override
	public ItemStack assemble(CobraniteArmorerTileEntity p_77572_1_) {
		// TODO Auto-generated method stub
		return output.copy();
	}

	@Override
	public ItemStack getResultItem() {
		return this.output;
	}

	@Override
	public ResourceLocation getId() {
		return this.id;
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return SERIALIZER;
	}

	@Override
	public IRecipeType<?> getType() {
		return RecipeInit.COBRANITE_ARMORER_RECIPE;
	}

	@Override
	public ItemStack getToastSymbol() {
		return new ItemStack(ItemInit.CONDUCTED_COBRANITE.get());
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return true;
	}

	private boolean testInputs(CobraniteArmorerTileEntity tileEntity, Ingredient input) {
		for (int i = 0; i < 4; i++) {
			if (input.test(tileEntity.getItem(i)))
				return true;
		}
		return false;
	}

	private static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>>
			implements IRecipeSerializer<CobraniteArmorerRecipe> {
		Serializer() {
			this.setRegistryName(FirstMod.MOD_ID, "cobranite_armorer_recipe");
		}

		@Override
		public CobraniteArmorerRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
			final JsonElement inputEL = JSONUtils.isArrayNode(json, "input") ? JSONUtils.getAsJsonArray(json, "input")
					: JSONUtils.getAsJsonObject(json, "input");
			final JsonElement inputEL1 = JSONUtils.isArrayNode(json, "input1")
					? JSONUtils.getAsJsonArray(json, "input1")
					: JSONUtils.getAsJsonObject(json, "input1");
			final JsonElement inputEL2 = JSONUtils.isArrayNode(json, "input2")
					? JSONUtils.getAsJsonArray(json, "input2")
					: JSONUtils.getAsJsonObject(json, "input2");
			final JsonElement inputEL3 = JSONUtils.isArrayNode(json, "input3")
					? JSONUtils.getAsJsonArray(json, "input3")
					: JSONUtils.getAsJsonObject(json, "input3");
			final Ingredient input = Ingredient.fromJson(inputEL);
			final Ingredient input1 = Ingredient.fromJson(inputEL1);
			final Ingredient input2 = Ingredient.fromJson(inputEL2);
			final Ingredient input3 = Ingredient.fromJson(inputEL3);

			final ItemStack output = ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(json, "output"));

			return new CobraniteArmorerRecipe(input, input1, input2, input3, output, recipeId);
		}

		@Override
		public CobraniteArmorerRecipe fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {
			// TODO Auto-generated method stub
			final Ingredient input = Ingredient.fromNetwork(buffer);
			final Ingredient input1 = Ingredient.fromNetwork(buffer);
			final Ingredient input2 = Ingredient.fromNetwork(buffer);
			final Ingredient input3 = Ingredient.fromNetwork(buffer);
			final ItemStack output = buffer.readItem();

			return new CobraniteArmorerRecipe(input, input1, input2, input3, output, recipeId);
		}

		@Override
		public void toNetwork(PacketBuffer buffer, CobraniteArmorerRecipe recipe) {
			recipe.input.toNetwork(buffer);
			recipe.input1.toNetwork(buffer);
			recipe.input2.toNetwork(buffer);
			recipe.input3.toNetwork(buffer);
			buffer.writeItem(recipe.output);
		}

	}

}
