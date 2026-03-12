package net.betterillager.core;

import net.betterillager.world.item.tools.EvokerScepterMode;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

public class ModDataComponents {
    public static final DeferredRegister<@NotNull DataComponentType<?>> DATA_COMPONENTS = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, "betterillager");

    public static final DeferredHolder<@NotNull DataComponentType<?>, @NotNull DataComponentType<@NotNull EvokerScepterMode>> EVOKER_SCEPTER_MODE =
            DATA_COMPONENTS.register(
                    "evoker_scepter_mode",
                    () -> DataComponentType.<EvokerScepterMode>builder()
                            .persistent(EvokerScepterMode.CODEC)
                            .networkSynchronized(EvokerScepterMode.STREAM_CODEC)
                            .build()
            );
}
