package com.uraneptus.frycooks_delight.client;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.uraneptus.frycooks_delight.core.registry.FCDParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;
import net.minecraftforge.registries.ForgeRegistries;
import org.joml.Vector3f;

import java.util.Locale;

public class OilBubbleOptions implements ParticleOptions {
    protected final Vector3f color;

    public OilBubbleOptions(Vector3f pColor) {
        this.color = pColor;
    }

    public static final Codec<OilBubbleOptions> CODEC = RecordCodecBuilder.create((p_253370_) -> p_253370_
            .group(ExtraCodecs.VECTOR3F.fieldOf("color").forGetter(OilBubbleOptions::getColor))
            .apply(p_253370_, OilBubbleOptions::new));

    public static final ParticleOptions.Deserializer<OilBubbleOptions> DESERIALIZER = new ParticleOptions.Deserializer<>() {
        public OilBubbleOptions fromCommand(ParticleType<OilBubbleOptions> p_123689_, StringReader p_123690_) throws CommandSyntaxException {
            Vector3f vector3f = OilBubbleOptions.readVector3f(p_123690_);
            p_123690_.expect(' ');
            return new OilBubbleOptions(vector3f);
        }

        public OilBubbleOptions fromNetwork(ParticleType<OilBubbleOptions> p_123692_, FriendlyByteBuf p_123693_) {
            return new OilBubbleOptions(OilBubbleOptions.readVector3f(p_123693_));
        }
    };

    public static Vector3f readVector3f(StringReader pReader) throws CommandSyntaxException {
        pReader.expect(' ');
        float f = pReader.readFloat();
        pReader.expect(' ');
        float f1 = pReader.readFloat();
        pReader.expect(' ');
        float f2 = pReader.readFloat();
        return new Vector3f(f, f1, f2);
    }

    public static Vector3f readVector3f(FriendlyByteBuf pBuffer) {
        return new Vector3f(pBuffer.readFloat(), pBuffer.readFloat(), pBuffer.readFloat());
    }

    public void writeToNetwork(FriendlyByteBuf pBuffer) {
        pBuffer.writeFloat(this.color.x());
        pBuffer.writeFloat(this.color.y());
        pBuffer.writeFloat(this.color.z());
    }

    public String writeToString() {
        return String.format(Locale.ROOT, "%s %.2f %.2f %.2f", ForgeRegistries.PARTICLE_TYPES.getKey(this.getType()), this.color.x(), this.color.y(), this.color.z());
    }

    public Vector3f getColor() {
        return this.color;
    }

    @Override
    public ParticleType<OilBubbleOptions> getType() {
        return FCDParticleTypes.OIL_BUBBLE.get();
    }
}
