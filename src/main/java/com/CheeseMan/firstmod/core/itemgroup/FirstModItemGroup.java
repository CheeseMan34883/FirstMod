package com.CheeseMan.firstmod.core.itemgroup;

import com.CheeseMan.firstmod.core.init.ItemInit;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class FirstModItemGroup extends ItemGroup {

	public static final FirstModItemGroup FIRST_MOD = new FirstModItemGroup(ItemGroup.TABS.length, "first_mod");

	public FirstModItemGroup(int index, String label) {
		super(index, label);

	}

	@Override
	public ItemStack makeIcon() {
		return new ItemStack(ItemInit.EXAMPLE_ITEM.get());
	}

}
