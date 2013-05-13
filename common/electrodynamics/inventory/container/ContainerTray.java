package electrodynamics.inventory.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import electrodynamics.inventory.InventoryItem;
import electrodynamics.item.EDItems;

public class ContainerTray extends Container {

	public InventoryItem inventory;
	
	public EntityPlayer activePlayer;
	
	public ContainerTray(EntityPlayer player, InventoryItem inventory) {
		this.inventory = inventory;
		inventory.parentContainer = this;
		this.activePlayer = player;
		
		// Tray Inventory
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				this.addSlotToContainer(new Slot(inventory, j + i * 3, 62 + j * 18, 17 + i * 18));
			}
		}

		// Player Inventory
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int i = 0; i < 9; ++i) {
			this.addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 142));
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return true;
	}

	public void onCraftGuiClosed(EntityPlayer player) {
		System.out.println("craft gui closed");
		
		if (!player.worldObj.isRemote) {
			if (this.activePlayer.getCurrentEquippedItem() != null && this.activePlayer.getCurrentEquippedItem().getItem() == EDItems.itemTray) {
				this.activePlayer.setCurrentItemOrArmor(0, this.inventory.parent);
				this.activePlayer.inventory.onInventoryChanged();
			}
		}
	}
	
//	@Override
//	public ItemStack slotClick(int slot, int x, int y, EntityPlayer player) {
//		Slot clickedSlot = (Slot)this.inventorySlots.get(slot);
//		
//		if (clickedSlot.getStack() != null) {
//			if (clickedSlot.getStack().getItem() == EDItems.itemTray) {
//				player.dropPlayerItem(clickedSlot.getStack());
//				if (!player.worldObj.isRemote) {
//					this.detectAndSendChanges();
//					player.inventory.onInventoryChanged();
//					player.closeScreen();
//				}
//			}
//		}
//		
//		return super.slotClick(slot, x, y, player);
//	}
	
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(par2);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (par2 < 9) {
				if (!this.mergeItemStack(itemstack1, 9, 45, true)) {
					return null;
				}
			} else if (!this.mergeItemStack(itemstack1, 0, 9, false)) {
				return null;
			}

			if (itemstack1.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.stackSize == itemstack.stackSize) {
				return null;
			}

			slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
		}

		return itemstack;
	}
	
}
