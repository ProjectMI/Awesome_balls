// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.api.requirement.logic;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.text.TextFormatting;
import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.requirement.RequirementCache;
import net.minecraft.entity.player.EntityPlayer;
import codersafterdark.reskillable.api.requirement.Requirement;

public abstract class DoubleRequirement extends Requirement implements OuterRequirement
{
    private final Requirement left;
    private final Requirement right;
    
    protected DoubleRequirement(final Requirement left, final Requirement right) {
        this.left = left;
        this.right = right;
    }
    
    public Requirement getLeft() {
        return this.left;
    }
    
    public Requirement getRight() {
        return this.right;
    }
    
    protected abstract String getFormat();
    
    protected boolean leftAchieved(final EntityPlayer player) {
        return RequirementCache.requirementAchieved(player, this.getLeft());
    }
    
    protected boolean rightAchieved(final EntityPlayer player) {
        return RequirementCache.requirementAchieved(player, this.getRight());
    }
    
    @Override
    public String getToolTip(final PlayerData data) {
        final TextFormatting color = (data == null || !data.requirementAchieved(this)) ? TextFormatting.RED : TextFormatting.GREEN;
        return TextFormatting.GRAY + " - " + this.getToolTipPart(data, this.getLeft()) + ' ' + color + this.getFormat() + ' ' + this.getToolTipPart(data, this.getRight());
    }
    
    private String getToolTipPart(final PlayerData data, final Requirement side) {
        String tooltip = side.getToolTip(data);
        if (tooltip != null && tooltip.startsWith(TextFormatting.GRAY + " - ")) {
            tooltip = tooltip.replaceFirst(TextFormatting.GRAY + " - ", "");
        }
        if (side instanceof DoubleRequirement) {
            tooltip = TextFormatting.GOLD + "(" + TextFormatting.RESET + tooltip + TextFormatting.GOLD + ')';
        }
        else {
            tooltip = TextFormatting.RESET + tooltip;
        }
        return tooltip;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof DoubleRequirement) {
            final DoubleRequirement dreq = (DoubleRequirement)o;
            return (this.getRight().equals(dreq.getRight()) && this.getLeft().equals(dreq.getLeft())) || (this.getRight().equals(dreq.getLeft()) && this.getLeft().equals(dreq.getRight()));
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        final long leftHash = this.getLeft().hashCode();
        final long rightHash = this.getRight().hashCode();
        return (int)((leftHash + rightHash) / 2L);
    }
    
    @Nonnull
    @Override
    public List<Class<? extends Requirement>> getInternalTypes() {
        final List<Class<? extends Requirement>> types = new ArrayList<Class<? extends Requirement>>();
        final Requirement lReq = this.getLeft();
        final Requirement rReq = this.getRight();
        if (lReq instanceof OuterRequirement) {
            types.addAll(((OuterRequirement)lReq).getInternalTypes());
        }
        else {
            types.add(lReq.getClass());
        }
        if (rReq instanceof OuterRequirement) {
            types.addAll(((OuterRequirement)rReq).getInternalTypes());
        }
        else {
            types.add(rReq.getClass());
        }
        return types;
    }
    
    @Override
    public boolean isCacheable() {
        return this.getLeft().isCacheable() && this.getRight().isCacheable();
    }
}
