package fuzs.magnumtorch.world.level.block;

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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
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
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class MagnumTorchBlock extends Block implements SimpleWaterloggedBlock {
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
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return direction == Direction.DOWN && !this.canSurvive(state, level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type) {
        return false;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        double d0 = (double)pos.getX() + 0.5D;
        double d1 = (double)pos.getY() + 1.125D;
        double d2 = (double)pos.getZ() + 0.5D;
        level.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0, 0.0, 0.0);
        level.addParticle(ParticleTypes.FLAME, d0, d1, d2, 0.0, 0.0, 0.0);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter level, List<Component> tooltip, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, level, tooltip, tooltipFlag);
        tooltip.add(Component.translatable("block.magnumtorch.magnum_torch.info").withStyle(ChatFormatting.GRAY));
        if (level != null) {
            if (!Proxy.INSTANCE.hasShiftDown()) {
                tooltip.add(Component.translatable("block.magnumtorch.magnum_torch.info.more", Component.translatable("block.magnumtorch.magnum_torch.info.shift").withStyle(ChatFormatting.YELLOW)).withStyle(ChatFormatting.GRAY));
            } else if (MagnumTorch.CONFIG.getHolder(ServerConfig.class).isAvailable()) {
                ServerConfig.MagnumTorchConfig config = this.type.getConfig();
                if (!config.mobCategories.isEmpty()) {
                    tooltip.add(Component.translatable("block.magnumtorch.magnum_torch.info.mob_types", mergeComponentList(config.mobCategories, ChatFormatting.YELLOW, Enum::name)).withStyle(ChatFormatting.GRAY));
                }
                if (!config.mobBlacklist.isEmpty()) {
                    tooltip.add(Component.translatable("block.magnumtorch.magnum_torch.info.blacklist", mergeComponentList(config.mobBlacklist, ChatFormatting.AQUA, value -> BuiltInRegistries.ENTITY_TYPE.getKey(value).toString())).withStyle(ChatFormatting.GRAY));
                }
                if (!config.mobWhitelist.isEmpty()) {
                    tooltip.add(Component.translatable("block.magnumtorch.magnum_torch.info.whitelist", mergeComponentList(config.mobWhitelist, ChatFormatting.AQUA, value -> BuiltInRegistries.ENTITY_TYPE.getKey(value).toString())).withStyle(ChatFormatting.GRAY));
                }
                tooltip.add(Component.translatable("block.magnumtorch.magnum_torch.info.shape_type", Component.literal(config.shapeType.name()).withStyle(ChatFormatting.GOLD)).withStyle(ChatFormatting.GRAY));
                tooltip.add(Component.translatable("block.magnumtorch.magnum_torch.info.horizontal_range", Component.literal(String.valueOf(config.horizontalRange)).withStyle(ChatFormatting.LIGHT_PURPLE)).withStyle(ChatFormatting.GRAY));
                tooltip.add(Component.translatable("block.magnumtorch.magnum_torch.info.vertical_range", Component.literal(String.valueOf(config.verticalRange)).withStyle(ChatFormatting.LIGHT_PURPLE)).withStyle(ChatFormatting.GRAY));
            }
        }
    }

    private static <T> Component mergeComponentList(Collection<? extends T> collection, ChatFormatting format, Function<T, String> keyExtractor) {
        return collection.stream()
                .map(mob -> Component.literal(keyExtractor.apply(mob))
                        .withStyle(format))
                .reduce((o1, o2) -> o1.append(", ").append(o2))
                .orElse(Component.empty());
    }
}
