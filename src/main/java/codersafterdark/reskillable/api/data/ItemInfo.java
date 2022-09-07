//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.api.data;

import java.util.Objects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.item.Item;

public class ItemInfo extends NBTLockKey
{
    private int metadata;
    private Item item;
    
    public ItemInfo(final Item item, final int metadata) {
        this(item, metadata, null);
    }
    
    public ItemInfo(final Item item, final int metadata, final NBTTagCompound tag) {
        super(tag);
        this.item = item;
        this.metadata = metadata;
    }
    
    public ItemInfo(final ItemStack stack) {
        this(stack.getItem(), stack.getMetadata(), stack.getTagCompound());
    }
    
    @Override
    public LockKey getNotFuzzy() {
        return this.isNotFuzzy() ? this : new ItemInfo(this.item, this.metadata);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ItemInfo)) {
            return false;
        }
        final ItemInfo other = (ItemInfo)o;
        if (this.getItem() != other.getItem()) {
            return false;
        }
        if (this.getTag() == null) {
            return other.getTag() == null && (this.getMetadata() == 32767 || other.getMetadata() == 32767 || this.getMetadata() == other.getMetadata());
        }
        return this.getTag().equals((Object)other.getTag()) && (this.getMetadata() == 32767 || other.getMetadata() == 32767 || this.getMetadata() == other.getMetadata());
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.item, this.tag);
    }
    
    public Item getItem() {
        return this.item;
    }
    
    public int getMetadata() {
        return this.metadata;
    }
}
