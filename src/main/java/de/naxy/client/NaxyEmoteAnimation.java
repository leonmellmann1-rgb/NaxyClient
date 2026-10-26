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

    public static void stop() {
        activeEmote = "";
    }

    public static boolean isPlaying() {
        if (activeEmote.isEmpty()) return false;
        // Sitting continues until the player starts moving.
        if ("Sit".equals(activeEmote)) return true;

        long duration = switch (activeEmote) {
            case "Dance" -> 7000L;
            case "Cheer" -> 3500L;
            default -> 1800L;
        };
        if (System.currentTimeMillis() - startedAtMillis > duration) {
            activeEmote = "";
            return false;
        }
        return true;
    }
}
