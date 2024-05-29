package com.uraneptus.frycooks_delight.data.server.recipe;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.uraneptus.frycooks_delight.FrycooksDelight;
import com.uraneptus.frycooks_delight.core.registry.FCDRecipes;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings({"unused", "deprecation"})
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class FryingRecipeBuilder {
    private final RecipeCategory category;
    private final List<Ingredient> ingredients = Lists.newArrayList();
    private final Item result;
    private final int count;
    private final RecipeSerializer<?> serializer;
    private String recipeGroup;

    private FryingRecipeBuilder(RecipeCategory pCategory, ItemLike result, int count, RecipeSerializer<?> serializer) {
        this.category = pCategory;
        this.result = result.asItem();
        this.count = count;
        this.serializer = serializer;
    }

    public static FryingRecipeBuilder frying(RecipeCategory pCategory, ItemLike result) {
        return frying(pCategory, result, 1);
    }

    public static FryingRecipeBuilder fryingWithoutCount(RecipeCategory pCategory, ItemLike result) {
        return frying(pCategory, result, 1);
    }

    public static FryingRecipeBuilder frying(RecipeCategory pCategory, ItemLike result, int count) {
        return new FryingRecipeBuilder(pCategory, result, count, FCDRecipes.FRYING_SERIALIZER.get());
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
        ResourceLocation resultLocation = ForgeRegistries.ITEMS.getKey(this.result);
        if (resultLocation != null) {
            consumer.accept(new FryingRecipeBuilder.Result(FrycooksDelight.modPrefix("frying/" + resultLocation.getPath() + suffix), this.serializer, this.recipeGroup == null ? "" : this.recipeGroup, this.ingredients, this.result, this.count));
        }
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final RecipeSerializer<?> serializer;
        private final String group;
        private final List<Ingredient> ingredients;
        private final Item result;
        private final int count;

        public Result(ResourceLocation pId, RecipeSerializer<?> serializer, String pGroup, List<Ingredient> pIngredients, Item pResult, int count) {
            this.id = pId;
            this.serializer = serializer;
            this.group = pGroup;
            this.ingredients = pIngredients;
            this.result = pResult;
            this.count = count;
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
            JsonObject jsonobject = new JsonObject();
            jsonobject.addProperty("item", BuiltInRegistries.ITEM.getKey(this.result).toString());
            if (this.count > 1) {
                jsonobject.addProperty("count", this.count);
            }
            pJson.add("result", jsonobject);
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
