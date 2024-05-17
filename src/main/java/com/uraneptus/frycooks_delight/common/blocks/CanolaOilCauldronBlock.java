package com.uraneptus.frycooks_delight.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;

public class CanolaOilCauldronBlock extends LayeredCauldronBlock {
    public static final IntegerProperty OIL_STAGE = IntegerProperty.create("oil_stage", 1, 8);

    public CanolaOilCauldronBlock(Properties pProperties) {
        super(pProperties, precipitation -> precipitation == Biome.Precipitation.NONE, null);
        this.registerDefaultState(this.stateDefinition.any().setValue(OIL_STAGE, 1));
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);

        return InteractionResult.PASS;
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pos, RandomSource random) {
        if (random.nextInt(10) == 0 && pState.getValue(OIL_STAGE) < 8) {
            BlockState state = pState.setValue(OIL_STAGE, pState.getValue(OIL_STAGE) + 1);
            pLevel.setBlock(pos, state, Block.UPDATE_ALL);
        }
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter pLevel, BlockPos pPos, BlockState pState) {
        return new ItemStack(Items.CAULDRON);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(OIL_STAGE);
    }
}
