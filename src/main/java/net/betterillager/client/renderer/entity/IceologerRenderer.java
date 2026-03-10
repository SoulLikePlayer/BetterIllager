package net.betterillager.client.renderer.entity;

import net.betterillager.core.BetterIllager;
import net.betterillager.world.entity.monster.illager.Iceologer;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.monster.illager.IllagerModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.state.IllagerRenderState;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public class IceologerRenderer extends IllagerRenderer<@NotNull Iceologer, @NotNull IllagerRenderState> {
    private static final Identifier ICEOLOGER = Identifier.fromNamespaceAndPath(BetterIllager.MODID, "textures/entity/illager/iceologer.png");

    public IceologerRenderer(EntityRendererProvider.Context context) {
        super(context, new IllagerModel<>(context.bakeLayer(ModelLayers.PILLAGER)), 0.5F);
    }

    public IllagerRenderState createRenderState() {
        return new IllagerRenderState();
    }

    public @NotNull Identifier getTextureLocation(IllagerRenderState illagerRenderState) {
        return ICEOLOGER;
    }
}
