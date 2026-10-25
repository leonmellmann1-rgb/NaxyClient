package de.naxy.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class NaxyClient implements ClientModInitializer {

    public static final String MOD_ID = "naxy";
    private static KeyBinding openMenuKey;

    @Override
    public void onInitializeClient() {
        System.out.println("[NAXY] Client wird gestartet!");

        openMenuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.naxy.open_menu",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                "category.naxy"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openMenuKey.wasPressed()) {
                if (client.currentScreen == null) {
                    client.setScreen(new NaxyMenuScreen());
                }
            }
        });
    }
}
