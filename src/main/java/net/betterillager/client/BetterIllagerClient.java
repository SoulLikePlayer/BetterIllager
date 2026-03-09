package net.betterillager.client;

import net.betterillager.core.BetterIllager;
import net.betterillager.world.entity.ModEntityType;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.PillagerRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

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

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(ModEntityType.ICEOLOGER, PillagerRenderer::new);
    }
}