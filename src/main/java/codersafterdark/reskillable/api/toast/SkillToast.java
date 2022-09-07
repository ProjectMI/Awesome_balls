//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.api.toast;

import org.apache.commons.lang3.tuple.Pair;
import net.minecraft.util.ResourceLocation;
import codersafterdark.reskillable.client.base.RenderHelper;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.toasts.GuiToast;
import net.minecraft.util.text.TextComponentTranslation;
import codersafterdark.reskillable.api.skill.Skill;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SkillToast extends AbstractToast
{
    private final Skill skill;
    private final int rank;
    
    public SkillToast(final Skill skill, final int level) {
        super(skill.getName(), new TextComponentTranslation("reskillable.toast.skill_desc", new Object[] { level }).getUnformattedComponentText());
        this.skill = skill;
        this.rank = this.skill.getRank(level);
    }
    
    @Override
    protected void renderImage(final GuiToast guiToast) {
        if (this.skill.hasCustomSprites()) {
            final ResourceLocation sprite = this.skill.getSpriteLocation(this.rank);
            if (sprite != null) {
                this.bindImage(guiToast, sprite);
                Gui.drawModalRectWithCustomSizedTexture(this.x, this.y, 0.0f, 0.0f, 16, 16, 16.0f, 16.0f);
            }
        }
        else {
            this.bindImage(guiToast, this.skill.getSpriteLocation());
            final Pair<Integer, Integer> pair = this.skill.getSpriteFromRank(this.rank);
            RenderHelper.drawTexturedModalRect(this.x, this.y, 1.0f, (int)pair.getKey(), (int)pair.getValue(), 16, 16, 0.015625f, 0.015625f);
        }
    }
    
    @Override
    protected boolean hasImage() {
        return !this.skill.hasCustomSprites() || this.skill.getSpriteLocation(this.rank) != null;
    }
}
