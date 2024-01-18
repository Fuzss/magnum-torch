package fuzs.magnumtorch.handler;

import fuzs.magnumtorch.MagnumTorch;
import fuzs.magnumtorch.config.ServerConfig;
import fuzs.magnumtorch.world.level.block.MagnumTorchBlock;
import fuzs.puzzleslib.api.event.v1.core.EventResult;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MobSpawningHandler {

    public static EventResult onLivingSpawn(Entity entity, ServerLevel level, @Nullable MobSpawnType spawnType) {
        if (spawnType == null || !MagnumTorch.CONFIG.getHolder(ServerConfig.class).isAvailable()) return EventResult.PASS;
        for (MagnumTorchBlock.Type type : MagnumTorchBlock.Type.values()) {
            if (isSpawnCancelled(level.getPoiManager(), entity.getType(), entity.blockPosition(), spawnType, type.getPoiTypeKey(), type.getConfig())) {
                return EventResult.INTERRUPT;
            }
        }
        return EventResult.PASS;
    }

    private static boolean isSpawnCancelled(PoiManager poiManager, EntityType<?> entityType, BlockPos toCheck, @NotNull MobSpawnType spawnType, ResourceKey<PoiType> poiTypeKey, ServerConfig.MagnumTorchConfig config) {
        if (config.blockedSpawnTypes.contains(spawnType) && config.isAffected(entityType)) {
            // range based on cuboid as it contains all other shape types
            PoiType poiType = Registry.POINT_OF_INTEREST_TYPE.get(poiTypeKey);
            int rangeInBlocks = (int) Math.ceil(Math.sqrt(config.horizontalRange * config.horizontalRange + config.verticalRange * config.verticalRange));
            return poiManager.findAll(currentPoiType -> {
                        return currentPoiType == poiType;
                    }, $ -> true, toCheck, rangeInBlocks, PoiManager.Occupancy.ANY)
                    .anyMatch(pos -> {
                        return config.shapeType.isPositionContained(pos, toCheck, config.horizontalRange, config.verticalRange);
                    });
        }
        return false;
    }
}
