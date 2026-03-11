package net.betterillager.world.item;

import net.betterillager.core.BetterIllager;
import net.betterillager.world.entity.ModEntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(BetterIllager.MODID);

    public static final DeferredItem<@NotNull Item> ICEOLOGER_SPAWN_EGG = ITEMS.registerItem(
            "iceologer_spawn_egg",
            (props) -> new SpawnEggItem(props.spawnEgg(ModEntityType.ICEOLOGER.get()))
    );
}