package fuzs.magnumtorch.data;

import fuzs.magnumtorch.init.ModRegistry;
import fuzs.puzzleslib.api.data.v1.AbstractLootProvider;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

public class ModLootTableProvider extends AbstractLootProvider.Blocks {

    public ModLootTableProvider(GatherDataEvent evt, String modId) {
        super(evt, modId);
    }

    @Override
    public void generate() {
        this.dropSelf(ModRegistry.DIAMOND_MAGNUM_TORCH_BLOCK.get());
        this.dropSelf(ModRegistry.EMERALD_MAGNUM_TORCH_BLOCK.get());
        this.dropSelf(ModRegistry.AMETHYST_MAGNUM_TORCH_BLOCK.get());
    }
}
