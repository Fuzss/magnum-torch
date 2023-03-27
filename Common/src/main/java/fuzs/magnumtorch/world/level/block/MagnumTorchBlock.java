package fuzs.magnumtorch.world.level.block;

import fuzs.magnumtorch.MagnumTorch;
import fuzs.magnumtorch.config.ServerConfig;
import fuzs.magnumtorch.init.ModRegistry;
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
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("deprecation")
public class MagnumTorchBlock extends Block implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final VoxelShape TORCH_AABB = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D);

    public MagnumTorchBlock(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    public VoxelShape getShape(BlockState p_154346_, BlockGetter p_154347_, BlockPos p_154348_, CollisionContext p_154349_) {
        return TORCH_AABB;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_153490_) {
        p_153490_.add(WATERLOGGED);
    }

    @Override
    public boolean canSurvive(BlockState p_57499_, LevelReader p_57500_, BlockPos p_57501_) {
        return canSupportCenter(p_57500_, p_57501_.below(), Direction.UP);
    }

    @Override
    public BlockState updateShape(BlockState p_153739_, Direction p_153740_, BlockState p_153741_, LevelAccessor p_153742_, BlockPos p_153743_, BlockPos p_153744_) {
        if (p_153739_.getValue(WATERLOGGED)) {
            p_153742_.scheduleTick(p_153743_, Fluids.WATER, Fluids.WATER.getTickDelay(p_153742_));
        }
        return p_153740_ == Direction.DOWN && !this.canSurvive(p_153739_, p_153742_, p_153743_) ? Blocks.AIR.defaultBlockState() : super.updateShape(p_153739_, p_153740_, p_153741_, p_153742_, p_153743_, p_153744_);
    }

    @Override
    public FluidState getFluidState(BlockState p_153759_) {
        return p_153759_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(p_153759_);
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState p_153494_) {
        return PushReaction.DESTROY;
    }

    @Override
    public boolean isPathfindable(BlockState p_154341_, BlockGetter p_154342_, BlockPos p_154343_, PathComputationType p_154344_) {
        return false;
    }

    @Override
    public void animateTick(BlockState p_57494_, Level p_57495_, BlockPos p_57496_, RandomSource p_57497_) {
        double d0 = (double)p_57496_.getX() + 0.5D;
        double d1 = (double)p_57496_.getY() + 1.125D;
        double d2 = (double)p_57496_.getZ() + 0.5D;
        p_57495_.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0, 0.0, 0.0);
        p_57495_.addParticle(ParticleTypes.FLAME, d0, d1, d2, 0.0, 0.0, 0.0);
    }

    @Override
    public void appendHoverText(ItemStack p_56193_, @Nullable BlockGetter p_56194_, List<Component> tooltip, TooltipFlag p_56196_) {
        super.appendHoverText(p_56193_, p_56194_, tooltip, p_56196_);
        tooltip.add(Component.translatable("block.magnumtorch.magnum_torch.info").withStyle(ChatFormatting.GRAY));
        if (p_56194_ == null) return;
        if (!Proxy.INSTANCE.hasShiftDown()) {
            tooltip.add(Component.translatable("block.magnumtorch.magnum_torch.info.more", Component.translatable("block.magnumtorch.magnum_torch.info.shift").withStyle(ChatFormatting.YELLOW)).withStyle(ChatFormatting.GRAY));
        } else if (MagnumTorch.CONFIG.getHolder(ServerConfig.class).isAvailable()) {
            ServerConfig.MagnumTorchConfig config = getTorchConfig(this);
            if (!config.mobCategories.isEmpty()) {
                tooltip.add(Component.translatable("block.magnumtorch.magnum_torch.info.mob_types", config.mobCategories.stream()
                        .map(category -> Component.literal(category.name()).withStyle(ChatFormatting.YELLOW))
                        .reduce((o1, o2) -> o1.append(", ").append(o2))
                        .orElse(Component.empty())).withStyle(ChatFormatting.GRAY));
            }
            if (!config.mobBlacklist.isEmpty()) {
                tooltip.add(Component.translatable("block.magnumtorch.magnum_torch.info.blacklist", config.mobBlacklist.stream()
                        .map(mob -> Component.literal(BuiltInRegistries.ENTITY_TYPE.getKey(mob).toString()).withStyle(ChatFormatting.AQUA))
                        .reduce((o1, o2) -> o1.append(", ").append(o2))
                        .orElse(Component.empty())).withStyle(ChatFormatting.GRAY));
            }
            if (!config.mobWhitelist.isEmpty()) {
                tooltip.add(Component.translatable("block.magnumtorch.magnum_torch.info.whitelist", config.mobWhitelist.stream()
                        .map(mob -> Component.literal(BuiltInRegistries.ENTITY_TYPE.getKey(mob).toString()).withStyle(ChatFormatting.AQUA))
                        .reduce((o1, o2) -> o1.append(", ").append(o2))
                        .orElse(Component.empty())).withStyle(ChatFormatting.GRAY));
            }
            tooltip.add(Component.translatable("block.magnumtorch.magnum_torch.info.shape_type", Component.literal(config.shapeType.name()).withStyle(ChatFormatting.GOLD)).withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.translatable("block.magnumtorch.magnum_torch.info.horizontal_range", Component.literal(String.valueOf(config.horizontalRange)).withStyle(ChatFormatting.LIGHT_PURPLE)).withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.translatable("block.magnumtorch.magnum_torch.info.vertical_range", Component.literal(String.valueOf(config.verticalRange)).withStyle(ChatFormatting.LIGHT_PURPLE)).withStyle(ChatFormatting.GRAY));
        }
    }

    public static ServerConfig.MagnumTorchConfig getTorchConfig(MagnumTorchBlock block) {
        if (block == ModRegistry.DIAMOND_MAGNUM_TORCH_BLOCK.get()) return MagnumTorch.CONFIG.get(ServerConfig.class).diamond;
        if (block == ModRegistry.EMERALD_MAGNUM_TORCH_BLOCK.get()) return MagnumTorch.CONFIG.get(ServerConfig.class).emerald;
        if (block == ModRegistry.AMETHYST_MAGNUM_TORCH_BLOCK.get()) return MagnumTorch.CONFIG.get(ServerConfig.class).amethyst;
        throw new IllegalStateException("Torch type does not exist");
    }
}
