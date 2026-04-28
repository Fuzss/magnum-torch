package fuzs.magnumtorch.common.data.tags;

import fuzs.magnumtorch.common.init.ModRegistry;
import fuzs.puzzleslib.common.api.data.v2.core.DataProviderContext;
import fuzs.puzzleslib.common.api.data.v2.tags.AbstractTagProvider;
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
        this.tag(ModRegistry.MAGNUM_TORCHES_BLOCK_TAG)
                .add(ModRegistry.DIAMOND_MAGNUM_TORCH_BLOCK.value(),
                        ModRegistry.EMERALD_MAGNUM_TORCH_BLOCK.value(),
                        ModRegistry.AMETHYST_MAGNUM_TORCH_BLOCK.value());
        this.tag(BlockTags.MINEABLE_WITH_AXE).addTag(ModRegistry.MAGNUM_TORCHES_BLOCK_TAG);
    }
}
