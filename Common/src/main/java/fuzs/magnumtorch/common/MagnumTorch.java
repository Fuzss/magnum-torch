package fuzs.magnumtorch.common;

import fuzs.magnumtorch.common.config.ServerConfig;
import fuzs.magnumtorch.common.handler.MobSpawningHandler;
import fuzs.magnumtorch.common.init.ModRegistry;
import fuzs.puzzleslib.common.api.config.v3.ConfigHolder;
import fuzs.puzzleslib.common.api.core.v1.ModConstructor;
import net.minecraft.resources.Identifier;
import fuzs.puzzleslib.common.api.event.v1.entity.ServerEntityLevelEvents;
import fuzs.puzzleslib.common.api.event.v1.level.GatherPotentialSpawnsCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MagnumTorch implements ModConstructor {
    public static final String MOD_ID = "magnumtorch";
    public static final String MOD_NAME = "Magnum Torch";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static final ConfigHolder CONFIG = ConfigHolder.builder(MOD_ID).server(ServerConfig.class);

    @Override
    public void onConstructMod() {
        ModRegistry.bootstrap();
        registerEventHandlers();
    }

    private static void registerEventHandlers() {
        ServerEntityLevelEvents.LOAD.register(MobSpawningHandler::onEntityLoad);
        GatherPotentialSpawnsCallback.EVENT.register(MobSpawningHandler::onGatherPotentialSpawns);
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
