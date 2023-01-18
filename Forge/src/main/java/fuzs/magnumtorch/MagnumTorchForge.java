package fuzs.magnumtorch;

import fuzs.magnumtorch.data.ModBlockTagsProvider;
import fuzs.magnumtorch.data.ModLootTableProvider;
import fuzs.magnumtorch.data.ModRecipeProvider;
import fuzs.magnumtorch.handler.MobSpawningHandler;
import fuzs.puzzleslib.core.CoreServices;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;

@Mod(MagnumTorch.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class MagnumTorchForge {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        CoreServices.FACTORIES.modConstructor(MagnumTorch.MOD_ID).accept(new MagnumTorch());
        registerHandlers();
    }

    private static void registerHandlers() {
        MinecraftForge.EVENT_BUS.addListener((final LivingSpawnEvent.CheckSpawn evt) -> {
            // handles most spawn attempts such as natural spawning at night, cats, phantoms, patrols and monster spawners
            if (!MobSpawningHandler.onLivingSpawn(evt.getEntity().getType(), evt.getLevel(), evt.getX(), evt.getY(), evt.getZ(), evt.getSpawnReason())) {
                evt.setResult(Event.Result.DENY);
            }
        });
        MinecraftForge.EVENT_BUS.addListener((final LivingSpawnEvent.SpecialSpawn evt) -> {
            // only respond to event firing from EntityType::spawn, other Forge implementations are broken, therefore specifically exclude those
            // this is fine since the ones we care about should already have been handled by CheckSpawn
            if (evt.getSpawnReason() == MobSpawnType.NATURAL || evt.getSpawnReason() == MobSpawnType.SPAWNER) return;
            // handles missing cases from CheckSpawn, most importantly wandering traders, zombie pigmen from portals
            if (!MobSpawningHandler.onLivingSpawn(evt.getEntity().getType(), evt.getLevel(), evt.getX(), evt.getY(), evt.getZ(), evt.getSpawnReason())) {
                evt.setCanceled(true);
            }
        });
    }

    @SubscribeEvent
    public static void onGatherData(final GatherDataEvent evt) {
        DataGenerator generator = evt.getGenerator();
        final ExistingFileHelper existingFileHelper = evt.getExistingFileHelper();
        generator.addProvider(true, new ModBlockTagsProvider(generator, MagnumTorch.MOD_ID, existingFileHelper));
        generator.addProvider(true, new ModLootTableProvider(generator, MagnumTorch.MOD_ID));
        generator.addProvider(true, new ModRecipeProvider(generator));
    }
}
