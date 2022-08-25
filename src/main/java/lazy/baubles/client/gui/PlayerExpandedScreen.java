package lazy.baubles.client.gui;

import com.mojang.blaze3d.platform.InputConstants;
import lazy.baubles.Baubles;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.world.inventory.Slot;
import java.util.function.Supplier;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.TranslatableComponent;
import javax.annotation.Nonnull;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import lazy.baubles.container.PlayerExpandedContainer;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;

public class PlayerExpandedScreen extends EffectRenderingInventoryScreen<PlayerExpandedContainer>
{
    public static final ResourceLocation BACKGROUND;
    private float oldMouseX;
    private float oldMouseY;
    
    public PlayerExpandedScreen(final PlayerExpandedContainer container, final Inventory inventory, final Component name) {
        super((AbstractContainerMenu)container, inventory, name);
    }
    
    public void m_181908_() {
        ((PlayerExpandedContainer)this.f_97732_).baubles.setEventBlock(false);
        this.m_194018_();
        this.resetGuiLeft();
    }
    
    protected void m_7856_() {
        this.f_169369_.clear();
        super.m_7856_();
        this.resetGuiLeft();
    }
    
    protected void m_7027_(@Nonnull final PoseStack matrixStack, final int mouseX, final int mouseY) {
        if (this.f_96541_ != null) {
            this.f_96541_.f_91062_.m_92889_(matrixStack, (Component)new TranslatableComponent("container.crafting"), 115.0f, 8.0f, 4210752);
        }
    }
    
    public void m_6305_(@Nonnull final PoseStack matrixStack, final int mouseX, final int mouseY, final float partialTicks) {
        this.m_7333_(matrixStack);
        super.m_6305_(matrixStack, mouseX, mouseY, partialTicks);
        this.m_7025_(matrixStack, mouseX, mouseY);
        this.oldMouseX = (float)mouseX;
        this.oldMouseY = (float)mouseY;
    }
    
    protected void m_7286_(@Nonnull final PoseStack matrixStack, final float partialTicks, final int mouseX, final int mouseY) {
        RenderSystem.m_157427_((Supplier)GameRenderer::m_172817_);
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.m_157456_(0, PlayerExpandedScreen.BACKGROUND);
        if (this.f_96541_ != null && this.f_96541_.f_91074_ != null) {
            final int k = this.f_97735_;
            final int l = this.f_97736_;
            this.m_93228_(matrixStack, k, l, 0, 0, this.f_97726_, this.f_97727_);
            for (int i1 = 0; i1 < ((PlayerExpandedContainer)this.f_97732_).f_38839_.size(); ++i1) {
                final Slot slot = (Slot)((PlayerExpandedContainer)this.f_97732_).f_38839_.get(i1);
                if (slot.m_6657_() && slot.m_6641_() == 1) {
                    this.m_93228_(matrixStack, k + slot.f_40220_, l + slot.f_40221_, 200, 0, 16, 16);
                }
            }
            InventoryScreen.m_98850_(k + 51, l + 75, 30, k + 51 - this.oldMouseX, l + 75 - 50 - this.oldMouseY, (LivingEntity)this.f_96541_.f_91074_);
        }
    }
    
    public boolean m_7933_(final int keyCode, final int scanCode, final int what) {
        if (Baubles.KEY_BAUBLES.isActiveAndMatches(InputConstants.m_84827_(keyCode, scanCode))) {
            if (this.f_96541_ != null && this.f_96541_.f_91074_ != null) {
                this.f_96541_.f_91074_.m_6915_();
            }
            return true;
        }
        return super.m_7933_(keyCode, scanCode, what);
    }
    
    private void resetGuiLeft() {
        this.f_97735_ = (this.f_96543_ - this.f_97726_) / 2;
    }
    
    static {
        BACKGROUND = new ResourceLocation("baubles", "textures/gui/expanded_inventory.png");
    }
}
