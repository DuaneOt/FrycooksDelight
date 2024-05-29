package com.uraneptus.frycooks_delight.core.events;

import com.uraneptus.frycooks_delight.FrycooksDelight;
import com.uraneptus.frycooks_delight.common.blocks.CanolaOilCauldronBlock;
import com.uraneptus.frycooks_delight.core.registry.FCDBlocks;
import com.uraneptus.frycooks_delight.core.registry.FCDDamageTypes;
import com.uraneptus.frycooks_delight.core.registry.FCDItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = FrycooksDelight.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FCDPlayerEvents {

    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        ItemStack itemStack = event.getItemStack();
        Entity entity = event.getTarget();
        Player player = event.getEntity();
        if (itemStack.is(FCDItems.CANOLA_OIL.get()) && entity instanceof ItemFrame && player.isShiftKeyDown()) {
            entity.setInvisible(true);
            emptyBottle(player, itemStack);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onBlockInteract(PlayerInteractEvent.RightClickBlock event) {
        ItemStack itemStack = event.getItemStack();
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        BlockState blockState = level.getBlockState(pos);
        Player player = event.getEntity();

        if (itemStack.is(FCDItems.CANOLA_OIL.get())) {
            if (blockState.is(Blocks.CAULDRON)) {
                level.setBlock(pos, FCDBlocks.CANOLA_OIL_CAULDRON.get().defaultBlockState().setValue(CanolaOilCauldronBlock.LEVEL, 1), Block.UPDATE_ALL);
                emptyBottle(player, itemStack);
            } else if (blockState.is(FCDBlocks.CANOLA_OIL_CAULDRON.get())) {
                int currentLevel = blockState.getValue(CanolaOilCauldronBlock.LEVEL);
                if (currentLevel < 3) {
                    level.setBlock(pos, blockState.setValue(CanolaOilCauldronBlock.LEVEL, currentLevel + 1), Block.UPDATE_ALL);
                    emptyBottle(player, itemStack);
                }
            }
        }
        if (itemStack.is(FCDItems.HOT_GREASE_BUCKET.get())) {
            if (blockState.is(Blocks.CAULDRON)) {
                level.setBlock(pos, FCDBlocks.CANOLA_OIL_CAULDRON.get().defaultBlockState().setValue(CanolaOilCauldronBlock.LEVEL, 3).setValue(CanolaOilCauldronBlock.OIL_STAGE, 8), Block.UPDATE_ALL);
                if (!player.getAbilities().instabuild) {
                    itemStack.shrink(1);
                    player.addItem(Items.BUCKET.getDefaultInstance());
                }
                player.level().playSound(null, player.blockPosition(), SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
                player.swing(player.getUsedItemHand());
                event.setCancellationResult(InteractionResult.FAIL);
                event.setCanceled(true);
            }
        }
    }

    public static void emptyBottle(Player player, ItemStack itemStack) {
        if (!player.getAbilities().instabuild) {
            itemStack.shrink(1);
            player.addItem(Items.GLASS_BOTTLE.getDefaultInstance());
        }
        player.level().playSound(null, player.blockPosition(), SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
        player.swing(player.getUsedItemHand());
    }

}