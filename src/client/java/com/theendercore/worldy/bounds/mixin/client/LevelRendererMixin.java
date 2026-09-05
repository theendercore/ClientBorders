package com.theendercore.worldy.bounds.mixin.client;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.theendercore.worldy.bounds.client.util.BorderGetterOverride;
import com.theendercore.worldy.bounds.client.render.CustomBorderRenderer;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {

    @Shadow
    private @Nullable ClientLevel level;

    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(method = "renderWorldBorder", at = @At("HEAD"))
    private void renderCustomBorders(Camera camera, CallbackInfo ci) {
        CustomBorderRenderer.renderWorldBorder(camera, level, minecraft);
        if (level instanceof BorderGetterOverride borderLevel) {
            borderLevel.cb_forceVanillaCall();
        }
    }

}