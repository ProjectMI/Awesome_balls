//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package baubles.client.gui;

import net.minecraftforge.fml.relauncher.ReflectionHelper;
import java.io.IOException;
import baubles.client.ClientProxy;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import baubles.common.container.ContainerPlayerExpanded;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.InventoryEffectRenderer;

public class GuiPlayerExpanded extends InventoryEffectRenderer
{
    public static final ResourceLocation background;
    private float oldMouseX;
    private float oldMouseY;
    
    public GuiPlayerExpanded(final EntityPlayer player) {
        super((Container)new ContainerPlayerExpanded(player.inventory, !player.getEntityWorld().isRemote, player));
        this.allowUserInput = true;
    }
    
    private void resetGuiLeft() {
        this.guiLeft = (this.width - this.xSize) / 2;
    }
    
    public void updateScreen() {
        ((ContainerPlayerExpanded)this.inventorySlots).baubles.setEventBlock(false);
        this.updateActivePotionEffects();
        this.resetGuiLeft();
    }
    
    public void initGui() {
        this.buttonList.clear();
        super.initGui();
        this.resetGuiLeft();
    }
    
    protected void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY) {
        this.fontRenderer.drawString(I18n.format("container.crafting", new Object[0]), 115, 8, 4210752);
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawDefaultBackground();
        this.oldMouseX = (float)mouseX;
        this.oldMouseY = (float)mouseY;
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }
    
    protected void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(GuiPlayerExpanded.background);
        final int k = this.guiLeft;
        final int l = this.guiTop;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        for (int i1 = 0; i1 < this.inventorySlots.inventorySlots.size(); ++i1) {
            final Slot slot = this.inventorySlots.inventorySlots.get(i1);
            if (slot.getHasStack() && slot.getSlotStackLimit() == 1) {
                this.drawTexturedModalRect(k + slot.xPos, l + slot.yPos, 200, 0, 16, 16);
            }
        }
        GuiInventory.drawEntityOnScreen(k + 51, l + 75, 30, k + 51 - this.oldMouseX, l + 75 - 50 - this.oldMouseY, (EntityLivingBase)this.mc.player);
    }
    
    protected void actionPerformed(final GuiButton button) {
        if (button.id == 0) {}
        if (button.id == 1) {
            this.mc.displayGuiScreen((GuiScreen)new GuiStats((GuiScreen)this, this.mc.player.getStatFileWriter()));
        }
    }
    
    protected void keyTyped(final char par1, final int par2) throws IOException {
        if (par2 == ClientProxy.KEY_BAUBLES.getKeyCode()) {
            this.mc.player.closeScreen();
        }
        else {
            super.keyTyped(par1, par2);
        }
    }
    
    public void displayNormalInventory() {
        final GuiInventory gui = new GuiInventory((EntityPlayer)this.mc.player);
        ReflectionHelper.setPrivateValue((Class)GuiInventory.class, (Object)gui, (Object)this.oldMouseX, new String[] { "oldMouseX", "oldMouseX" });
        ReflectionHelper.setPrivateValue((Class)GuiInventory.class, (Object)gui, (Object)this.oldMouseY, new String[] { "oldMouseY", "oldMouseY" });
        this.mc.displayGuiScreen((GuiScreen)gui);
    }
    
    static {
        background = new ResourceLocation("baubles", "textures/gui/expanded_inventory.png");
    }
}
