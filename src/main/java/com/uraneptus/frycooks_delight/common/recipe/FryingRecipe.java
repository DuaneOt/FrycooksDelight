package com.uraneptus.frycooks_delight.common.recipe;

import com.google.gson.JsonArray;
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

import java.util.List;

public class FryingRecipe implements Recipe<Container> {
    public static final String NAME = "frying";
    private final ResourceLocation id;
    private final String recipeGroup;
    private final NonNullList<Ingredient> ingredients;
    public final ItemStack result;

    public FryingRecipe(ResourceLocation id, String recipeGroup, NonNullList<Ingredient> pIngredients, ItemStack result) {
        this.id = id;
        this.recipeGroup = recipeGroup;
        this.ingredients = pIngredients;
        this.result = result;
    }

    @Override
    public boolean matches(Container pContainer, Level pLevel) {
        return true;
    }

    @Override
    public ItemStack assemble(Container pContainer, RegistryAccess pRegistryAccess) {
        return this.result.copy();
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
        return result;
    }

    public int getResultCount() {
        return this.result.getCount();
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

            NonNullList<Ingredient> nonnulllist = itemsFromJson(GsonHelper.getAsJsonArray(jsonObject, "ingredients"));
            if (nonnulllist.isEmpty()) {
                throw new JsonParseException("No ingredients for polishing recipe");
            }
            if (!jsonObject.has("result")) throw new com.google.gson.JsonSyntaxException("Missing result, expected to find a string or object");
            ItemStack result;
            if (jsonObject.get("result").isJsonObject()) {
                result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(jsonObject, "result"));
            }
            else {
                String resultItem = GsonHelper.getAsString(jsonObject, "result");
                ResourceLocation resourcelocation = new ResourceLocation(resultItem);
                result = new ItemStack(ForgeRegistries.ITEMS.getDelegate(resourcelocation).orElseThrow(() -> new IllegalStateException("Item: " + resultItem + " does not exist")));
            }

            return new FryingRecipe(pRecipeId, group, nonnulllist, result);
        }

        private static NonNullList<Ingredient> itemsFromJson(JsonArray pIngredientArray) {
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
            int i = pBuffer.readVarInt();
            NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i, Ingredient.EMPTY);
            for (int j = 0; j < i; j++) {
                nonnulllist.set(j, Ingredient.fromNetwork(pBuffer));
            }
            ItemStack result = pBuffer.readItem();
            return new FryingRecipe(pRecipeId, group, nonnulllist, result);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, FryingRecipe pRecipe) {
            pBuffer.writeUtf(pRecipe.recipeGroup);
            pBuffer.writeVarInt(pRecipe.ingredients.size());
            for(Ingredient ingredient : pRecipe.ingredients) {
                ingredient.toNetwork(pBuffer);
            }
            pBuffer.writeItem(pRecipe.result);
        }
    }
}
