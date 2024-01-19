package fuzs.magnumtorch;

import fuzs.magnumtorch.config.ServerConfig;
import fuzs.magnumtorch.handler.MobSpawningHandler;
import fuzs.magnumtorch.init.ModRegistry;
import fuzs.puzzleslib.api.config.v3.ConfigHolder;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.puzzleslib.api.core.v1.context.CreativeModeTabContext;
import fuzs.puzzleslib.api.event.v1.entity.ServerEntityLevelEvents;
import fuzs.puzzleslib.api.item.v2.CreativeModeTabConfigurator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MagnumTorch implements ModConstructor {
    public static final String MOD_ID = "magnumtorch";
    public static final String MOD_NAME = "Magnum Torch";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static final ConfigHolder CONFIG = ConfigHolder.builder(MOD_ID).server(ServerConfig.class);

    @Override
    public void onConstructMod() {
        ModRegistry.touch();
        registerHandlers();
    }

    private static void registerHandlers() {
        ServerEntityLevelEvents.SPAWN.register(MobSpawningHandler::onEntitySpawn);
    }

    @Override
    public void onRegisterCreativeModeTabs(CreativeModeTabContext context) {
        context.registerCreativeModeTab(CreativeModeTabConfigurator.from(MOD_ID, () -> new ItemStack(ModRegistry.DIAMOND_MAGNUM_TORCH_ITEM)).displayItems((itemDisplayParameters, output) -> {
            output.accept(ModRegistry.DIAMOND_MAGNUM_TORCH_ITEM.value());
            output.accept(ModRegistry.EMERALD_MAGNUM_TORCH_ITEM.value());
            output.accept(ModRegistry.AMETHYST_MAGNUM_TORCH_ITEM.value());
        }));
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
