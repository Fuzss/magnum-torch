package fuzs.magnumtorch.init;

import fuzs.magnumtorch.MagnumTorch;
import fuzs.magnumtorch.world.level.block.MagnumTorchBlock;
import fuzs.puzzleslib.api.init.v2.RegistryManager;
import fuzs.puzzleslib.api.init.v2.RegistryReference;
import fuzs.puzzleslib.api.init.v2.builder.PoiTypeBuilder;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class ModRegistry {
    static final RegistryManager REGISTRY = RegistryManager.instant(MagnumTorch.MOD_ID);
    public static final RegistryReference<Block> DIAMOND_MAGNUM_TORCH_BLOCK = REGISTRY.registerBlock("diamond_magnum_torch", () -> new MagnumTorchBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.5F, 3.5F).sound(SoundType.WOOD).lightLevel((p_187431_) -> {
        return 10;
    }).noOcclusion()));
    public static final RegistryReference<Block> EMERALD_MAGNUM_TORCH_BLOCK = REGISTRY.registerBlock("emerald_magnum_torch", () -> new MagnumTorchBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.5F, 3.5F).sound(SoundType.WOOD).lightLevel((p_187431_) -> {
        return 10;
    }).noOcclusion()));
    public static final RegistryReference<Block> AMETHYST_MAGNUM_TORCH_BLOCK = REGISTRY.registerBlock("amethyst_magnum_torch", () -> new MagnumTorchBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.5F, 3.5F).sound(SoundType.WOOD).lightLevel((p_187431_) -> {
        return 10;
    }).noOcclusion()));
    public static final RegistryReference<Item> DIAMOND_MAGNUM_TORCH_ITEM = REGISTRY.registerBlockItem(DIAMOND_MAGNUM_TORCH_BLOCK);
    public static final RegistryReference<Item> EMERALD_MAGNUM_TORCH_ITEM = REGISTRY.registerBlockItem(EMERALD_MAGNUM_TORCH_BLOCK);
    public static final RegistryReference<Item> AMETHYST_MAGNUM_TORCH_ITEM = REGISTRY.registerBlockItem(AMETHYST_MAGNUM_TORCH_BLOCK);
    public static final RegistryReference<PoiType> DIAMOND_MAGNUM_TORCH_POI_TYPE = REGISTRY.registerPoiTypeBuilder("diamond_magnum_torch", () -> PoiTypeBuilder.of(0, 1, ModRegistry.DIAMOND_MAGNUM_TORCH_BLOCK.get()));
    public static final RegistryReference<PoiType> EMERALD_MAGNUM_TORCH_POI_TYPE = REGISTRY.registerPoiTypeBuilder("emerald_magnum_torch", () -> PoiTypeBuilder.of(0, 1, ModRegistry.EMERALD_MAGNUM_TORCH_BLOCK.get()));
    public static final RegistryReference<PoiType> AMETHYST_MAGNUM_TORCH_POI_TYPE = REGISTRY.registerPoiTypeBuilder("amethyst_magnum_torch", () -> PoiTypeBuilder.of(0, 1, ModRegistry.AMETHYST_MAGNUM_TORCH_BLOCK.get()));

    public static void touch() {

    }
}