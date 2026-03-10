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

import java.util.List;

public class IceBlockProjectile extends Entity {
    private int hoverTime = 35;

    public IceBlockProjectile(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    public void tick() {
        super.tick();

        if(hoverTime > 0){
            hoverTime--;
            setDeltaMovement(0, 0, 0);
        } else {
            setDeltaMovement(0, -0.6, 0);
        }

        move(MoverType.SELF, getDeltaMovement());

        if(onGround()){
            explode();
            discard();
        }
    }

    private void explode() {
        List<LivingEntity> entities = level().getEntitiesOfClass(
                LivingEntity.class,
                getBoundingBox().inflate(2)
        );

        for (LivingEntity entity : entities) {
            entity.hurt(damageSources().magic(), 6.0F);
            entity.setTicksFrozen(100);
        }
    }

    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {

    }

    public boolean hurtServer(@NotNull ServerLevel serverLevel, @NotNull DamageSource damageSource, float v) {
        return false;
    }

    protected void readAdditionalSaveData(@NotNull ValueInput valueInput) {

    }

    protected void addAdditionalSaveData(@NotNull ValueOutput valueOutput) {

    }
}
