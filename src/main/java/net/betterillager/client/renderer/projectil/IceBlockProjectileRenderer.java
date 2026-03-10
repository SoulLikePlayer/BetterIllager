package net.betterillager.client.renderer.projectil;

import com.mojang.blaze3d.vertex.PoseStack;
import net.betterillager.client.model.projectil.IceBlockProjectileModel;
import net.betterillager.client.renderer.state.IceBlockProjectileRenderState;
import net.betterillager.core.BetterIllager;
import net.betterillager.world.entity.projectile.IceBlockProjectile;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public class IceBlockProjectileRenderer extends EntityRenderer<@NotNull IceBlockProjectile, @NotNull IceBlockProjectileRenderState> {
    private final IceBlockProjectileModel model;
    private static final Identifier TEXTURE =
            Identifier.fromNamespaceAndPath(BetterIllager.MODID,"textures/entity/ice_block_projectile.png");

    public IceBlockProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new IceBlockProjectileModel(context.bakeLayer(IceBlockProjectileModel.ICE_BLOCK_PROJECTIL_LAYER));
    }

    public IceBlockProjectileRenderState createRenderState() {
        return new IceBlockProjectileRenderState();
    }

    public void extractRenderState(@NotNull IceBlockProjectile entity, @NotNull IceBlockProjectileRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);

        reusedState.spawnAnimationProgress = entity.getSpawnAnimationProgress();
        reusedState.ageInTicks = entity.tickCount;
        reusedState.spawnAnimationStarted = entity.isSpawnAnimationStarted(); // Ajoute cette ligne

        if (entity.isSpawnAnimationStarted() && !reusedState.spawnAnimation.isStarted()) {
            reusedState.spawnAnimation.start(entity.tickCount);
        }
    }

    public void submit(@NotNull IceBlockProjectileRenderState renderState, @NotNull PoseStack poseStack, @NotNull SubmitNodeCollector nodeCollector, @NotNull CameraRenderState cameraRenderState) {
        poseStack.pushPose();

        nodeCollector.submitModel(
                model,
                renderState,
                poseStack,
                RenderTypes.entityCutout(this.getTextureLocation(renderState)),
                renderState.lightCoords,
                OverlayTexture.NO_OVERLAY,
                renderState.outlineColor,
                null
        );

        poseStack.popPose();
        super.submit(renderState, poseStack, nodeCollector, cameraRenderState);
    }

    public @NotNull Identifier getTextureLocation(IceBlockProjectileRenderState entityRenderState) {
        return TEXTURE;
    }
}