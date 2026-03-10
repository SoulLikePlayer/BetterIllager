package net.betterillager.world.entity;

import net.betterillager.core.BetterIllager;
import net.betterillager.world.entity.monster.illager.Iceologer;
import net.betterillager.world.entity.projectile.IceBlockProjectile;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ModEntityType {
    public static final DeferredRegister.Entities ENTITY_TYPES = DeferredRegister.createEntities(BetterIllager.MODID);

    public static final Supplier<EntityType<@NotNull Iceologer>> ICEOLOGER =
            ENTITY_TYPES.registerEntityType("iceologer", Iceologer::new, MobCategory.MONSTER);

    public static final Supplier<EntityType<@NotNull IceBlockProjectile>> ICE_BLOCK_PROJECTILE =
            ENTITY_TYPES.registerEntityType("ice_block_projectile", IceBlockProjectile::new, MobCategory.MISC);
}