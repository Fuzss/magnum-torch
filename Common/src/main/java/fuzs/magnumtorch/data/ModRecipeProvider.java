package fuzs.magnumtorch.data;

import fuzs.magnumtorch.init.ModRegistry;
import fuzs.puzzleslib.api.data.v2.AbstractRecipeProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;

public class ModRecipeProvider extends AbstractRecipeProvider {

    public ModRecipeProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addRecipes(RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(this.items(),
                        RecipeCategory.DECORATIONS,
                        ModRegistry.DIAMOND_MAGNUM_TORCH_BLOCK.value())
                .define('L', ItemTags.LOGS)
                .define('T', Items.FIRE_CHARGE)
                .define('G', Items.GOLD_INGOT)
                .define('#', Items.DIAMOND)
                .pattern("GTG")
                .pattern("#L#")
                .pattern("#L#")
                .unlockedBy(getHasName(Items.DIAMOND), this.has(Items.DIAMOND))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(this.items(),
                        RecipeCategory.DECORATIONS,
                        ModRegistry.EMERALD_MAGNUM_TORCH_BLOCK.value())
                .define('L', ItemTags.LOGS)
                .define('T', Items.FIRE_CHARGE)
                .define('G', Items.GOLD_INGOT)
                .define('#', Items.EMERALD)
                .pattern("GTG")
                .pattern("#L#")
                .pattern("#L#")
                .unlockedBy(getHasName(Items.EMERALD), this.has(Items.EMERALD))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(this.items(),
                        RecipeCategory.DECORATIONS,
                        ModRegistry.AMETHYST_MAGNUM_TORCH_BLOCK.value())
                .define('L', ItemTags.LOGS)
                .define('T', Items.FIRE_CHARGE)
                .define('G', Items.GOLD_INGOT)
                .define('#', Items.AMETHYST_SHARD)
                .pattern("GTG")
                .pattern("#L#")
                .pattern("#L#")
                .unlockedBy(getHasName(Items.AMETHYST_SHARD), this.has(Items.AMETHYST_SHARD))
                .save(recipeOutput);
    }
}
