package com.CheeseMan.firstmod.core.init;

import com.CheeseMan.firstmod.FirstMod;
import com.CheeseMan.firstmod.common.te.CobraniteArmorerTileEntity;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityTypesInit {

	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPE = DeferredRegister
			.create(ForgeRegistries.TILE_ENTITIES, FirstMod.MOD_ID);

	public static final RegistryObject<TileEntityType<CobraniteArmorerTileEntity>> COBRANITE_ARMORER_TILE_ENTITY_TYPE = TILE_ENTITY_TYPE
			.register("cobranite_armorer", () -> TileEntityType.Builder.of(CobraniteArmorerTileEntity::new,
					BlockInit.COBRANITE_ARMORER.get()).build(null));

}