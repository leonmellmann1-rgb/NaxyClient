package de.naxy.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class NaxyClient implements ClientModInitializer {

    public static final String MOD_ID = "naxy";
    private static KeyBinding openMenuKey;

    @Override
    public void onInitializeClient() {
        System.out.println("[NAXY] Client wird gestartet!");

        KeyBinding.Category naxyCategory = KeyBinding.Category.create(Identifier.of(MOD_ID, "main"));
        openMenuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.naxy.open_menu",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                naxyCategory
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if ("Sit".equals(NaxyEmoteAnimation.activeEmote) && client.player != null) {
                // Ignore vertical velocity (falling/jumping); only stop sitting for actual movement.
                var velocity = client.player.getVelocity();
                boolean moving = Math.abs(velocity.x) > 0.04 || Math.abs(velocity.z) > 0.04
                        || client.options.forwardKey.isPressed()
                        || client.options.backKey.isPressed()
                        || client.options.leftKey.isPressed()
                        || client.options.rightKey.isPressed()
                        || client.options.jumpKey.isPressed();
                if (moving) NaxyEmoteAnimation.stop();
            }

            while (openMenuKey.wasPressed()) {
                if (client.currentScreen == null) {
                    client.setScreen(new NaxyMenuScreen());
                }
            }
        });
    }
}
