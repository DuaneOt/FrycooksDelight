package com.uraneptus.frycooks_delight.core.registry;

import com.uraneptus.frycooks_delight.FrycooksDelight;
import com.uraneptus.frycooks_delight.common.recipe.FryingRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FCDRecipes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, FrycooksDelight.MOD_ID);
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, FrycooksDelight.MOD_ID);

    public static final RegistryObject<RecipeType<FryingRecipe>> FRYING = RECIPE_TYPES.register(FryingRecipe.NAME, () -> new RecipeType<FryingRecipe>() {
        @Override
        public String toString() {
            return FrycooksDelight.modPrefix(FryingRecipe.NAME).toString();
        }
    });

    public static final RegistryObject<RecipeSerializer<FryingRecipe>> FRYING_SERIALIZER = SERIALIZERS.register(FryingRecipe.NAME, FryingRecipe.Serializer::new);

}
