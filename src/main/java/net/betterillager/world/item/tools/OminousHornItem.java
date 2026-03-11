package net.betterillager.world.item.tools;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raids;
import net.minecraft.world.item.InstrumentItem;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class OminousHornItem extends InstrumentItem {
    public OminousHornItem(Properties properties) {
        super(properties);
    }

    public @NotNull InteractionResult use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        InteractionResult interaction = super.use(level, player, hand);

        if(!level.isClientSide() && level instanceof ServerLevel serverLevel && player instanceof ServerPlayer serverPlayer){
            createSpiralRaidOmenParticle(serverPlayer, serverLevel);

            BlockPos pos = player.blockPosition();
            Raids raids = serverLevel.getRaids();

            if (serverLevel.isVillage(pos)){
                Raid raid = raids.createOrExtendRaid(serverPlayer, pos);

                if (raid != null){
                    raid.absorbRaidOmen(serverPlayer);
                }
            }
        }

        player.getCooldowns().addCooldown(this.getDefaultInstance(), 20 * 60);
        return interaction;
    }

    private void createSpiralRaidOmenParticle(ServerPlayer player, ServerLevel level){
        for (int i = 0; i < 60; i++) {
            double angle = i * 0.3;
            double radius = 1.5;

            double x = player.getX() + Math.cos(angle) * radius;
            double z = player.getZ() + Math.sin(angle) * radius;
            double y = player.getY() + (i * 0.05);

            level.sendParticles(
                    ParticleTypes.TRIAL_OMEN,
                    x, y, z,
                    1,
                    0, 0, 0,
                    0
            );
        }
    }
}
