package net.betterillager.world.item.tools;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.EvokerFangs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class EvokerScepterItem extends Item {
    public EvokerScepterItem(Properties properties) {
        super(properties);
    }

    public @NotNull InteractionResult use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        float rotation = (player.getYRot() + 90.0F) * ((float) Math.PI / 180F);

        level.playSound(
                null,
                player.blockPosition(),
                SoundEvents.EVOKER_CAST_SPELL,
                SoundSource.PLAYERS,
                1.0F,
                1.0F
        );

        for (int i = 0; i < 10; i++) {
            double distance = 1.25D * (i + 1);

            double x = player.getX() + Mth.cos(rotation) * distance;
            double z = player.getZ() + Mth.sin(rotation) * distance;

            spawnFangs(
                    level,
                    x,
                    z,
                    player.getY() - 1,
                    player.getY() + 1,
                    rotation,
                    i * 2,
                    player
            );
        }

        return InteractionResult.SUCCESS;
    }

    private void spawnFangs(Level level, double x, double z, double minY, double maxY, float rotation, int delay, LivingEntity caster) {

        BlockPos pos = BlockPos.containing(x, maxY, z);
        boolean foundGround = false;
        double yOffset = 0.0;

        while (pos.getY() >= Mth.floor(minY) - 1) {

            BlockPos below = pos.below();
            BlockState blockstate = level.getBlockState(below);

            if (blockstate.isFaceSturdy(level, below, Direction.UP)) {

                if (!level.isEmptyBlock(pos)) {
                    VoxelShape shape = level.getBlockState(pos).getCollisionShape(level, pos);

                    if (!shape.isEmpty()) {
                        yOffset = shape.max(Direction.Axis.Y);
                    }
                }

                foundGround = true;
                break;
            }

            pos = pos.below();
        }

        if (foundGround) {
            level.addFreshEntity(
                    new EvokerFangs(
                            level,
                            x,
                            pos.getY() + yOffset,
                            z,
                            rotation,
                            delay,
                            caster
                    )
            );
        }
    }
}