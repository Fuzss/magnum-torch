package fuzs.magnumtorch.world.level.block;

import fuzs.magnumtorch.MagnumTorch;
import fuzs.magnumtorch.config.ServerConfig;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.ai.village.poi.PoiType;

import java.util.function.Supplier;

public enum MagnumTorchType implements StringRepresentable {
    DIAMOND("diamond_magnum_torch", () -> MagnumTorch.CONFIG.get(ServerConfig.class).diamond), EMERALD("emerald_magnum_torch", () -> MagnumTorch.CONFIG.get(ServerConfig.class).emerald), AMETHYST("amethyst_magnum_torch", () -> MagnumTorch.CONFIG.get(ServerConfig.class).amethyst);

    private final String name;
    private final Supplier<ServerConfig.MagnumTorchConfig> config;

    MagnumTorchType(String name, Supplier<ServerConfig.MagnumTorchConfig> config) {
        this.name = name;
        this.config = config;
    }

    public ServerConfig.MagnumTorchConfig getConfig() {
        return this.config.get();
    }

    public ResourceKey<PoiType> getPoiTypeKey() {
        return ResourceKey.create(Registries.POINT_OF_INTEREST_TYPE, MagnumTorch.id(this.name));
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
