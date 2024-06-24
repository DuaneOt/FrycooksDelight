package com.uraneptus.frycooks_delight.common.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.uraneptus.frycooks_delight.core.registry.FCDRecipes;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.crafting.ingredient.ChanceResult;

import java.util.Iterator;
import java.util.List;

public class FryingRecipe implements Recipe<Container> {
    public static final String NAME = "frying";
    private final ResourceLocation id;
    private final String recipeGroup;
    private final NonNullList<Ingredient> ingredients;
    private final NonNullList<ItemStack> results;

    public FryingRecipe(ResourceLocation id, String recipeGroup, NonNullList<Ingredient> pIngredients, NonNullList<ItemStack> results) {
        this.id = id;
        this.recipeGroup = recipeGroup;
        this.ingredients = pIngredients;
        this.results = results;
    }

    @Override
    public boolean matches(Container pContainer, Level pLevel) {
        return true;
    }

    @Override
    public ItemStack assemble(Container pContainer, RegistryAccess pRegistryAccess) {
        return this.results.get(0);
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.ingredients;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return this.results.get(0);
    }

    public List<ItemStack> getResults() {
        return this.results;
    }

    @Override
    public String getGroup() {
        return this.recipeGroup;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    public static List<FryingRecipe> getRecipes(Level level) {
        return level.getRecipeManager().getAllRecipesFor(FCDRecipes.FRYING.get());
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return FCDRecipes.FRYING_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return FCDRecipes.FRYING.get();
    }

    public static class Serializer implements RecipeSerializer<FryingRecipe> {

        @Override
        public FryingRecipe fromJson(ResourceLocation pRecipeId, JsonObject jsonObject) {
            String group = GsonHelper.getAsString(jsonObject, "group", "");

            NonNullList<Ingredient> nonnulllist = readIngredients(GsonHelper.getAsJsonArray(jsonObject, "ingredients"));
            if (nonnulllist.isEmpty()) {
                throw new JsonParseException("No ingredients for polishing recipe");
            }
            if (!jsonObject.has("result")) throw new com.google.gson.JsonSyntaxException("Missing result, expected to find a string or object");
            NonNullList<ItemStack> results = readResults(GsonHelper.getAsJsonArray(jsonObject, "result"));

            return new FryingRecipe(pRecipeId, group, nonnulllist, results);
        }

        private static NonNullList<ItemStack> readResults(JsonArray resultArray) {
            NonNullList<ItemStack> results = NonNullList.create();
            for (JsonElement element : resultArray) {
                JsonObject json = element.getAsJsonObject();
                if (json.get("item").isJsonObject()) {
                    results.add(ShapedRecipe.itemStackFromJson(json));
                } else {
                    String resultItem = GsonHelper.getAsString(json, "item");
                    int count = GsonHelper.getAsInt(json, "count", 1);
                    results.add(new ItemStack(ForgeRegistries.ITEMS.getDelegate(new ResourceLocation(resultItem)).orElseThrow(() -> new IllegalStateException("Item: " + resultItem + " does not exist")), count));
                }
            }

            return results;
        }

        private static NonNullList<Ingredient> readIngredients(JsonArray pIngredientArray) {
            NonNullList<Ingredient> nonnulllist = NonNullList.create();
            int ingredientSize = pIngredientArray.size();
            for(int i = 0; i < ingredientSize; ++i) {
                Ingredient ingredient = Ingredient.fromJson(pIngredientArray.get(i));
                if (!ingredient.isEmpty()) {
                    nonnulllist.add(ingredient);
                }
            }
            return nonnulllist;
        }

        @Nullable
        @Override
        public FryingRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            String group = pBuffer.readUtf();
            int ingredientsAmount = pBuffer.readVarInt();
            NonNullList<Ingredient> nonnulllist = NonNullList.withSize(ingredientsAmount, Ingredient.EMPTY);
            for (int j = 0; j < ingredientsAmount; j++) {
                nonnulllist.set(j, Ingredient.fromNetwork(pBuffer));
            }
            int resultsAmount = pBuffer.readVarInt();
            NonNullList<ItemStack> results = NonNullList.withSize(resultsAmount, ItemStack.EMPTY);
            for (int j = 0; j < resultsAmount; j++) {
                results.set(j, pBuffer.readItem());
            }
            return new FryingRecipe(pRecipeId, group, nonnulllist, results);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, FryingRecipe pRecipe) {
            pBuffer.writeUtf(pRecipe.recipeGroup);
            pBuffer.writeVarInt(pRecipe.ingredients.size());
            for(Ingredient ingredient : pRecipe.ingredients) {
                ingredient.toNetwork(pBuffer);
            }
            pBuffer.writeVarInt(pRecipe.results.size());
            for (ItemStack itemStack : pRecipe.results) {
                pBuffer.writeItem(itemStack);
            }
        }
    }
}
