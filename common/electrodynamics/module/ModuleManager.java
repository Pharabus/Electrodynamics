package electrodynamics.module;

import java.io.File;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import electrodynamics.Electrodynamics;
import electrodynamics.core.EDLogger;

public class ModuleManager {

	public static final String MODULE_CONFIG_FILE = "EDModules.cfg";
	public static final String CATEGORY_MODULES = "modules";
	
	private static Map<Module, EDModule> modules = new EnumMap<Module, EDModule>(Module.class);
	
	private static EnumSet<Module> loadedModules = EnumSet.noneOf(Module.class);
	
	public static EDModule getModule(Module m) {
		if (loadedModules.contains(m)) {
			return modules.get(m);
		}
		
		EDLogger.warn("Something tried to call for a module that's disabled.");
		return null;
	}
	
	/* SERVER METHODS */
	public static void preInit() {
		registerModule(Module.CORE, new EDModuleCore());
		registerModule(Module.WORLD, new EDModuleWorld());
		registerModule(Module.LOGIC, new EDModuleLogic());
//		registerModule(Module.LASER, new EDModuleLaser());
		registerModule(Module.MACHINE, new EDModuleMachine());
//		registerModule(Module.GAS,  new EDModuleGas());
		registerModule(Module.ELMAG, new EDModuleElMag());
		
		Configuration config = new Configuration(new File(Electrodynamics.instance.configFolder, MODULE_CONFIG_FILE));
		config.load();
		
		config.addCustomCategoryComment(CATEGORY_MODULES, "Enable/Disable modules as you see fit.\nBe warned however that some modules depend on others, and will disable themselves if said dependency isn't found.");
	
		//Removes default categories, as I have no use for them here
		config.removeCategory(config.getCategory(Configuration.CATEGORY_BLOCK));
		config.removeCategory(config.getCategory(Configuration.CATEGORY_ITEM));
		config.removeCategory(config.getCategory(Configuration.CATEGORY_GENERAL));
		
		for (Module module : Module.values()) {
			if (module == Module.CORE || isEnabled(config, module)) {
				EDModule instance = modules.get(module);
				
				if (instance != null) {
					if (instance.canLoad()) {
						loadedModules.add(module);
						EDLogger.info("Loading module " + module.toString());
					} else {
						EDLogger.fine("Module " + module.toString() + " decided not to load. Reason: " + instance.failLoadReason());
					}
				} else {
					EDLogger.warn("Module " + module.toString() + " is missing a mapping!");
				}
			}
		}
		
		config.save();
		
		for (Module module : loadedModules) {
			if (dependenciesLoaded(module)) {
				preInit(module);
			} else {
				loadedModules.remove(module);
				
				StringBuilder missing = new StringBuilder();
				for (Module missingModule : getMissingDependencies(module)) {
					missing.append(missingModule.toString());
					missing.append(", ");
				}
				String missingString = missing.toString().substring(0, missing.length() - 2);
				
				EDLogger.warn("Module " + module + " failed to load because it's missing dependencies: [" + missingString + "]");
			}
		}
	}
	
	public static void init() {
		for (Module module : loadedModules) {
			init(module);
		}
	}
	
	public static void postInit() {
		for (Module module : loadedModules) {
			postInit(module);
		}
	}
	
	private static void preInit(Module m) {
		modules.get(m).preInit();
	}
	
	private static void init(Module m) {
		modules.get(m).init();
	}
	
	private static void postInit(Module m) {
		modules.get(m).postInit();
	}
	
	/* CLIENT METHODS */
	public static void preInitClient() {
		for (Module module : loadedModules) {
			preInitClient(module);
		}
	}
	
	public static void initClient() {
		for (Module module : loadedModules) {
			initClient(module);
		}
	}
	
	public static void postInitClient() {
		for (Module module : loadedModules) {
			postInitClient(module);
		}
	}
	
	private static void preInitClient(Module m) {
		modules.get(m).preInitClient();
	}
	
	private static void initClient(Module m) {
		modules.get(m).initClient();
	}
	
	private static void postInitClient(Module m) {
		modules.get(m).postInitClient();
	}
	
	private static boolean isEnabled(Configuration config, Module module) {
		Property moduleEnabled = config.get(CATEGORY_MODULES, module.toString(), true);
		return moduleEnabled.getBoolean(true);
	}
	
	private static boolean dependenciesLoaded(Module module) {
		EDModule instance = modules.get(module);
		
		if (instance.dependencies() == null) return true;
		
		for (Module depends : instance.dependencies()) {
			if (!loadedModules.contains(depends)) {
				return false;
			}
		}
		
		return true;
	}
	
	private static EnumSet<Module> getMissingDependencies(Module module) {
		EDModule instance = modules.get(module);
		EnumSet<Module> missing = EnumSet.noneOf(Module.class);
		
		for (Module depends : instance.dependencies()) {
			if (!loadedModules.contains(depends)) {
				missing.add(depends);
			}
		}
		
		return missing;
	}
	
	private static void registerModule(Module module, EDModule moduleInstance) {
		modules.put(module, moduleInstance);
	}
	
	public static enum Module {
		CORE,
		WORLD,
		LOGIC,
//		LASER,
		MACHINE,
//		GAS,
		ELMAG;
	}
	
}
