package com.uraneptus.frycooks_delight.data.client;

import com.uraneptus.frycooks_delight.FrycooksDelight;
import com.uraneptus.frycooks_delight.core.registry.FCDBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

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
    }

    private void basicBlock(Supplier<? extends Block> block) {
        simpleBlock(block.get());
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
        this.simpleBlock(block.get(), this.models().cross(name(block.get()), modBlockLocation("canola_plant7")).renderType("cutout"));
        itemModels().getBuilder(name(block.get())).parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", modBlockLocation("canola_plant7")); //TODO use name(block.get()) later
        //this.simpleBlock(block.get(), this.models().cross(name(block.get()), modBlockLocation(name(block.get()))).renderType("cutout")); //TODO use this when custom texture is here
    }
}
