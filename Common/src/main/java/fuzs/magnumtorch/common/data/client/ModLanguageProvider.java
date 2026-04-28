package fuzs.magnumtorch.common.data.client;

import fuzs.magnumtorch.common.MagnumTorch;
import fuzs.magnumtorch.common.client.util.TorchTooltipHelper;
import fuzs.magnumtorch.common.init.ModRegistry;
import fuzs.puzzleslib.common.api.client.data.v2.AbstractLanguageProvider;
import fuzs.puzzleslib.common.api.data.v2.core.DataProviderContext;

public class ModLanguageProvider extends AbstractLanguageProvider {

    public ModLanguageProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addTranslations(TranslationBuilder builder) {
        builder.addCreativeModeTab(ModRegistry.CREATIVE_MODE_TAB, MagnumTorch.MOD_NAME);
        builder.add(ModRegistry.DIAMOND_MAGNUM_TORCH_BLOCK.value(), "Diamond Magnum Torch");
        builder.add(ModRegistry.EMERALD_MAGNUM_TORCH_BLOCK.value(), "Emerald Magnum Torch");
        builder.add(ModRegistry.AMETHYST_MAGNUM_TORCH_BLOCK.value(), "Amethyst Magnum Torch");
        builder.add(TorchTooltipHelper.TooltipComponent.DESCRIPTION.getTranslationKey(),
                "Prevents mob spawns in a huge area.");
        builder.add(TorchTooltipHelper.TooltipComponent.ADDITIONAL.getTranslationKey(),
                "Hold %s to view more information.");
        builder.add(TorchTooltipHelper.TooltipComponent.SHIFT.getTranslationKey(), "\u21E7 Shift");
        builder.add(TorchTooltipHelper.TooltipComponent.MOB_TYPES.getTranslationKey(), "Mob Type: %s");
        builder.add(TorchTooltipHelper.TooltipComponent.BLACKLIST.getTranslationKey(), "Blacklist: %s");
        builder.add(TorchTooltipHelper.TooltipComponent.WHITELIST.getTranslationKey(), "Whitelist: %s");
        builder.add(TorchTooltipHelper.TooltipComponent.SHAPE_TYPE.getTranslationKey(), "Shape Type: %s");
        builder.add(TorchTooltipHelper.TooltipComponent.HORIZONTAL_RANGE.getTranslationKey(),
                "Horizontal Block Range: %s");
        builder.add(TorchTooltipHelper.TooltipComponent.VERTICAL_RANGE.getTranslationKey(), "Vertical Block Range: %s");
    }
}
