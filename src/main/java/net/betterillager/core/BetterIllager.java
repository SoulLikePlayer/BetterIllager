package net.betterillager.core;

import net.betterillager.world.entity.ModEntityType;
import net.betterillager.world.item.ModItemGroups;
import net.betterillager.world.item.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

/**
 * Main mod class for Better Illager.
 * <p>
 * This is the core entry point for the mod, handling both common (server + client)
 * initialization. It registers event listeners, configuration, and serves as the
 * central hub for mod-wide constants and logging.
 * </p>
 */
@Mod(BetterIllager.MODID)
public class BetterIllager {
    public static final String MODID = "betterillager";
    public static final Logger LOGGER = LogUtils.getLogger();

    /**
     * Constructs the main mod instance.
     * <p>
     * This constructor is called by NeoForge during mod loading. It registers
     * lifecycle event handlers and sets up the mod configuration.
     * </p>
     *
     * @param modEventBus The mod-specific event bus for lifecycle events
     * @param modContainer The mod container providing configuration and mod metadata
     */
    public BetterIllager(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::addCreative);

        ModEntityType.ENTITY_TYPES.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModItemGroups.CREATIVE_MODE_TAB.register(modEventBus);
        ModDataComponents.DATA_COMPONENTS.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    /**
     * Handles common setup (client and server).
     * <p>
     * This method is invoked during the mod initialization phase, after
     * registries are frozen but before the game fully loads. It's suitable
     * for cross-registration logic or global setup tasks.
     * </p>
     *
     * @param event The common setup event, providing access to enqueued work
     *              and the current Minecraft instance (where applicable)
     */
    private void commonSetup(FMLCommonSetupEvent event) {}

    /**
     * Handles adding items to creative mode tabs.
     * <p>
     * This event is fired when creative tabs are being populated. It allows
     * the mod to insert its items, blocks, or other elements into appropriate
     * creative inventory tabs dynamically.
     * </p>
     *
     * @param event The event containing the creative tab and the mutable list
     *              of items to be displayed
     */
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.SPAWN_EGGS){
            event.accept(ModItems.ICEOLOGER_SPAWN_EGG);
        }
    }
}