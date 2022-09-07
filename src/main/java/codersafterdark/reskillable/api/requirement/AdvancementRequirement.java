//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.api.requirement;

import codersafterdark.reskillable.api.data.RequirementHolder;
import net.minecraft.advancements.Advancement;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import codersafterdark.reskillable.api.data.PlayerData;
import java.util.function.Function;
import net.minecraft.advancements.AdvancementProgress;
import codersafterdark.reskillable.api.ReskillableAPI;
import java.util.Optional;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class AdvancementRequirement extends Requirement
{
    private ResourceLocation advancementName;
    
    public AdvancementRequirement(final ResourceLocation advancementName) {
        this.advancementName = advancementName;
    }
    
    @Override
    public boolean achievedByPlayer(final EntityPlayer entityPlayer) {
        return Optional.ofNullable(this.getAdvancement()).map(advancement -> ReskillableAPI.getInstance().getAdvancementProgress(entityPlayer, advancement)).map((Function<? super Object, ? extends Boolean>)AdvancementProgress::isDone).orElse(false);
    }
    
    @Override
    public String getToolTip(final PlayerData data) {
        if (this.tooltip.isEmpty()) {
            final Advancement adv = this.getAdvancement();
            this.tooltip = TextFormatting.GRAY + " - " + TextFormatting.GOLD + new TextComponentTranslation("reskillable.requirements.format.advancement", new Object[] { "%S", (adv == null) ? "" : adv.getDisplayText().getUnformattedText().replaceAll("[\\[\\]]", "") }).getUnformattedComponentText();
        }
        return super.getToolTip(data);
    }
    
    public Advancement getAdvancement() {
        return RequirementHolder.getAdvancementList().getAdvancement(this.advancementName);
    }
    
    @Override
    public RequirementComparision matches(final Requirement other) {
        return (other instanceof AdvancementRequirement && this.advancementName.equals((Object)((AdvancementRequirement)other).advancementName)) ? RequirementComparision.EQUAL_TO : RequirementComparision.NOT_EQUAL;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o == this || (o instanceof AdvancementRequirement && this.advancementName.equals((Object)((AdvancementRequirement)o).advancementName));
    }
    
    @Override
    public int hashCode() {
        return this.advancementName.hashCode();
    }
}
