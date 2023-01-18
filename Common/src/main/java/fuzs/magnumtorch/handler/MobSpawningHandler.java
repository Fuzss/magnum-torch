package fuzs.magnumtorch.handler;

import fuzs.magnumtorch.MagnumTorch;
import fuzs.magnumtorch.config.ServerConfig;
import fuzs.magnumtorch.init.ModRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;

import java.util.stream.Stream;

public class MobSpawningHandler {

    public static boolean onLivingSpawn(EntityType<?> entityType, LevelAccessor levelAccessor, double posX, double posY, double posZ, MobSpawnType spawnType) {
        if (levelAccessor.isClientSide() || !MagnumTorch.CONFIG.getHolder(ServerConfig.class).isAvailable()) return true;
        PoiManager poiManager = ((ServerLevelAccessor) levelAccessor).getLevel().getPoiManager();
        BlockPos pos = new BlockPos(posX, posY, posZ);
        if (isSpawnCancelled(poiManager, entityType, pos, spawnType, ModRegistry.DIAMOND_MAGNUM_TORCH_POI_TYPE.getResourceKey(), MagnumTorch.CONFIG.get(ServerConfig.class).diamond)) {
            return false;
        } else if (isSpawnCancelled(poiManager, entityType, pos, spawnType, ModRegistry.EMERALD_MAGNUM_TORCH_POI_TYPE.getResourceKey(), MagnumTorch.CONFIG.get(ServerConfig.class).emerald)) {
            return false;
        } else return !isSpawnCancelled(poiManager, entityType, pos, spawnType, ModRegistry.AMETHYST_MAGNUM_TORCH_POI_TYPE.getResourceKey(), MagnumTorch.CONFIG.get(ServerConfig.class).amethyst);
    }

    private static boolean isSpawnCancelled(PoiManager poiManager, EntityType<?> entityType, BlockPos pos, MobSpawnType spawnType, ResourceKey<PoiType> poiType, ServerConfig.MagnumTorchConfig config) {
        return config.blockedSpawnTypes.contains(spawnType) && isAffected(entityType, config) && anyInRange(poiManager, poiType, pos, config);
    }

    private static boolean isAffected(EntityType<?> entityType, ServerConfig.MagnumTorchConfig config) {
        if (config.mobWhitelist.contains(entityType)) return false;
        if (config.mobCategories.contains(entityType.getCategory())) return true;
        return config.mobBlacklist.contains(entityType);
    }

    private static boolean anyInRange(PoiManager poiManager, ResourceKey<PoiType> poiType, BlockPos pos, ServerConfig.MagnumTorchConfig config) {
        // range based on cuboid
        Stream<BlockPos> all = poiManager.findAll(poiType1 -> poiType1.is(poiType), pos1 -> true, pos, (int) Math.ceil(Math.sqrt(config.horizontalRange * config.horizontalRange + config.verticalRange * config.verticalRange)), PoiManager.Occupancy.ANY);
        return all.anyMatch(center -> isInRange(center, pos, config.horizontalRange, config.verticalRange, config.shapeType));
    }

    private static boolean isInRange(BlockPos center, BlockPos pos, int horizontalRange, int verticalRange, ServerConfig.ShapeType shapeType) {
        int dimX = Math.abs(center.getX() - pos.getX());
        int dimY = Math.abs(center.getY() - pos.getY());
        int dimZ = Math.abs(center.getZ() - pos.getZ());
        return switch (shapeType) {
            case ELLIPSOID -> {
                yield (dimX * dimX + dimZ * dimZ) / (float) (horizontalRange * horizontalRange) + (dimY * dimY) / (float) (verticalRange * verticalRange) <= 1.0;
            }
            case CYLINDER -> {
                yield dimX * dimX + dimZ * dimZ <= horizontalRange * horizontalRange && dimY <= verticalRange;
            }
            case CUBOID -> {
                yield dimX <= horizontalRange && dimZ <= horizontalRange && dimY <= verticalRange;
            }
        };
    }
}
