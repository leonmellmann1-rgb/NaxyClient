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
            float pump = (float) Math.sin(t * 9.0F) * 0.28F;
            rightArm.pitch = -3.05F + pump;
            leftArm.pitch = -3.05F - pump;
            rightArm.roll = -0.22F + (float) Math.sin(t * 9.0F) * 0.18F;
            leftArm.roll = 0.22F - (float) Math.sin(t * 9.0F) * 0.18F;
            body.pitch = (float) Math.sin(t * 9.0F) * 0.08F;
        } else if ("Dance".equals(emote)) {
            float beat = (float) Math.sin(t * 8.5F);
            float fastBeat = (float) Math.sin(t * 17.0F);
            rightArm.pitch = -1.25F + beat * 1.15F;
            leftArm.pitch = -1.25F - beat * 1.15F;
            rightArm.yaw = -0.35F + fastBeat * 0.35F;
            leftArm.yaw = 0.35F - fastBeat * 0.35F;
            rightArm.roll = 0.25F + beat * 0.3F;
            leftArm.roll = -0.25F - beat * 0.3F;
            body.yaw = beat * 0.38F;
            body.pitch = (float) Math.sin(t * 17.0F) * 0.12F;
            rightLeg.pitch = fastBeat * 0.45F;
            leftLeg.pitch = -fastBeat * 0.45F;
            rightLeg.yaw = beat * 0.16F;
            leftLeg.yaw = -beat * 0.16F;
        } else if ("Sit".equals(emote)) {
            // Lower the torso and fold the legs so the pose reads as a seated position.
            body.pitch = 0.22F;
            rightArm.pitch = -0.18F;
            leftArm.pitch = -0.18F;
            rightLeg.pitch = -1.75F;
            leftLeg.pitch = -1.75F;
            rightLeg.yaw = 0.12F;
            leftLeg.yaw = -0.12F;
        }
    }
}