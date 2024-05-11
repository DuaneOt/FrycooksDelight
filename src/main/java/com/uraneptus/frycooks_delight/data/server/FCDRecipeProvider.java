package com.uraneptus.frycooks_delight.data.server;

import com.uraneptus.frycooks_delight.core.registry.FCDBlocks;
import com.uraneptus.frycooks_delight.core.registry.FCDItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import vectorwing.farmersdelight.common.crafting.CuttingBoardRecipe;
import vectorwing.farmersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.tag.ForgeTags;
import vectorwing.farmersdelight.data.builder.CookingPotRecipeBuilder;
import vectorwing.farmersdelight.data.builder.CuttingBoardRecipeBuilder;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.uraneptus.frycooks_delight.data.FCDDatagenUtil.*;

@SuppressWarnings("SameParameterValue")
public class FCDRecipeProvider extends RecipeProvider {

    public FCDRecipeProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.YELLOW_DYE).requires(FCDItems.CANOLA.get()).unlockedBy(getHasName(FCDItems.CANOLA.get()), has(FCDItems.CANOLA.get())).save(consumer);
        packableBlockRecipes(FCDItems.CANOLA, FCDBlocks.CANOLA_CRATE.get().asItem(), consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(FCDItems.CANOLA.get()), Ingredient.of(ForgeTags.TOOLS_KNIVES), FCDItems.CANOLA_SEEDS.get(), 2).addResult(Items.YELLOW_DYE, 1).build(consumer);
        CookingPotRecipeBuilder.cookingPotRecipe(FCDItems.CANOLA_OIL.get(), 1, 200, 1.0F, Items.GLASS_BOTTLE).addIngredient(FCDItems.CANOLA_SEEDS.get(), 3).unlockedBy(getHasName(FCDItems.CANOLA_SEEDS.get()), has(FCDItems.CANOLA_SEEDS.get())).build(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(FCDBlocks.WILD_CANOLA.get()), Ingredient.of(ForgeTags.TOOLS_KNIVES), FCDItems.CANOLA_SEEDS.get(), 1).addResultWithChance(Items.YELLOW_DYE, 0.5F).build(consumer);

    }

    protected static void packableBlockRecipes(Supplier<? extends ItemLike> unpacked, ItemLike packed, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, packed).define('#', unpacked.get()).pattern("###").pattern("###").pattern("###")
                .unlockedBy(getHasName(unpacked.get()), has(unpacked.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, unpacked.get(), 9).requires(packed)
                .unlockedBy(getHasName(packed), has(packed)).save(consumer);
    }
}
