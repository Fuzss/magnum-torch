package fuzs.magnumtorch.common.data;

import fuzs.magnumtorch.common.init.ModRegistry;
import fuzs.puzzleslib.common.api.data.v2.AbstractRecipeProvider;
import fuzs.puzzleslib.common.api.data.v2.core.DataProviderContext;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

public class ModRecipeProvider extends AbstractRecipeProvider {

    public ModRecipeProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addRecipes(RecipeOutput recipeOutput) {
        this.magnumTorch(recipeOutput, ModRegistry.DIAMOND_MAGNUM_TORCH_BLOCK.value(), Items.DIAMOND);
        this.magnumTorch(recipeOutput, ModRegistry.EMERALD_MAGNUM_TORCH_BLOCK.value(), Items.EMERALD);
        this.magnumTorch(recipeOutput, ModRegistry.AMETHYST_MAGNUM_TORCH_BLOCK.value(), Items.AMETHYST_SHARD);
    }

    public final void magnumTorch(RecipeOutput recipeOutput, ItemLike resultItem, ItemLike ingredientItem) {
        ShapedRecipeBuilder.shaped(this.items(), RecipeCategory.DECORATIONS, resultItem)
                .define('L', ItemTags.LOGS)
                .define('T', Items.FIRE_CHARGE)
                .define('G', Items.GOLD_INGOT)
                .define('#', ingredientItem)
                .pattern("GTG")
                .pattern("#L#")
                .pattern("#L#")
                .unlockedBy(getHasName(ingredientItem), this.has(ingredientItem))
                .save(recipeOutput);
    }
}
