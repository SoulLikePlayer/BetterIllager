package net.betterillager.world.item.ingredient;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.SmithingTemplateItem;

import java.util.List;

public class FrostUpgradeTemplate extends SmithingTemplateItem {
    public FrostUpgradeTemplate(Properties properties) {
        super(
                Component.translatable("item.betterillager.frost_upgrade.applies_to"),
                Component.translatable("item.betterillager.frost_upgrade.ingredients"),
                Component.translatable("item.betterillager.frost_upgrade.base_slot_description"),
                Component.translatable("item.betterillager.frost_upgrade.additions_slot_description"),
                List.of(),
                List.of(),
                properties
        );
    }
}
