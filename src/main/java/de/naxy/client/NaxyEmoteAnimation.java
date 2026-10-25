package de.naxy.client;

/**
 * Client-side state for the currently playing NAXY emote.
 */
public final class NaxyEmoteAnimation {
    private NaxyEmoteAnimation() {}

    public static volatile String activeEmote = "";
    public static long startedAtMillis;

    public static void play(String emote) {
        activeEmote = emote;
        startedAtMillis = System.currentTimeMillis();
    }

    public static boolean isPlaying() {
        if (activeEmote.isEmpty()) return false;
        if (System.currentTimeMillis() - startedAtMillis > 1800L) {
            activeEmote = "";
            return false;
        }
        return true;
    }
}
