package net.betterillager.client.renderer.projectil;

import com.mojang.blaze3d.vertex.PoseStack;
import net.betterillager.client.model.projectil.IceBlockProjectileModel;
import net.betterillager.world.entity.projectile.IceBlockProjectile;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public class IceBlockProjectileRenderer extends EntityRenderer<@NotNull IceBlockProjectile, @NotNull EntityRenderState> {
    private final IceBlockProjectileModel model;
    private static final Identifier TEXTURE =
            Identifier.fromNamespaceAndPath("betterillager","textures/entity/ice_block_projectile.png");

    public IceBlockProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new IceBlockProjectileModel(context.bakeLayer(IceBlockProjectileModel.ICE_BLOCK_PROJECTIL_LAYER));
    }

    public EntityRenderState createRenderState() {
        return new EntityRenderState();
    }

    public void extractRenderState(@NotNull IceBlockProjectile entity, @NotNull EntityRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
    }

    public void submit(@NotNull EntityRenderState renderState, @NotNull PoseStack poseStack, @NotNull SubmitNodeCollector nodeCollector, @NotNull CameraRenderState cameraRenderState) {
        poseStack.pushPose();

        nodeCollector.submitModel(
                model,
                renderState,
                poseStack,
                model.renderType(getTextureLocation(renderState)),
                15728880,
                0,
                -1,
                null
        );

        poseStack.popPose();
    }

    public @NotNull Identifier getTextureLocation(EntityRenderState entityRenderState) {
        return TEXTURE;
    }
}
