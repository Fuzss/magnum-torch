package fuzs.magnumtorch.init;

import fuzs.magnumtorch.MagnumTorch;
import fuzs.magnumtorch.world.level.block.MagnumTorchBlock;
import fuzs.puzzleslib.client.core.ClientFactories;
import fuzs.puzzleslib.core.CommonFactories;
import fuzs.puzzleslib.init.RegistryManager;
import fuzs.puzzleslib.init.RegistryReference;
import fuzs.puzzleslib.init.builder.ModPoiTypeBuilder;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

import java.util.function.Supplier;

public class ModRegistry {
    private static final CreativeModeTab CREATIVE_MODE_TAB = ClientFactories.INSTANCE.creativeTab(MagnumTorch.MOD_ID, new Supplier<>() {

        @Override
        public ItemStack get() {
            return new ItemStack(DIAMOND_MAGNUM_TORCH_BLOCK.get());
        }
    });
    private static final RegistryManager REGISTRY = CommonFactories.INSTANCE.registration(MagnumTorch.MOD_ID);
    public static final RegistryReference<Block> DIAMOND_MAGNUM_TORCH_BLOCK = REGISTRY.registerBlockWithItem("diamond_magnum_torch", () -> new MagnumTorchBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.5F, 3.5F).sound(SoundType.WOOD).lightLevel((p_187431_) -> {
        return 10;
    }).noOcclusion()), CREATIVE_MODE_TAB);
    public static final RegistryReference<Block> EMERALD_MAGNUM_TORCH_BLOCK = REGISTRY.registerBlockWithItem("emerald_magnum_torch", () -> new MagnumTorchBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.5F, 3.5F).sound(SoundType.WOOD).lightLevel((p_187431_) -> {
        return 10;
    }).noOcclusion()), CREATIVE_MODE_TAB);
    public static final RegistryReference<Block> AMETHYST_MAGNUM_TORCH_BLOCK = REGISTRY.registerBlockWithItem("amethyst_magnum_torch", () -> new MagnumTorchBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.5F, 3.5F).sound(SoundType.WOOD).lightLevel((p_187431_) -> {
        return 10;
    }).noOcclusion()), CREATIVE_MODE_TAB);
    public static final RegistryReference<PoiType> DIAMOND_MAGNUM_TORCH_POI_TYPE = REGISTRY.registerPoiTypeBuilder("diamond_magnum_torch", () -> ModPoiTypeBuilder.of(0, 1, ModRegistry.DIAMOND_MAGNUM_TORCH_BLOCK.get()));
    public static final RegistryReference<PoiType> EMERALD_MAGNUM_TORCH_POI_TYPE = REGISTRY.registerPoiTypeBuilder("emerald_magnum_torch", () -> ModPoiTypeBuilder.of(0, 1, ModRegistry.EMERALD_MAGNUM_TORCH_BLOCK.get()));
    public static final RegistryReference<PoiType> AMETHYST_MAGNUM_TORCH_POI_TYPE = REGISTRY.registerPoiTypeBuilder("amethyst_magnum_torch", () -> ModPoiTypeBuilder.of(0, 1, ModRegistry.AMETHYST_MAGNUM_TORCH_BLOCK.get()));

    public static void touch() {

    }
}