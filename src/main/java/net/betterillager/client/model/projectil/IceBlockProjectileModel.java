package net.betterillager.client.model.projectil;

import net.betterillager.core.BetterIllager;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public class IceBlockProjectileModel extends EntityModel<@NotNull EntityRenderState> {
    private final ModelPart base;
    private AnimationDefinition currentAnimation;
    private float animationTime;

    public static final ModelLayerLocation ICE_BLOCK_PROJECTIL_LAYER = new ModelLayerLocation(
            Identifier.fromNamespaceAndPath(BetterIllager.MODID, "ice_block_projectile"),
            "main"
    );

    public IceBlockProjectileModel(ModelPart root) {
        super(root);
        this.base = root.getChild("base");
    }

    public static LayerDefinition createBodyLayer(){
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition partDefinition = mesh.getRoot();

        PartDefinition base = partDefinition.addOrReplaceChild("base",
                CubeListBuilder.create().texOffs(14, 9).addBox(-8.0F, -4.0F, -1.0F, 9.0F, 4.0F, 9.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 0).addBox(-8.0F, -10.0F, -8.0F, 16.0F, 6.0F, 16.0F, new CubeDeformation(0.0F))
                        .texOffs(28, 36).addBox(1.0F, -14.0F, -8.0F, 7.0F, 4.0F, 7.0F, new CubeDeformation(0.0F))
                        .texOffs(26, 36).addBox(-8.0F, -12.0F, -8.0F, 9.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
                        .texOffs(26, 34).addBox(1.0F, -13.0F, -1.0F, 7.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F)
        );

        return LayerDefinition.create(mesh, 64, 32);
    }
}