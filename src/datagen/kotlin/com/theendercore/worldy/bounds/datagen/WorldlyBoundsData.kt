package com.theendercore.worldy.bounds.datagen

import com.theendercore.worldy.bounds.client.WorldlyBounds
import com.theendercore.worldy.bounds.client.WorldlyBounds.log
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import com.theendercore.worldy.bounds.datagen.assets.EnLangProvider

object WorldlyBoundsData : DataGeneratorEntrypoint {

    override fun getEffectiveModId(): String = WorldlyBounds.MODID

    override fun onInitializeDataGenerator(gen: FabricDataGenerator) {
        val pack = gen.createPack()
        log.info("Running \"${gen.modContainer.metadata.name}\" Datagen!")

        // Assets
        pack.addProvider(::EnLangProvider)
    }

}