package com.uraneptus.frycooks_delight.common;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import com.uraneptus.frycooks_delight.FrycooksDelight;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.function.Consumer;

public class CanolaOilFluidType extends FluidType {
    private static final ResourceLocation UNDERWATER_LOCATION = FrycooksDelight.modPrefix("textures/misc/canola_oil_inside.png"),
            STILL = new ResourceLocation("block/water_still"),
            FLOW = new ResourceLocation("block/water_flow"),
            OVERLAY = new ResourceLocation("block/water_overlay");

    public CanolaOilFluidType() {
        super(Properties.create().canSwim(false).canDrown(false).viscosity(6000).density(3000));
    }

    @Override
    public double motionScale(Entity entity) {
        return 0.0023333333333333335D;
    }

    @Override
    public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
        consumer.accept(new IClientFluidTypeExtensions() {
            @Override
            public ResourceLocation getStillTexture() {
                return STILL;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return FLOW;
            }

            @Override
            public ResourceLocation getOverlayTexture() {
                return OVERLAY;
            }

            @Override
            public ResourceLocation getRenderOverlayTexture(Minecraft mc) {
                return UNDERWATER_LOCATION;
            }

            @Override
            public int getTintColor() {
                return 0xE6E3B825;
            }

            @NotNull
            @Override
            public Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor) {
                return new Vector3f(0.63F, 0.50F, 0.08F);
            }

            @Override
            public void modifyFogRender(Camera camera, FogRenderer.FogMode mode, float renderDistance, float partialTick, float nearDistance, float farDistance, FogShape shape) {
                RenderSystem.setShaderFogStart(1F);
                RenderSystem.setShaderFogEnd(4F);
            }
        });
    }
}
