package com.rabbitminers.druidry.events;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.rabbitminers.druidry.DruidryClient;
import com.rabbitminers.druidry.render.base.TriBufferSource;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;

public class ClientEvents {
    public static void onTick() {
        DruidryClient.OUTLININATOR.tickOutlines();
    }

    public static void onRenderWorld(PoseStack ms) {
        Vec3 cameraPos = Minecraft.getInstance().gameRenderer.getMainCamera()
                .getPosition();
        float pt = DruidryClient.getPartialTicks();

        ms.pushPose();
        ms.translate(-cameraPos.x(), -cameraPos.y(), -cameraPos.z());

        TriBufferSource buffer = TriBufferSource.getInstance();
        DruidryClient.OUTLININATOR.renderOutlines(ms, TriBufferSource.getInstance(), pt);

        buffer.draw();
        RenderSystem.enableCull();

        ms.popPose();
    }
}
