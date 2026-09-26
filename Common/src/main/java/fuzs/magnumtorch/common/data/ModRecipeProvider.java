package fuzs.magnumtorch.common.data;

import fuzs.magnumtorch.common.init.ModRegistry;
import fuzs.puzzleslib.common.api.data.v3.recipes.AbstractRecipeProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;

public class ModRecipeProvider extends AbstractRecipeProvider {

    public ModRecipeProvider(BootstrapContext<Recipe<?>> recipeOutput, BootstrapContext<Advancement> advancementOutput) {
        super(recipeOutput, advancementOutput);
    }

    @Override
    public void buildRecipes() {
        this.magnumTorch(ModRegistry.DIAMOND_MAGNUM_TORCH_BLOCK.value(), Items.DIAMOND);
        this.magnumTorch(ModRegistry.EMERALD_MAGNUM_TORCH_BLOCK.value(), Items.EMERALD);
        this.magnumTorch(ModRegistry.AMETHYST_MAGNUM_TORCH_BLOCK.value(), Items.AMETHYST_SHARD);
    }

    public final void magnumTorch(ItemLike resultItem, ItemLike ingredientItem) {
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.DECORATIONS, resultItem)
                .define('L', ItemTags.LOGS)
                .define('T', Items.FIRE_CHARGE)
                .define('G', Items.GOLD_INGOT)
                .define('#', ingredientItem)
                .pattern("GTG")
                .pattern("#L#")
                .pattern("#L#")
                .unlockedBy(getHasName(ingredientItem), this.has(ingredientItem))
                .save(this.output);
    }
}
