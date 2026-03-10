package net.betterillager.client.renderer.state;

import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.entity.AnimationState;

public class IceBlockProjectileRenderState extends EntityRenderState {
    public final AnimationState spawnAnimation = new AnimationState();
    public float spawnAnimationProgress = 0.0f;
    public int ageInTicks = 0;
    public boolean spawnAnimationStarted = false;
}