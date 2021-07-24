package com.CheeseMan.firstmod.core.init;

import com.CheeseMan.firstmod.FirstMod;
import com.CheeseMan.firstmod.common.container.CobraniteArmorerContainer;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ContainerTypesInit {

	public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister
			.create(ForgeRegistries.CONTAINERS, FirstMod.MOD_ID);
	
	public static final RegistryObject<ContainerType<CobraniteArmorerContainer>> COBRANITE_ARMORER_CONTAINER_TYPE = CONTAINER_TYPES
			.register("cobranite_armorer", () -> IForgeContainerType.create(CobraniteArmorerContainer::new));



}
