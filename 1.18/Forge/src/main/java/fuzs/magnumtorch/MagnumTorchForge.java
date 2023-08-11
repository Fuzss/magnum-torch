package fuzs.magnumtorch;

import fuzs.magnumtorch.data.ModBlockTagsProvider;
import fuzs.magnumtorch.data.ModLootTableProvider;
import fuzs.magnumtorch.data.ModRecipeProvider;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod(MagnumTorch.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class MagnumTorchForge {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        ModConstructor.construct(MagnumTorch.MOD_ID, MagnumTorch::new);
    }

    @SubscribeEvent
    public static void onGatherData(final GatherDataEvent evt) {
        evt.getGenerator().addProvider(new ModBlockTagsProvider(evt, MagnumTorch.MOD_ID));
        evt.getGenerator().addProvider(new ModLootTableProvider(evt, MagnumTorch.MOD_ID));
        evt.getGenerator().addProvider(new ModRecipeProvider(evt, MagnumTorch.MOD_ID));
    }
}
