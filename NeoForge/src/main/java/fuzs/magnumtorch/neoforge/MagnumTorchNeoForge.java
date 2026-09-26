package fuzs.magnumtorch.neoforge;

import fuzs.magnumtorch.common.MagnumTorch;
import fuzs.magnumtorch.common.data.tags.ModBlockTagsProvider;
import fuzs.magnumtorch.common.data.loot.ModLootTableProvider;
import fuzs.magnumtorch.common.data.ModRecipeProvider;
import fuzs.puzzleslib.common.api.core.v1.ModConstructor;
import fuzs.puzzleslib.neoforge.api.data.v3.core.DataProviderBuilder;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.fml.common.Mod;

@Mod(MagnumTorch.MOD_ID)
public class MagnumTorchNeoForge {

    public MagnumTorchNeoForge() {
        ModConstructor.construct(MagnumTorch.MOD_ID, MagnumTorch::new);
        DataProviderBuilder.of(MagnumTorch.MOD_ID)
                .addProvider(ModBlockTagsProvider::new)
                .addLootProvider(ModLootTableProvider::new, LootContextParamSets.BLOCK)
                .addRecipeProvider(ModRecipeProvider::new);
    }
}
