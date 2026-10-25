package de.naxy.client.mixin;

import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PlayerEntityRenderState.class)
public abstract class PlayerEntityRenderStateMixin implements NaxyEmoteRenderState {
    @Unique
    private boolean naxy$localPlayer;

    @Override
    public boolean naxy$isLocalPlayer() {
        return naxy$localPlayer;
    }

    @Override
    public void naxy$setLocalPlayer(boolean localPlayer) {
        this.naxy$localPlayer = localPlayer;
    }
}
