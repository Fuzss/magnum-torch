package fuzs.magnumtorch.handler;

import com.google.common.collect.Sets;
import fuzs.magnumtorch.MagnumTorch;
import fuzs.magnumtorch.config.ServerConfig;
import fuzs.magnumtorch.world.level.block.MagnumTorchBlock;
import fuzs.puzzleslib.api.event.v1.core.EventResult;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.Set;

public class MobSpawningHandler {
    private static final Set<Entity> PASSENGERS_TO_REMOVE = Sets.newLinkedHashSet();

    public static EventResult onLivingSpawn(Entity entity, ServerLevel level, @Nullable MobSpawnType spawnType) {
        if (spawnType == null || !MagnumTorch.CONFIG.getHolder(ServerConfig.class).isAvailable()) return EventResult.PASS;
        for (MagnumTorchBlock.Type type : MagnumTorchBlock.Type.values()) {
            if (isSpawnCancelled(level.getPoiManager(), entity.getType(), entity.blockPosition(), spawnType, type.getPoiTypeKey(), type.getConfig())) {
                // collect for running at the end of this server tick, other passengers might still be added to the level after this,
                // and calling Entity::discard to early for them will log an annoying warning
                entity.getRootVehicle().getSelfAndPassengers().distinct().filter(passenger -> passenger != entity).forEach(PASSENGERS_TO_REMOVE::add);
                return EventResult.INTERRUPT;
            }
        }
        return EventResult.PASS;
    }

    public static void onEndServerTick(MinecraftServer server) {
        Iterator<Entity> iterator = PASSENGERS_TO_REMOVE.iterator();
        while (iterator.hasNext()) {
            // discarding will also eject self (to prevent any serialization) and all passengers
            iterator.next().discard();
            iterator.remove();
        }
    }

    private static boolean isSpawnCancelled(PoiManager poiManager, EntityType<?> entityType, BlockPos toCheck, @NotNull MobSpawnType spawnType, ResourceKey<PoiType> poiTypeKey, ServerConfig.MagnumTorchConfig config) {
        if (config.blockedSpawnTypes.contains(spawnType) && config.isAffected(entityType)) {
            // range based on cuboid as it contains all other shape types, 1.7320508076=3**0.5
            return poiManager.findAll(poiType -> poiType.is(poiTypeKey), $ -> true, toCheck, (int) Math.ceil(Math.max(config.horizontalRange, config.verticalRange) * 1.7320508076), PoiManager.Occupancy.ANY)
                    .anyMatch(pos -> config.shapeType.isPositionContained(pos, toCheck, config.horizontalRange, config.verticalRange));
        }
        return false;
    }
}
