package net.betterillager.world.item.tools;

import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.EvokerFangs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class EvokerScepterItem extends Item {
    public EvokerScepterItem(Properties properties) {
        super(properties);
    }

    public @NotNull InteractionResult use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        float rotation = (player.getYRot() + 90.0F) * ((float) Math.PI / 180F);

        for (int i = 0; i < 10; i++) {
            double distance = 1.25D * (i + 1);

            double x = player.getX() + Mth.cos(rotation) * distance;
            double z = player.getZ() + Mth.sin(rotation) * distance;

            double y = player.getY();

            EvokerFangs fangs = new EvokerFangs(
                    level,
                    x,
                    y,
                    z,
                    rotation,
                    i * 2,
                    player
            );

            level.addFreshEntity(fangs);
        }

        return InteractionResult.SUCCESS;
    }
}