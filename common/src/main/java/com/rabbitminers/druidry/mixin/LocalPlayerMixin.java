package com.rabbitminers.druidry.mixin;

import com.mojang.authlib.GameProfile;
import com.rabbitminers.druidry.content.herbs.herbarum.HerbarumScreen;
import com.rabbitminers.druidry.registry.DruidryItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.BookEditScreen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.ProfilePublicKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = LocalPlayer.class, remap = false)
public class LocalPlayerMixin extends AbstractClientPlayer {

    @Shadow @Final protected Minecraft minecraft;

    public LocalPlayerMixin(ClientLevel clientLevel, GameProfile gameProfile, @Nullable ProfilePublicKey profilePublicKey) {
        super(clientLevel, gameProfile, profilePublicKey);
    }

    @Inject(method = "openItemGui", at = @At("TAIL"))
    public void openItemGUI$druidry(ItemStack itemStack, InteractionHand interactionHand, CallbackInfo ci) {
        if (itemStack.is(DruidryItems.HERBARUM.get())) {
            this.minecraft.setScreen(new HerbarumScreen());
        }
    }
}
