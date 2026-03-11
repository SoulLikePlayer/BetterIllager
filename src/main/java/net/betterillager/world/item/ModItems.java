package net.betterillager.world.item;

import net.betterillager.core.BetterIllager;
import net.betterillager.world.entity.ModEntityType;
import net.betterillager.world.item.tools.OminousHornItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.InstrumentComponent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(BetterIllager.MODID);

    public static final DeferredItem<@NotNull Item> ICEOLOGER_SPAWN_EGG = ITEMS.registerItem(
            "iceologer_spawn_egg",
            (props) -> new SpawnEggItem(props.spawnEgg(ModEntityType.ICEOLOGER.get()))
    );

    public static final DeferredItem<@NotNull Item> OMINOUS_HORN = ITEMS.registerItem(
            "ominous_horn",
            (props) -> new OminousHornItem(new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(BetterIllager.MODID,"ominous_horn")))
                    .rarity(Rarity.RARE)
                    .stacksTo(1)
                    .component(DataComponents.INSTRUMENT, new InstrumentComponent(Instruments.SEEK_GOAT_HORN))
    ));
}