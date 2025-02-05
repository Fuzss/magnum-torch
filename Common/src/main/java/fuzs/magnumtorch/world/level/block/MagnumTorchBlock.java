package fuzs.magnumtorch.world.level.block;

import com.google.common.base.Predicates;
import fuzs.magnumtorch.MagnumTorch;
import fuzs.magnumtorch.config.ServerConfig;
import fuzs.puzzleslib.api.core.v1.Proxy;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.function.Predicate;

public class MagnumTorchBlock extends Block implements SimpleWaterloggedBlock {
    public static final String TRANSLATION_KEY_MAGNUM_TORCH_INFO = "block.magnumtorch.magnum_torch.info";
    public static final String TRANSLATION_KEY_MAGNUM_TORCH_MORE = TRANSLATION_KEY_MAGNUM_TORCH_INFO + ".more";
    public static final String TRANSLATION_KEY_MAGNUM_TORCH_SHIFT = TRANSLATION_KEY_MAGNUM_TORCH_INFO + ".shift";
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final VoxelShape TORCH_AABB = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D);

    private final MagnumTorchType type;

    public MagnumTorchBlock(MagnumTorchType type, Properties properties) {
        super(properties);
        this.type = type;
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return TORCH_AABB;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(WATERLOGGED);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return canSupportCenter(level, pos.below(), Direction.UP);
    }

    @Override
    protected BlockState updateShape(BlockState blockState, LevelReader levelReader, ScheduledTickAccess scheduledTickAccess, BlockPos blockPos, Direction direction, BlockPos blockPos2, BlockState blockState2, RandomSource randomSource) {
        if (blockState.getValue(WATERLOGGED)) {
            scheduledTickAccess.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelReader));
        }
        return direction == Direction.DOWN && !this.canSurvive(blockState, levelReader, blockPos) ?
                Blocks.AIR.defaultBlockState() : super.updateShape(blockState,
                levelReader,
                scheduledTickAccess,
                blockPos,
                direction,
                blockPos2,
                blockState2,
                randomSource);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public boolean isPathfindable(BlockState state, PathComputationType type) {
        return false;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        double d0 = (double) pos.getX() + 0.5D;
        double d1 = (double) pos.getY() + 1.125D;
        double d2 = (double) pos.getZ() + 0.5D;
        level.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0, 0.0, 0.0);
        level.addParticle(ParticleTypes.FLAME, d0, d1, d2, 0.0, 0.0, 0.0);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltip, tooltipFlag);
        tooltip.add(Component.translatable(TRANSLATION_KEY_MAGNUM_TORCH_INFO).withStyle(ChatFormatting.GRAY));
        if (context != Item.TooltipContext.EMPTY) {
            if (!Proxy.INSTANCE.hasShiftDown()) {
                tooltip.add(Component.translatable(TRANSLATION_KEY_MAGNUM_TORCH_MORE,
                                Component.translatable(TRANSLATION_KEY_MAGNUM_TORCH_SHIFT).withStyle(ChatFormatting.YELLOW))
                        .withStyle(ChatFormatting.GRAY));
            } else if (MagnumTorch.CONFIG.getHolder(ServerConfig.class).isAvailable()) {
                ServerConfig.MagnumTorchConfig config = this.type.getConfig();
                for (DescriptionComponent descriptionComponent : DescriptionComponent.values()) {
                    if (descriptionComponent.notEmptyChecker.test(config)) {
                        tooltip.add(descriptionComponent.getComponent(config));
                    }
                }
            }
        }
    }

    public enum DescriptionComponent {
        MOB_TYPES((ServerConfig.MagnumTorchConfig config) -> !config.mobCategories.isEmpty(),
                (ServerConfig.MagnumTorchConfig config) -> {
                    return mergeComponentList(config.mobCategories, ChatFormatting.YELLOW, Enum::name);
                }),
        BLACKLIST((ServerConfig.MagnumTorchConfig config) -> !config.mobBlacklist.isEmpty(),
                (ServerConfig.MagnumTorchConfig config) -> {
                    return mergeComponentList(config.mobBlacklist,
                            ChatFormatting.AQUA,
                            (EntityType<?> entityType) -> BuiltInRegistries.ENTITY_TYPE.getKey(entityType).toString());
                }),
        WHITELIST((ServerConfig.MagnumTorchConfig config) -> !config.mobWhitelist.isEmpty(),
                (ServerConfig.MagnumTorchConfig config) -> {
                    return mergeComponentList(config.mobWhitelist,
                            ChatFormatting.AQUA,
                            (EntityType<?> entityType) -> BuiltInRegistries.ENTITY_TYPE.getKey(entityType).toString());
                }),
        SHAPE_TYPE((ServerConfig.MagnumTorchConfig config) -> {
            return Component.literal(config.shapeType.name()).withStyle(ChatFormatting.GOLD);
        }),
        HORIZONTAL_RANGE((ServerConfig.MagnumTorchConfig config) -> {
            return Component.literal(String.valueOf(config.horizontalRange)).withStyle(ChatFormatting.LIGHT_PURPLE);
        }),
        VERTICAL_RANGE((ServerConfig.MagnumTorchConfig config) -> {
            return Component.literal(String.valueOf(config.verticalRange)).withStyle(ChatFormatting.LIGHT_PURPLE);
        });

        final Predicate<ServerConfig.MagnumTorchConfig> notEmptyChecker;
        final Function<ServerConfig.MagnumTorchConfig, Component> componentFactory;

        DescriptionComponent(Function<ServerConfig.MagnumTorchConfig, Component> componentFactory) {
            this(Predicates.alwaysTrue(), componentFactory);
        }

        DescriptionComponent(Predicate<ServerConfig.MagnumTorchConfig> notEmptyChecker, Function<ServerConfig.MagnumTorchConfig, Component> componentFactory) {
            this.notEmptyChecker = notEmptyChecker;
            this.componentFactory = componentFactory;
        }

        @Override
        public String toString() {
            return this.name().toLowerCase(Locale.ROOT);
        }

        public String getTranslationKey() {
            return TRANSLATION_KEY_MAGNUM_TORCH_INFO + "." + this;
        }

        public Component getComponent(ServerConfig.MagnumTorchConfig config) {
            Component component = this.componentFactory.apply(config);
            return Component.translatable(this.getTranslationKey(), component).withStyle(ChatFormatting.GRAY);
        }

        private static <T> Component mergeComponentList(Collection<? extends T> collection, ChatFormatting format, Function<T, String> keyExtractor) {
            return collection.stream()
                    .map(mob -> Component.literal(keyExtractor.apply(mob)).withStyle(format))
                    .reduce((o1, o2) -> o1.append(", ").append(o2))
                    .orElse(Component.empty());
        }
    }
}
