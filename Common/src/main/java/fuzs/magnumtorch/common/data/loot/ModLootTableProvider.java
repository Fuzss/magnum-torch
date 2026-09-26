package fuzs.magnumtorch.common.data.loot;

import fuzs.magnumtorch.common.init.ModRegistry;
import fuzs.puzzleslib.common.api.data.v3.loot.AbstractBlockLootSubProvider;
import net.minecraft.data.loot.LootTableSubProvider;

public class ModLootTableProvider extends AbstractBlockLootSubProvider {

    public ModLootTableProvider(LootTableSubProvider.Context context) {
        super(context);
    }

    @Override
    public void generate() {
        this.dropSelf(ModRegistry.DIAMOND_MAGNUM_TORCH_BLOCK.value());
        this.dropSelf(ModRegistry.EMERALD_MAGNUM_TORCH_BLOCK.value());
        this.dropSelf(ModRegistry.AMETHYST_MAGNUM_TORCH_BLOCK.value());
    }
}
