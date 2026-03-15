package net.betterillager.world.entity;

import net.betterillager.core.BetterIllager;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.NotNull;

public class ModEntityTypeTag {
    public static final TagKey<@NotNull EntityType<?>> ICE_RAIDERS =
            TagKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(BetterIllager.MODID, "ice_illagers"));
}
