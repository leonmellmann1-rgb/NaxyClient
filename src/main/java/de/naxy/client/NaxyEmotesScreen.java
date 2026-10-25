package de.naxy.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class NaxyEmotesScreen extends Screen {
    private final Screen parent;
    private String selectedEmote = "Wave";
    private static final int ACCENT = 0xFF6688FF;
    private long previewStart = System.currentTimeMillis();

    public NaxyEmotesScreen(Screen parent) {
        super(Text.literal("NAXY Emotes"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int left = this.width / 2 - 165;
        int top = this.height / 2 - 105;

        addDrawableChild(ButtonWidget.builder(Text.literal("Wave"), button -> select("Wave"))
                .dimensions(left + 18, top + 62, 120, 24).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Dance"), button -> select("Dance"))
                .dimensions(left + 152, top + 62, 120, 24).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Cheer"), button -> select("Cheer"))
                .dimensions(left + 18, top + 94, 120, 24).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Sit"), button -> select("Sit"))
                .dimensions(left + 152, top + 94, 120, 24).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Back"), button -> client.setScreen(parent))
                .dimensions(left + 80, top + 155, 130, 22).build());
    }

    private void select(String emote) {
        selectedEmote = emote;
        previewStart = System.currentTimeMillis();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        int left = this.width / 2 - 165;
        int top = this.height / 2 - 105;
        context.fill(left, top, left + 330, top + 210, 0xF0141724);
        context.fill(left, top, left + 330, top + 3, ACCENT);
        context.drawTextWithShadow(textRenderer, Text.literal("NAXY EMOTES"), left + 18, top + 16, 0xFFFFFFFF);
        context.drawTextWithShadow(textRenderer, Text.literal("LOCAL PREVIEW"), left + 205, top + 18, 0xFFB9C0D4);

        // Animated preview character. This is a GUI preview, not yet an animation on the real player.
        double t = (System.currentTimeMillis() - previewStart) / 180.0;
        int cx = left + 250;
        int cy = top + 92;
        int body = 0xFFE1E5F0;
        int headY = cy + (selectedEmote.equals("Dance") ? (int)(Math.sin(t) * 4) : 0);
        context.fill(cx - 7, headY - 27, cx + 7, headY - 13, ACCENT);
        context.fill(cx - 5, headY - 24, cx + 5, headY - 16, 0xFFFFFFFF);

        int shoulderY = headY - 8;
        int hipY = headY + 20;
        context.fill(cx - 3, shoulderY, cx + 3, hipY, body);

        int leftArmX = cx - 10;
        int rightArmX = cx + 10;
        int leftArmEndY = shoulderY + 20;
        int rightArmEndY = shoulderY + 20;
        if (selectedEmote.equals("Wave")) {
            rightArmX += (int)(Math.sin(t) * 5);
            rightArmEndY = shoulderY - 15 + (int)(Math.sin(t) * 4);
        } else if (selectedEmote.equals("Cheer")) {
            leftArmEndY = shoulderY - 13 + (int)(Math.sin(t) * 3);
            rightArmEndY = shoulderY - 13 - (int)(Math.sin(t) * 3);
        } else if (selectedEmote.equals("Dance")) {
            leftArmEndY = shoulderY + 7 + (int)(Math.sin(t) * 8);
            rightArmEndY = shoulderY + 7 - (int)(Math.sin(t) * 8);
            leftArmX -= (int)(Math.sin(t) * 4);
            rightArmX += (int)(Math.sin(t) * 4);
        }
        context.fill(leftArmX - 2, shoulderY, leftArmX + 2, leftArmEndY, ACCENT);
        context.fill(rightArmX - 2, shoulderY, rightArmX + 2, rightArmEndY, ACCENT);

        if (selectedEmote.equals("Sit")) {
            context.fill(cx - 10, hipY, cx - 2, hipY + 13, body);
            context.fill(cx - 2, hipY + 9, cx + 12, hipY + 13, body);
        } else {
            context.fill(cx - 7, hipY, cx - 3, hipY + 18, body);
            context.fill(cx + 3, hipY, cx + 7, hipY + 18, body);
        }

        context.drawTextWithShadow(textRenderer, Text.literal("Selected: " + selectedEmote), left + 18, top + 137, ACCENT);
        context.drawTextWithShadow(textRenderer, Text.literal("Animated preview • client-side only"), left + 18, top + 153, 0xFFB9C0D4);
        context.drawTextWithShadow(textRenderer, Text.literal("Real player animation comes next."), left + 18, top + 171, 0xFFB9C0D4);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
