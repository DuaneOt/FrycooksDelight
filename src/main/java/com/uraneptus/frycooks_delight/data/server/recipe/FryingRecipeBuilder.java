package com.uraneptus.frycooks_delight.data.server.recipe;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.uraneptus.frycooks_delight.FrycooksDelight;
import com.uraneptus.frycooks_delight.core.registry.FCDRecipes;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings({"unused", "deprecation"})
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class FryingRecipeBuilder {
    private final List<Ingredient> ingredients = Lists.newArrayList();
    private final List<ItemStack> results = new ArrayList<>();
    private final RecipeSerializer<?> serializer;
    private String recipeGroup;

    private FryingRecipeBuilder(Item primaryItem, int count, RecipeSerializer<?> serializer) {
        this.results.add(0, new ItemStack(primaryItem, count));
        this.serializer = serializer;
    }

    public static FryingRecipeBuilder frying(Item primaryItem) {
        return frying(primaryItem, 1);
    }

    public static FryingRecipeBuilder frying(Item primaryItem, int count) {
        return new FryingRecipeBuilder(primaryItem, count, FCDRecipes.FRYING_SERIALIZER.get());
    }

    public FryingRecipeBuilder extraResult(ItemLike item) {
        return extraResult(item, 1);
    }

    public FryingRecipeBuilder extraResult(ItemLike item, int count) {
        this.results.add(new ItemStack(item, count));
        return this;
    }

    public FryingRecipeBuilder requires(TagKey<Item> pTag) {
        return this.requires(Ingredient.of(pTag));
    }

    public FryingRecipeBuilder requires(ItemLike pItem) {
        return this.requires(pItem, 1);
    }

    public FryingRecipeBuilder requires(ItemLike pItem, int pQuantity) {
        for(int i = 0; i < pQuantity; ++i) {
            this.requires(Ingredient.of(pItem));
        }
        return this;
    }

    public FryingRecipeBuilder requires(Ingredient pIngredient) {
        return this.requires(pIngredient, 1);
    }

    public FryingRecipeBuilder requires(Ingredient pIngredient, int pQuantity) {
        for(int i = 0; i < pQuantity; ++i) {
            this.ingredients.add(pIngredient);
        }
        return this;
    }

    public FryingRecipeBuilder group(String pGroupName) {
        this.recipeGroup = pGroupName;
        return this;
    }

    public void save(Consumer<FinishedRecipe> consumer) {
        save(consumer, "");
    }

    public void save(Consumer<FinishedRecipe> consumer, String suffix) {
        ResourceLocation resultLocation = ForgeRegistries.ITEMS.getKey(this.results.get(0).getItem());
        if (resultLocation != null) {
            consumer.accept(new FryingRecipeBuilder.Result(FrycooksDelight.modPrefix("frying/" + resultLocation.getPath() + suffix), this.serializer, this.recipeGroup == null ? "" : this.recipeGroup, this.ingredients, this.results));
        }
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final RecipeSerializer<?> serializer;
        private final String group;
        private final List<Ingredient> ingredients;
        private final List<ItemStack> results;

        public Result(ResourceLocation pId, RecipeSerializer<?> serializer, String pGroup, List<Ingredient> pIngredients, List<ItemStack> pResults) {
            this.id = pId;
            this.serializer = serializer;
            this.group = pGroup;
            this.ingredients = pIngredients;
            this.results = pResults;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            if (!this.group.isEmpty()) {
                pJson.addProperty("group", this.group);
            }

            JsonArray jsonarray = new JsonArray();
            for(Ingredient ingredient : this.ingredients) {
                jsonarray.add(ingredient.toJson());
            }
            pJson.add("ingredients", jsonarray);

            JsonArray resultsArray = new JsonArray();
            for (ItemStack stack : this.results) {
                JsonObject obj = new JsonObject();
                obj.addProperty("item", ForgeRegistries.ITEMS.getKey(stack.getItem()).toString());
                if (stack.getCount() > 1) {
                    obj.addProperty("count", stack.getCount());
                }
                resultsArray.add(obj);
            }
            pJson.add("result", resultsArray);

        }

        @Override
        public ResourceLocation getId() {
            return this.id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return this.serializer;
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return null;
        }
    }
}
