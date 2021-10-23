package com.CheeseMan.firstmod.common.te;

import javax.annotation.Nullable;

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
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.Constants;

public class CobraniteArmorerTileEntity extends LockableLootTileEntity implements ITickableTileEntity {

	public int getFuelCounter() {
		return fuelCounter;
	}

	public void setFuelCounter(int fuelCounter) {
		this.fuelCounter = fuelCounter;
	}

	public int getMaxFuelCounter() {
		return maxFuelCounter;
	}

	public void setMaxFuelCounter(int maxFuelCounter) {
		this.maxFuelCounter = maxFuelCounter;
	}

	public static int slots = 6, WORKING_TIME = 200;

	protected NonNullList<ItemStack> items = NonNullList.withSize(slots, ItemStack.EMPTY);

	private int fuelCounter;

	private int maxFuelCounter = 0;
	
	private int counter;

	public CobraniteArmorerTileEntity(TileEntityType<?> typeIn) {
		super(typeIn);

	}

	public CobraniteArmorerTileEntity() {
		this(TileEntityTypesInit.COBRANITE_ARMORER_TILE_ENTITY_TYPE.get());
		counter = 0;
		fuelCounter = -1;

	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
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
        compound.putInt("counter", counter);
        compound.putInt("fuelCounter", fuelCounter); 
        compound.putInt("maxfuelCounter", maxFuelCounter);
        if (!this.trySaveLootTable(compound)) {
            ItemStackHelper.saveAllItems(compound, this.items);
            }
        return compound;
    }

	@Override
	public void load(BlockState state, CompoundNBT nbt) {
		super.load(state, nbt);
		this.counter = nbt.getInt("counter");
		this.maxFuelCounter = nbt.getInt("maxfuelCounter");
		this.fuelCounter = nbt.getInt("fuelCounter");
		this.items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
		if (!this.trySaveLootTable(nbt)) {
			ItemStackHelper.loadAllItems(nbt, this.items);
		}
	}

	@Override
	public void tick() {
		if(level.isClientSide()) {
            return;
		}
		BlockState state = level.getBlockState(getBlockPos());
        if (state.getValue(BlockStateProperties.POWERED) != counter > 0) {
            level.setBlock(getBlockPos(), state.setValue(BlockStateProperties.POWERED, counter > 0),
                    Constants.BlockFlags.NOTIFY_NEIGHBORS + Constants.BlockFlags.BLOCK_UPDATE);

        }
        if(fuelCounter > 0)
            fuelCounter--;
        if(getItem(0).isEmpty() || getItem(0).isEmpty() || getItem(0).isEmpty() || getItem(0).isEmpty()){
            reset();
            return;
           }
        // this checks whether there is a matching recipe, and then checks whether fuel
        // is available and the checks whether the outputSlot is occupied
        CobraniteArmorerRecipe recipe = getRecipe();
        if(recipe == null || !checkFuel() || !canStartWorking(recipe)){
          reset();
           return;
        }
        if(counter <= 0){
            //ill create this method in a second
            startWorking(recipe);
        }
        if(counter > 0){
          counter--;
          if(counter == 0){
            //ill create the method in a second
            finishWork(recipe);
          }
        }
}
	private boolean canStartWorking(CobraniteArmorerRecipe recipe){
	    //when the output slot is Empty u can smelt of course
	    if(getItem(5).isEmpty())
	      return true;
	    //when in the there is an item in the output slot that matches the current output u can also start working
	    if(getItem(5).getItem() == recipe.assemble(this).getItem())
	      return true;
	  //default is u can't start working
	  return false;
	}
	private void reset() {
		this.counter = 0;
		
	}
	/**
	*executed when a recipe is starting to be processed
	*/
	private void startWorking(CobraniteArmorerRecipe recipe){
	    counter = WORKING_TIME;
	}
	/**
	* executed when the recipe is finished, use to shring the input and set the output
	*/
	private void finishWork(CobraniteArmorerRecipe recipe){
	    for(int i =0;i<4;i++){
	        getItem(i).shrink(1);
	    }
	    if(getItem(5).isEmpty())
	      setItem(5, recipe.assemble(this).copy());
	    else if(getItem(5).getItem() == recipe.assemble(this).getItem())
	        getItem(5).grow(1);
	}
	private boolean checkFuel(){
        if(fuelCounter >= 0){
            fuelCounter--;
            return true;
        }    
        
        else if(!getItem(4).isEmpty()){
                    fuelCounter = ForgeHooks.getBurnTime(getItem(4));
                    maxFuelCounter = fuelCounter;
                    getItem(4).shrink(1);
                     return true;
        }
        return false;    
    }

	@Nullable
	private CobraniteArmorerRecipe getRecipe() {
		return this.level.getRecipeManager().getRecipeFor(RecipeInit.COBRANITE_ARMORER_RECIPE, this, this.level)
				.orElse(null);
	
	}
	
		
	
}
