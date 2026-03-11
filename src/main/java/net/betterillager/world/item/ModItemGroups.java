package net.betterillager.world.item;

import net.betterillager.core.BetterIllager;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

public class ModItemGroups {
    public static final DeferredRegister<@NotNull CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BetterIllager.MODID);

    public static final Supplier<CreativeModeTab> ILLAGER_SPAWN_EGG = CREATIVE_MODE_TAB.register(
            "illager_spawn_egg",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("creativetab.betterillager.illager_spawn_egg"))
                    .displayItems(((itemDisplayParameters, output) -> {
                        output.accept(Items.VEX_SPAWN_EGG);
                        output.accept(Items.VINDICATOR_SPAWN_EGG);
                        output.accept(Items.PILLAGER_SPAWN_EGG);
                        output.accept(Items.EVOKER_SPAWN_EGG);
                        output.accept(Items.RAVAGER_SPAWN_EGG);
                        output.accept(ModItems.ICEOLOGER_SPAWN_EGG);
                    }))
                    .build()
    );

    private static final Logger log = LoggerFactory.getLogger(ModItemGroups.class);
}
