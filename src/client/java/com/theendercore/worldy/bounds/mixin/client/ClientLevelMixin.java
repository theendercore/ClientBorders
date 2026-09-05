package com.theendercore.worldy.bounds.mixin.client;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import com.theendercore.worldy.bounds.client.util.BorderGetterOverride;

import java.util.function.Supplier;

import static com.theendercore.worldy.bounds.client.BorderHandler.handleWorldBorderCode;

@Mixin(ClientLevel.class)
public abstract class ClientLevelMixin extends Level implements BorderGetterOverride {

    protected ClientLevelMixin(WritableLevelData writableLevelData, ResourceKey<Level> resourceKey, RegistryAccess registryAccess, Holder<DimensionType> holder, Supplier<ProfilerFiller> supplier, boolean bl, boolean bl2, long l, int i) {
        super(writableLevelData, resourceKey, registryAccess, holder, supplier, bl, bl2, l, i);
    }

    @Unique
    private boolean client_boarders$forceVanilla = false;

    @Override
    public @NotNull WorldBorder getWorldBorder() {
        var vanillaBorder = super.getWorldBorder();
        if (client_boarders$forceVanilla) {
            client_boarders$forceVanilla = false;
            return vanillaBorder;
        }
        return handleWorldBorderCode(vanillaBorder, dimension());
    }

    @Override
    public void cb_forceVanillaCall() {
        client_boarders$forceVanilla = true;
    }

}