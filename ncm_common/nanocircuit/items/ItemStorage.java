package nanocircuit.items;

import nanocircuit.core.Reference;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemStorage extends ItemBlock
{
	public ItemStorage(int i)
    {
        super(i);
        setHasSubtypes(true);
        setItemName("ncStorage");
    }

    public int getPlacedBlockMetadata(int i)
    {
        return i;
    }

    public int getMetadata(int i)
    {
        return i;
    }

    public String getItemNameIS(ItemStack itemstack)
    {
        switch (itemstack.getItemDamage())
        {
            case Reference.BLOCK_STORAGE_LODESTONE_META:
                return "tile.strgLodestone";
        }

        throw new IndexOutOfBoundsException();
    }
}
