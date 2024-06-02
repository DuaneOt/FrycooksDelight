package com.uraneptus.frycooks_delight.jei;

import com.uraneptus.frycooks_delight.FrycooksDelight;
import com.uraneptus.frycooks_delight.common.recipe.FryingRecipe;
import com.uraneptus.frycooks_delight.core.other.FCDTextUtil;
import com.uraneptus.frycooks_delight.core.registry.FCDBlocks;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public class FryingRecipeCategory implements IRecipeCategory<FryingRecipe> {
    private final IDrawable background;
    private final IDrawable icon;

    public FryingRecipeCategory(IGuiHelper helper) {
        background = helper.createDrawable(FrycooksDelight.modPrefix("textures/gui/jei_frying.png"), 0, 0, 54, 60);
        icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(FCDBlocks.CANOLA_OIL_CAULDRON.get()));
    }

    @Override
    public Component getTitle() {
        return FCDTextUtil.JEI_FRYING_TITLE;
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public @NotNull RecipeType<FryingRecipe> getRecipeType() {
        return JEIPlugin.JEI_FRYING_RECIPE_TYPE;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder layout, FryingRecipe fryingRecipe, IFocusGroup ingredients) {
        layout.addSlot(RecipeIngredientRole.INPUT, 1, 1).addIngredients(fryingRecipe.getIngredients().iterator().next());
        layout.addSlot(RecipeIngredientRole.OUTPUT, 37, 24).addItemStack(new ItemStack(fryingRecipe.result.getItem(), fryingRecipe.getResultCount()));
    }

    @Override
    public void draw(FryingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        guiGraphics.renderItem(new ItemStack(FCDBlocks.CANOLA_OIL_CAULDRON.get()), 0, 23);
    }

}
