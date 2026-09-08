package com.theendercore.worldy.bounds.client

import me.fzzyhmstrs.fzzy_config.api.ConfigApi
import me.fzzyhmstrs.fzzy_config.api.RegisterType
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents
import net.minecraft.resources.ResourceLocation
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import com.theendercore.worldy.bounds.client.config.WorldlyBoundsConfig

object WorldlyBounds {

    const val MODID = "worldly_bounds"

    @JvmField
    val log: Logger = LoggerFactory.getLogger(MODID)

    @JvmField
    var config = ConfigApi.registerAndLoadConfig(::WorldlyBoundsConfig, RegisterType.CLIENT)

    fun init() {
        log.info("I love walking in to inviable walls!")
        ClientPlayConnectionEvents.JOIN.register { _, _, _ -> BorderHandler.reloadAllBorders() }
        //TODO reload borders on resource pack reload
    }

    fun id(namespace: String, path: String): ResourceLocation = ResourceLocation.fromNamespaceAndPath(namespace, path)
    fun mc(path: String): ResourceLocation = ResourceLocation.withDefaultNamespace(path)
    fun id(path: String) = id(MODID, path)

}