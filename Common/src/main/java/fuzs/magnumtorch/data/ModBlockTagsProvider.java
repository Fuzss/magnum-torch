package fuzs.magnumtorch.data;

import fuzs.magnumtorch.init.ModRegistry;
import fuzs.puzzleslib.api.data.v2.AbstractTagProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;

public class ModBlockTagsProvider extends AbstractTagProvider.Blocks {

    public ModBlockTagsProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
        this.tag(BlockTags.MINEABLE_WITH_AXE).add(ModRegistry.DIAMOND_MAGNUM_TORCH_BLOCK.value(), ModRegistry.EMERALD_MAGNUM_TORCH_BLOCK.value(), ModRegistry.AMETHYST_MAGNUM_TORCH_BLOCK.value());
    }
}
