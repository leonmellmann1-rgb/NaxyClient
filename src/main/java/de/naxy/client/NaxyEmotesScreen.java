package de.naxy.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class NaxyEmotesScreen extends Screen {
    private final Screen parent;
    private String selectedEmote = "Choose an emote";
    private static final int ACCENT = 0xFF6688FF;

    public NaxyEmotesScreen(Screen parent) {
        super(Text.literal("NAXY Emotes"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int left = this.width / 2 - 145;
        int top = this.height / 2 - 95;

        addDrawableChild(ButtonWidget.builder(Text.literal("Wave"), button -> selectedEmote = "Wave selected")
                .dimensions(left + 18, top + 62, 120, 24).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Dance"), button -> selectedEmote = "Dance selected")
                .dimensions(left + 152, top + 62, 120, 24).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Cheer"), button -> selectedEmote = "Cheer selected")
                .dimensions(left + 18, top + 94, 120, 24).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Sit"), button -> selectedEmote = "Sit selected")
                .dimensions(left + 152, top + 94, 120, 24).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Back"), button -> client.setScreen(parent))
                .dimensions(left + 80, top + 135, 130, 22).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        int left = this.width / 2 - 145;
        int top = this.height / 2 - 95;
        context.fill(left, top, left + 290, top + 180, 0xF0141724);
        context.fill(left, top, left + 290, top + 3, ACCENT);
        context.drawTextWithShadow(textRenderer, Text.literal("NAXY EMOTES"), left + 18, top + 18, 0xFFFFFFFF);
        context.drawTextWithShadow(textRenderer, Text.literal("Wave • Dance • Cheer • Sit"), left + 18, top + 37, 0xFFB9C0D4);
        context.drawTextWithShadow(textRenderer, Text.literal(selectedEmote), left + 18, top + 124, ACCENT);
        context.drawTextWithShadow(textRenderer, Text.literal("Animation playback is the next step."), left + 18, top + 161, 0xFFB9C0D4);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
