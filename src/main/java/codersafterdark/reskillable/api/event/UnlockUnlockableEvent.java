// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.api.event;

import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraft.entity.player.EntityPlayer;
import codersafterdark.reskillable.api.unlockable.Unlockable;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class UnlockUnlockableEvent extends PlayerEvent
{
    private Unlockable unlockable;
    
    protected UnlockUnlockableEvent(final EntityPlayer player, final Unlockable unlockable) {
        super(player);
        this.unlockable = unlockable;
    }
    
    public Unlockable getUnlockable() {
        return this.unlockable;
    }
    
    @Cancelable
    public static class Pre extends UnlockUnlockableEvent
    {
        public Pre(final EntityPlayer player, final Unlockable unlockable) {
            super(player, unlockable);
        }
    }
    
    public static class Post extends UnlockUnlockableEvent
    {
        public Post(final EntityPlayer player, final Unlockable unlockable) {
            super(player, unlockable);
        }
    }
}
