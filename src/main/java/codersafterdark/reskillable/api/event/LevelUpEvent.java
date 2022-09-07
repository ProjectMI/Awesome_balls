// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.api.event;

import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraft.entity.player.EntityPlayer;
import codersafterdark.reskillable.api.skill.Skill;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class LevelUpEvent extends PlayerEvent
{
    private Skill skill;
    private int level;
    private int oldLevel;
    
    protected LevelUpEvent(final EntityPlayer player, final Skill skill, final int level, final int oldLevel) {
        super(player);
        this.skill = skill;
        this.level = level;
        this.oldLevel = oldLevel;
    }
    
    public Skill getSkill() {
        return this.skill;
    }
    
    public int getLevel() {
        return this.level;
    }
    
    public int getOldLevel() {
        return this.oldLevel;
    }
    
    @Cancelable
    public static class Pre extends LevelUpEvent
    {
        public Pre(final EntityPlayer player, final Skill skill, final int level) {
            this(player, skill, level, level - 1);
        }
        
        public Pre(final EntityPlayer player, final Skill skill, final int level, final int oldLevel) {
            super(player, skill, level, oldLevel);
        }
    }
    
    public static class Post extends LevelUpEvent
    {
        public Post(final EntityPlayer player, final Skill skill, final int level) {
            this(player, skill, level, level - 1);
        }
        
        public Post(final EntityPlayer player, final Skill skill, final int level, final int oldLevel) {
            super(player, skill, level, oldLevel);
        }
    }
}
