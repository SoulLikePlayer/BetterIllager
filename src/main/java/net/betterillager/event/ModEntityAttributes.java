package net.betterillager.event;

import net.betterillager.core.BetterIllager;
import net.betterillager.world.entity.ModEntityType;
import net.betterillager.world.entity.monster.illager.Iceologer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = BetterIllager.MODID)
public class ModEntityAttributes {

    @SubscribeEvent
    public static void createDefaultAttributes(EntityAttributeCreationEvent event){
        event.put(
                ModEntityType.ICEOLOGER.get(),
                Iceologer.createAttributes()
                        .build()
        );
    }
}
