package net.betterillager.client.renderer.entity;

import net.betterillager.core.BetterIllager;
import net.betterillager.world.entity.monster.illager.Iceologer;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.monster.illager.IllagerModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.PillagerRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.state.IllagerRenderState;
import net.minecraft.resources.Identifier;

public class IceologerRenderer extends IllagerRenderer<Iceologer, IllagerRenderState> {
    private static final Identifier ICEOLOGER = Identifier.fromNamespaceAndPath(BetterIllager.MODID, "textures/entity/illager/iceologer.png");

    public IceologerRenderer(EntityRendererProvider.Context context) {
        super(context, new IllagerModel(context.bakeLayer(ModelLayers.PILLAGER)), 0.5F);
        this.addLayer(new ItemInHandLayer(this));
    }

    public IllagerRenderState createRenderState() {
        return new IllagerRenderState();
    }

    public Identifier getTextureLocation(IllagerRenderState illagerRenderState) {
        return null;
    }
}
