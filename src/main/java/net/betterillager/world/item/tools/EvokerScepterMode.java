package net.betterillager.world.item.tools;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum EvokerScepterMode implements StringRepresentable {
    LINE("line"),
    CIRCLE("circle"),
    WAVE("wave"),
    SNAKE("snake"),
    CROSS("cross"),
    STAR("star"),
    SHOCKWAVE("shockwave"),
    SPIRAL("spiral"),
    WALL("wall");

    public String name;

    EvokerScepterMode(String name){
        this.name = name;
    }

    public @NotNull String getSerializedName() {
        return name;
    }

    public static final Codec<EvokerScepterMode> CODEC =
            Codec.STRING.xmap(EvokerScepterMode::valueOf, EvokerScepterMode::name);

    public static final StreamCodec<@NotNull ByteBuf, @NotNull EvokerScepterMode> STREAM_CODEC =
            ByteBufCodecs.STRING_UTF8.map(EvokerScepterMode::valueOf, EvokerScepterMode::name);
}