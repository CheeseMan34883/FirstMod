package com.CheeseMan.firstmod.core.util;

import com.CheeseMan.firstmod.FirstMod;
import com.CheeseMan.firstmod.client.screen.CobraniteArmorerScreen;
import com.CheeseMan.firstmod.core.init.ContainerTypesInit;

import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = FirstMod.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)

public class ClientEventBusSubscriber {

	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event) {
		ScreenManager.register(ContainerTypesInit.COBRANITE_ARMORER_CONTAINER_TYPE.get(), CobraniteArmorerScreen::new);
	}
}
