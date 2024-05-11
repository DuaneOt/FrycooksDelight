package com.uraneptus.frycooks_delight.data.server;

import com.uraneptus.frycooks_delight.FrycooksDelight;
import com.uraneptus.frycooks_delight.core.other.tags.FCDBiomeTags;
import com.uraneptus.frycooks_delight.core.registry.FCDBlocks;
import com.uraneptus.frycooks_delight.core.registry.FCDDamageTypes;
import com.uraneptus.frycooks_delight.data.FCDDatagenUtil;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;
import vectorwing.farmersdelight.common.registry.ModBiomeFeatures;
import vectorwing.farmersdelight.common.world.configuration.WildCropConfiguration;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class FCDDatapackBuiltinEntriesProvider extends DatapackBuiltinEntriesProvider {
    private static final RegistrySetBuilder SET_BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, ConfiguredFeatures::create)
            .add(Registries.PLACED_FEATURE, PlacedFeatures::create)
            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, BiomeModifiers::create)
            .add(Registries.DAMAGE_TYPE, DamageSources::create);

    public FCDDatapackBuiltinEntriesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, SET_BUILDER, Set.of(FrycooksDelight.MOD_ID));
    }

    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_WILD_CANOLA = ResourceKey.create(Registries.CONFIGURED_FEATURE, FrycooksDelight.modPrefix("wild_canola"));
    public static final ResourceKey<PlacedFeature> PLACED_WILD_CANOLA = ResourceKey.create(Registries.PLACED_FEATURE, FrycooksDelight.modPrefix("wild_canola"));

    private static class ConfiguredFeatures {
        static Holder<ConfiguredFeature<?, ?>> primaryConfiguredFeature = Holder.direct(new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(FCDBlocks.WILD_CANOLA.get()))));
        static Holder<PlacedFeature> primaryPlacedFeature = Holder.direct(new PlacedFeature(primaryConfiguredFeature, List.of(BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.matchesBlocks(Blocks.AIR), BlockPredicate.matchesTag(new Vec3i(0, -1, 0), BlockTags.DIRT))))));

        static Holder<ConfiguredFeature<?, ?>> secondaryConfiguredFeature = Holder.direct(new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.DANDELION))));
        static Holder<PlacedFeature> secondaryPlacedFeature = Holder.direct(new PlacedFeature(secondaryConfiguredFeature, List.of(BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.matchesBlocks(Blocks.AIR), BlockPredicate.matchesTag(new Vec3i(0, -1, 0), BlockTags.DIRT))))));

        public static void create(BootstapContext<ConfiguredFeature<?, ?>> context) {

            register(context, CONFIGURED_WILD_CANOLA, () -> addWildCropConfig(64, 6, 3, primaryPlacedFeature, secondaryPlacedFeature, null));
        }

        private static ConfiguredFeature<?, ?> addWildCropConfig(int tries, int xzSpread, int ySpread, Holder<PlacedFeature> primaryFeature, Holder<PlacedFeature> secondaryFeature, @Nullable Holder<PlacedFeature> floorFeature) {
            return new ConfiguredFeature<>(ModBiomeFeatures.WILD_CROP.get(), new WildCropConfiguration(tries, xzSpread, ySpread, primaryFeature, secondaryFeature, floorFeature));
        }

        private static void register(BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> featureKey, Supplier<? extends ConfiguredFeature<?, ?>> feature) {
            context.register(featureKey, feature.get());
        }
    }

    private static class PlacedFeatures {
        public static void create(BootstapContext<PlacedFeature> context) {
            register(context, PLACED_WILD_CANOLA, addFeaturePlacement(context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(CONFIGURED_WILD_CANOLA), RarityFilter.onAverageOnceEvery(100), PlacementUtils.HEIGHTMAP, InSquarePlacement.spread(), BiomeFilter.biome()));
        }

        private static PlacedFeature addFeaturePlacement(Holder<ConfiguredFeature<?, ?>> configureFeature, PlacementModifier... placementModifiers) {
            return new PlacedFeature(configureFeature, List.of(placementModifiers));
        }

        private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> featureKey, PlacedFeature feature) {
            context.register(featureKey, feature);
        }
    }

    private static class BiomeModifiers {
        public static void create(BootstapContext<BiomeModifier> context) {
            register(context, "wild_canola", () -> addFeatureModifier(context, FCDDatagenUtil.getPlacedHolderSet(context, PLACED_WILD_CANOLA), FCDBiomeTags.CANOLA_SPAWN_IN, GenerationStep.Decoration.VEGETAL_DECORATION));
        }

        private static ForgeBiomeModifiers.AddFeaturesBiomeModifier addFeatureModifier(BootstapContext<BiomeModifier> context, HolderSet<PlacedFeature> placedSet, TagKey<Biome> biomeTag, GenerationStep.Decoration decoration) {
            return new ForgeBiomeModifiers.AddFeaturesBiomeModifier(context.lookup(Registries.BIOME).getOrThrow(biomeTag), placedSet, decoration);
        }

        private static void register(BootstapContext<BiomeModifier> context, String name, Supplier<? extends BiomeModifier> modifier) {
            context.register(ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, FrycooksDelight.modPrefix(name)), modifier.get());
        }
    }

    private static class DamageSources {
        protected static void create(BootstapContext<DamageType> context) {
            FCDDamageTypes.damageTypeMap.forEach(((damageTypeKey, damageType) -> register(context, damageTypeKey, damageType)));
        }

        protected static void register(BootstapContext<DamageType> context, ResourceKey<DamageType> key, DamageType damageType) {
            context.register(key, damageType);
        }
    }
}
