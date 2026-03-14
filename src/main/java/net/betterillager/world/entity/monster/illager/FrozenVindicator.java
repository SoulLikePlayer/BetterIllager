package net.betterillager.world.entity.monster.illager;

import net.betterillager.world.item.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.entity.animal.golem.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.creaking.Creaking;
import net.minecraft.world.entity.monster.illager.AbstractIllager;
import net.minecraft.world.entity.npc.villager.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.providers.EnchantmentProvider;
import net.minecraft.world.item.enchantment.providers.VanillaEnchantmentProviders;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

import java.util.EnumSet;
import java.util.function.Predicate;

public class FrozenVindicator extends AbstractIllager {
    static final Predicate<Difficulty> DOOR_BREAKING_PREDICATE = (p_480948_) -> p_480948_ == Difficulty.NORMAL || p_480948_ == Difficulty.HARD;

    public FrozenVindicator(EntityType<? extends @NotNull FrozenVindicator> EntityType, Level Level) {
        super(EntityType, Level);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Creaking.class, 8.0F, (double)1.0F, 1.2));
        this.goalSelector.addGoal(2, new FrozenVindicator.VindicatorBreakDoorGoal(this));
        this.goalSelector.addGoal(3, new AbstractIllager.RaiderOpenDoorGoal(this));
        this.goalSelector.addGoal(4, new Raider.HoldGroundAttackGoal(this, 10.0F));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, (double)1.0F, false));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, new Class[]{Raider.class})).setAlertOthers(new Class[0]));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
    }

    protected void customServerAiStep(@NotNull ServerLevel serverLevel) {
        if (!this.isNoAi() && GoalUtils.hasGroundPathNavigation(this)) {
            boolean flag = serverLevel.isRaided(this.blockPosition());
            this.getNavigation().setCanOpenDoors(flag);
        }

        super.customServerAiStep(serverLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, (double)0.35F).add(Attributes.FOLLOW_RANGE, (double)12.0F).add(Attributes.MAX_HEALTH, (double)24.0F).add(Attributes.ATTACK_DAMAGE, (double)5.0F);
    }

    protected void addAdditionalSaveData(@NotNull ValueOutput valueOutput) {
        super.addAdditionalSaveData(valueOutput);
    }

    public AbstractIllager.@NotNull IllagerArmPose getArmPose() {
        if (this.isAggressive()) {
            return IllagerArmPose.ATTACKING;
        } else {
            return this.isCelebrating() ? IllagerArmPose.CELEBRATING : IllagerArmPose.CROSSED;
        }
    }

    protected void readAdditionalSaveData(@NotNull ValueInput p_480481_) {
        super.readAdditionalSaveData(p_480481_);
    }

    public @NotNull SoundEvent getCelebrateSound() {
        return SoundEvents.VINDICATOR_CELEBRATE;
    }

    public @Nullable SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor serverLevelAccessor, @NotNull DifficultyInstance difficultyInstance, @NotNull EntitySpawnReason entitySpawnReason, @Nullable SpawnGroupData spawnGroupData) {
        SpawnGroupData spawngroupdata = super.finalizeSpawn(serverLevelAccessor, difficultyInstance, entitySpawnReason, spawnGroupData);
        this.getNavigation().setCanOpenDoors(true);
        RandomSource randomsource = serverLevelAccessor.getRandom();
        this.populateDefaultEquipmentSlots(randomsource, difficultyInstance);
        this.populateDefaultEquipmentEnchantments(serverLevelAccessor, randomsource, difficultyInstance);
        return spawngroupdata;
    }

    protected void populateDefaultEquipmentSlots(@NotNull RandomSource randomSource, @NotNull DifficultyInstance difficultyInstance) {
        if (this.getCurrentRaid() == null) {
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ModItems.FROZEN_IRON_AXE.get()));
        }

    }

    public void setCustomName(@Nullable Component p_482175_) {
        super.setCustomName(p_482175_);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.VINDICATOR_AMBIENT;
    }

    protected @NotNull SoundEvent getDeathSound() {
        return SoundEvents.VINDICATOR_DEATH;
    }

    protected @NotNull SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return SoundEvents.VINDICATOR_HURT;
    }

    public void applyRaidBuffs(@NotNull ServerLevel serverLevel, int p_481618_, boolean p_480070_) {
        ItemStack itemstack = new ItemStack(ModItems.FROZEN_IRON_AXE.get());
        Raid raid = this.getCurrentRaid();
        boolean flag = this.random.nextFloat() <= raid.getEnchantOdds();
        if (flag) {
            ResourceKey<@NotNull EnchantmentProvider> resourcekey = p_481618_ > raid.getNumGroups(Difficulty.NORMAL) ? VanillaEnchantmentProviders.RAID_VINDICATOR_POST_WAVE_5 : VanillaEnchantmentProviders.RAID_VINDICATOR;
            EnchantmentHelper.enchantItemFromProvider(itemstack, serverLevel.registryAccess(), resourcekey, serverLevel.getCurrentDifficultyAt(this.blockPosition()), this.random);
        }

        this.setItemSlot(EquipmentSlot.MAINHAND, itemstack);
    }

    static class VindicatorBreakDoorGoal extends BreakDoorGoal {
        public VindicatorBreakDoorGoal(Mob p_479318_) {
            super(p_479318_, 6, FrozenVindicator.DOOR_BREAKING_PREDICATE);
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        public boolean canContinueToUse() {
            FrozenVindicator vindicator = (FrozenVindicator)this.mob;
            return vindicator.hasActiveRaid() && super.canContinueToUse();
        }

        public boolean canUse() {
            FrozenVindicator vindicator = (FrozenVindicator)this.mob;
            return vindicator.hasActiveRaid() && vindicator.random.nextInt(reducedTickDelay(10)) == 0 && super.canUse();
        }

        public void start() {
            super.start();
            this.mob.setNoActionTime(0);
        }
    }
}