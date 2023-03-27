package fuzs.magnumtorch.client;

import fuzs.magnumtorch.MagnumTorch;
import fuzs.magnumtorch.init.ModRegistry;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.api.client.core.v1.context.BuildCreativeModeTabContentsContext;

public class MagnumTorchClient implements ClientModConstructor {

    @Override
    public void onBuildCreativeModeTabContents(BuildCreativeModeTabContentsContext context) {
        context.registerBuildListener(MagnumTorch.MOD_ID, (featureFlagSet, output, bl) -> {
            output.accept(ModRegistry.DIAMOND_MAGNUM_TORCH_ITEM.get());
            output.accept(ModRegistry.EMERALD_MAGNUM_TORCH_ITEM.get());
            output.accept(ModRegistry.AMETHYST_MAGNUM_TORCH_ITEM.get());
        });
    }
}
