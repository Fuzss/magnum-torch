package fuzs.magnumtorch.data.client;

import fuzs.magnumtorch.MagnumTorch;
import fuzs.magnumtorch.init.ModRegistry;
import fuzs.puzzleslib.api.client.data.v2.AbstractModelProvider;
import fuzs.puzzleslib.api.client.data.v2.models.ModelTemplateHelper;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public class ModModelProvider extends AbstractModelProvider {
    public static final ModelTemplate MAGNUM_TORCH = ModelTemplateHelper.createBlockModelTemplate(MagnumTorch.id(
            "template_magnum_torch"), TextureSlot.SIDE);
    public static final TexturedModel.Provider MAGNUM_TORCH_SIDE = TexturedModel.createDefault((Block block) -> TextureMapping.singleSlot(
            TextureSlot.SIDE,
            TextureMapping.getBlockTexture(block, "_side")), MAGNUM_TORCH);

    public ModModelProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addBlockModels(BlockModelGenerators builder) {
        this.createMagnumTorch(ModRegistry.DIAMOND_MAGNUM_TORCH_BLOCK.value(), builder);
        this.createMagnumTorch(ModRegistry.EMERALD_MAGNUM_TORCH_BLOCK.value(), builder);
        this.createMagnumTorch(ModRegistry.AMETHYST_MAGNUM_TORCH_BLOCK.value(), builder);
    }

    private void createMagnumTorch(Block block, BlockModelGenerators builder) {
        ResourceLocation resourceLocation = MAGNUM_TORCH_SIDE.create(block, builder.modelOutput);
        builder.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, resourceLocation));
    }
}
