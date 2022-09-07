// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.api.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.Event;

public class CacheInvalidatedEvent extends Event
{
    private EntityPlayer player;
    private boolean modified;
    
    public CacheInvalidatedEvent(final EntityPlayer player) {
        this(player, false);
    }
    
    public CacheInvalidatedEvent(final EntityPlayer player, final boolean modified) {
        this.player = player;
        this.modified = modified;
    }
    
    public EntityPlayer getPlayer() {
        return this.player;
    }
    
    public boolean anyModified() {
        return this.modified;
    }
}
