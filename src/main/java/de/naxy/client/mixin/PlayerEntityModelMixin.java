package de.naxy.client.mixin;

import de.naxy.client.NaxyEmoteAnimation;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityModel.class)
public abstract class PlayerEntityModelMixin {
    @Shadow public ModelPart rightArm;
    @Shadow public ModelPart leftArm;
    @Shadow public ModelPart body;
    @Shadow public ModelPart rightLeg;
    @Shadow public ModelPart leftLeg;

    @Inject(method = "setAngles", at = @At("TAIL"))
    private void naxy$animateEmote(PlayerEntityRenderState state, CallbackInfo ci) {
        if (!(state instanceof NaxyEmoteRenderState naxyState) || !naxyState.naxy$isLocalPlayer()) return;
        if (!NaxyEmoteAnimation.isPlaying()) return;

        float t = (System.currentTimeMillis() - NaxyEmoteAnimation.startedAtMillis) / 1000.0F;
        String emote = NaxyEmoteAnimation.activeEmote;

        if ("Wave".equals(emote)) {
            rightArm.pitch = -2.6F + (float) Math.sin(t * 12.0F) * 0.35F;
            rightArm.yaw = -0.25F + (float) Math.sin(t * 12.0F) * 0.25F;
        } else if ("Cheer".equals(emote)) {
            rightArm.pitch = -3.0F;
            leftArm.pitch = -3.0F;
        } else if ("Dance".equals(emote)) {
            rightArm.pitch = -1.0F + (float) Math.sin(t * 10.0F) * 0.8F;
            leftArm.pitch = -1.0F - (float) Math.sin(t * 10.0F) * 0.8F;
            rightArm.roll = (float) Math.sin(t * 10.0F) * 0.25F;
            leftArm.roll = -(float) Math.sin(t * 10.0F) * 0.25F;
        } else if ("Sit".equals(emote)) {
            body.pitch = -0.18F;
            rightArm.pitch = -0.25F;
            leftArm.pitch = -0.25F;
            rightLeg.pitch = -1.35F;
            leftLeg.pitch = -1.35F;
            rightLeg.yaw = 0.18F;
            leftLeg.yaw = -0.18F;
        }
    }
}
