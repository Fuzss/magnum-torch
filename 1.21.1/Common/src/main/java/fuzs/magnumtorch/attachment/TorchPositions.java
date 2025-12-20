package fuzs.magnumtorch.attachment;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import fuzs.magnumtorch.init.ModRegistry;
import fuzs.magnumtorch.world.level.block.MagnumTorchBlock;
import fuzs.magnumtorch.world.level.block.MagnumTorchType;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMaps;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class TorchPositions {
    public static final TorchPositions EMPTY = new TorchPositions();
    public static final Codec<TorchPositions> CODEC = TypedBlockArea.CODEC.listOf()
            .xmap(TorchPositions::new, (TorchPositions torchPositions) -> {
                return new ArrayList<>(torchPositions.torchPositions.values());
            });

    private final Map<BlockPos, TypedBlockArea> torchPositions;
    private final Long2ObjectMap<Map<BlockPos, TypedBlockArea>> sectionPositions;

    private TorchPositions() {
        this.torchPositions = Collections.emptyMap();
        this.sectionPositions = Long2ObjectMaps.emptyMap();
    }

    private TorchPositions(List<TypedBlockArea> torchPositions) {
        this(torchPositions.stream().collect(Collectors.toMap(TypedBlockArea::position, Function.identity())),
                createSectionPositions(torchPositions));
    }

    private TorchPositions(Map<BlockPos, TypedBlockArea> torchPositions, Long2ObjectMap<Map<BlockPos, TypedBlockArea>> sectionPositions) {
        this.torchPositions = torchPositions;
        this.sectionPositions = sectionPositions;
    }

    private static Long2ObjectMap<Map<BlockPos, TypedBlockArea>> createSectionPositions(List<TypedBlockArea> torchPositions) {
        Long2ObjectMap<Map<BlockPos, TypedBlockArea>> sectionPositions = new Long2ObjectOpenHashMap<>();
        for (TypedBlockArea torchPosition : torchPositions) {
            addAllSections(torchPosition, sectionPositions);
        }

        return sectionPositions;
    }

    private static void addAllSections(TypedBlockArea torchPosition, Long2ObjectMap<Map<BlockPos, TypedBlockArea>> sectionPositions) {
        torchPosition.blockArea().getAllSections((SectionPos sectionPos) -> {
            Map<BlockPos, TypedBlockArea> positions = sectionPositions.computeIfAbsent(sectionPos.asLong(),
                    (long sectionPosX) -> new LinkedHashMap<>());
            positions.put(torchPosition.position(), torchPosition);
        });
    }

    private static void removeAllSections(TypedBlockArea torchPosition, Long2ObjectMap<Map<BlockPos, TypedBlockArea>> sectionPositions) {
        torchPosition.blockArea().getAllSections((SectionPos sectionPos) -> {
            Map<BlockPos, TypedBlockArea> positions = sectionPositions.get(sectionPos.asLong());
            if (positions != null) {
                positions.remove(torchPosition.position(), torchPosition);
                if (positions.isEmpty()) {
                    sectionPositions.remove(sectionPos.asLong(), positions);
                }
            }
        });
    }

    public Collection<? extends TypedBlockArea> getTorchesInSection(BlockPos blockPos) {
        return this.sectionPositions.getOrDefault(SectionPos.asLong(blockPos), Collections.emptyMap()).values();
    }

    public TorchPositions add(BlockPos blockPos, MagnumTorchType type) {
        TypedBlockArea oldType = this.torchPositions.get(blockPos);
        if (oldType == null || oldType.type() != type) {
            TypedBlockArea torchPosition = new TypedBlockArea(type, blockPos);
            Map<BlockPos, TypedBlockArea> torchPositions = ImmutableMap.<BlockPos, TypedBlockArea>builder()
                    .putAll(this.torchPositions)
                    .put(blockPos, torchPosition)
                    .buildKeepingLast();
            Long2ObjectMap<Map<BlockPos, TypedBlockArea>> sectionPositions = new Long2ObjectOpenHashMap<>(this.sectionPositions);
            addAllSections(torchPosition, sectionPositions);
            return new TorchPositions(torchPositions, sectionPositions);
        } else {
            return this;
        }
    }

    public TorchPositions remove(BlockPos blockPos, MagnumTorchType type) {
        TypedBlockArea oldType = this.torchPositions.get(blockPos);
        if (oldType != null && oldType.type() == type) {
            Map<BlockPos, TypedBlockArea> torchPositions = this.torchPositions.entrySet()
                    .stream()
                    .filter((Map.Entry<BlockPos, TypedBlockArea> entry) -> {
                        return !entry.getKey().equals(blockPos);
                    })
                    .collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, Map.Entry::getValue));
            Long2ObjectMap<Map<BlockPos, TypedBlockArea>> sectionPositions = new Long2ObjectOpenHashMap<>(this.sectionPositions);
            removeAllSections(oldType, sectionPositions);
            return new TorchPositions(torchPositions, sectionPositions);
        } else {
            return this;
        }
    }

    public static void onBlockStateChange(ServerLevel serverLevel, BlockPos pos, BlockState oldState, BlockState newState) {
        Optional<MagnumTorchType> oldType = getMagnumTorchType(oldState);
        Optional<MagnumTorchType> newType = getMagnumTorchType(newState);
        if (!Objects.equals(oldType, newType)) {
            BlockPos blockPos = pos.immutable();
            oldType.ifPresent((MagnumTorchType type) -> {
                serverLevel.getServer().execute(() -> {
                    TorchPositions torchPositions = ModRegistry.TORCH_POSITIONS_ATTACHMENT_TYPE.getOrDefault(serverLevel,
                            EMPTY);
                    ModRegistry.TORCH_POSITIONS_ATTACHMENT_TYPE.set(serverLevel, torchPositions.remove(blockPos, type));
                });
            });
            newType.ifPresent((MagnumTorchType type) -> {
                serverLevel.getServer().execute(() -> {
                    TorchPositions torchPositions = ModRegistry.TORCH_POSITIONS_ATTACHMENT_TYPE.getOrDefault(serverLevel,
                            EMPTY);
                    ModRegistry.TORCH_POSITIONS_ATTACHMENT_TYPE.set(serverLevel, torchPositions.add(blockPos, type));
                });
            });
        }
    }

    private static Optional<MagnumTorchType> getMagnumTorchType(BlockState blockState) {
        return blockState.getBlock() instanceof MagnumTorchBlock block ? Optional.of(block.getType()) :
                Optional.empty();
    }
}
