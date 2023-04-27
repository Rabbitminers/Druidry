package com.rabbitminers.druidry.content.grove.golems.copper;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.rabbitminers.druidry.render.entity.model.FancyEntityModel;
import com.rabbitminers.druidry.render.entity.model.FancyEntityModelPart;
import org.jetbrains.annotations.NotNull;

public class CopperGolemModel extends FancyEntityModel<CopperGolemEntity> {
    public FancyEntityModelPart
            root,
            leftLeg, rightLeg,
            body,
            leftArm, leftHand, rightArm, rightHand,
            head, lightningRod;

    public CopperGolemModel() {
        super(32, 35);

        this.root = this.createPart(0, 0)
                .setRotationPoint(0, 0, 0)
                .addBox(0F, 0F, 0F, 0, 0, 0, 0.0F);
        this.leftLeg = this.createPart(22, 13)
                .setRotationPoint(0, 24f, 0)
                .addBox(0.5F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F);
        this.rightLeg = this.createPart(22, 13)
                .setRotationPoint(0, 24f,0)
                .addBox(-2.5F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F);
        this.body = this.createPart(9, 30)
                .setRotationPoint(0, 24, 0)
                .addBox(-3.0F, -5.0F, -1.5F, 6.0F, 2.0F, 3.0F)
                .setTextureOffset(0, 13)
                .addBox(-4.0f, -9.0f, -3.0f, 8.0f, 4.0f, 6.0f);
        this.leftArm = this.createPart(0, 23)
                .setRotationPoint(0, 0, 0)
                .addBox(4.0F, -10.0F, -1.5F, 3.0F, 7.0F, 3.0F);
        this.leftHand = this.createPart(12, 23)
                .setRotationPoint(0, 0, 0)
                .addBox(4.5F, -3.0F, -1.0F, 2.0F, 2.0F, 2.0F);
        this.rightArm = this.createPart(0, 23)
                .setRotationPoint(0, 0, 0)
                .addBox(-7.0F, -10.0F, -1.5F, 3.0F, 7.0F, 3.0F);
        this.rightHand = this.createPart(12, 23)
                .setRotationPoint(0, 0, 0)
                .addBox(-6.5F, -3.0F, -1.0F, 2.0F, 2.0F, 2.0F);
        this.head = this.createPart(0, 0)
                .setRotationPoint(0, 0, 0)
                .addBox(-4.0F, -14.0F, -4.0F, 8.0F, 5.0F, 8.0F)
                .setTextureOffset(0, 3)
                .addBox(-1.0F, -11.0F, -6.0F, 2.0F, 3.0F, 2.0F);
        this.lightningRod = this.createPart(23, 3)
                .setRotationPoint(0, 0, 0)
                .addBox(-1.0F, -17.0F, -1.0F, 2.0F, 3.0F, 2.0F)
                .setTextureOffset(16, 23)
                .addBox(-2.0F, -20.0F, -2.0F, 4.0F, 3.0F, 4.0F);

        this.root.addChildren(this.body, this.leftLeg, this.rightLeg);
        this.body.addChildren(this.rightArm, leftArm, head);
        this.leftArm.addChildren(this.leftHand);
        this.rightArm.addChildren(this.rightHand);
        this.head.addChildren(this.lightningRod);
    }

    @Override
    public void setupAnim(@NotNull CopperGolemEntity entity, float f, float g, float h, float i, float j) {

    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int i, int j,
                               float f, float g, float h, float k) {
        this.root.render(poseStack, vertexConsumer, i, j, f, g, h, k);
    }
}
