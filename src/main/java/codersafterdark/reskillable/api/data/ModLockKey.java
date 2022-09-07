//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.api.data;

import java.util.Objects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ModLockKey extends NBTLockKey
{
    private final String modName;
    
    public ModLockKey(final String modName) {
        this(modName, null);
    }
    
    public ModLockKey(final String modName, final NBTTagCompound tag) {
        super(tag);
        this.modName = ((modName == null) ? "" : modName.toLowerCase());
    }
    
    public ModLockKey(final ItemStack stack) {
        super(stack.getTagCompound());
        final ResourceLocation registryName = stack.getItem().getRegistryName();
        this.modName = ((registryName == null) ? "" : registryName.getNamespace());
    }
    
    @Override
    public LockKey getNotFuzzy() {
        return this.isNotFuzzy() ? this : new ModLockKey(this.modName);
    }
    
    @Override
    public boolean equals(final Object o) {
        return o == this || (o instanceof ModLockKey && this.getModName().equals(((ModLockKey)o).getModName()) && ((this.getTag() == null) ? (((ModLockKey)o).getTag() == null) : this.getTag().equals((Object)((ModLockKey)o).getTag())));
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.modName, this.tag);
    }
    
    public String getModName() {
        return this.modName;
    }
}
