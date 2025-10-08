package fuzs.magnumtorch.world.level.block;

import fuzs.magnumtorch.MagnumTorch;
import fuzs.magnumtorch.config.ServerConfig;
import fuzs.magnumtorch.init.ModRegistry;
import net.minecraft.core.Holder;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.ai.village.poi.PoiType;

import java.util.Locale;

public enum MagnumTorchType implements StringRepresentable {
    DIAMOND {
        @Override
        public ServerConfig.MagnumTorchConfig getConfig() {
            return MagnumTorch.CONFIG.get(ServerConfig.class).diamond;
        }

        @Override
        public Holder.Reference<PoiType> getPoiType() {
            return ModRegistry.DIAMOND_MAGNUM_TORCH_POI_TYPE;
        }
    },
    EMERALD {
        @Override
        public ServerConfig.MagnumTorchConfig getConfig() {
            return MagnumTorch.CONFIG.get(ServerConfig.class).emerald;
        }

        @Override
        public Holder.Reference<PoiType> getPoiType() {
            return ModRegistry.EMERALD_MAGNUM_TORCH_POI_TYPE;
        }
    },
    AMETHYST {
        @Override
        public ServerConfig.MagnumTorchConfig getConfig() {
            return MagnumTorch.CONFIG.get(ServerConfig.class).amethyst;
        }

        @Override
        public Holder.Reference<PoiType> getPoiType() {
            return ModRegistry.AMETHYST_MAGNUM_TORCH_POI_TYPE;
        }
    };

    public static final StringRepresentable.EnumCodec<MagnumTorchType> CODEC = StringRepresentable.fromEnum(
            MagnumTorchType::values);

    public abstract ServerConfig.MagnumTorchConfig getConfig();

    public abstract Holder.Reference<PoiType> getPoiType();

    @Override
    public String getSerializedName() {
        return this.name().toLowerCase(Locale.ROOT);
    }
}
