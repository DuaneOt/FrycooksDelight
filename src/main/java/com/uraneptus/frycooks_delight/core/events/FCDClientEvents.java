package com.uraneptus.frycooks_delight.core.events;

import com.uraneptus.frycooks_delight.FrycooksDelight;
import com.uraneptus.frycooks_delight.client.OilBubbleParticle;
import com.uraneptus.frycooks_delight.core.registry.FCDFluids;
import com.uraneptus.frycooks_delight.core.registry.FCDParticleTypes;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = FrycooksDelight.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class FCDClientEvents {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(FCDFluids.HOT_GREASE_SOURCE.get(), RenderType.solid());
        ItemBlockRenderTypes.setRenderLayer(FCDFluids.HOT_GREASE_FLOWING.get(), RenderType.solid());
    }

    @SubscribeEvent
    public static void registerParticleProvider(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(FCDParticleTypes.OIL_BUBBLE.get(), OilBubbleParticle.Provider::new);
    }

}
