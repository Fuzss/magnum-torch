package fuzs.magnumtorch.data;

import fuzs.magnumtorch.init.ModRegistry;
import fuzs.puzzleslib.api.data.v2.AbstractLootProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;

public class ModLootTableProvider extends AbstractLootProvider.Blocks {

    public ModLootTableProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addLootTables() {
        this.dropSelf(ModRegistry.DIAMOND_MAGNUM_TORCH_BLOCK.value());
        this.dropSelf(ModRegistry.EMERALD_MAGNUM_TORCH_BLOCK.value());
        this.dropSelf(ModRegistry.AMETHYST_MAGNUM_TORCH_BLOCK.value());
    }
}
