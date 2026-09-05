package com.theendercore.worldy.bounds.client

import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.Level
import net.minecraft.world.level.border.WorldBorder
import com.theendercore.worldy.bounds.client.config.WorldlyBoundsConfig
import kotlin.collections.iterator

object BorderHandler {

    class ConfiguredBorder(settings: WorldlyBoundsConfig.BorderSettings) : WorldBorder() {
        var hard = settings.hard
        var color = settings.color.get()
        var radius = settings.radius.get()
        var center = settings.center.get()
        var texture: ResourceLocation = settings.texture.get().withSuffix(".png")

        init {
            size = radius + radius
            setCenter(center.left, center.right)
            warningBlocks = settings.warningBlocks.get()
        }
    }

    private var CACHE = mapOf<ResourceLocation, ConfiguredBorder>()

    fun getBorder(id: ResourceLocation): ConfiguredBorder? {
        if (CACHE.isEmpty() && WorldlyBounds.config.worldBoarders.isNotEmpty()) {
            reloadAllBorders()
        }
        return CACHE[id]
    }

    fun reloadAllBorders() {
        val newMap = mutableMapOf<ResourceLocation, ConfiguredBorder>()

        for ((dim, settings) in WorldlyBounds.config.worldBoarders) {
            newMap[dim] = ConfiguredBorder(settings)
        }

        CACHE = newMap.toMap()
    }

    @JvmStatic
    fun handleWorldBorderCode(vanillaBorder: WorldBorder, dimension: ResourceKey<Level>): WorldBorder {
        val worldBorder = getBorder(dimension.location()) ?: return vanillaBorder
        if (!worldBorder.hard) {
            return vanillaBorder
        }
        if (worldBorder.size > vanillaBorder.size) {
            return vanillaBorder
        }

        return worldBorder
    }

}