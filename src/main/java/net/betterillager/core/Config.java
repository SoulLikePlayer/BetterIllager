package net.betterillager.core;

import net.neoforged.neoforge.common.ModConfigSpec;

/**
 * Configuration handler for the Better Illager mod.
 * <p>
 * This class defines and manages the mod's configuration options using
 * NeoForge's {@link ModConfigSpec} system. It provides a centralized
 * specification that can be accessed throughout the mod to respect
 * user-configured settings.
 * </p>
 */
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    static final ModConfigSpec SPEC = BUILDER.build();
}