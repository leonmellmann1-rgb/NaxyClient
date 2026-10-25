
package de.naxy.client;

import net.fabricmc.api.ClientModInitializer;

public class NaxyClient implements ClientModInitializer {

    public static final String MOD_ID = "naxy";

    @Override
    public void onInitializeClient() {
        System.out.println("[NAXY] Client wird gestartet!");

        // Hier bauen wir später:
        // - NAXY Ingame-GUI
        // - Coins und Cosmetics
        // - Freundesliste und Chat
        // - Admin-Menü und Redeem-Codes
        // - LASSE-Modus
    }
}
