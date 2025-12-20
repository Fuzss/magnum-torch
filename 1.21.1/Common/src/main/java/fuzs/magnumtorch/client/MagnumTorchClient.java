package fuzs.magnumtorch.client;

import fuzs.magnumtorch.client.util.ItemTooltipRegistry;
import fuzs.magnumtorch.client.util.TorchTooltipHelper;
import fuzs.magnumtorch.world.level.block.MagnumTorchBlock;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;

public class MagnumTorchClient implements ClientModConstructor {

    @Override
    public void onClientSetup() {
        ItemTooltipRegistry.BLOCK.registerItemTooltipLines(MagnumTorchBlock.class, TorchTooltipHelper::appendHoverText);
    }
}
