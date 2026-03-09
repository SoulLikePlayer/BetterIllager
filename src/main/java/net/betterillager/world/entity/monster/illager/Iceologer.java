package net.betterillager.world.entity.monster.illager;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.illager.AbstractIllager;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class Iceologer extends AbstractIllager {
    public Iceologer(EntityType<? extends @NotNull Iceologer> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, (double)0.35F)
                .add(Attributes.MAX_HEALTH, (double)24.0F)
                .add(Attributes.ATTACK_DAMAGE, (double)5.0F)
                .add(Attributes.FOLLOW_RANGE, (double)32.0F);
    }

    public void applyRaidBuffs(@NotNull ServerLevel serverLevel, int i, boolean b) {}

    public @NotNull SoundEvent getCelebrateSound() {
        return SoundEvents.PILLAGER_CELEBRATE;
    }
}