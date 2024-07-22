package com.uraneptus.frycooks_delight.common.advancement;

import com.google.gson.JsonObject;
import com.uraneptus.frycooks_delight.FrycooksDelight;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class CodeTrigger extends SimpleCriterionTrigger<CodeTrigger.TriggerInstance> {
    private final ResourceLocation id;

    public CodeTrigger(ResourceLocation id) {
        this.id = id;
    }

    @Override
    protected TriggerInstance createInstance(JsonObject pJson, ContextAwarePredicate pPredicate, DeserializationContext pDeserializationContext) {
        return new CodeTrigger.TriggerInstance(this.id, pPredicate);
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    public void trigger(ServerPlayer pPlayer) {
        this.trigger(pPlayer, predicate -> true);
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        ResourceLocation id;

        public TriggerInstance(ResourceLocation id, ContextAwarePredicate pPlayer) {
            super(id, pPlayer);
            this.id = id;
        }

        public static CodeTrigger.TriggerInstance instance(CodeTrigger criterionTrigger) {
            return new CodeTrigger.TriggerInstance(criterionTrigger.getId(), ContextAwarePredicate.ANY);
        }

        public ResourceLocation getCriterion() {
            return id;
        }

        public JsonObject serializeToJson(SerializationContext pConditions) {
            return new JsonObject();
        }
    }
}
