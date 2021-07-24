package com.CheeseMan.firstmod.core.init;

import com.CheeseMan.firstmod.FirstMod;
import com.CheeseMan.firstmod.common.items.ConductedCobranite;
import com.CheeseMan.firstmod.core.itemgroup.FirstModItemGroup;

import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FirstMod.MOD_ID);

	public static final RegistryObject<Item> EXAMPLE_ITEM = ITEMS.register("example_item",
			() -> new Item(new Item.Properties().tab(FirstModItemGroup.FIRST_MOD)));

	public static final RegistryObject<Item> UGLYMANTANITE_INGOT = ITEMS.register("uglymantanite_ingot",
			() -> new Item(new Item.Properties().tab(FirstModItemGroup.FIRST_MOD)));

	public static final RegistryObject<Item> UGLYMANTANITE_CRUST = ITEMS.register("uglymantanite_crust",
			() -> new Item(new Item.Properties().tab(FirstModItemGroup.FIRST_MOD)));

	public static final RegistryObject<ConductedCobranite> CONDUCTED_COBRANITE = ITEMS.register("conducted_cobranite",
			() -> new ConductedCobranite(new Item.Properties().tab(FirstModItemGroup.FIRST_MOD)));

	public static final RegistryObject<Item> EXAMPLE_FOOD = ITEMS.register("example_food",
			() -> new Item(new Item.Properties().tab(FirstModItemGroup.FIRST_MOD)
					.food(new Food.Builder().nutrition(4).saturationMod(1.2f)
							.effect(new EffectInstance(Effects.REGENERATION, 200, 0), 1f)
							.effect(new EffectInstance(Effects.SLOW_FALLING, 200, 0), 1f).build())));

}
