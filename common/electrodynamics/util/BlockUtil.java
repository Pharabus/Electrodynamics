package electrodynamics.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

import java.util.Random;

public class BlockUtil {

	/**
	 * Determines which side of the block is facing the specified entity, by using angles.
	 * Will only look around in the XZ-plane.
	 *
	 * @param entity the entity facing the block.
	 * @param x      the x coordinate of the block
	 * @param z      the z coordinate of the block
	 */
	public static ForgeDirection getSideFacingEntity(Entity entity, int x, int z) {
		double diffX = entity.posX - x;
		double diffZ = entity.posZ - z;
		double angle = Math.atan2( diffZ, diffX ) / Math.PI * 180 + 180;

		if( angle < 45 || angle > 315 )
			return ForgeDirection.getOrientation( 4 ); // WEST  (-X)
		else if( angle < 135 )
			return ForgeDirection.getOrientation( 2 ); // NORTH (-Z)
		else if( angle < 225 )
			return ForgeDirection.getOrientation( 5 ); // EAST  (+X)
		else
			return ForgeDirection.getOrientation( 3 ); // SOUTH (+Z)
	}


	public static void dropItemFromBlock(World world, int x, int y, int z, ItemStack stack, Random random) {
		if( stack != null ) {
			float f = random.nextFloat() * 0.9F + 0.1F;
			float f1 = random.nextFloat() * 0.9F + 0.1F;
			float f2 = random.nextFloat() * 0.9F + 0.1F;
			EntityItem entityitem = new EntityItem( world, (double) ((float) x + f), (double) ((float) y + 1.1F + f1), (double) ((float) z + f2), stack );

			float f3 = 0.05F;
			entityitem.motionX = (double) ((float) random.nextGaussian() * f3);
			entityitem.motionY = (double) ((float) random.nextGaussian() * f3 + 0.1F);
			entityitem.motionZ = (double) ((float) random.nextGaussian() * f3);
			if( stack.hasTagCompound() ) {
				entityitem.getEntityItem().setTagCompound( (NBTTagCompound) stack.getTagCompound().copy() );
			}

			world.spawnEntityInWorld( entityitem );
		}
	}

}
