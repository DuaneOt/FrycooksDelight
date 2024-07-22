package com.uraneptus.frycooks_delight.data.server.advancements;

import com.uraneptus.frycooks_delight.FrycooksDelight;
import com.uraneptus.frycooks_delight.common.advancement.CodeTrigger;
import com.uraneptus.frycooks_delight.core.other.FCDCriteriaTriggers;
import com.uraneptus.frycooks_delight.core.other.FCDTextUtil;
import com.uraneptus.frycooks_delight.core.other.tags.FCDItemTags;
import com.uraneptus.frycooks_delight.core.registry.FCDItems;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import org.apache.commons.lang3.tuple.Pair;
import vectorwing.farmersdelight.FarmersDelight;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class FCDFDAdvancements implements ForgeAdvancementProvider.AdvancementGenerator {
    ResourceLocation parent = new ResourceLocation(FarmersDelight.MODID, "main/root");
    private static final String PATH = "advancements.farmersdelight.";

    //Advancement definitions
    private static final String FIRST_FRY_NAME = "first_fry";
    private static Pair<Component, Component> FIRST_FRY_ADV;
    private static final String FRYING_ICE_NAME = "frying_ice";
    private static Pair<Component, Component> FRYING_ICE_ADV;
    private static final String ILLEGAL_IN_MINNESOTA_NAME = "illegal_in_minnesota";
    private static Pair<Component, Component> ILLEGAL_IN_MINNESOTA_ADV;

    @Override
    public void generate(HolderLookup.Provider registries, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
        Advancement FIRST_FRY = createAdv(FIRST_FRY_NAME, null, FrameType.TASK, FCDItems.FRIED_POTATO.get(), FIRST_FRY_ADV, addCriterion("first_fry", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(FCDItemTags.IS_FRIED).build())), null, saver, existingFileHelper);
        Advancement FRYING_ICE = createAdv(FRYING_ICE_NAME, null, FrameType.TASK, FCDItems.HOT_GREASE_BUCKET.get(), FRYING_ICE_ADV, addCriterion("frying_ice", CodeTrigger.TriggerInstance.instance(FCDCriteriaTriggers.FRYING_ICE)), null, saver, existingFileHelper);
        Advancement ILLEGAL_IN_MINNESOTA = createAdv(ILLEGAL_IN_MINNESOTA_NAME, null, FrameType.TASK, FCDItems.CANOLA_OIL.get(), ILLEGAL_IN_MINNESOTA_ADV, addCriterion("oiled_pig", CodeTrigger.TriggerInstance.instance(FCDCriteriaTriggers.OILED_PIG)), null, saver, existingFileHelper);
    }

    public Advancement createAdv(String advName, @Nullable Advancement parentAdvancement, FrameType type, ItemLike displayItem, Pair<Component, Component> titleDescPair, @Nullable Pair<String, CriterionTriggerInstance> criterionTriggerInstancePair, @Nullable AdvancementRewards.Builder reward, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
        return createAdvBuilder(parentAdvancement, type, displayItem, titleDescPair, criterionTriggerInstancePair, reward).save(saver, FrycooksDelight.modPrefix("main/" + advName), existingFileHelper);
    }

    public Advancement.Builder createAdvBuilder(@Nullable Advancement parentAdvancement, FrameType type, ItemLike displayItem, Pair<Component, Component> titleDescPair, @Nullable Pair<String, CriterionTriggerInstance> criterionTriggerInstancePair, @Nullable AdvancementRewards.Builder reward) {
        Advancement.Builder builder = Advancement.Builder.advancement().display(displayItem, titleDescPair.getLeft(), titleDescPair.getRight(), null, type, true, true, false)
                .requirements(RequirementsStrategy.OR);
        if (criterionTriggerInstancePair != null) {
            builder.addCriterion(criterionTriggerInstancePair.getLeft(), criterionTriggerInstancePair.getRight());
        }
        if (reward != null) {
            builder.rewards(reward.build());
        }
        if (parentAdvancement == null) {
            builder.parent(parent);
        } else builder.parent(parentAdvancement);

        return builder;
    }

    public Pair<String, CriterionTriggerInstance> addCriterion(String name, CriterionTriggerInstance triggerInstance) {
        return Pair.of(name, triggerInstance);
    }

    public static void initAdvancementTranslations() {
        FIRST_FRY_ADV = FCDTextUtil.addAdvancementTranslatables(PATH + FIRST_FRY_NAME, "Now You're Cooking With Oil!", "Fry your first item in an cauldron of canola oil");
        FRYING_ICE_ADV = FCDTextUtil.addAdvancementTranslatables(PATH + FRYING_ICE_NAME, "Deep Fried Ice", "Throw ice or snow in a cauldron of hot canola oil");
        ILLEGAL_IN_MINNESOTA_ADV = FCDTextUtil.addAdvancementTranslatables(PATH + ILLEGAL_IN_MINNESOTA_NAME, "Illegal in Minnesota", "Cover a pig or a hoglin in oil");
    }
}
