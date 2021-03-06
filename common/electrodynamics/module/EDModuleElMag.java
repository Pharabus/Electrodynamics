package electrodynamics.module;

import cpw.mods.fml.common.registry.GameRegistry;
import electrodynamics.core.lang.EDLanguage;
import electrodynamics.item.EDItems;
import electrodynamics.item.ItemTeslaModule;
import electrodynamics.item.elmag.ItemElMagArmor;
import electrodynamics.lib.core.Strings;
import electrodynamics.lib.item.ItemIDs;
import electrodynamics.lib.item.TeslaModule;

public class EDModuleElMag extends EDModule {

	public void preInit() {
		EDItems.itemTeslaHelm = new ItemElMagArmor(ItemIDs.ITEM_ELMAG_HELM_ID, 0).setUnlocalizedName(Strings.ITEM_ELMAG_HAT_NAME);
		GameRegistry.registerItem(EDItems.itemTeslaHelm, Strings.ITEM_ELMAG_HAT_NAME);
		EDLanguage.getInstance().registerItem(EDItems.itemTeslaHelm);
		
		EDItems.itemTeslaChest = new ItemElMagArmor(ItemIDs.ITEM_ELMAG_CHEST_ID, 1).setUnlocalizedName(Strings.ITEM_ELMAG_CHEST_NAME);
		GameRegistry.registerItem(EDItems.itemTeslaChest, Strings.ITEM_ELMAG_CHEST_NAME);
		EDLanguage.getInstance().registerItem(EDItems.itemTeslaChest);
		
		EDItems.itemTeslaLegs = new ItemElMagArmor(ItemIDs.ITEM_ELMAG_LEGS_ID, 2).setUnlocalizedName(Strings.ITEM_ELMAG_LEGS_NAME);
		GameRegistry.registerItem(EDItems.itemTeslaLegs, Strings.ITEM_ELMAG_LEGS_NAME);
		EDLanguage.getInstance().registerItem(EDItems.itemTeslaLegs);
		
		EDItems.itemTeslaBoots = new ItemElMagArmor(ItemIDs.ITEM_ELMAG_BOOTS_ID, 3).setUnlocalizedName(Strings.ITEM_ELMAG_BOOTS_NAME);
		GameRegistry.registerItem(EDItems.itemTeslaBoots, Strings.ITEM_ELMAG_BOOTS_NAME);
		EDLanguage.getInstance().registerItem(EDItems.itemTeslaBoots);
	
		EDItems.itemTeslaModule = new ItemTeslaModule(ItemIDs.ITEM_ELMAG_MODULE_ID).setUnlocalizedName(Strings.ITEM_ELMAG_MODULE);
		GameRegistry.registerItem(EDItems.itemTeslaModule, Strings.ITEM_ELMAG_MODULE);
		for (TeslaModule module : TeslaModule.values()) {
			EDLanguage.getInstance().registerItemStack(module.toItemStack(), module.unlocalizedName);
		}
	}
	
	public void init() {
		
	}
	
	public void postInit() {
		
	}
	
}
