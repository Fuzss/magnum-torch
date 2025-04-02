package fuzs.magnumtorch.data.client;

import fuzs.magnumtorch.MagnumTorch;
import fuzs.magnumtorch.client.util.TorchTooltipHelper;
import fuzs.magnumtorch.init.ModRegistry;
import fuzs.puzzleslib.api.client.data.v2.AbstractLanguageProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;

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
        builder.add(TorchTooltipHelper.TRANSLATION_KEY_MAGNUM_TORCH_INFO, "Prevents mob spawns in a huge area.");
        builder.add(TorchTooltipHelper.TRANSLATION_KEY_MAGNUM_TORCH_MORE, "Hold %s for more information");
        builder.add(TorchTooltipHelper.TRANSLATION_KEY_MAGNUM_TORCH_SHIFT, "Shift");
        builder.add(TorchTooltipHelper.DescriptionComponent.MOB_TYPES.getTranslationKey(), "Mob Type(s): %s");
        builder.add(TorchTooltipHelper.DescriptionComponent.BLACKLIST.getTranslationKey(), "Blacklist: %s");
        builder.add(TorchTooltipHelper.DescriptionComponent.WHITELIST.getTranslationKey(), "Whitelist: %s");
        builder.add(TorchTooltipHelper.DescriptionComponent.SHAPE_TYPE.getTranslationKey(), "Shape Type: %s");
        builder.add(TorchTooltipHelper.DescriptionComponent.HORIZONTAL_RANGE.getTranslationKey(), "Horizontal Range: %s");
        builder.add(TorchTooltipHelper.DescriptionComponent.VERTICAL_RANGE.getTranslationKey(), "Vertical Range: %s");
    }
}
