package net.betterillager.client;

import net.betterillager.core.BetterIllager;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * Client-side entry point for the Better Illager mod.
 * <p>
 * This class handles all client-specific initialization and event registration.
 * It is only loaded on the physical client side, thanks to the {@code dist = Dist.CLIENT}
 * attribute in the {@code @Mod} annotation.
 * </p>
 */
@Mod(value = BetterIllager.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = BetterIllager.MODID, value = Dist.CLIENT)
public class BetterIllagerClient {

    /**
     * Constructs the client-side mod instance.
     * <p>
     * This constructor is called by NeoForge during client initialization.
     * It can be used to register client-specific events or setup resources
     * that don't require the client to be fully initialized.
     * </p>
     *
     * @param container The mod container providing mod-specific information and services
     */
    public BetterIllagerClient(ModContainer container) {}

    /**
     * Handles the client setup event.
     * <p>
     * This method is called during the client initialization phase, after the mod
     * has been constructed. It's the ideal place to register entity renderers,
     * screen factories, key bindings, and other client-side only features.
     * </p>
     *
     * @param event The client setup event, providing access to the client's
     *              {@link net.minecraft.client.Minecraft} instance and enqueued work
     */
    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {}
}