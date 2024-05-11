package com.uraneptus.frycooks_delight.core.events;

import com.uraneptus.frycooks_delight.FrycooksDelight;
import com.uraneptus.frycooks_delight.core.registry.FCDItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FrycooksDelight.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FCDPlayerEvents {

    @SubscribeEvent
    public static void onBlockInteract(PlayerInteractEvent.EntityInteract event) {
        ItemStack itemStack = event.getItemStack();
        Entity entity = event.getTarget();
        Player player = event.getEntity();
        if (itemStack.is(FCDItems.CANOLA_OIL.get()) && entity instanceof ItemFrame && player.isShiftKeyDown()) {
            entity.setInvisible(true);
            if (!player.getAbilities().instabuild) {
                itemStack.shrink(1);
            }
            event.setCanceled(true);
        }

    }

}