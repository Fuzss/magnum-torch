package fuzs.magnumtorch.init;

import fuzs.magnumtorch.MagnumTorch;
import fuzs.magnumtorch.world.level.block.MagnumTorchBlock;
import fuzs.magnumtorch.world.level.block.MagnumTorchType;
import fuzs.puzzleslib.api.init.v3.registry.RegistryManager;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class ModRegistry {
    public static final RegistryManager REGISTRY = RegistryManager.from(MagnumTorch.MOD_ID);
    public static final Holder.Reference<Block> DIAMOND_MAGNUM_TORCH_BLOCK = REGISTRY.registerLazily(Registries.BLOCK, MagnumTorchType.DIAMOND.getSerializedName());
    public static final Holder.Reference<Block> EMERALD_MAGNUM_TORCH_BLOCK = REGISTRY.registerLazily(Registries.BLOCK, MagnumTorchType.EMERALD.getSerializedName());
    public static final Holder.Reference<Block> AMETHYST_MAGNUM_TORCH_BLOCK = REGISTRY.registerLazily(Registries.BLOCK, MagnumTorchType.AMETHYST.getSerializedName());
    public static final Holder.Reference<Item> DIAMOND_MAGNUM_TORCH_ITEM = REGISTRY.registerLazily(Registries.ITEM, MagnumTorchType.DIAMOND.getSerializedName());
    public static final Holder.Reference<Item> EMERALD_MAGNUM_TORCH_ITEM = REGISTRY.registerLazily(Registries.ITEM, MagnumTorchType.EMERALD.getSerializedName());
    public static final Holder.Reference<Item> AMETHYST_MAGNUM_TORCH_ITEM = REGISTRY.registerLazily(Registries.ITEM, MagnumTorchType.AMETHYST.getSerializedName());
    public static final Holder.Reference<PoiType> DIAMOND_MAGNUM_TORCH_POI_TYPE = REGISTRY.registerLazily(Registries.POINT_OF_INTEREST_TYPE, MagnumTorchType.DIAMOND.getSerializedName());
    public static final Holder.Reference<PoiType> EMERALD_MAGNUM_TORCH_POI_TYPE = REGISTRY.registerLazily(Registries.POINT_OF_INTEREST_TYPE, MagnumTorchType.EMERALD.getSerializedName());
    public static final Holder.Reference<PoiType> AMETHYST_MAGNUM_TORCH_POI_TYPE = REGISTRY.registerLazily(Registries.POINT_OF_INTEREST_TYPE, MagnumTorchType.AMETHYST.getSerializedName());

    public static void touch() {
        for (MagnumTorchType type : MagnumTorchType.values()) {
            Holder.Reference<Block> block = REGISTRY.registerBlock(type.getSerializedName(), () -> new MagnumTorchBlock(type, BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(2.5F, 3.5F).sound(SoundType.WOOD).lightLevel(state -> {
                return 10;
            }).noOcclusion().pushReaction(PushReaction.DESTROY)));
            REGISTRY.registerBlockItem(block);
            REGISTRY.registerPoiType(type.getSerializedName(), block::value);
        }
    }
}