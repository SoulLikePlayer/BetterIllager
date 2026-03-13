package net.betterillager.world.entity.monster.illager;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.illager.AbstractIllager;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class FrozenVindicator extends AbstractIllager {
    protected FrozenVindicator(EntityType<? extends @NotNull FrozenVindicator> EntityType, Level Level) {
        super(EntityType, Level);
    }

    public void applyRaidBuffs(ServerLevel serverLevel, int i, boolean b) {

    }

    public SoundEvent getCelebrateSound() {
        return null;
    }
}
