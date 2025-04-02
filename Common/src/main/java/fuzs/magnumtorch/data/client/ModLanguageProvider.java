package fuzs.magnumtorch.data.client;

import fuzs.magnumtorch.MagnumTorch;
import fuzs.magnumtorch.client.handler.TorchTooltipHandler;
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
        builder.add(TorchTooltipHandler.TRANSLATION_KEY_MAGNUM_TORCH_INFO, "Prevents mob spawns in a huge area.");
        builder.add(TorchTooltipHandler.TRANSLATION_KEY_MAGNUM_TORCH_MORE, "Hold %s for more information");
        builder.add(TorchTooltipHandler.TRANSLATION_KEY_MAGNUM_TORCH_SHIFT, "Shift");
        builder.add(TorchTooltipHandler.DescriptionComponent.MOB_TYPES.getTranslationKey(), "Mob Type(s): %s");
        builder.add(TorchTooltipHandler.DescriptionComponent.BLACKLIST.getTranslationKey(), "Blacklist: %s");
        builder.add(TorchTooltipHandler.DescriptionComponent.WHITELIST.getTranslationKey(), "Whitelist: %s");
        builder.add(TorchTooltipHandler.DescriptionComponent.SHAPE_TYPE.getTranslationKey(), "Shape Type: %s");
        builder.add(TorchTooltipHandler.DescriptionComponent.HORIZONTAL_RANGE.getTranslationKey(), "Horizontal Range: %s");
        builder.add(TorchTooltipHandler.DescriptionComponent.VERTICAL_RANGE.getTranslationKey(), "Vertical Range: %s");
    }
}
