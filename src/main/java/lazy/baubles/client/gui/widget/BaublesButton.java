package lazy.baubles.client.gui.widget;

import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TranslatableComponent;
import lazy.baubles.client.gui.PlayerExpandedScreen;
import java.util.function.Supplier;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.GameRenderer;
import javax.annotation.Nonnull;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.player.Player;
import lazy.baubles.network.msg.OpenNormalInvPacket;
import lazy.baubles.network.msg.OpenBaublesInvPacket;
import lazy.baubles.network.PacketHandler;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.AbstractButton;

public class BaublesButton extends AbstractButton
{
    private final AbstractContainerScreen parentGui;
    private final Minecraft minecraft;
    
    public BaublesButton(final AbstractContainerScreen parentGui, final int x, final int y, final int width, final int height) {
        super(x + parentGui.getGuiLeft(), parentGui.getGuiTop() + y, width, height, (Component)new TextComponent(""));
        this.parentGui = parentGui;
        this.minecraft = Minecraft.m_91087_();
    }
    
    public void m_5691_() {
        if (this.parentGui instanceof InventoryScreen) {
            PacketHandler.INSTANCE.sendToServer((Object)new OpenBaublesInvPacket());
        }
        else if (this.minecraft.f_91074_ != null) {
            PacketHandler.INSTANCE.sendToServer((Object)new OpenNormalInvPacket());
            this.displayNormalInventory((Player)this.minecraft.f_91074_);
        }
    }
    
    public void m_6305_(@Nonnull final PoseStack poseStack, final int mouseX, final int mouseY, final float partialTicks) {
        if (this.minecraft == null) {
            return;
        }
        if (this.minecraft.f_91074_ != null && !this.minecraft.f_91074_.m_7500_() && this.f_93624_) {
            RenderSystem.m_157427_((Supplier)GameRenderer::m_172817_);
            RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.m_157456_(0, PlayerExpandedScreen.BACKGROUND);
            this.f_93622_ = (mouseX >= this.f_93620_ && mouseY >= this.f_93621_ && mouseX < this.f_93620_ + this.f_93618_ && mouseY < this.f_93621_ + this.f_93619_);
            RenderSystem.m_69478_();
            RenderSystem.m_69411_(770, 771, 1, 0);
            RenderSystem.m_69411_(770, 771, 1, 0);
            RenderSystem.m_69405_(770, 771);
            poseStack.m_85836_();
            poseStack.m_85837_(0.0, 0.0, 200.0);
            poseStack.m_85837_(0.0, 0.0, 200.0);
            if (!this.f_93622_) {
                this.m_93228_(poseStack, this.f_93620_, this.f_93621_, 200, 48, 10, 10);
            }
            else {
                this.m_93228_(poseStack, this.f_93620_, this.f_93621_, 210, 48, 10, 10);
                this.minecraft.f_91062_.m_92883_(poseStack, new TranslatableComponent("button.displayText").getString(), (float)(this.f_93620_ + 5), (float)(this.f_93621_ + this.f_93619_), 16777215);
            }
            poseStack.m_85849_();
        }
    }
    
    public void displayNormalInventory(final Player player) {
        Minecraft.m_91087_().m_91152_((Screen)new InventoryScreen(player));
    }
    
    public void m_142291_(@Nonnull final NarrationElementOutput output) {
        this.m_168802_(output);
    }
}
