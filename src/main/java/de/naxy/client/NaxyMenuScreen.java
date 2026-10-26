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
        clearChildren();

        int panelWidth = Math.min(460, this.width - 32);
        int panelHeight = Math.min(278, this.height - 32);
        int left = (this.width - panelWidth) / 2;
        int top = (this.height - panelHeight) / 2;
        int sidebarWidth = 132;
        int contentLeft = left + sidebarWidth + 18;
        int contentWidth = panelWidth - sidebarWidth - 34;

        String[] pages = {"Home", "HUD", "Cosmetics", "Emotes", "Friends"};
        for (int i = 0; i < pages.length; i++) {
            final String page = pages[i];
            int y = top + 58 + i * 30;
            addDrawableChild(ButtonWidget.builder(Text.literal(page), button -> {
                if ("Emotes".equals(page)) {
                    client.setScreen(new NaxyEmotesScreen(this));
                } else {
                    selectedPage = page;
                }
            }).dimensions(left + 14, y, sidebarWidth - 28, 24).build());
        }

        addDrawableChild(ButtonWidget.builder(Text.literal("Change accent"), button -> {
            accentColor = accentColor == 0xFF6688FF ? 0xFFB56CFF : 0xFF6688FF;
        }).dimensions(contentLeft, top + panelHeight - 56, contentWidth, 22).build());

        addDrawableChild(ButtonWidget.builder(Text.literal("Close"), button -> close())
                .dimensions(contentLeft, top + panelHeight - 29, contentWidth, 22).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, this.width, this.height, 0xB8000000);

        int panelWidth = Math.min(460, this.width - 32);
        int panelHeight = Math.min(278, this.height - 32);
        int left = (this.width - panelWidth) / 2;
        int top = (this.height - panelHeight) / 2;
        int sidebarWidth = 132;
        int contentLeft = left + sidebarWidth + 18;

        context.fill(left, top, left + panelWidth, top + panelHeight, 0xF0141724);
        context.fill(left, top, left + panelWidth, top + 4, accentColor);
        context.fill(left + sidebarWidth, top + 18, left + sidebarWidth + 1, top + panelHeight - 16, 0xFF30364A);
        context.drawTextWithShadow(this.textRenderer, Text.literal("NAXY CLIENT"), left + 18, top + 17, 0xFFFFFFFF);
        context.drawTextWithShadow(this.textRenderer, Text.literal("BETA  •  1.21.11"), left + 18, top + 34, 0xFF9DA8C4);
        context.drawTextWithShadow(this.textRenderer, Text.literal("MENU"), left + 18, top + 46, 0xFF7E8BAA);

        context.drawTextWithShadow(this.textRenderer, Text.literal(selectedPage.toUpperCase()), contentLeft, top + 26, accentColor);
        context.fill(contentLeft, top + 43, left + panelWidth - 18, top + 44, 0xFF30364A);

        String description = switch (selectedPage) {
            case "HUD" -> "Personalize your on-screen display.";
            case "Cosmetics" -> "Your look, your style.";
            case "Friends" -> "Social features are coming soon.";
            default -> "Welcome to your client.";
        };
        context.drawTextWithShadow(this.textRenderer, Text.literal(description), contentLeft, top + 57, 0xFFE1E5F0);
        context.drawTextWithShadow(this.textRenderer, Text.literal("Clean. Simple. NAXY."), contentLeft, top + 78, 0xFF9DA8C4);
        context.drawTextWithShadow(this.textRenderer, Text.literal("Accent color"), contentLeft, top + 111, 0xFFFFFFFF);
        context.fill(contentLeft, top + 128, contentLeft + 28, top + 132, accentColor);
        context.drawTextWithShadow(this.textRenderer, Text.literal("Settings are kept lightweight."), contentLeft, top + 151, 0xFF9DA8C4);

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
