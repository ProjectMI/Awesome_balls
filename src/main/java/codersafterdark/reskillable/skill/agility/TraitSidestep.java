//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.skill.agility;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.MathHelper;
import net.minecraft.client.settings.GameSettings;
import codersafterdark.reskillable.client.base.ClientTickHandler;
import codersafterdark.reskillable.api.unlockable.Unlockable;
import net.minecraft.entity.player.EntityPlayer;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraft.util.ResourceLocation;
import codersafterdark.reskillable.api.unlockable.Trait;

public class TraitSidestep extends Trait
{
    public static final int MAX_CD = 20;
    private int leftDown;
    private int rightDown;
    private int cd;
    
    public TraitSidestep() {
        super(new ResourceLocation("reskillable", "sidestep"), 3, 1, new ResourceLocation("reskillable", "agility"), 10, new String[] { "reskillable:agility|26", "reskillable:defense|20" });
        if (FMLCommonHandler.instance().getSide().isClient()) {
            MinecraftForge.EVENT_BUS.register((Object)this);
        }
    }
    
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void clientTick(final TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END && this.cd > 0) {
            --this.cd;
        }
    }
    
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onKeyDown(final InputEvent.KeyInputEvent event) {
        if (this.cd > 0) {
            return;
        }
        final Minecraft mc = Minecraft.getMinecraft();
        if (!PlayerDataHandler.get((EntityPlayer)mc.player).getSkillInfo(this.getParentSkill()).isUnlocked(this) || mc.player.isSneaking()) {
            return;
        }
        final int threshold = 4;
        if (mc.gameSettings.keyBindLeft.isKeyDown()) {
            final int oldLeft = this.leftDown;
            this.leftDown = ClientTickHandler.ticksInGame;
            if (this.leftDown - oldLeft < threshold) {
                this.dodge((EntityPlayer)mc.player, true);
            }
        }
        else if (mc.gameSettings.keyBindRight.isKeyDown()) {
            final int oldRight = this.rightDown;
            this.rightDown = ClientTickHandler.ticksInGame;
            if (this.rightDown - oldRight < threshold) {
                this.dodge((EntityPlayer)mc.player, false);
            }
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void dodge(final EntityPlayer player, final boolean left) {
        if (player.capabilities.isFlying || !player.onGround || player.moveForward >= 0.0f || !GameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindSprint)) {
            return;
        }
        final float yaw = player.rotationYaw;
        final float x = MathHelper.sin(-yaw * 0.017453292f - 3.1415927f);
        final float z = MathHelper.cos(-yaw * 0.017453292f - 3.1415927f);
        final Vec3d lookVec = new Vec3d((double)x, 0.0, (double)z);
        final Vec3d sideVec = lookVec.crossProduct(new Vec3d(0.0, left ? 1.0 : -1.0, 0.0)).scale(1.25);
        player.motionX = sideVec.x;
        player.motionY = sideVec.y;
        player.motionZ = sideVec.z;
        this.cd = 20;
    }
    
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void renderHUD(final RenderGameOverlayEvent.Post event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) {
            return;
        }
        final ScaledResolution res = event.getResolution();
        final Minecraft mc = Minecraft.getMinecraft();
        final int xo = res.getScaledWidth() / 2 - 20;
        final int y = res.getScaledHeight() / 2 + 20;
        GlStateManager.enableBlend();
        if (!mc.player.capabilities.isFlying) {
            final int width = Math.min((int)((this.cd - event.getPartialTicks()) * 2.0f), 40);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            if (width > 0) {
                Gui.drawRect(xo, y - 2, xo + 40, y - 1, 1140850688);
                Gui.drawRect(xo, y - 2, xo + width, y - 1, -1);
            }
        }
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }
}
