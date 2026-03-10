package net.betterillager.world.entity.projectile;

import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class IceBlockProjectile extends Entity {
    private int hoverTime = 35;
    private final LivingEntity target;
    private static final double EYE_HEIGHT_OFFSET = 0.4;

    private boolean spawnAnimationStarted = false;
    private int spawnAnimationTime = 0;

    public IceBlockProjectile(EntityType<?> entityType, Level level, @Nullable LivingEntity target) {
        super(entityType, level);
        this.target = target;
    }

    public IceBlockProjectile(EntityType<?> entityType, Level level) {
        this(entityType, level, null);
    }

    public void tick() {
        super.tick();

        if (!spawnAnimationStarted) {
            spawnAnimationStarted = true;
            spawnAnimationTime = 10;
        }

        if (spawnAnimationTime > 0) {
            spawnAnimationTime--;
        }

        if (level().isClientSide()) {
            for (int i = 0; i < 3; i++) {
                double offsetX = (random.nextDouble() - 0.5) * 0.5;
                double offsetZ = (random.nextDouble() - 0.5) * 0.5;

                level().addParticle(
                        ParticleTypes.SNOWFLAKE,
                        getX() + offsetX,
                        getY() + EYE_HEIGHT_OFFSET + 0.3,
                        getZ() + offsetZ,
                        0, 0, 0
                );
            }

            if (tickCount % 5 == 0) {
                level().addParticle(
                        ParticleTypes.END_ROD,
                        getX(),
                        getY() + EYE_HEIGHT_OFFSET + 0.3,
                        getZ(),
                        (random.nextDouble() - 0.5) * 0.1,
                        0.05,
                        (random.nextDouble() - 0.5) * 0.1
                );
            }
        }

        hoverTime--;

        if (hoverTime > 0) {
            if (target != null && target.isAlive()) {
                double dx = target.getX() - getX();
                double dz = target.getZ() - getZ();
                double distance = Math.sqrt(dx * dx + dz * dz);

                if (distance > 0.2) {
                    double accel = 0.05;
                    double vx = getDeltaMovement().x + (dx / distance) * accel;
                    double vz = getDeltaMovement().z + (dz / distance) * accel;

                    double maxSpeed = 0.25;
                    vx = Math.max(-maxSpeed, Math.min(maxSpeed, vx));
                    vz = Math.max(-maxSpeed, Math.min(maxSpeed, vz));

                    double vy = Math.sin(tickCount * 0.25) * 0.03;
                    setDeltaMovement(vx, vy, vz);
                }
            }

            if (level() instanceof ServerLevel serverLevel && tickCount % 2 == 0) {
                serverLevel.sendParticles(
                        ParticleTypes.SNOWFLAKE,
                        getX(), getY() + EYE_HEIGHT_OFFSET, getZ(),
                        2,
                        0.1, 0.1, 0.1,
                        0.02
                );
            }
        } else {
            setDeltaMovement(getDeltaMovement().x, -0.9, getDeltaMovement().z);
        }

        move(MoverType.SELF, getDeltaMovement());

        if (onGround()) {
            explode();
            discard();
        }
    }

    private void explode() {
        if (level().isClientSide()) return;

        ServerLevel serverLevel = (ServerLevel) level();

        double explosionY = getY() + 0.5;

        serverLevel.sendParticles(
                ParticleTypes.ITEM_SNOWBALL,
                getX(), explosionY, getZ(),
                30,
                0.5, 0.5, 0.5,
                0.2
        );

        serverLevel.sendParticles(
                ParticleTypes.SNOWFLAKE,
                getX(), explosionY, getZ(),
                50,
                0.8, 0.8, 0.8,
                0.3
        );

        serverLevel.sendParticles(
                new BlockParticleOption(ParticleTypes.BLOCK, Blocks.PACKED_ICE.defaultBlockState()),
                getX(), explosionY, getZ(),
                20,
                0.4, 0.4, 0.4,
                0.1
        );

        serverLevel.sendParticles(
                ParticleTypes.POOF,
                getX(), explosionY, getZ(),
                15,
                0.3, 0.3, 0.3,
                0.05
        );

        getNearbyEntities(LivingEntity.class, 2).forEach(entity -> {
            entity.hurt(damageSources().magic(), 6.0F);
            entity.setTicksFrozen(100);

            for (int i = 0; i < 8; i++) {
                serverLevel.sendParticles(
                        ParticleTypes.ITEM_SNOWBALL,
                        entity.getX(),
                        entity.getY() + entity.getBbHeight() * 0.5,
                        entity.getZ(),
                        1,
                        (random.nextDouble() - 0.5) * 0.5,
                        0.2,
                        (random.nextDouble() - 0.5) * 0.5,
                        0.05
                );
            }
        });
    }

    private <T extends LivingEntity> java.util.List<T> getNearbyEntities(Class<T> type, double range) {
        return level().getEntitiesOfClass(type, getBoundingBox().inflate(range));
    }

    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {}

    public boolean hurtServer(@NotNull ServerLevel serverLevel, @NotNull DamageSource damageSource, float v) {
        return false;
    }

    protected void readAdditionalSaveData(@NotNull ValueInput valueInput) {}

    protected void addAdditionalSaveData(@NotNull ValueOutput valueOutput) {}

    public float getSpawnAnimationProgress() {
        if (!spawnAnimationStarted) return 0f;
        return 1.0f - ((float) spawnAnimationTime / 10.0f);
    }

    public boolean isSpawnAnimationStarted() {
        return spawnAnimationStarted;
    }

    public int getSpawnAnimationTime() {
        return spawnAnimationTime;
    }
}