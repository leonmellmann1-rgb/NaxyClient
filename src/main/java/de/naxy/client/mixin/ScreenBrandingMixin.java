package de.naxy.client.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public abstract class ScreenBrandingMixin {
    @Inject(method = "render", at = @At("TAIL"))
    private void naxy$drawBranding(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        Screen screen = (Screen) (Object) this;
        int right = screen.width - 8;
        int bottom = screen.height - 8;
        context.fill(right - 112, bottom - 15, right, bottom + 2, 0xB0141724);
        context.fill(right - 112, bottom - 15, right - 109, bottom + 2, 0xFF6688FF);
        context.drawTextWithShadow(screen.getTextRenderer(), Text.literal("NAXY CLIENT"), right - 103, bottom - 11, 0xFFFFFFFF);
    }
}
