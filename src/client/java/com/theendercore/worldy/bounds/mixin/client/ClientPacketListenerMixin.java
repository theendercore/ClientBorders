package com.theendercore.worldy.bounds.mixin.client;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.theendercore.worldy.bounds.client.util.BorderGetterOverride;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {

    @Shadow
    private ClientLevel level;

    @Inject(method = "handleInitializeBorder", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;getWorldBorder()Lnet/minecraft/world/level/border/WorldBorder;"))
    void handleInitializeBorderFix(ClientboundInitializeBorderPacket p, CallbackInfo ci) {
        ((BorderGetterOverride) level).cb_forceVanillaCall();
    }

    @Inject(method = "handleSetBorderCenter", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;getWorldBorder()Lnet/minecraft/world/level/border/WorldBorder;"))
    void handleSetBorderCenterFix(ClientboundSetBorderCenterPacket p, CallbackInfo ci) {
        ((BorderGetterOverride) level).cb_forceVanillaCall();
    }

    @Inject(method = "handleSetBorderSize", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;getWorldBorder()Lnet/minecraft/world/level/border/WorldBorder;"))
    void handleSetBorderSizeFix(ClientboundSetBorderSizePacket p, CallbackInfo ci) {
        ((BorderGetterOverride) level).cb_forceVanillaCall();
    }

    @Inject(method = "handleSetBorderWarningDistance", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;getWorldBorder()Lnet/minecraft/world/level/border/WorldBorder;"))
    void handleSetBorderWarningDistanceFix(ClientboundSetBorderWarningDistancePacket p, CallbackInfo ci) {
        ((BorderGetterOverride) level).cb_forceVanillaCall();
    }

    @Inject(method = "handleSetBorderWarningDelay", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;getWorldBorder()Lnet/minecraft/world/level/border/WorldBorder;"))
    void handleSetBorderWarningDelayFix(ClientboundSetBorderWarningDelayPacket p, CallbackInfo ci) {
        ((BorderGetterOverride) level).cb_forceVanillaCall();
    }

}