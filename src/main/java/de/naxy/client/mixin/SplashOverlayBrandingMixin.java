package de.naxy.client.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.SplashOverlay;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SplashOverlay.class)
public abstract class SplashOverlayBrandingMixin {
    @Inject(method = "render", at = @At("TAIL"))
    private void naxy$drawLoadingBranding(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        context.drawTextWithShadow(
                net.minecraft.client.MinecraftClient.getInstance().textRenderer,
                Text.literal("NAXY CLIENT"),
                12,
                12,
                0xFF6688FF
        );
        context.drawTextWithShadow(
                net.minecraft.client.MinecraftClient.getInstance().textRenderer,
                Text.literal("Loading Minecraft"),
                12,
                24,
                0xFFFFFFFF
        );
    }
}
