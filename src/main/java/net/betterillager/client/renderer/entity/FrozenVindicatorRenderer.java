package net.betterillager.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.betterillager.core.BetterIllager;
import net.betterillager.world.entity.monster.illager.FrozenVindicator;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.monster.illager.IllagerModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.state.IllagerRenderState;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public class FrozenVindicatorRenderer extends IllagerRenderer<@NotNull FrozenVindicator, @NotNull IllagerRenderState> {
    private static final Identifier FROZEN_VINDICATOR = Identifier.fromNamespaceAndPath(BetterIllager.MODID, "textures/entity/illager/frozen_vindicator.png");

    public FrozenVindicatorRenderer(EntityRendererProvider.Context p_174439_) {
        super(p_174439_, new IllagerModel<>(p_174439_.bakeLayer(ModelLayers.VINDICATOR)), 0.5F);
        this.addLayer(new ItemInHandLayer<>(this) {
            public void submit(@NotNull PoseStack p_434569_, @NotNull SubmitNodeCollector p_434409_, int p_433214_, IllagerRenderState p_433367_, float p_435149_, float p_433947_) {
                if (p_433367_.isAggressive) {
                    super.submit(p_434569_, p_434409_, p_433214_, p_433367_, p_435149_, p_433947_);
                }

            }
        });
    }

    public Identifier getTextureLocation(IllagerRenderState p_364813_) {
        return FROZEN_VINDICATOR;
    }

    public IllagerRenderState createRenderState() {
        return new IllagerRenderState();
    }
}
