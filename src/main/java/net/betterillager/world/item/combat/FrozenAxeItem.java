package net.betterillager.world.item.combat;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import org.jetbrains.annotations.NotNull;

public class FrozenAxeItem extends AxeItem {
    private final int amplifier;
    private final int duration;

    public FrozenAxeItem(ToolMaterial material, float attackDamage, float attackSpeed, Properties properties, int amplifier) {
        super(material, attackDamage, attackSpeed, properties);
        this.amplifier = amplifier;
        this.duration = 100 + (amplifier * 20);
    }

    public void hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        super.hurtEnemy(stack, target, attacker);

        target.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, duration, this.amplifier));

        switch (amplifier){
            case 1:
                target.addEffect(new MobEffectInstance(MobEffects.MINING_FATIGUE, duration, this.amplifier));
                break;
            case 2:
                target.addEffect(new MobEffectInstance(MobEffects.MINING_FATIGUE, duration, this.amplifier));
                target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, duration, this.amplifier));
                break;
            case 3:
                target.addEffect(new MobEffectInstance(MobEffects.MINING_FATIGUE, duration, this.amplifier));
                target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, duration, this.amplifier));
                target.setTicksFrozen(duration);
                break;
        }
    }
}
