//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.api.data;

import java.util.function.Consumer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.client.gui.GuiScreen;
import codersafterdark.reskillable.base.ConfigHandler;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import codersafterdark.reskillable.lib.LibObfuscation;
import net.minecraft.advancements.AdvancementManager;
import codersafterdark.reskillable.api.requirement.RequirementComparision;
import codersafterdark.reskillable.api.requirement.logic.TrueRequirement;
import codersafterdark.reskillable.api.ReskillableAPI;
import codersafterdark.reskillable.api.requirement.NoneRequirement;
import java.util.ArrayList;
import java.util.Collection;
import com.google.common.collect.Lists;
import codersafterdark.reskillable.api.requirement.Requirement;
import java.util.List;
import net.minecraft.advancements.AdvancementList;

public class RequirementHolder
{
    private static AdvancementList advList;
    private final List<Requirement> requirements;
    private final boolean forcedEmpty;
    private boolean hasNone;
    
    public RequirementHolder() {
        this.requirements = (List<Requirement>)Lists.newArrayList();
        this.forcedEmpty = true;
    }
    
    public RequirementHolder(final List<Requirement> requirements) {
        this.requirements = requirements;
        this.forcedEmpty = false;
    }
    
    public RequirementHolder(final RequirementHolder... others) {
        this.requirements = (List<Requirement>)Lists.newArrayList();
        this.forcedEmpty = false;
        for (final RequirementHolder other : others) {
            if (other.hasNone) {
                this.requirements.addAll(other.requirements);
                this.hasNone = true;
                break;
            }
            other.requirements.forEach(otherRequirement -> addRequirement(this.requirements, otherRequirement));
        }
    }
    
    public static RequirementHolder noneHolder() {
        final RequirementHolder requirementHolder = new RequirementHolder(new ArrayList<Requirement>());
        requirementHolder.hasNone = true;
        requirementHolder.requirements.add(new NoneRequirement());
        return requirementHolder;
    }
    
    public static RequirementHolder realEmpty() {
        return new RequirementHolder();
    }
    
    public static RequirementHolder fromStringList(final String[] requirementStringList) {
        final List<Requirement> requirements = new ArrayList<Requirement>();
        for (final String s : requirementStringList) {
            final Requirement requirement = ReskillableAPI.getInstance().getRequirementRegistry().getRequirement(s);
            if (requirement instanceof NoneRequirement) {
                return noneHolder();
            }
            addRequirement(requirements, requirement);
        }
        return requirements.isEmpty() ? realEmpty() : new RequirementHolder(requirements);
    }
    
    public static RequirementHolder fromString(final String s) {
        RequirementHolder requirementHolder;
        if (s.matches("(?i)^(null|nil)$")) {
            requirementHolder = realEmpty();
        }
        else {
            requirementHolder = fromStringList(s.split(","));
        }
        return requirementHolder;
    }
    
    private static void addRequirement(final List<Requirement> requirements, final Requirement requirement) {
        if (requirement == null || requirement instanceof TrueRequirement) {
            return;
        }
        for (int i = 0; i < requirements.size(); ++i) {
            final RequirementComparision match = requirements.get(i).matches(requirement);
            if (match.equals(RequirementComparision.EQUAL_TO) || match.equals(RequirementComparision.GREATER_THAN)) {
                return;
            }
            if (match.equals(RequirementComparision.LESS_THAN)) {
                requirements.remove(i);
                break;
            }
        }
        requirements.add(requirement);
    }
    
    public static AdvancementList getAdvancementList() {
        if (RequirementHolder.advList == null) {
            RequirementHolder.advList = (AdvancementList)ReflectionHelper.getPrivateValue((Class)AdvancementManager.class, (Object)null, LibObfuscation.ADVANCEMENT_LIST);
        }
        return RequirementHolder.advList;
    }
    
    public boolean isRealLock() {
        return this.getRestrictionLength() > 0 && !this.forcedEmpty;
    }
    
    public boolean isForcedEmpty() {
        return this.forcedEmpty;
    }
    
    public int getRestrictionLength() {
        return this.requirements.size();
    }
    
    @SideOnly(Side.CLIENT)
    public void addRequirementsToTooltip(final PlayerData data, final List<String> tooltip) {
        if (!this.isRealLock()) {
            return;
        }
        if (!ConfigHandler.hideRequirements || GuiScreen.isShiftKeyDown()) {
            tooltip.add(TextFormatting.DARK_PURPLE + new TextComponentTranslation("reskillable.misc.requirements", new Object[0]).getUnformattedComponentText());
            this.addRequirementsIgnoreShift(data, tooltip);
        }
        else {
            tooltip.add(TextFormatting.DARK_PURPLE + new TextComponentTranslation("reskillable.misc.requirements_shift", new Object[0]).getUnformattedComponentText());
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void addRequirementsIgnoreShift(final PlayerData data, final List<String> tooltip) {
        if (this.isRealLock()) {
            this.requirements.stream().map(requirement -> requirement.getToolTip(data)).forEach(tooltip::add);
        }
    }
    
    public List<Requirement> getRequirements() {
        return this.requirements;
    }
    
    public boolean hasNone() {
        return this.hasNone;
    }
}
