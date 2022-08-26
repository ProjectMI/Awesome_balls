package ru.zonasumraka.maturinextensions.gui;

import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.client.gui.GuiSkills;
import codersafterdark.reskillable.client.gui.handler.InventoryTabHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import ru.zonasumraka.maturinextensions.util.ClientUtils;

import javax.annotation.Nonnull;
import java.util.function.Predicate;

public class GuiButtonInventoryTab extends GuiButton {
    public final TabType type;
    private final Predicate<GuiScreen> selectedPred;
    
    public static final ResourceLocation TAB_RES = new ResourceLocation("maturinextensions:textures/gui/tabs.png");

    public GuiButtonInventoryTab(int id, int x, int y, TabType type, Predicate<GuiScreen> selectedPred) {
        super(id, x, y, 32, 28, "");
        this.type = type;
        this.selectedPred = selectedPred;
    }

    @Override
    public void drawButton(@Nonnull Minecraft mc, int mouseX, int mouseY, float f) {
        enabled = type.shouldRender() && !mc.player.getRecipeBook().isGuiOpen();

        GuiScreen curr = mc.currentScreen;
        if (curr instanceof GuiContainerCreative && ((GuiContainerCreative) curr).getSelectedTabIndex() != CreativeTabs.INVENTORY.getTabIndex()) {
            enabled = false;
        }

        if (enabled) {
            GlStateManager.color(1F, 1F, 1F);
            mc.renderEngine.bindTexture(GuiSkills.SKILLS_RES);

            int x = this.x;
            int y = this.y;
            int u = 176;
            int v = 0;
            int w = width;
            int h = height;

            if (isSelected()) {
                x += 4;
                u += w;
            }

            drawTexturedModalRect(x, y, u, v, w, h);
            
            mc.renderEngine.bindTexture(TAB_RES);
            ClientUtils.drawNonStandardTexturedRect(this.x + 12, y + 6, type.iconIndex * 16, 0, 16, 16, 16, 16);

            if (mouseX > this.x && mouseY > this.y && mouseX < this.x + width && mouseY < this.y + height) {
                InventoryTabHandler.tooltip = new TextComponentTranslation("skillable.tab." + type.name().toLowerCase()).getUnformattedComponentText();
                InventoryTabHandler.mx = mouseX;
                InventoryTabHandler.my = mouseY;
            }
        }
    }

    public boolean isSelected() {
        return selectedPred.test(Minecraft.getMinecraft().currentScreen);
    }

    public enum TabType {
    	STATS(0, null);

        public final int iconIndex;
        private Predicate<PlayerData> renderPred;

        TabType(int iconIndex, Predicate<PlayerData> render) {
            this.iconIndex = iconIndex;
            this.renderPred = render;
            if (renderPred == null) {
                renderPred = data -> true;
            }
        }

        public boolean shouldRender() {
            return renderPred.test(PlayerDataHandler.get(Minecraft.getMinecraft().player));
        }
    }
}