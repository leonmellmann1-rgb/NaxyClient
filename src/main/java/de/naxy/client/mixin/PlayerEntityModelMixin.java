package de.naxy.client.mixin;

import de.naxy.client.NaxyEmoteAnimation;
import de.naxy.client.NaxyEmoteRenderState;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedEntityModel.class)
public abstract class PlayerEntityModelMixin {
    @Shadow @Final public ModelPart rightArm;
    @Shadow @Final public ModelPart leftArm;
    @Shadow @Final public ModelPart body;
    @Shadow @Final public ModelPart rightLeg;
    @Shadow @Final public ModelPart leftLeg;

    @Inject(method = "setAngles", at = @At("TAIL"))
    private void naxy$animateEmote(BipedEntityRenderState state, CallbackInfo ci) {
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