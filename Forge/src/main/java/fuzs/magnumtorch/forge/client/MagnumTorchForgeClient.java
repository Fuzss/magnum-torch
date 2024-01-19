package fuzs.magnumtorch.forge.client;

import fuzs.magnumtorch.MagnumTorch;
import fuzs.magnumtorch.client.MagnumTorchClient;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;

@Mod.EventBusSubscriber(modid = MagnumTorch.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class MagnumTorchForgeClient {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        ClientModConstructor.construct(MagnumTorch.MOD_ID, MagnumTorchClient::new);
    }
}
