package fuzs.magnumtorch.forge;

import fuzs.magnumtorch.MagnumTorch;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;

@Mod(MagnumTorch.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class MagnumTorchForge {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        ModConstructor.construct(MagnumTorch.MOD_ID, MagnumTorch::new);
    }
}
