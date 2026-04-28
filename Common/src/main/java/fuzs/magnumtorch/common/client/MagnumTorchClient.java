package fuzs.magnumtorch.common.client;

import fuzs.magnumtorch.common.client.util.TorchTooltipHelper;
import fuzs.magnumtorch.common.world.level.block.MagnumTorchBlock;
import fuzs.puzzleslib.common.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.common.api.client.gui.v2.tooltip.ItemTooltipRegistry;

public class MagnumTorchClient implements ClientModConstructor {

    @Override
    public void onClientSetup() {
        ItemTooltipRegistry.BLOCK.registerItemTooltipLines(MagnumTorchBlock.class, TorchTooltipHelper::appendHoverText);
    }
}
