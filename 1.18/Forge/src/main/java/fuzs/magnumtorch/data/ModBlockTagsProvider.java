package fuzs.magnumtorch.data;

import fuzs.magnumtorch.init.ModRegistry;
import fuzs.puzzleslib.api.data.v1.AbstractTagProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

public class ModBlockTagsProvider extends AbstractTagProvider.Blocks {

    public ModBlockTagsProvider(GatherDataEvent evt, String modId) {
        super(evt, modId);
    }

    @Override
    protected void addTags() {
        this.tag(BlockTags.MINEABLE_WITH_AXE).add(ModRegistry.DIAMOND_MAGNUM_TORCH_BLOCK.get(), ModRegistry.EMERALD_MAGNUM_TORCH_BLOCK.get(), ModRegistry.AMETHYST_MAGNUM_TORCH_BLOCK.get());
    }
}
