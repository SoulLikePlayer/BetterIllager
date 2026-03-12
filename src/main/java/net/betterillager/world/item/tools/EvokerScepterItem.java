package net.betterillager.world.item.tools;

import net.betterillager.core.ModDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.EvokerFangs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class EvokerScepterItem extends Item {
    public EvokerScepterItem(Properties properties) {
        super(properties);
    }

    public @NotNull InteractionResult use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {

        ItemStack stack = player.getItemInHand(hand);

        if (player.isShiftKeyDown()) {

            EvokerScepterMode next = nextMode(getMode(stack));
            setMode(stack, next);

            if (!level.isClientSide()) {
                player.displayClientMessage(
                        Component.literal("Mode : " + next.getSerializedName()),
                        true
                );
            }

            return InteractionResult.SUCCESS;
        }

        switch (getMode(stack)) {
            case LINE -> castLine(level, player);
            case CIRCLE -> castCircle(level, player);
            case WAVE -> castWave(level, player);
            case STAR -> castStar(level, player);
            case SHOCKWAVE -> castShockwave(level, player);
            case CROSS -> castCross(level, player);
            case SNAKE -> castSnake(level, player);
            case SPIRAL -> castSpiral(level, player);
            case WALL -> castWall(level, player);
        }

        return InteractionResult.SUCCESS;
    }

    private void castLine(Level level, Player player) {

        float rotation = (player.getYRot() + 90.0F) * ((float) Math.PI / 180F);

        level.playSound(null, player.blockPosition(),
                SoundEvents.EVOKER_CAST_SPELL,
                SoundSource.PLAYERS,
                1.0F, 1.0F);

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
    }

    private void castCircle(Level level, Player player) {

        level.playSound(null, player.blockPosition(),
                SoundEvents.EVOKER_CAST_SPELL,
                SoundSource.PLAYERS,
                1.0F, 1.0F);

        for (int i = 0; i < 12; i++) {

            float angle = (float)(i * (2 * Math.PI / 12));

            double x = player.getX() + Mth.cos(angle) * 3;
            double z = player.getZ() + Mth.sin(angle) * 3;

            spawnFangs(
                    level,
                    x,
                    z,
                    player.getY() - 1,
                    player.getY() + 1,
                    angle,
                    0,
                    player
            );
        }
    }

    private void castWave(Level level, Player player) {

        float rotation = (player.getYRot() + 90.0F) * ((float)Math.PI / 180F);

        level.playSound(null, player.blockPosition(),
                SoundEvents.EVOKER_CAST_SPELL,
                SoundSource.PLAYERS,
                1.0F, 1.0F);

        for (int i = 0; i < 12; i++) {

            double forward = i * 1.2;
            double width = 0.5 + i * 0.4; // la vague s'élargit

            for (int j = -2; j <= 2; j++) {

                double side = j * width + Math.sin(i * 0.8) * 1.2;

                double x =
                        player.getX()
                                + Mth.cos(rotation) * forward
                                + Mth.cos(rotation + Math.PI / 2) * side;

                double z =
                        player.getZ()
                                + Mth.sin(rotation) * forward
                                + Mth.sin(rotation + Math.PI / 2) * side;

                spawnFangs(
                        level,
                        x,
                        z,
                        player.getY() - 1,
                        player.getY() + 1,
                        rotation,
                        i + Math.abs(j),
                        player
                );
            }
        }
    }

    private void castStar(Level level, Player player) {

        level.playSound(null, player.blockPosition(),
                SoundEvents.EVOKER_CAST_SPELL,
                SoundSource.PLAYERS,
                1.0F, 1.0F);

        int rays = 5;

        for (int r = 0; r < rays; r++) {

            float angle = (float)(r * (2 * Math.PI / rays));

            for (int i = 1; i <= 6; i++) {

                double distance = i * 1.2;

                double x = player.getX() + Mth.cos(angle) * distance;
                double z = player.getZ() + Mth.sin(angle) * distance;

                spawnFangs(
                        level,
                        x,
                        z,
                        player.getY() - 1,
                        player.getY() + 1,
                        angle,
                        i,
                        player
                );
            }
        }
    }

    private void castShockwave(Level level, Player player) {

        level.playSound(null, player.blockPosition(),
                SoundEvents.EVOKER_CAST_SPELL,
                SoundSource.PLAYERS,
                1.0F, 1.0F);

        int rings = 3;

        for (int r = 1; r <= rings; r++) {

            int points = 10 + r * 4;
            double radius = r * 2.0;

            for (int i = 0; i < points; i++) {

                float angle = (float)(i * (2 * Math.PI / points));

                double x = player.getX() + Mth.cos(angle) * radius;
                double z = player.getZ() + Mth.sin(angle) * radius;

                spawnFangs(
                        level,
                        x,
                        z,
                        player.getY() - 1,
                        player.getY() + 1,
                        angle,
                        r * 3,
                        player
                );
            }
        }
    }

    private void castCross(Level level, Player player) {

        float rotation = (player.getYRot() + 90.0F) * ((float)Math.PI / 180F);

        level.playSound(null, player.blockPosition(),
                SoundEvents.EVOKER_CAST_SPELL,
                SoundSource.PLAYERS,
                1.0F, 1.0F);

        for (int i = -5; i <= 5; i++) {

            double forward = i * 1.2;

            double x1 = player.getX() + Mth.cos(rotation) * forward;
            double z1 = player.getZ() + Mth.sin(rotation) * forward;

            spawnFangs(
                    level,
                    x1,
                    z1,
                    player.getY() - 1,
                    player.getY() + 1,
                    rotation,
                    Math.abs(i),
                    player
            );

            double x2 = player.getX() + Mth.cos(rotation + Math.PI / 2) * forward;
            double z2 = player.getZ() + Mth.sin(rotation + Math.PI / 2) * forward;

            spawnFangs(
                    level,
                    x2,
                    z2,
                    player.getY() - 1,
                    player.getY() + 1,
                    rotation,
                    Math.abs(i),
                    player
            );
        }
    }

    private void castSnake(Level level, Player player) {

        float rotation = (player.getYRot() + 90.0F) * ((float)Math.PI / 180F);

        level.playSound(null, player.blockPosition(),
                SoundEvents.EVOKER_CAST_SPELL,
                SoundSource.PLAYERS,
                1.0F, 1.0F);

        for (int i = 0; i < 20; i++) {

            double forward = i * 1.0;
            double side = Math.sin(i * 1.5) * 3.0;

            double x =
                    player.getX()
                            + Mth.cos(rotation) * forward
                            + Mth.cos(rotation + Math.PI / 2) * side;

            double z =
                    player.getZ()
                            + Mth.sin(rotation) * forward
                            + Mth.sin(rotation + Math.PI / 2) * side;

            spawnFangs(
                    level,
                    x,
                    z,
                    player.getY() - 1,
                    player.getY() + 1,
                    rotation,
                    i,
                    player
            );
        }
    }

    private void castSpiral(Level level, Player player) {

        level.playSound(null, player.blockPosition(),
                SoundEvents.EVOKER_CAST_SPELL,
                SoundSource.PLAYERS,
                1.0F, 1.0F);

        int points = 24;

        for (int i = 0; i < points; i++) {

            float angle = (float)(i * 0.5);
            double radius = 0.5 + i * 0.25;

            double x = player.getX() + Mth.cos(angle) * radius;
            double z = player.getZ() + Mth.sin(angle) * radius;

            spawnFangs(
                    level,
                    x,
                    z,
                    player.getY() - 1,
                    player.getY() + 1,
                    angle,
                    i,
                    player
            );
        }
    }

    private void castWall(Level level, Player player) {

        float rotation = (player.getYRot() + 90.0F) * ((float)Math.PI / 180F);

        level.playSound(null, player.blockPosition(),
                SoundEvents.EVOKER_CAST_SPELL,
                SoundSource.PLAYERS,
                1.0F, 1.0F);

        for (int i = -6; i <= 6; i++) {

            double side = i * 0.8;

            double x =
                    player.getX()
                            + Mth.cos(rotation + Math.PI / 2) * side
                            + Mth.cos(rotation) * 3;

            double z =
                    player.getZ()
                            + Mth.sin(rotation + Math.PI / 2) * side
                            + Mth.sin(rotation) * 3;

            spawnFangs(
                    level,
                    x,
                    z,
                    player.getY() - 1,
                    player.getY() + 1,
                    rotation,
                    Math.abs(i),
                    player
            );
        }
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

    private EvokerScepterMode getMode(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.EVOKER_SCEPTER_MODE.get(), EvokerScepterMode.LINE);
    }

    private void setMode(ItemStack stack, EvokerScepterMode mode) {
        stack.set(ModDataComponents.EVOKER_SCEPTER_MODE.get(), mode);
    }

    private EvokerScepterMode nextMode(EvokerScepterMode mode) {
        EvokerScepterMode[] values = EvokerScepterMode.values();
        return values[(mode.ordinal() + 1) % values.length];
    }

    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull TooltipDisplay tooltipDisplay, @NotNull Consumer<Component> tooltipAdder, @NotNull TooltipFlag flag) {
        EvokerScepterMode mode = getMode(stack);

        tooltipAdder.accept(Component.literal("Mode: "+ mode.getSerializedName()));
        tooltipAdder.accept(Component.empty());
        tooltipAdder.accept(Component.literal("When Used :"));
        tooltipAdder.accept(Component.literal(" Cast Evoker Fang Spell"));
    }
}