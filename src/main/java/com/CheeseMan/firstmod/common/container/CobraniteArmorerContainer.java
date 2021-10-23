
package com.CheeseMan.firstmod.common.container;

import java.util.Objects;

import com.CheeseMan.firstmod.common.te.CobraniteArmorerTileEntity;
import com.CheeseMan.firstmod.core.init.ContainerTypesInit;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;
import net.minecraftforge.common.ForgeHooks;

public class CobraniteArmorerContainer extends Container {

	public final CobraniteArmorerTileEntity te;
	private final IWorldPosCallable canInteractWithCallable;
	private IIntArray array = getIIntArray();
	
	public CobraniteArmorerContainer(final int windowId, final PlayerInventory playerInv, final CobraniteArmorerTileEntity te) {
		super(ContainerTypesInit.COBRANITE_ARMORER_CONTAINER_TYPE.get(), windowId);
		this.te = te;		
		this.canInteractWithCallable = IWorldPosCallable.create(te.getLevel(), te.getBlockPos());
		
		//Tile Entity 
		this.addSlot(new Slot(te, 0, 26, 9));
		this.addSlot(new Slot(te, 1, 44, 9));
		this.addSlot(new Slot(te, 2, 44, 27));
		this.addSlot(new Slot(te, 3, 26, 27));
		this.addSlot(new FuelSlot( te, 4, 79, 58));
		this.addSlot(new Slot(te, 5, 115, 17) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }
		}
	);
		
		//Main Player Inventory 
		for (int row = 0; row < 3; row++ ) {
			for (int col = 0; col < 9; col++) {
				this.addSlot(new Slot(playerInv, col + row * 9 + 9, 8 + col * 18, 166 - (4 - row) * 18 - 10));
				
				
			}
		}
		//Player Hotbar
		for (int col = 0; col < 9; col++) {
			this.addSlot(new Slot (playerInv, col, 8 + col * 18, 142));
		}
		this.addDataSlots(this.array);
		
	}
	public CobraniteArmorerContainer(final int windowId, final PlayerInventory playerInv, final PacketBuffer data) {
		this(windowId, playerInv, getTileEntity(playerInv, data));
	}
	
	private static CobraniteArmorerTileEntity getTileEntity(final PlayerInventory playerInv, final PacketBuffer data) {
		Objects.requireNonNull(playerInv, "Player Inventory cannot be null.");
		Objects.requireNonNull(data, "Packet Buffer cannot be null.");
		final TileEntity te = playerInv.player.level.getBlockEntity(data.readBlockPos());
		if (te instanceof CobraniteArmorerTileEntity) {
			return (CobraniteArmorerTileEntity) te;
		}
		throw new IllegalStateException("Tile Entity is not correct."); 
	}

	@Override
	public boolean stillValid(PlayerEntity playerIn) {
			return true;
	}
	
	@Override 
	public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot != null && slot.hasItem()) {
			ItemStack stack1 = slot.getItem();
			stack = stack1.copy();
			if (index < CobraniteArmorerTileEntity.slots && !this.moveItemStackTo(stack1, CobraniteArmorerTileEntity.slots, this.slots.size(), false)) {
				return ItemStack.EMPTY;
			}
			if (!this.moveItemStackTo(stack1, 0, CobraniteArmorerTileEntity.slots, true)) {
				return ItemStack.EMPTY;
				
			}
			if (stack1.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			}
			else {
				slot.setChanged();
			}
		}
		return stack;
	}
	public static class FuelSlot extends Slot{

        public FuelSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
            super(inventoryIn, index, xPosition, yPosition);
        }
        
        @Override
        public boolean mayPlace(ItemStack stack) {
            return ForgeHooks.getBurnTime(stack) > 0;
        }
        
    }
	
	public double getCounterPercentage() {
        double counter = this.array.get(0);
        if(counter == 0)
          return 0d;
        double maxCounter = CobraniteArmorerTileEntity.WORKING_TIME;
        return 1d - (counter / maxCounter);
    }
	public double getFuelCounterPercentage() {
        double fuelCounter = this.array.get(1);
        double maxFuelCounter = this.array.get(2);
        return fuelCounter / maxFuelCounter;
    }
	
	public boolean isPowered(){
        return te.getLevel().getBlockState(te.getBlockPos()).getValue(BlockStateProperties.POWERED);
    }
	private IIntArray getIIntArray() {
		return new IIntArray() {
			
			@Override
			public void set(int index, int value) {
				switch(index){
				  case 0:
				    te.setCounter(value);
				    break;
				  case 1:
				    te.setFuelCounter(value);
				    break;
				  case 2:
				    te.setMaxFuelCounter(value);
				     break;
				  default:
				    break;
				}
				
			}
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return 3;
			}
			
			@Override
            public int get(int index) {
                switch(index){
                  case 0:
                    return te.getCounter();
                  case 1:
                    return te.getFuelCounter();
                  case 2:
                    return te.getMaxFuelCounter();
                  default:
                      return 0;
                }
                
            }
			
		};
	}
}	
