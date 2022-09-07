//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.api.data;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class GenericNBTLockKey extends NBTLockKey
{
    public GenericNBTLockKey(final NBTTagCompound tag) {
        super(tag);
    }
    
    public GenericNBTLockKey(final ItemStack stack) {
        this(stack.getTagCompound());
    }
    
    @Override
    public LockKey getNotFuzzy() {
        return null;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o == this || (o instanceof GenericNBTLockKey && ((this.getTag() != null) ? this.getTag().equals((Object)((GenericNBTLockKey)o).getTag()) : (((GenericNBTLockKey)o).getTag() == null)));
    }
    
    @Override
    public int hashCode() {
        return (this.tag == null) ? super.hashCode() : this.tag.hashCode();
    }
}
