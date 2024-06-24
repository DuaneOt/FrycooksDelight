package com.uraneptus.frycooks_delight.data.client;

import com.uraneptus.frycooks_delight.FrycooksDelight;
import com.uraneptus.frycooks_delight.common.blocks.CanolaOilCauldronBlock;
import com.uraneptus.frycooks_delight.core.registry.FCDBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

import static com.uraneptus.frycooks_delight.data.FCDDatagenUtil.*;

@SuppressWarnings("SameParameterValue")
public class FCDBlockStateProvider extends BlockStateProvider {
    public FCDBlockStateProvider(PackOutput packOutput, ExistingFileHelper exFileHelper) {
        super(packOutput, FrycooksDelight.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        crateBlock(FCDBlocks.CANOLA_CRATE, "canola");
        stageCropBlock(FCDBlocks.CANOLA_PLANT);
        wildCropBlock(FCDBlocks.WILD_CANOLA);
        canolaCauldronBlock(FCDBlocks.CANOLA_OIL_CAULDRON);
        basicBlock(FCDBlocks.LARD_BLOCK);
    }

    private void basicBlock(Supplier<? extends Block> block) {
        simpleBlock(block.get());
        this.simpleBlockItem(block.get(), new ModelFile.ExistingModelFile(blockTexture(block.get()), this.models().existingFileHelper));
    }

    public void crateBlock(Supplier<? extends Block> block, String cropName) {
        this.simpleBlock(block.get(), this.models().cubeBottomTop(
                name(block.get()),
                modBlockLocation(cropName + "_crate_side"),
                fdBlockLocation("crate_bottom"),
                modBlockLocation(cropName + "_crate_top")));

        this.simpleBlockItem(block.get(), new ModelFile.ExistingModelFile(blockTexture(block.get()), this.models().existingFileHelper));
    }

    public void stageCropBlock(Supplier<? extends Block> block) {
        getVariantBuilder(block.get()).forAllStates(blockstate -> {
            int age = blockstate.getValue(CropBlock.AGE);
            ModelFile file = models().withExistingParent(name(block.get()) + age, fdBlockLocation("crop_cross"))
                    .renderType("cutout")
                    .texture("cross", modBlockLocation(name(block.get()) + age));

            return ConfiguredModel.builder().modelFile(file).build();
        });
    }

    public void wildCropBlock(Supplier<? extends Block> block) {
        this.simpleBlock(block.get(), this.models().cross(name(block.get()), modBlockLocation(name(block.get()))).renderType("cutout"));
        itemModels().getBuilder(name(block.get())).parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", modBlockLocation(name(block.get())));
    }

    public void canolaCauldronBlock(Supplier<? extends Block> block) {
        getVariantBuilder(block.get()).forAllStates(blockState -> {
            int level = blockState.getValue(CanolaOilCauldronBlock.LEVEL);
            int oil_stage = blockState.getValue(CanolaOilCauldronBlock.OIL_STAGE);

            String levelSuffix = switch (level) {
                case 1 -> "_level1";
                case 2 -> "_level2";
                case 3 -> "_full";
                default -> "";
            };

            ResourceLocation templateLoc = level == 3 ? modBlockLocation("fcd_template_cauldron_full") : vanillaBlockLocation("template_cauldron" + levelSuffix);
            String oilStageSuffix = "_oil_stage_" + oil_stage;
            String modelName = name(block.get()) + oilStageSuffix + levelSuffix;
            ResourceLocation cauldronLoc = vanillaBlockLocation(name(Blocks.CAULDRON));
            ResourceLocation contentTexture = oil_stage == 9 ? modBlockLocation("lard_block") : modBlockLocation("canola_oil" + oil_stage);

            ModelFile file = models().withExistingParent(modelName, templateLoc)
                    .texture("bottom", cauldronLoc + "_bottom")
                    .texture("content", contentTexture)
                    .texture("inside", cauldronLoc + "_inner")
                    .texture("particle", cauldronLoc + "_side")
                    .texture("side", cauldronLoc + "_side")
                    .texture("top", cauldronLoc + "_top");

            return ConfiguredModel.builder().modelFile(file).build();
        });
        this.simpleBlockItem(block.get(), new ModelFile.ExistingModelFile(FrycooksDelight.modPrefix(ModelProvider.BLOCK_FOLDER + "/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath() + "_oil_stage_1_full"), this.models().existingFileHelper));
    }
}
