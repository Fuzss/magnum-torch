package fuzs.magnumtorch.client;

import fuzs.magnumtorch.MagnumTorch;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import net.fabricmc.api.ClientModInitializer;

public class MagnumTorchFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientModConstructor.construct(MagnumTorch.MOD_ID, MagnumTorchClient::new);
    }
}
