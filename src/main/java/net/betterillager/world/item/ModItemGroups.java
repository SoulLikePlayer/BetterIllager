package net.betterillager.world.item;

import net.betterillager.core.BetterIllager;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.OminousBottleAmplifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

public class ModItemGroups {
    private static final Logger LOGGER = LoggerFactory.getLogger(ModItemGroups.class);

    public static final DeferredRegister<@NotNull CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BetterIllager.MODID);

    public static final Supplier<CreativeModeTab> ILLAGER_SPAWN_EGG = CREATIVE_MODE_TAB.register(
            "illager_spawn_egg",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(Items.PILLAGER_SPAWN_EGG))
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

    public static final Supplier<CreativeModeTab> ILLAGER_COMBAT = CREATIVE_MODE_TAB.register(
            "illager_combat",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(Items.CROSSBOW))
                    .title(Component.translatable("creativetab.betterillager.illager_combat"))
                    .displayItems(((itemDisplayParameters, output) -> {
                        output.accept(Items.CROSSBOW);
                        output.accept(ModItems.EVOKER_SCEPTER);
                        output.accept(Items.IRON_AXE);
                        output.accept(ModItems.FROZEN_IRON_AXE);
                    }))
                    .build()
    );

    public static final Supplier<CreativeModeTab> ILLAGER_TOOLS_AND_UTILITIES = CREATIVE_MODE_TAB.register(
            "illager_tools_and_utilities",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(Items.OMINOUS_BOTTLE))
                    .title(Component.translatable("creativetab.betterillager.illager_tools_and_utilities"))
                    .displayItems(((itemDisplayParameters, output) -> {
                        for (int i = 0; i < 5; i++) {
                            ItemStack stack = new ItemStack(Items.OMINOUS_BOTTLE);
                            stack.set(DataComponents.OMINOUS_BOTTLE_AMPLIFIER, new OminousBottleAmplifier(i));
                            output.accept(stack);
                        }
                        output.accept(ModItems.OMINOUS_HORN);
                        output.accept(ModItems.EVOKER_SCEPTER);
                    }))
                    .build()
    );
}