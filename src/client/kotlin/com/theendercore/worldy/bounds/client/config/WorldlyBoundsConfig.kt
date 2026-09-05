package com.theendercore.worldy.bounds.client.config

import com.theendercore.worldy.bounds.client.BorderHandler
import com.theendercore.worldy.bounds.client.WorldlyBounds.MODID
import com.theendercore.worldy.bounds.client.WorldlyBounds.id
import com.theendercore.worldy.bounds.client.WorldlyBounds.mc
import me.fzzyhmstrs.fzzy_config.config.Config
import me.fzzyhmstrs.fzzy_config.util.Walkable
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedMap
import me.fzzyhmstrs.fzzy_config.validation.minecraft.ValidatedIdentifier
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedAny
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedColor
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedPair
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedPair.Companion.withLabels
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedDouble
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.Level

class WorldlyBoundsConfig : Config(id(MODID)) {

    @Suppress("DEPRECATION")
    var worldBoarders = ValidatedMap.Builder<ResourceLocation, BorderSettings>()
        .keyHandler(ValidatedIdentifier.ofRegistryKey(Level.OVERWORLD.location(), Registries.DIMENSION))
        .valueHandler(ValidatedAny(BorderSettings()))
        .defaults()
        .build()

    override fun onUpdateClient() {
        super.onUpdateClient()
        BorderHandler.reloadAllBorders()
    }

    class BorderSettings : Walkable {
        var hard = false
        var color = ValidatedColor(false)
        var radius = ValidatedDouble(1024.0, Double.MAX_VALUE, 0.5)

        @Suppress("UnstableApiUsage")
        var center = ValidatedPair.of(ValidatedDouble(0.0)).withLabels(Component.literal("X"), Component.literal("Z"))
        var warningBlocks = ValidatedInt(0)
        var texture = ValidatedIdentifier(mc("textures/misc/forcefield"))
    }

}