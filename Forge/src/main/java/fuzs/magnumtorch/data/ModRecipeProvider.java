package fuzs.magnumtorch.data;

import fuzs.magnumtorch.init.ModRegistry;
import fuzs.puzzleslib.api.data.v1.AbstractRecipeProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

public class ModRecipeProvider extends AbstractRecipeProvider {

    public ModRecipeProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> exporter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModRegistry.DIAMOND_MAGNUM_TORCH_BLOCK.get())
                .define('L', ItemTags.LOGS)
                .define('T', Items.FIRE_CHARGE)
                .define('G', Items.GOLD_INGOT)
                .define('#', Items.DIAMOND)
                .pattern("GTG")
                .pattern("#L#")
                .pattern("#L#")
                .unlockedBy(getHasName(Items.DIAMOND), has(Items.DIAMOND))
                .save(exporter);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModRegistry.EMERALD_MAGNUM_TORCH_BLOCK.get())
                .define('L', ItemTags.LOGS)
                .define('T', Items.FIRE_CHARGE)
                .define('G', Items.GOLD_INGOT)
                .define('#', Items.EMERALD)
                .pattern("GTG")
                .pattern("#L#")
                .pattern("#L#")
                .unlockedBy(getHasName(Items.EMERALD), has(Items.EMERALD))
                .save(exporter);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModRegistry.AMETHYST_MAGNUM_TORCH_BLOCK.get())
                .define('L', ItemTags.LOGS)
                .define('T', Items.FIRE_CHARGE)
                .define('G', Items.GOLD_INGOT)
                .define('#', Items.AMETHYST_SHARD)
                .pattern("GTG")
                .pattern("#L#")
                .pattern("#L#")
                .unlockedBy(getHasName(Items.AMETHYST_SHARD), has(Items.AMETHYST_SHARD))
                .save(exporter);
    }
}
