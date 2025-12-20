package fuzs.magnumtorch.handler;

import fuzs.magnumtorch.MagnumTorch;
import fuzs.magnumtorch.attachment.TorchPositions;
import fuzs.magnumtorch.attachment.TypedBlockArea;
import fuzs.magnumtorch.config.ServerConfig;
import fuzs.magnumtorch.init.ModRegistry;
import fuzs.magnumtorch.world.level.block.MagnumTorchType;
import fuzs.puzzleslib.api.core.v1.CommonAbstractions;
import fuzs.puzzleslib.api.event.v1.core.EventResult;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.chunk.ChunkGenerator;
import org.apache.commons.lang3.mutable.MutableBoolean;

import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class MobSpawningHandler {

    public static EventResult onEntityLoad(Entity entity, ServerLevel serverLevel, boolean isNewlySpawned) {
        if (!isNewlySpawned || !(entity instanceof Mob mob) || !MagnumTorch.CONFIG.getHolder(ServerConfig.class)
                .isAvailable()) {
            return EventResult.PASS;
        }

        MobSpawnType entitySpawnReason = CommonAbstractions.INSTANCE.getMobSpawnType(mob);
        // natural spawning is handled elsewhere
        if (entitySpawnReason != null && entitySpawnReason != MobSpawnType.NATURAL) {
            MutableBoolean mutableBoolean = new MutableBoolean();
            preventSpawning(serverLevel, entity.blockPosition(), entitySpawnReason, (MagnumTorchType type) -> {
                if (type.getConfig().preventSpawning(entity.getType())) {
                    mutableBoolean.setTrue();
                    return true;
                } else {
                    return false;
                }
            });
            if (mutableBoolean.isTrue()) {
                removeEntitySafely(serverLevel, entity);
                return EventResult.INTERRUPT;
            }
        }

        return EventResult.PASS;
    }

    private static void removeEntitySafely(ServerLevel serverLevel, Entity entity) {
        // collect for running at the end of this server tick, other passengers might still be added to the level after this,
        // and calling Entity::discard to early for them will log an annoying warning
        List<Entity> entities = entity.getRootVehicle()
                .getSelfAndPassengers()
                .distinct()
                .filter((Entity passenger) -> passenger != entity)
                .toList();
        serverLevel.getServer().execute(() -> {
            entities.forEach(Entity::discard);
        });
    }

    public static void onGatherPotentialSpawns(ServerLevel serverLevel, StructureManager structureManager, ChunkGenerator chunkGenerator, MobCategory mobCategory, BlockPos blockPos, List<MobSpawnSettings.SpawnerData> mobs) {
        if (mobs.isEmpty() || !MagnumTorch.CONFIG.getHolder(ServerConfig.class).isAvailable()) {
            return;
        }

        preventSpawning(serverLevel, blockPos, MobSpawnType.NATURAL, (MagnumTorchType type) -> {
            mobs.removeIf((MobSpawnSettings.SpawnerData spawnerData) -> {
                return type.getConfig().preventSpawning(spawnerData.type);
            });
            return false;
        });
    }

    private static void preventSpawning(ServerLevel serverLevel, BlockPos blockPos, MobSpawnType entitySpawnReason, Predicate<MagnumTorchType> spawnBlocker) {
        TorchPositions torchPositions = ModRegistry.TORCH_POSITIONS_ATTACHMENT_TYPE.getOrDefault(serverLevel,
                TorchPositions.EMPTY);
        Collection<? extends TypedBlockArea> torchesInSection = torchPositions.getTorchesInSection(blockPos);
        if (!torchesInSection.isEmpty()) {
            Set<MagnumTorchType> handledTypes = EnumSet.noneOf(MagnumTorchType.class);
            for (TypedBlockArea typedBlockArea : torchesInSection) {
                MagnumTorchType type = typedBlockArea.type();
                if (!handledTypes.contains(type) && typedBlockArea.blockArea().isPositionInside(blockPos)) {
                    if (type.getConfig().blockedSpawnReasons.contains(entitySpawnReason) && spawnBlocker.test(type)) {
                        break;
                    } else {
                        handledTypes.add(type);
                    }
                }
            }
        }
    }
}
