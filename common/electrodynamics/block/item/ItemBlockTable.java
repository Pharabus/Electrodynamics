package electrodynamics.block.item;

import electrodynamics.block.BlockTable;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockTable extends ItemBlock {

	public ItemBlockTable(int id) {
		super(id);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return BlockTable.subNames[stack.getItemDamage()];
	}
	
}
