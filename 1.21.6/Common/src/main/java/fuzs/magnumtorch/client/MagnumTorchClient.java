package fuzs.magnumtorch.client;

import fuzs.magnumtorch.client.util.TorchTooltipHelper;
import fuzs.magnumtorch.world.level.block.MagnumTorchBlock;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.api.client.gui.v2.tooltip.ItemTooltipRegistry;

public class MagnumTorchClient implements ClientModConstructor {

    @Override
    public void onClientSetup() {
        ItemTooltipRegistry.registerItemTooltip(MagnumTorchBlock.class, TorchTooltipHelper::appendHoverText);
    }
}
