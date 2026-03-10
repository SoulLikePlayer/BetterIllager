package net.betterillager.world.entity.projectile;

import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class IceBlockProjectile extends Entity {
    private int hoverTime = 35;
    private final double hoverHeight;
    private final LivingEntity target;

    public IceBlockProjectile(EntityType<?> entityType, Level level, @Nullable LivingEntity target, double y) {
        super(entityType, level);
        this.target = target;
        hoverHeight = y;
    }

    public IceBlockProjectile(EntityType<?> entityType, Level level) {
        this(entityType, level, null, 8);
    }

    public void tick() {
        super.tick();

        if(hoverTime > 0){
            hoverTime--;

            if(target != null){
                double dx = target.getX() - getX();
                double dz = target.getZ() - getZ();
                double distance = Math.sqrt(dx*dx + dz*dz);
                double speed = 0.3;

                double vx = dx / distance * speed;
                double vz = dz / distance * speed;

                setDeltaMovement(vx, 0, vz);
                setPos(getX() + vx, hoverHeight, getZ() + vz);
            } else {
                setDeltaMovement(0, 0, 0);
            }
        } else {
            setDeltaMovement(0, -0.6, 0);
            move(MoverType.SELF, getDeltaMovement());
        }

        move(MoverType.SELF, getDeltaMovement());

        if(onGround()){
            explode();
            discard();
        }
    }

    private void explode() {
        if(level().isClientSide()) return;

        getNearbyEntities(LivingEntity.class, 2).forEach(entity -> {
            entity.hurt(damageSources().magic(), 6.0F);
            entity.setTicksFrozen(100);
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
}
