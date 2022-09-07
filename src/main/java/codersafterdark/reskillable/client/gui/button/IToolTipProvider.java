// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.client.gui.button;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import javax.annotation.Nullable;

public interface IToolTipProvider
{
    @Nullable
    @SideOnly(Side.CLIENT)
    ToolTip getToolTip(final int p0, final int p1);
    
    @SideOnly(Side.CLIENT)
    boolean isToolTipVisible();
    
    @SideOnly(Side.CLIENT)
    boolean isMouseOver(final int p0, final int p1);
}
