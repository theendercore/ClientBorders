package com.theendercore.worldy.bounds.client.render

import com.mojang.blaze3d.platform.GlStateManager
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.BufferUploader
import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.Tesselator
import com.mojang.blaze3d.vertex.VertexFormat
import net.minecraft.Util
import net.minecraft.client.Camera
import net.minecraft.client.Minecraft
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.util.Mth
import com.theendercore.worldy.bounds.client.BorderHandler
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow

object CustomBorderRenderer {

    @JvmStatic
    fun renderWorldBorder(camera: Camera, level: ClientLevel?, minecraft: Minecraft?) {
        if (level == null || minecraft == null) {
            return
        }

        val worldBorder = BorderHandler.getBorder(level.dimension().location()) ?: return
        val renderDist = (minecraft.options.effectiveRenderDistance * 16).toDouble()
        if (
            !(camera.position.x < worldBorder.maxX - renderDist) ||
            !(camera.position.x > worldBorder.minX + renderDist) ||
            !(camera.position.z < worldBorder.maxZ - renderDist) ||
            !(camera.position.z > worldBorder.minZ + renderDist)
        ) {
            var e = 1.0 - worldBorder.getDistanceToBorder(camera.position.x, camera.position.z) / renderDist
            e = e.pow(4.0)
            e = Mth.clamp(e, 0.0, 1.0)
            val camX = camera.position.x
            val camZ = camera.position.z
            val h = minecraft.gameRenderer.depthFar.toDouble()
            RenderSystem.enableBlend()
            RenderSystem.enableDepthTest()
            RenderSystem.blendFuncSeparate(
                GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE,
                GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO
            )
            RenderSystem.setShaderTexture(0, worldBorder.texture)
            RenderSystem.depthMask(Minecraft.useShaderTransparency())
            val color = worldBorder.color.toInt()
            val j = (color shr 16 and 0xFF) / 255.0f
            val k = (color shr 8 and 0xFF) / 255.0f
            val l = (color and 0xFF) / 255.0f
            RenderSystem.setShaderColor(j, k, l, e.toFloat())
            RenderSystem.setShader(GameRenderer::getPositionTexShader)
            RenderSystem.polygonOffset(-3.0f, -3.0f)
            RenderSystem.enablePolygonOffset()
            RenderSystem.disableCull()
            val m = (Util.getMillis() % 3000L).toFloat() / 3000.0f
            val n = (-Mth.frac(camera.position.y * 0.5)).toFloat()
            val o = n + h.toFloat()
            val bufferBuilder =
                Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX)
            var p = max(Mth.floor(camZ - renderDist).toDouble(), worldBorder.minZ)
            var q = min(Mth.ceil(camZ + renderDist).toDouble(), worldBorder.maxZ)
            var r = (Mth.floor(p) and 1) * 0.5f
            if (camX > worldBorder.maxX - renderDist) {
                var s = r

                var t = p
                while (t < q) {
                    val u = min(1.0, q - t)
                    val v = u.toFloat() * 0.5f
                    bufferBuilder
                        .addVertex((worldBorder.maxX - camX).toFloat(), (-h).toFloat(), (t - camZ).toFloat())
                        .setUv(m - s, m + o)
                    bufferBuilder
                        .addVertex(
                            (worldBorder.maxX - camX).toFloat(), (-h).toFloat(), (t + u - camZ).toFloat()
                        )
                        .setUv(m - (v + s), m + o)
                    bufferBuilder
                        .addVertex((worldBorder.maxX - camX).toFloat(), h.toFloat(), (t + u - camZ).toFloat())
                        .setUv(m - (v + s), m + n)
                    bufferBuilder
                        .addVertex((worldBorder.maxX - camX).toFloat(), h.toFloat(), (t - camZ).toFloat())
                        .setUv(m - s, m + n)
                    t++
                    s += 0.5f
                }
            }

            if (camX < worldBorder.minX + renderDist) {
                var s = r

                var t = p
                while (t < q) {
                    val u = min(1.0, q - t)
                    val v = u.toFloat() * 0.5f
                    bufferBuilder
                        .addVertex((worldBorder.minX - camX).toFloat(), (-h).toFloat(), (t - camZ).toFloat())
                        .setUv(m + s, m + o)
                    bufferBuilder
                        .addVertex((worldBorder.minX - camX).toFloat(), (-h).toFloat(), (t + u - camZ).toFloat())
                        .setUv(m + v + s, m + o)
                    bufferBuilder
                        .addVertex((worldBorder.minX - camX).toFloat(), h.toFloat(), (t + u - camZ).toFloat())
                        .setUv(m + v + s, m + n)
                    bufferBuilder
                        .addVertex((worldBorder.minX - camX).toFloat(), h.toFloat(), (t - camZ).toFloat())
                        .setUv(m + s, m + n)
                    t++
                    s += 0.5f
                }
            }

            p = max(Mth.floor(camX - renderDist).toDouble(), worldBorder.minX)
            q = min(Mth.ceil(camX + renderDist).toDouble(), worldBorder.maxX)
            r = (Mth.floor(p) and 1) * 0.5f
            if (camZ > worldBorder.maxZ - renderDist) {
                var s = r

                var t = p
                while (t < q) {
                    val u = min(1.0, q - t)
                    val v = u.toFloat() * 0.5f
                    bufferBuilder
                        .addVertex((t - camX).toFloat(), (-h).toFloat(), (worldBorder.maxZ - camZ).toFloat())
                        .setUv(m + s, m + o)
                    bufferBuilder
                        .addVertex((t + u - camX).toFloat(), (-h).toFloat(), (worldBorder.maxZ - camZ).toFloat())
                        .setUv(m + v + s, m + o)
                    bufferBuilder.addVertex((t + u - camX).toFloat(), h.toFloat(), (worldBorder.maxZ - camZ).toFloat())
                        .setUv(m + v + s, m + n)
                    bufferBuilder.addVertex((t - camX).toFloat(), h.toFloat(), (worldBorder.maxZ - camZ).toFloat())
                        .setUv(m + s, m + n)
                    t++
                    s += 0.5f
                }
            }

            if (camZ < worldBorder.minZ + renderDist) {
                var s = r

                var t = p
                while (t < q) {
                    val u = min(1.0, q - t)
                    val v = u.toFloat() * 0.5f
                    bufferBuilder
                        .addVertex((t - camX).toFloat(), (-h).toFloat(), (worldBorder.minZ - camZ).toFloat())
                        .setUv(m - s, m + o)
                    bufferBuilder
                        .addVertex((t + u - camX).toFloat(), (-h).toFloat(), (worldBorder.minZ - camZ).toFloat())
                        .setUv(m - (v + s), m + o)
                    bufferBuilder
                        .addVertex((t + u - camX).toFloat(), h.toFloat(), (worldBorder.minZ - camZ).toFloat())
                        .setUv(m - (v + s), m + n)
                    bufferBuilder
                        .addVertex((t - camX).toFloat(), h.toFloat(), (worldBorder.minZ - camZ).toFloat())
                        .setUv(m - s, m + n)
                    t++
                    s += 0.5f
                }
            }

            val meshData = bufferBuilder.build()
            if (meshData != null) {
                BufferUploader.drawWithShader(meshData)
            }

            RenderSystem.enableCull()
            RenderSystem.polygonOffset(0.0f, 0.0f)
            RenderSystem.disablePolygonOffset()
            RenderSystem.disableBlend()
            RenderSystem.defaultBlendFunc()
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f)
            RenderSystem.depthMask(true)
        }
    }

}