package fuzs.magnumtorch.fabric;

import fuzs.magnumtorch.common.MagnumTorch;
import fuzs.puzzleslib.common.api.core.v1.ModConstructor;
import net.fabricmc.api.ModInitializer;

public class MagnumTorchFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        ModConstructor.construct(MagnumTorch.MOD_ID, MagnumTorch::new);
    }
}
