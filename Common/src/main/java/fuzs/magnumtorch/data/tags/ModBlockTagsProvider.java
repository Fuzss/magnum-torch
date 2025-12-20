package fuzs.magnumtorch.data.tags;

import fuzs.magnumtorch.init.ModRegistry;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import fuzs.puzzleslib.api.data.v2.tags.AbstractTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;

public class ModBlockTagsProvider extends AbstractTagProvider<Block> {

    public ModBlockTagsProvider(DataProviderContext context) {
        super(Registries.BLOCK, context);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
        this.add(ModRegistry.MAGNUM_TORCHES_BLOCK_TAG)
                .add(ModRegistry.DIAMOND_MAGNUM_TORCH_BLOCK.value(),
                        ModRegistry.EMERALD_MAGNUM_TORCH_BLOCK.value(),
                        ModRegistry.AMETHYST_MAGNUM_TORCH_BLOCK.value());
        this.add(BlockTags.MINEABLE_WITH_AXE).addTag(ModRegistry.MAGNUM_TORCHES_BLOCK_TAG);
    }
}
