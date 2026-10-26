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
        long duration = switch (activeEmote) {
            case "Sit" -> 6000L;
            case "Dance" -> 4000L;
            case "Cheer" -> 2500L;
            default -> 1800L;
        };
        if (System.currentTimeMillis() - startedAtMillis > duration) {
            activeEmote = "";
            return false;
        }
        return true;
    }
}
