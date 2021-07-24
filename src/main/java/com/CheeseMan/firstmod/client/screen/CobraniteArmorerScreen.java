package com.CheeseMan.firstmod.client.screen;

import com.CheeseMan.firstmod.FirstMod;
import com.CheeseMan.firstmod.common.container.CobraniteArmorerContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CobraniteArmorerScreen extends ContainerScreen<CobraniteArmorerContainer> {

	private static final ResourceLocation COBRANITE_ARMORER_GUI = new ResourceLocation(FirstMod.MOD_ID,
			"textures/gui/cobranite_armorer.png");

	public CobraniteArmorerScreen(CobraniteArmorerContainer screenContainer, PlayerInventory playerInv,
			ITextComponent titleIn) {
		super(screenContainer, playerInv, titleIn);

		this.leftPos = 0;
		this.topPos = 0;
		this.imageWidth = 175;
		this.imageHeight = 201;

	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		// TODO Auto-generated method stub
		this.renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		this.renderTooltip(matrixStack, mouseX, mouseY);
	}

	@Override
	protected void renderLabels(MatrixStack matrixStack, int x, int y) {
		this.font.draw(matrixStack, this.inventory.getDisplayName(), (float) this.inventoryLabelX,
				(float) this.inventoryLabelY, 4210752);
	}
	@SuppressWarnings("deprecation")
	@Override
	protected void renderBg(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
		RenderSystem.color4f(1f, 1f, 1f, 1f);
		this.minecraft.textureManager.bind(COBRANITE_ARMORER_GUI);
		int x = (this.width - this.imageWidth) / 2;
		int y = (this.height - this.imageHeight) / 2;
		this.blit(matrixStack, x, y, 0, 0, this.imageWidth, this.imageHeight);
	}

}
