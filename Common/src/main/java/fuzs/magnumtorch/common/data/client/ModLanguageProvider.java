package fuzs.magnumtorch.common.data.client;

import fuzs.magnumtorch.common.MagnumTorch;
import fuzs.magnumtorch.common.client.util.TorchTooltipHelper;
import fuzs.magnumtorch.common.init.ModRegistry;
import fuzs.puzzleslib.common.api.client.data.v3.language.AbstractLanguageProvider;
import fuzs.puzzleslib.common.api.data.v3.core.DataProviderContext;

public class ModLanguageProvider extends AbstractLanguageProvider {

    public ModLanguageProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addTranslations() {
        this.addCreativeModeTab(ModRegistry.CREATIVE_MODE_TAB, MagnumTorch.MOD_NAME);
        this.add(ModRegistry.DIAMOND_MAGNUM_TORCH_BLOCK.value(), "Diamond Magnum Torch");
        this.add(ModRegistry.EMERALD_MAGNUM_TORCH_BLOCK.value(), "Emerald Magnum Torch");
        this.add(ModRegistry.AMETHYST_MAGNUM_TORCH_BLOCK.value(), "Amethyst Magnum Torch");
        this.add(TorchTooltipHelper.TooltipComponent.DESCRIPTION.getTranslationKey(),
                "Prevents mob spawns in a huge area.");
        this.add(TorchTooltipHelper.TooltipComponent.ADDITIONAL.getTranslationKey(),
                "Hold %s to view more information.");
        this.add(TorchTooltipHelper.TooltipComponent.SHIFT.getTranslationKey(), "\u21E7 Shift");
        this.add(TorchTooltipHelper.TooltipComponent.MOB_TYPES.getTranslationKey(), "Mob Type: %s");
        this.add(TorchTooltipHelper.TooltipComponent.BLACKLIST.getTranslationKey(), "Blacklist: %s");
        this.add(TorchTooltipHelper.TooltipComponent.WHITELIST.getTranslationKey(), "Whitelist: %s");
        this.add(TorchTooltipHelper.TooltipComponent.SHAPE_TYPE.getTranslationKey(), "Shape Type: %s");
        this.add(TorchTooltipHelper.TooltipComponent.HORIZONTAL_RANGE.getTranslationKey(),
                "Horizontal Block Range: %s");
        this.add(TorchTooltipHelper.TooltipComponent.VERTICAL_RANGE.getTranslationKey(), "Vertical Block Range: %s");
    }
}
