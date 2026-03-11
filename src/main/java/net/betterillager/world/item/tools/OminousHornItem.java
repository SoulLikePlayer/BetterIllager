package net.betterillager.world.item.tools;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raids;
import net.minecraft.world.item.InstrumentItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class OminousHornItem extends InstrumentItem {
    public OminousHornItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, Level level, @NotNull LivingEntity entity) {

        if (!level.isClientSide() && entity instanceof ServerPlayer player && level instanceof ServerLevel serverLevel) {

            BlockPos pos = player.blockPosition();
            Raids raids = serverLevel.getRaids();

            if (serverLevel.isVillage(pos)) {
                Raid raid = raids.createOrExtendRaid(player, pos);

                if (raid != null) {
                    raid.absorbRaidOmen(player);
                }
            }
        }

        return super.finishUsingItem(stack, level, entity);
    }
}
