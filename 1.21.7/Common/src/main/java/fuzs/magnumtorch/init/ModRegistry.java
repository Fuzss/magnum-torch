package fuzs.magnumtorch.init;

import fuzs.magnumtorch.MagnumTorch;
import fuzs.magnumtorch.world.level.block.MagnumTorchBlock;
import fuzs.magnumtorch.world.level.block.MagnumTorchType;
import fuzs.puzzleslib.api.init.v3.registry.RegistryManager;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class ModRegistry {
    public static final RegistryManager REGISTRIES = RegistryManager.from(MagnumTorch.MOD_ID);
    public static final Holder.Reference<Block> DIAMOND_MAGNUM_TORCH_BLOCK = REGISTRIES.registerBlock(
            "diamond_magnum_torch",
            (BlockBehaviour.Properties properties) -> new MagnumTorchBlock(MagnumTorchType.DIAMOND, properties),
            ModRegistry::magnumTorchProperties);
    public static final Holder.Reference<Block> EMERALD_MAGNUM_TORCH_BLOCK = REGISTRIES.registerBlock(
            "emerald_magnum_torch",
            (BlockBehaviour.Properties properties) -> new MagnumTorchBlock(MagnumTorchType.EMERALD, properties),
            ModRegistry::magnumTorchProperties);
    public static final Holder.Reference<Block> AMETHYST_MAGNUM_TORCH_BLOCK = REGISTRIES.registerBlock(
            "amethyst_magnum_torch",
            (BlockBehaviour.Properties properties) -> new MagnumTorchBlock(MagnumTorchType.AMETHYST, properties),
            ModRegistry::magnumTorchProperties);
    public static final Holder.Reference<Item> DIAMOND_MAGNUM_TORCH_ITEM = REGISTRIES.registerBlockItem(
            DIAMOND_MAGNUM_TORCH_BLOCK);
    public static final Holder.Reference<Item> EMERALD_MAGNUM_TORCH_ITEM = REGISTRIES.registerBlockItem(
            EMERALD_MAGNUM_TORCH_BLOCK);
    public static final Holder.Reference<Item> AMETHYST_MAGNUM_TORCH_ITEM = REGISTRIES.registerBlockItem(
            AMETHYST_MAGNUM_TORCH_BLOCK);
    public static final Holder.Reference<PoiType> DIAMOND_MAGNUM_TORCH_POI_TYPE = REGISTRIES.registerPoiType(
            "diamond_magnum_torch",
            DIAMOND_MAGNUM_TORCH_BLOCK);
    public static final Holder.Reference<PoiType> EMERALD_MAGNUM_TORCH_POI_TYPE = REGISTRIES.registerPoiType(
            "emerald_magnum_torch",
            EMERALD_MAGNUM_TORCH_BLOCK);
    public static final Holder.Reference<PoiType> AMETHYST_MAGNUM_TORCH_POI_TYPE = REGISTRIES.registerPoiType(
            "amethyst_magnum_torch",
            AMETHYST_MAGNUM_TORCH_BLOCK);
    public static final Holder.Reference<CreativeModeTab> CREATIVE_MODE_TAB = REGISTRIES.registerCreativeModeTab(
            DIAMOND_MAGNUM_TORCH_ITEM);

    public static void bootstrap() {
        REGISTRIES.register(Registries.BLOCK_TYPE, "magnum_torch", () -> MagnumTorchBlock.CODEC);
    }

    private static BlockBehaviour.Properties magnumTorchProperties() {
        return BlockBehaviour.Properties.of()
                .mapColor(MapColor.WOOD)
                .instrument(NoteBlockInstrument.BASS)
                .strength(2.5F, 3.5F)
                .sound(SoundType.WOOD)
                .lightLevel((BlockState blockState) -> {
                    return 10;
                })
                .noOcclusion()
                .pushReaction(PushReaction.DESTROY);
    }
}