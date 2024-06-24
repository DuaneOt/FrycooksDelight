package com.uraneptus.frycooks_delight.common.blocks;

import com.uraneptus.frycooks_delight.client.OilBubbleOptions;
import com.uraneptus.frycooks_delight.common.recipe.FryingRecipe;
import com.uraneptus.frycooks_delight.core.other.tags.FCDBlockTags;
import com.uraneptus.frycooks_delight.core.other.tags.FCDItemTags;
import com.uraneptus.frycooks_delight.core.registry.FCDBlocks;
import com.uraneptus.frycooks_delight.core.registry.FCDDamageTypes;
import com.uraneptus.frycooks_delight.core.registry.FCDItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import vectorwing.farmersdelight.common.registry.ModItems;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class CanolaOilCauldronBlock extends LayeredCauldronBlock implements WorldlyContainerHolder {
    public static final IntegerProperty OIL_STAGE = IntegerProperty.create("oil_stage", 1, 9);
    private final Predicate<BlockState> HEATING_BLOCKS = (blockstate) -> blockstate.is(FCDBlockTags.FRY_HEAT_SOURCES)
            || (blockstate.getBlock() instanceof CampfireBlock campfireBlock && campfireBlock.defaultBlockState().getValue(CampfireBlock.LIT));

    public CanolaOilCauldronBlock(Properties pProperties) {
        super(pProperties, precipitation -> precipitation == Biome.Precipitation.NONE, null);
        this.registerDefaultState(this.stateDefinition.any().setValue(OIL_STAGE, 1));
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (itemstack.is(Items.GLASS_BOTTLE)) {
            if (pState.getValue(CanolaOilCauldronBlock.OIL_STAGE) < 8) {
                int currentLevel = pState.getValue(CanolaOilCauldronBlock.LEVEL);
                if (currentLevel > 1) {
                    pLevel.setBlock(pPos, pState.setValue(CanolaOilCauldronBlock.LEVEL, currentLevel - 1), Block.UPDATE_ALL);
                    fillOilBottle(pPlayer, itemstack);
                    return InteractionResult.sidedSuccess(pLevel.isClientSide);
                } else if (currentLevel == 1) {
                    pLevel.setBlock(pPos, Blocks.CAULDRON.defaultBlockState(), Block.UPDATE_ALL);
                    fillOilBottle(pPlayer, itemstack);
                    return InteractionResult.sidedSuccess(pLevel.isClientSide);
                }
            } else {
                if (!pPlayer.getAbilities().instabuild) {
                    pPlayer.hurt(pLevel.damageSources().source(FCDDamageTypes.FRYING), 1.0F);
                    return InteractionResult.SUCCESS;
                }
            }
        } else if (itemstack.is(Items.BUCKET)) {
            if (pState.getValue(CanolaOilCauldronBlock.OIL_STAGE) == 8) {
                itemstack.shrink(1);
                pPlayer.addItem(FCDItems.HOT_GREASE_BUCKET.get().getDefaultInstance());
                pLevel.setBlock(pPos, Blocks.CAULDRON.defaultBlockState(), Block.UPDATE_ALL);
                return InteractionResult.sidedSuccess(pLevel.isClientSide);
            }
        } else if (itemstack.isEmpty() && pState.getValue(OIL_STAGE) == 9) {
            pPlayer.addItem(FCDBlocks.LARD_BLOCK.get().asItem().getDefaultInstance());
            pLevel.setBlock(pPos, Blocks.CAULDRON.defaultBlockState(), Block.UPDATE_ALL);
            pLevel.gameEvent(GameEvent.BLOCK_CHANGE, pPos, GameEvent.Context.of(pPlayer, pState));
            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        }
        return InteractionResult.PASS;
    }

    public static void fillOilBottle(Player player, ItemStack itemStack) {
        if (!player.getAbilities().instabuild) {
            itemStack.shrink(1);
            player.addItem(FCDItems.CANOLA_OIL.get().getDefaultInstance());
        }
        player.level().playSound(null, player.blockPosition(), SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
        player.swing(player.getUsedItemHand());
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if (isBoiling(pState, pLevel, pPos) && pState.getValue(OIL_STAGE) <= 8) {
            int color = pState.getValue(OIL_STAGE) < 8 ? 12955730 : 5848363;

            for (int i = 0; i < 2; i++) {
                double xPos = pPos.getX() + 0.125 + 0.75 * pRandom.nextFloat();
                double yPos = pPos.getY() + 0.9375F;
                double zPos = pPos.getZ() + 0.125 + 0.75 * pRandom.nextFloat();
                pLevel.addParticle(new OilBubbleOptions(Vec3.fromRGB24(color).toVector3f()), xPos, yPos, zPos, 0.0F, 0.02, 0.0F);
            }
        }
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pos, RandomSource random) {
        if (pState.getValue(OIL_STAGE) < 8) {
            if (isBoiling(pState, pLevel, pos) && random.nextInt(10) == 0) {
                BlockState state = pState.setValue(OIL_STAGE, pState.getValue(OIL_STAGE) + 1);
                pLevel.setBlock(pos, state, Block.UPDATE_ALL);
            }
        } else if (pState.getValue(OIL_STAGE) == 8 && !HEATING_BLOCKS.test(pLevel.getBlockState(pos.below())) && !pLevel.dimensionType().ultraWarm()) {
            BlockState state = pState.setValue(OIL_STAGE, 9);
            pLevel.setBlock(pos, state, Block.UPDATE_ALL);
        }
    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if (isBoiling(pState, pLevel, pPos)) {
            if (pEntity instanceof ItemEntity itemEntity) {
                ItemStack itemstack = itemEntity.getItem();
                if (itemstack.is(FCDItemTags.OIL_DESTROYS) || itemstack.getItem() instanceof BannerPatternItem) {
                    itemEntity.discard();
                    pLevel.playSound(null, pPos, SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
                    randomStageIncrease(itemstack, pLevel, pState, pPos);
                }
                if (itemstack.is(FCDItemTags.CAUSE_OIL_OVERFLOW)) {
                    pLevel.setBlock(pPos.above(), FCDBlocks.HOT_GREASE.get().defaultBlockState(), Block.UPDATE_ALL);
                    pLevel.setBlock(pPos, Blocks.CAULDRON.defaultBlockState(), Block.UPDATE_ALL);
                    itemEntity.discard();
                }
                if (pState.getValue(OIL_STAGE) < 8) {
                    boolean recipeFlag = false;
                    ArrayList<FryingRecipe> recipes = new ArrayList<>(FryingRecipe.getRecipes(pLevel));
                    for (FryingRecipe recipe : recipes) {
                        for (ItemStack ingredient : recipe.getIngredients().iterator().next().getItems()) {
                            if (itemstack.is(ingredient.getItem())) {
                                recipeFlag = true;
                                List<ItemStack> results = recipe.getResults();
                                int resultsAmount = results.size();
                                for (int i = 0; i < itemEntity.getItem().getCount(); i++) {
                                    ItemStack resultItem;
                                    if (resultsAmount > 1) {
                                        for (int j = 0; j <= results.size(); j++) {
                                            resultItem = results.get(j);
                                            //resultItem.setCount(results.get(j).getCount());
                                            itemEntity.discard();
                                            finishFrying(resultItem, pLevel, pPos, 1.0F);
                                            randomStageIncrease(resultItem, pLevel, pState, pPos);
                                        }
                                    } else {
                                        resultItem = results.get(0);
                                        itemEntity.discard();
                                        finishFrying(resultItem, pLevel, pPos, 1.0F);
                                        randomStageIncrease(resultItem, pLevel, pState, pPos);
                                    }

                                }

                            }
                        }
                    }
                    if (!recipeFlag && (itemstack.isEdible() || itemstack.is(FCDItemTags.BURNS_TO_MORSEL) || itemstack.getItem() instanceof SpawnEggItem)) {
                        ItemStack result;
                        if (itemstack.is(FCDItemTags.IS_FRIED)) {
                            result = itemstack.copyWithCount(itemstack.getCount());
                            itemEntity.discard();
                            finishFrying(result, pLevel, pPos, 0.5F);
                        } else {
                            result = FCDItems.BURNT_MORSEL.get().getDefaultInstance();
                            result.setCount(itemEntity.getItem().getCount());
                            itemEntity.discard();
                            finishFrying(result, pLevel, pPos, 1.0F);
                            randomStageIncrease(result, pLevel, pState, pPos);
                        }
                    }
                } else if (pState.getValue(OIL_STAGE) == 8) {
                    ItemStack burntNugget = FCDItems.BURNT_MORSEL.get().getDefaultInstance();
                    burntNugget.setCount(itemEntity.getItem().getCount());
                    itemEntity.discard();
                    finishFrying(burntNugget, pLevel, pPos, 1.0F);
                    randomStageIncrease(burntNugget, pLevel, pState, pPos);
                }
            } else if (pEntity instanceof LivingEntity) {
                pEntity.hurt(pLevel.damageSources().source(FCDDamageTypes.FRYING), 2.0F);
            }
        }
    }

    public void finishFrying(ItemStack resultItem, Level pLevel, BlockPos pPos, float volume) {
        pLevel.addFreshEntity(new ItemEntity(pLevel, pPos.getX() + 0.5, pPos.getY() + 1.5F, pPos.getZ() + 0.5, resultItem));
        pLevel.playSound(null, pPos, SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS, volume, 1.0F);
    }

    public void randomStageIncrease(ItemStack itemStack, Level pLevel, BlockState pState, BlockPos pPos) {
        int itemCount = itemStack.getCount();
        for (int i = 0; i < itemCount; i++) {
            if (pLevel.random.nextInt(10) == 0 && pState.getValue(OIL_STAGE) < 8) {
                BlockState nextStage = pState.setValue(OIL_STAGE, pState.getValue(OIL_STAGE) + 1);
                pLevel.setBlock(pPos, nextStage, Block.UPDATE_ALL);
            }
        }
    }

    public boolean isBoiling(BlockState state, Level pLevel, BlockPos pPos) {
        return HEATING_BLOCKS.test(pLevel.getBlockState(pPos.below())) && state.getValue(LEVEL) == 3;
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

    @Override
    public WorldlyContainer getContainer(BlockState pState, LevelAccessor pLevel, BlockPos pPos) {
        if (pState.getValue(OIL_STAGE) == 9) {
            return new CanolaOilCauldronBlock.OutputContainer(pState, pLevel, pPos);
        }
        return new CanolaOilCauldronBlock.EmptyContainer();
    }

    static class EmptyContainer extends SimpleContainer implements WorldlyContainer {
        public EmptyContainer() {
            super(0);
        }

        @Override
        public int[] getSlotsForFace(Direction pSide) {
            return new int[0];
        }

        @Override
        public boolean canPlaceItemThroughFace(int pIndex, ItemStack pItemStack, @Nullable Direction pDirection) {
            return false;
        }

        @Override
        public boolean canTakeItemThroughFace(int pIndex, ItemStack pStack, Direction pDirection) {
            return false;
        }
    }

    static class OutputContainer extends SimpleContainer implements WorldlyContainer {
        private final BlockState state;
        private final LevelAccessor level;
        private final BlockPos pos;
        private boolean changed;

        public OutputContainer(BlockState pState, LevelAccessor pLevel, BlockPos pPos) {
            super(FCDBlocks.LARD_BLOCK.get().asItem().getDefaultInstance());
            this.state = pState;
            this.level = pLevel;
            this.pos = pPos;
        }

        @Override
        public int getMaxStackSize() {
            return 1;
        }

        @Override
        public int[] getSlotsForFace(Direction pSide) {
            return pSide == Direction.DOWN ? new int[]{0} : new int[0];
        }

        @Override
        public boolean canPlaceItemThroughFace(int pIndex, ItemStack pItemStack, @Nullable Direction pDirection) {
            return false;
        }

        @Override
        public boolean canTakeItemThroughFace(int pIndex, ItemStack pStack, Direction pDirection) {
            return !this.changed && pDirection == Direction.DOWN && pStack.is(FCDBlocks.LARD_BLOCK.get().asItem());
        }

        @Override
        public void setChanged() {
            level.setBlock(pos, Blocks.CAULDRON.defaultBlockState(), Block.UPDATE_ALL);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(null, state));
            this.changed = true;
        }
    }
}