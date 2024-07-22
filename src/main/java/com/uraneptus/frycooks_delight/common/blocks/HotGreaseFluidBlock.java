package com.uraneptus.frycooks_delight.common.blocks;

import com.uraneptus.frycooks_delight.core.other.tags.FCDEntityTypeTags;
import com.uraneptus.frycooks_delight.core.other.tags.FCDItemTags;
import com.uraneptus.frycooks_delight.core.registry.FCDDamageTypes;
import com.uraneptus.frycooks_delight.core.registry.FCDItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BannerPatternItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;

import java.util.function.Supplier;

public class HotGreaseFluidBlock extends LiquidBlock {

    public HotGreaseFluidBlock(Supplier<? extends FlowingFluid> pFluid, Properties pProperties) {
        super(pFluid, pProperties);
    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if (pEntity instanceof LivingEntity && !pEntity.getType().is(FCDEntityTypeTags.FRYING_IMMUNE_ENTITY_TYPES)) {
            pEntity.hurt(pLevel.damageSources().source(FCDDamageTypes.FRYING), 1.0F);
        }
        if  (pEntity instanceof ItemEntity itemEntity) {
            ItemStack itemstack = itemEntity.getItem();

            if (itemstack.is(FCDItemTags.OIL_DESTROYS) || itemstack.getItem() instanceof BannerPatternItem) {
                itemEntity.discard();
                pLevel.playSound(null, pPos, SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
            }

            if (itemstack.isEdible() || itemstack.is(FCDItemTags.BURNS_TO_MORSEL) || itemstack.getItem() instanceof SpawnEggItem) {
                ItemStack burntNugget = FCDItems.BURNT_MORSEL.get().getDefaultInstance();
                burntNugget.setCount(itemEntity.getItem().getCount());
                itemEntity.discard();
                CanolaOilCauldronBlock.finishFrying(burntNugget, pLevel, pPos, 1.0F);
            }
        }
    }
}
