package de.naxy.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class NaxyMenuScreen extends Screen {
    private int accentColor = 0xFF6688FF;
    private String selectedPage = "Home";

    public NaxyMenuScreen() {
        super(Text.literal("NAXY Client"));
    }

    @Override
    protected void init() {
        int panelWidth = 320;
        int left = (this.width - panelWidth) / 2;
        int top = (this.height - 225) / 2;

        addDrawableChild(ButtonWidget.builder(Text.literal("Home"), button -> selectedPage = "Home")
                .dimensions(left + 12, top + 52, 90, 22).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("HUD"), button -> selectedPage = "HUD (coming soon)")
                .dimensions(left + 12, top + 78, 90, 22).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Cosmetics"), button -> selectedPage = "Cosmetics (coming soon)")
                .dimensions(left + 12, top + 104, 90, 22).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Emotes"), button -> client.setScreen(new NaxyEmotesScreen(this)))
                .dimensions(left + 12, top + 130, 90, 22).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Friends"), button -> selectedPage = "Friends (coming soon)")
                .dimensions(left + 12, top + 156, 90, 22).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Change accent"), button -> {
            accentColor = accentColor == 0xFF6688FF ? 0xFFB56CFF : 0xFF6688FF;
        }).dimensions(left + 116, top + 100, 188, 22).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Close"), button -> close())
                .dimensions(left + 116, top + 136, 188, 22).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Draw a simple dim overlay instead of Screen's blur background.
        // Minecraft 1.21.11 can throw if the blur pass is invoked twice in one frame.
        context.fill(0, 0, this.width, this.height, 0xA0000000);

        int panelWidth = 320;
        int panelHeight = 225;
        int left = (this.width - panelWidth) / 2;
        int top = (this.height - panelHeight) / 2;

        context.fill(left, top, left + panelWidth, top + panelHeight, 0xF0141724);
        context.fill(left, top, left + panelWidth, top + 3, accentColor);
        context.fill(left + 108, top + 44, left + 110, top + panelHeight - 12, 0xFF30364A);
        context.drawTextWithShadow(this.textRenderer, Text.literal("NAXY CLIENT"), left + 12, top + 14, 0xFFFFFFFF);
        context.drawTextWithShadow(this.textRenderer, Text.literal("Beta • Minecraft 1.21.11"), left + 12, top + 28, 0xFFB9C0D4);
        context.drawTextWithShadow(this.textRenderer, Text.literal(selectedPage), left + 116, top + 58, accentColor);
        context.drawTextWithShadow(this.textRenderer, Text.literal("Your client. Your style."), left + 116, top + 78, 0xFFE1E5F0);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
