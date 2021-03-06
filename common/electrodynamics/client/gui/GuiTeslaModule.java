package electrodynamics.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import electrodynamics.api.tool.IElMagModule;
import electrodynamics.core.handler.GuiHandler;
import electrodynamics.inventory.container.ContainerTeslaModule;

public class GuiTeslaModule extends GuiContainer {

	public EntityPlayer player;
	
	public ContainerTeslaModule container;
	
	public GuiTeslaModule(EntityPlayer player, ContainerTeslaModule container) {
		super(container);
		
		this.ySize = 133;
		this.container = container;
		this.player = player;
	}

	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		if (this.inventorySlots.getSlot(0).getHasStack()) {
			ItemStack stack = this.inventorySlots.getSlot(0).getStack();
			IElMagModule module = (IElMagModule) stack.getItem();
			
			this.fontRenderer.drawString(module.getModuleName(stack), 8 + 16 + 4, 8, 4210752);
			this.fontRenderer.drawSplitString(module.getModuleDescription(stack), 8, 8 + 16 + 4, 160, 4210752);
		}
	}

	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(GuiHandler.GuiType.TESLA_MODULE.guiFile);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
	}
	
}
