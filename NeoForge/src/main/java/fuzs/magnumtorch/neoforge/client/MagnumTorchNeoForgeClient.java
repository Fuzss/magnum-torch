package fuzs.magnumtorch.neoforge.client;

import fuzs.magnumtorch.MagnumTorch;
import fuzs.magnumtorch.client.MagnumTorchClient;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLConstructModEvent;

@Mod.EventBusSubscriber(modid = MagnumTorch.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class MagnumTorchNeoForgeClient {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        ClientModConstructor.construct(MagnumTorch.MOD_ID, MagnumTorchClient::new);
    }
}
