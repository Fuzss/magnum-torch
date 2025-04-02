package fuzs.magnumtorch.client;

import fuzs.magnumtorch.client.handler.TorchTooltipHandler;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.api.client.event.v1.gui.ItemTooltipCallback;

public class MagnumTorchClient implements ClientModConstructor {

    @Override
    public void onClientSetup() {
        registerEventHandlers();
    }

    private static void registerEventHandlers() {
        ItemTooltipCallback.EVENT.register(TorchTooltipHandler::onItemTooltip);
    }
}
