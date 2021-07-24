package com.CheeseMan.firstmod.common.te;

import com.CheeseMan.firstmod.FirstMod;
import com.CheeseMan.firstmod.common.container.CobraniteArmorerContainer;
import com.CheeseMan.firstmod.common.recipe.CobraniteArmorerRecipe;
import com.CheeseMan.firstmod.core.init.RecipeInit;
import com.CheeseMan.firstmod.core.init.TileEntityTypesInit;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class CobraniteArmorerTileEntity extends LockableLootTileEntity implements ITickableTileEntity {

	public static int slots = 6;

	protected NonNullList<ItemStack> items = NonNullList.withSize(slots, ItemStack.EMPTY);

	public CobraniteArmorerTileEntity(TileEntityType<?> typeIn) {
		super(typeIn);

	}

	public CobraniteArmorerTileEntity() {
		this(TileEntityTypesInit.COBRANITE_ARMORER_TILE_ENTITY_TYPE.get());
	}

	@Override
	public int getContainerSize() {
		return slots;
	}

	@Override
	protected NonNullList<ItemStack> getItems() {
		return this.items;
	}

	@Override
	protected void setItems(NonNullList<ItemStack> itemsIn) {
		this.items = itemsIn;
	}

	@Override
	protected ITextComponent getDefaultName() {
		return new TranslationTextComponent("container." + FirstMod.MOD_ID + ".cobranite_armorer");
	}

	@Override
	protected Container createMenu(int id, PlayerInventory player) {
		return new CobraniteArmorerContainer(id, player, this);
	}

	@Override
	public CompoundNBT save(CompoundNBT compound) {
		super.save(compound);
		if (!this.trySaveLootTable(compound)) {
			ItemStackHelper.loadAllItems(compound, this.items);
		}
		return compound;
	}

	@Override
	public void load(BlockState state, CompoundNBT nbt) {
		super.load(state, nbt);
		this.items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
		if (!this.trySaveLootTable(nbt)) {
			ItemStackHelper.loadAllItems(nbt, this.items);
		}
	}

	@Override
	public void tick() {
		for (final IRecipe<?> recipe : RecipeInit
				.getRecipes(RecipeInit.COBRANITE_ARMORER_RECIPE, level.getRecipeManager()).values()) {
			CobraniteArmorerRecipe currentRecipe = (CobraniteArmorerRecipe) recipe;
				
			if (currentRecipe.isValid(getItem(0),getItem(1),getItem(2),getItem(3))){
				getItem(0).shrink(1);
				getItem(1).shrink(1);
				getItem(2).shrink(1);
				getItem(3).shrink(1);
				setItem(5, currentRecipe.getResultItem());
		}

		
	}
}
	
}
