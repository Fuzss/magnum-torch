package fuzs.magnumtorch.neoforge;

import fuzs.magnumtorch.MagnumTorch;
import fuzs.magnumtorch.data.ModBlockTagsProvider;
import fuzs.magnumtorch.data.ModLootTableProvider;
import fuzs.magnumtorch.data.ModRecipeProvider;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
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
