package fuzs.magnumtorch.fabric.client;

import fuzs.magnumtorch.common.MagnumTorch;
import fuzs.magnumtorch.common.client.MagnumTorchClient;
import fuzs.puzzleslib.common.api.client.core.v1.ClientModConstructor;
import net.fabricmc.api.ClientModInitializer;

public class MagnumTorchFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientModConstructor.construct(MagnumTorch.MOD_ID, MagnumTorchClient::new);
    }
}
