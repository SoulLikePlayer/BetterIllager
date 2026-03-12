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
import net.minecraft.world.entity.Entity;
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

        for (int i = 0; i < 16; i++) {

            double forward = i * 0.8;
            double side = Math.sin(i * 0.8) * 1.5;

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
    }
}