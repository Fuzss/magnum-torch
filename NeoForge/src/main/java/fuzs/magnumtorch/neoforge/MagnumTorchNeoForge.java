package fuzs.magnumtorch.neoforge;

import fuzs.magnumtorch.common.MagnumTorch;
import fuzs.magnumtorch.common.data.tags.ModBlockTagsProvider;
import fuzs.magnumtorch.common.data.loot.ModLootTableProvider;
import fuzs.magnumtorch.common.data.ModRecipeProvider;
import fuzs.puzzleslib.common.api.core.v1.ModConstructor;
import fuzs.puzzleslib.neoforge.api.data.v2.core.DataProviderHelper;
import net.neoforged.fml.common.Mod;

@Mod(MagnumTorch.MOD_ID)
public class MagnumTorchNeoForge {

    public MagnumTorchNeoForge() {
        ModConstructor.construct(MagnumTorch.MOD_ID, MagnumTorch::new);
        DataProviderHelper.registerDataProviders(MagnumTorch.MOD_ID,
                ModBlockTagsProvider::new,
                ModLootTableProvider::new,
                ModRecipeProvider::new);
    }
}
