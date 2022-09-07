// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.client.gui.button;

import java.util.Collections;
import java.util.Iterator;
import javax.annotation.Nullable;
import net.minecraft.util.text.TextFormatting;
import java.util.ArrayList;
import java.util.List;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ToolTip
{
    private final List<String> lines;
    private final long delay;
    private long mouseOverStart;
    
    public ToolTip() {
        this.lines = new ArrayList<String>();
        this.delay = 0L;
    }
    
    public ToolTip(final int delay) {
        this.lines = new ArrayList<String>();
        this.delay = delay;
    }
    
    public void clear() {
        this.lines.clear();
    }
    
    public boolean add(final String line) {
        return this.add(line, null);
    }
    
    public boolean add(final String line, @Nullable final TextFormatting formatting) {
        return this.lines.add((formatting != null) ? (formatting + line) : line);
    }
    
    public boolean add(final List lines) {
        boolean changed = false;
        for (final Object line : lines) {
            if (line instanceof String) {
                changed |= this.add((String)line);
            }
        }
        return changed;
    }
    
    public List<String> getLines() {
        return Collections.unmodifiableList((List<? extends String>)this.lines);
    }
    
    public void onTick(final boolean mouseOver) {
        if (this.delay == 0L) {
            return;
        }
        if (mouseOver) {
            if (this.mouseOverStart == 0L) {
                this.mouseOverStart = System.currentTimeMillis();
            }
        }
        else {
            this.mouseOverStart = 0L;
        }
    }
    
    public boolean isReady() {
        return this.delay == 0L || (this.mouseOverStart != 0L && System.currentTimeMillis() - this.mouseOverStart >= this.delay);
    }
    
    public void refresh() {
    }
}
