package com.CheeseMan.firstmod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.CheeseMan.firstmod.common.block.ExampleCrop;
import com.CheeseMan.firstmod.core.init.BlockInit;
import com.CheeseMan.firstmod.core.init.ContainerTypesInit;
import com.CheeseMan.firstmod.core.init.FeatureInit;
import com.CheeseMan.firstmod.core.init.ItemInit;
import com.CheeseMan.firstmod.core.init.RecipeInit;
import com.CheeseMan.firstmod.core.init.TileEntityTypesInit;
import com.CheeseMan.firstmod.core.itemgroup.FirstModItemGroup;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("firstmod")
@Mod.EventBusSubscriber(modid = FirstMod.MOD_ID, bus = Bus.MOD)
public class FirstMod {
	// Directly reference a log4j logger.
	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MOD_ID = "firstmod";

	public FirstMod() {
		// Register ourselves for server and other game events we are interested in
		
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

		bus.addGenericListener(IRecipeSerializer.class, RecipeInit::registerRecipes); 
		ItemInit.ITEMS.register(bus);
		BlockInit.BLOCKS.register(bus);
		
		ContainerTypesInit.CONTAINER_TYPES.register(bus);
		TileEntityTypesInit.TILE_ENTITY_TYPE.register(bus);
		MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, FeatureInit::addOres);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public static void onRegisterItems(final RegistryEvent.Register<Item> event) {
		BlockInit.BLOCKS.getEntries().stream().filter(block -> !(block.get() instanceof ExampleCrop)).map(RegistryObject::get).forEach(block -> {
			event.getRegistry().register(new BlockItem(block, new Item.Properties().tab(FirstModItemGroup.FIRST_MOD))
					.setRegistryName(block.getRegistryName()));
		});
	}
}
