package com.rabbitminers.druidry.render.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.minecraft.core.Direction;

import java.util.Random;

public class FancyEntityModelPart {
    public float textureWidth, textureHeight;
    public int textureOffsetX, textureOffsetY;
    public float rotationPointX, rotationPointY, rotationPointZ;
    public float rotateAngleX, rotateAngleY, rotateAngleZ;
    public boolean mirror;
    public boolean showModel;
    public final ObjectList<ModelBox> cubeList;
    public final ObjectList<FancyEntityModelPart> childModels;

    public FancyEntityModelPart(FancyEntityModel model) {
        this.textureWidth = 64.0F;
        this.textureHeight = 32.0F;
        this.showModel = true;
        this.cubeList = new ObjectArrayList<>();
        this.childModels = new ObjectArrayList<>();
        model.accept(this);
        this.setTextureSize(model.textureWidth, model.textureHeight);
    }

    public FancyEntityModelPart(FancyEntityModel<?> model, int p_i46358_2_, int p_i46358_3_) {
        this(model.textureWidth, model.textureHeight, p_i46358_2_, p_i46358_3_);
        model.accept(this);
    }

    public FancyEntityModelPart(int p_i225949_1_, int p_i225949_2_, int p_i225949_3_, int p_i225949_4_) {
        this.textureWidth = 64.0F;
        this.textureHeight = 32.0F;
        this.showModel = true;
        this.cubeList = new ObjectArrayList<>();
        this.childModels = new ObjectArrayList<>();
        this.setTextureSize(p_i225949_1_, p_i225949_2_);
        this.setTextureOffset(p_i225949_3_, p_i225949_4_);
    }

    private FancyEntityModelPart() {
        this.textureWidth = 64.0F;
        this.textureHeight = 32.0F;
        this.showModel = true;
        this.cubeList = new ObjectArrayList<>();
        this.childModels = new ObjectArrayList<>();
    }

    public FancyEntityModelPart getModelAngleCopy() {
        FancyEntityModelPart renderer = new FancyEntityModelPart();
        renderer.cloneModelAngles(this);
        return renderer;
    }

    public void cloneModelAngles(FancyEntityModelPart funkyEntityModelRenderer) {
        this.rotateAngleX = funkyEntityModelRenderer.rotateAngleX;
        this.rotateAngleY = funkyEntityModelRenderer.rotateAngleY;
        this.rotateAngleZ = funkyEntityModelRenderer.rotateAngleZ;
        this.rotationPointX = funkyEntityModelRenderer.rotationPointX;
        this.rotationPointY = funkyEntityModelRenderer.rotationPointY;
        this.rotationPointZ = funkyEntityModelRenderer.rotationPointZ;
    }

    public void appendChild(FancyEntityModelPart p_78792_1_) {
        this.childModels.add(p_78792_1_);
    }

    public FancyEntityModelPart setTextureOffset(int offX, int offY) {
        this.textureOffsetX = offX;
        this.textureOffsetY = offY;
        return this;
    }

    public FancyEntityModelPart addBox(String partName, float x, float y, float z, int width, int height, int depth, float delta, int texX, int texY) {
        this.setTextureOffset(texX, texY);
        this.addBox(this.textureOffsetX, this.textureOffsetY, x, y, z, (float)width, (float)height, (float)depth, delta, delta, delta, this.mirror, false);
        return this;
    }

    public FancyEntityModelPart addBox(float x, float y, float z, float width, float height, float depth) {
        this.addBox(this.textureOffsetX, this.textureOffsetY, x, y, z, width, height, depth, 0.0F, 0.0F, 0.0F, this.mirror, false);
        return this;
    }

    public FancyEntityModelPart addBox(float x, float y, float z, float width, float height, float depth, boolean mirrorIn) {
        this.addBox(this.textureOffsetX, this.textureOffsetY, x, y, z, width, height, depth, 0.0F, 0.0F, 0.0F, mirrorIn, false);
        return this;
    }

    public void addBox(float x, float y, float z, float width, float height, float depth, float delta) {
        this.addBox(this.textureOffsetX, this.textureOffsetY, x, y, z, width, height, depth, delta, delta, delta, this.mirror, false);
    }

    public void addBox(float x, float y, float z, float width, float height, float depth, float deltaX, float deltaY, float deltaZ) {
        this.addBox(this.textureOffsetX, this.textureOffsetY, x, y, z, width, height, depth, deltaX, deltaY, deltaZ, this.mirror, false);
    }

    public void addBox(float x, float y, float z, float width, float height, float depth, float delta, boolean mirrorIn) {
        this.addBox(this.textureOffsetX, this.textureOffsetY, x, y, z, width, height, depth, delta, delta, delta, mirrorIn, false);
    }

    private void addBox(int texOffX, int p_228305_2_, float p_228305_3_, float p_228305_4_, float p_228305_5_, float p_228305_6_, float p_228305_7_, float p_228305_8_, float p_228305_9_, float p_228305_10_, float p_228305_11_, boolean p_228305_12_, boolean p_228305_13_) {
        this.cubeList.add(new FancyEntityModelPart.ModelBox(texOffX, p_228305_2_, p_228305_3_, p_228305_4_, p_228305_5_, p_228305_6_, p_228305_7_, p_228305_8_, p_228305_9_, p_228305_10_, p_228305_11_, p_228305_12_, this.textureWidth, this.textureHeight));
    }

    public void setRotationPoint(float x, float y, float z) {
        this.rotationPointX = x;
        this.rotationPointY = y;
        this.rotationPointZ = z;
    }

    public void render(PoseStack ms, VertexConsumer vb, int x, int y) {
        this.render(ms, vb, x, y, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    public void render(PoseStack ms, VertexConsumer vb, int x, int light, float overlay, float r, float g, float b) {
        if (!this.showModel) {
            return;
        }

        if (!this.cubeList.isEmpty() || !this.childModels.isEmpty()) {
            ms.pushPose();
            this.translateRotate(ms);
            this.doRender(ms.last(), vb, x, light, overlay, r, g, b);

            for (FancyEntityModelPart renderer : this.childModels) {
                renderer.render(ms, vb, x, light, overlay, r, g, b);
            }

            ms.popPose();
        }
    }

    public void translateRotate(PoseStack ms) {
        ms.translate(
                (this.rotationPointX / 16.0F),
                (this.rotationPointY / 16.0F),
                (this.rotationPointZ / 16.0F)
        );
        
        if (this.rotateAngleZ != 0.0F) {
            ms.mulPose(Vector3f.ZP.rotation(this.rotateAngleZ));
        }

        if (this.rotateAngleY != 0.0F) {
            ms.mulPose(Vector3f.YP.rotation(this.rotateAngleY));
        }

        if (this.rotateAngleX != 0.0F) {
            ms.mulPose(Vector3f.XP.rotation(this.rotateAngleX));
        }

    }

    private void doRender(PoseStack.Pose ms, VertexConsumer vb, int light, int overlay, float r, float g, float b, float a) {
        Matrix4f pose = ms.pose();
        Matrix3f normal = ms.normal();

        for (ModelBox modelBox : this.cubeList) {
            TexturedQuad[] quads = modelBox.quads;

            for (TexturedQuad texturedQuad : quads) {
                Vector3f quadNormal = texturedQuad.normal.copy();
                quadNormal.transform(normal);
                float x = quadNormal.x();
                float y = quadNormal.y();
                float z = quadNormal.z();

                for (int j = 0; j < 4; ++j) {
                    PositionTextureVertex positionTextureVertex = texturedQuad.vertexPositions[j];
                    float x2 = positionTextureVertex.position.x() / 16.0F;
                    float y2 = positionTextureVertex.position.y() / 16.0F;
                    float z2 = positionTextureVertex.position.z() / 16.0F;
                    Vector4f vector4f = new Vector4f(x2, y2, z2, 1.0F);
                    vector4f.transform(pose);
                    vb.vertex(vector4f.x(), vector4f.y(), vector4f.z(), r, g, b, a, positionTextureVertex.textureU,
                            positionTextureVertex.textureV, overlay, light, x, y, z);
                }
            }
        }

    }

    public FancyEntityModelPart setTextureSize(int width, int height) {
        this.textureWidth = (float) width;
        this.textureHeight = (float) height;
        return this;
    }

    public FancyEntityModelPart.ModelBox getRandomCube(Random random) {
        return (FancyEntityModelPart.ModelBox)this.cubeList.get(random.nextInt(this.cubeList.size()));
    }

    static class PositionTextureVertex {
        public final Vector3f position;
        public final float textureU;
        public final float textureV;

        public PositionTextureVertex(float x, float y, float z, float u, float v) {
            this(new Vector3f(x, y, z), u, v);
        }

        public FancyEntityModelPart.PositionTextureVertex setTextureUV(float u, float v) {
            return new FancyEntityModelPart.PositionTextureVertex(this.position, u, v);
        }

        public PositionTextureVertex(Vector3f pos, float u, float v) {
            this.position = pos;
            this.textureU = u;
            this.textureV = v;
        }
    }

    static class TexturedQuad {
        public final FancyEntityModelPart.PositionTextureVertex[] vertexPositions;
        public final Vector3f normal;

        public TexturedQuad(FancyEntityModelPart.PositionTextureVertex[] vertices, float u1, float v1, float u2,
                            float v2, float width, float height, boolean mirror, Direction direction) {
            this.vertexPositions = vertices;
            float f = 0.0F / width;
            float f1 = 0.0F / height;
            vertices[0] = vertices[0].setTextureUV(u2 / width - f, v1 / height + f1);
            vertices[1] = vertices[1].setTextureUV(u1 / width + f, v1 / height + f1);
            vertices[2] = vertices[2].setTextureUV(u1 / width + f, v2 / height - f1);
            vertices[3] = vertices[3].setTextureUV(u2 / width - f, v2 / height - f1);
            if (mirror) {
                int i = vertices.length;

                for(int j = 0; j < i / 2; ++j) {
                    FancyEntityModelPart.PositionTextureVertex vertex = vertices[j];
                    vertices[j] = vertices[i - 1 - j];
                    vertices[i - 1 - j] = vertex;
                }
            }

            this.normal = direction.step();
            if (mirror) {
                this.normal.mul(-1.0F, 1.0F, 1.0F);
            }

        }
    }

    public static class ModelBox {
        private final FancyEntityModelPart.TexturedQuad[] quads;
        public final float posX1;
        public final float posY1;
        public final float posZ1;
        public final float posX2;
        public final float posY2;
        public final float posZ2;

        public ModelBox(int texOffX, int texOffY, float x, float y, float z, float width, float height, float depth,
                        float dX, float dY, float dZ, boolean mirror, float u, float v) {
            this.posX1 = x;
            this.posY1 = y;
            this.posZ1 = z;
            this.posX2 = x + width;
            this.posY2 = y + height;
            this.posZ2 = z + depth;
            this.quads = new FancyEntityModelPart.TexturedQuad[6];
            float f = x + width;
            float f1 = y + height;
            float f2 = z + depth;
            x -= dX;
            y -= dY;
            z -= dZ;
            f += dX;
            f1 += dY;
            f2 += dZ;
            if (mirror) {
                float f3 = f;
                f = x;
                x = f3;
            }

            FancyEntityModelPart.PositionTextureVertex vertex7 = new FancyEntityModelPart.PositionTextureVertex(x, y, z, 0.0F, 0.0F);
            FancyEntityModelPart.PositionTextureVertex vertex = new FancyEntityModelPart.PositionTextureVertex(f, y, z, 0.0F, 8.0F);
            FancyEntityModelPart.PositionTextureVertex vertex1 = new FancyEntityModelPart.PositionTextureVertex(f, f1, z, 8.0F, 8.0F);
            FancyEntityModelPart.PositionTextureVertex vertex2 = new FancyEntityModelPart.PositionTextureVertex(x, f1, z, 8.0F, 0.0F);
            FancyEntityModelPart.PositionTextureVertex vertex3 = new FancyEntityModelPart.PositionTextureVertex(x, y, f2, 0.0F, 0.0F);
            FancyEntityModelPart.PositionTextureVertex vertex4 = new FancyEntityModelPart.PositionTextureVertex(f, y, f2, 0.0F, 8.0F);
            FancyEntityModelPart.PositionTextureVertex vertex5 = new FancyEntityModelPart.PositionTextureVertex(f, f1, f2, 8.0F, 8.0F);
            FancyEntityModelPart.PositionTextureVertex vertex6 = new FancyEntityModelPart.PositionTextureVertex(x, f1, f2, 8.0F, 0.0F);

            float f4 = (float) texOffX;
            float f5 = (float) texOffX + depth;
            float f6 = (float) texOffX + depth + width;
            float f7 = (float) texOffX + depth + width + width;
            float f8 = (float) texOffX + depth + width + depth;
            float f9 = (float) texOffX + depth + width + depth + width;
            float f10 = (float) texOffY;
            float f11 = (float) texOffY + depth;
            float f12 = (float) texOffY + depth + height;

            this.quads[2] = new FancyEntityModelPart.TexturedQuad(new FancyEntityModelPart.PositionTextureVertex[]{vertex4, vertex3, vertex7, vertex}, f5, f10, f6, f11, u, v, mirror, Direction.DOWN);
            this.quads[3] = new FancyEntityModelPart.TexturedQuad(new FancyEntityModelPart.PositionTextureVertex[]{vertex1, vertex2, vertex6, vertex5}, f6, f11, f7, f10, u, v, mirror, Direction.UP);
            this.quads[1] = new FancyEntityModelPart.TexturedQuad(new FancyEntityModelPart.PositionTextureVertex[]{vertex7, vertex3, vertex6, vertex2}, f4, f11, f5, f12, u, v, mirror, Direction.WEST);
            this.quads[4] = new FancyEntityModelPart.TexturedQuad(new FancyEntityModelPart.PositionTextureVertex[]{vertex, vertex7, vertex2, vertex1}, f5, f11, f6, f12, u, v, mirror, Direction.NORTH);
            this.quads[0] = new FancyEntityModelPart.TexturedQuad(new FancyEntityModelPart.PositionTextureVertex[]{vertex4, vertex, vertex1, vertex5}, f6, f11, f8, f12, u, v, mirror, Direction.EAST);
            this.quads[5] = new FancyEntityModelPart.TexturedQuad(new FancyEntityModelPart.PositionTextureVertex[]{vertex3, vertex4, vertex5, vertex6}, f8, f11, f9, f12, u, v, mirror, Direction.SOUTH);
        }
    }
}