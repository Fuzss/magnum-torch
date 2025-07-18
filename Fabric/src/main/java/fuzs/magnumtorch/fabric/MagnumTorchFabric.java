package fuzs.magnumtorch.fabric;

import fuzs.magnumtorch.MagnumTorch;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import net.fabricmc.api.ModInitializer;

public class MagnumTorchFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        ModConstructor.construct(MagnumTorch.MOD_ID, MagnumTorch::new);
    }
}
