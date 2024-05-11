package com.uraneptus.frycooks_delight.data.client;

import com.uraneptus.frycooks_delight.FrycooksDelight;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinition;
import net.minecraftforge.common.data.SoundDefinitionsProvider;

import java.util.function.Supplier;

@SuppressWarnings("SameParameterValue")
public class FCDSoundDefinitionsProvider extends SoundDefinitionsProvider {

    public FCDSoundDefinitionsProvider(PackOutput packOutput, ExistingFileHelper helper) {
        super(packOutput, FrycooksDelight.MOD_ID, helper);
    }

    @Override
    public void registerSounds() {

    }

    private void addBasicSound(Supplier<SoundEvent> soundEvent, String subtitle, SoundDefinition.Sound... sounds) {
        this.add(soundEvent.get(), SoundDefinition.definition().subtitle("subtitles." + subtitle).with(sounds));
    }

    private void addBasicSound(Supplier<SoundEvent> soundEvent, SoundDefinition.Sound... sounds) {
        this.addBasicSound(soundEvent, soundEvent.get().getLocation().getPath(), sounds);
    }
}
