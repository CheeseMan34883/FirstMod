
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
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;

public class CobraniteArmorerContainer extends Container {

	public final CobraniteArmorerTileEntity te;
	private final IWorldPosCallable canInteractWithCallable;
	
	public CobraniteArmorerContainer(final int windowId, final PlayerInventory playerInv, final CobraniteArmorerTileEntity te) {
		super(ContainerTypesInit.COBRANITE_ARMORER_CONTAINER_TYPE.get(), windowId);
		this.te = te;
		this.canInteractWithCallable = IWorldPosCallable.create(te.getLevel(), te.getBlockPos());
		
		//Tile Entity 
		this.addSlot(new Slot((IInventory) te, 0, 25, 8));
		this.addSlot(new Slot((IInventory) te, 1, 45, 8));
		this.addSlot(new Slot((IInventory) te, 2, 45, 27));
		this.addSlot(new Slot((IInventory) te, 3, 25, 27));
		this.addSlot(new Slot((IInventory) te, 4, 79, 58));
		this.addSlot(new Slot((IInventory) te, 5, 115, 17) {
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

}
