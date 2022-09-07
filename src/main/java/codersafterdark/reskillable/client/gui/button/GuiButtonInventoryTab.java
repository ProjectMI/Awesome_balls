//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.client.gui.button;

import net.minecraft.entity.player.EntityPlayer;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.client.gui.handler.InventoryTabHandler;
import net.minecraft.util.text.TextComponentTranslation;
import codersafterdark.reskillable.client.gui.GuiSkills;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import javax.annotation.Nonnull;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import java.util.function.Predicate;
import net.minecraft.client.gui.GuiButton;

public class GuiButtonInventoryTab extends GuiButton
{
    public final TabType type;
    private final Predicate<GuiScreen> selectedPred;
    
    public GuiButtonInventoryTab(final int id, final int x, final int y, final TabType type, final Predicate<GuiScreen> selectedPred) {
        super(id, x, y, 32, 28, "");
        this.type = type;
        this.selectedPred = selectedPred;
    }
    
    public void drawButton(@Nonnull final Minecraft mc, final int mouseX, final int mouseY, final float f) {
        this.enabled = (this.type.shouldRender() && !mc.player.getRecipeBook().isGuiOpen());
        final GuiScreen curr = mc.currentScreen;
        if (curr instanceof GuiContainerCreative && ((GuiContainerCreative)curr).getSelectedTabIndex() != CreativeTabs.INVENTORY.getIndex()) {
            this.enabled = false;
        }
        if (this.enabled) {
            GlStateManager.color(1.0f, 1.0f, 1.0f);
            mc.renderEngine.bindTexture(GuiSkills.SKILLS_RES);
            int x = this.x;
            final int y = this.y;
            int u = 176;
            final int v = 0;
            final int w = this.width;
            final int h = this.height;
            if (this.isSelected()) {
                x += 4;
                u += w;
            }
            this.drawTexturedModalRect(x, y, u, v, w, h);
            this.drawTexturedModalRect(this.x + 12, y + 6, 176 + this.type.iconIndex * 16, 28, 16, 16);
            if (mouseX > this.x && mouseY > this.y && mouseX < this.x + this.width && mouseY < this.y + this.height) {
                InventoryTabHandler.tooltip = new TextComponentTranslation("reskillable.tab." + this.type.name().toLowerCase(), new Object[0]).getUnformattedComponentText();
                InventoryTabHandler.mx = mouseX;
                InventoryTabHandler.my = mouseY;
            }
        }
    }
    
    public boolean isSelected() {
        return this.selectedPred.test(Minecraft.getMinecraft().currentScreen);
    }
    
    public enum TabType
    {
        INVENTORY(1, (Predicate<PlayerData>)null), 
        SKILLS(0, (Predicate<PlayerData>)null), 
        ABILITIES(2, PlayerData::hasAnyAbilities);
        
        public final int iconIndex;
        private Predicate<PlayerData> renderPred;
        
        private TabType(final int iconIndex, final Predicate<PlayerData> render) {
            this.iconIndex = iconIndex;
            this.renderPred = render;
            if (this.renderPred == null) {
                this.renderPred = (data -> true);
            }
        }
        
        public boolean shouldRender() {
            final PlayerData data = PlayerDataHandler.get((EntityPlayer)Minecraft.getMinecraft().player);
            return data != null && this.renderPred.test(data);
        }
    }
}
