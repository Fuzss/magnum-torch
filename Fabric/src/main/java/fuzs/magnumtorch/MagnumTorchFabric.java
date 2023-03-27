package fuzs.magnumtorch;

import fuzs.magnumtorch.api.event.player.LivingCheckSpawnCallback;
import fuzs.magnumtorch.handler.MobSpawningHandler;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import net.fabricmc.api.ModInitializer;

public class MagnumTorchFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        ModConstructor.construct(MagnumTorch.MOD_ID, MagnumTorch::new);
        registerHandlers();
    }

    private static void registerHandlers() {
        LivingCheckSpawnCallback.EVENT.register(MobSpawningHandler::onLivingSpawn);
    }
}
