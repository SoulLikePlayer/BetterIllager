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

    public FrozenAxeItem(ToolMaterial material, float attackDamage, float attackSpeed, Properties properties, int amplifier) {
        super(material, attackDamage, attackSpeed, properties);
        this.amplifier = amplifier;
    }

    public void hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        super.hurtEnemy(stack, target, attacker);

        target.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 100, this.amplifier));
    }
}
