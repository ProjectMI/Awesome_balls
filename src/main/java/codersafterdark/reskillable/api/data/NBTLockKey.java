//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.api.data;

import java.util.List;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.nbt.NBTTagLongArray;
import net.minecraft.nbt.NBTTagIntArray;
import java.util.ArrayList;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

public abstract class NBTLockKey implements FuzzyLockKey
{
    protected NBTTagCompound tag;
    
    protected NBTLockKey(final NBTTagCompound tag) {
        this.tag = ((tag == null || tag.isEmpty()) ? null : tag);
    }
    
    protected static boolean similarNBT(final NBTBase full, final NBTBase partial) {
        if (full == null) {
            return partial == null;
        }
        if (partial == null) {
            return true;
        }
        if (full.getId() != partial.getId()) {
            return false;
        }
        if (full.equals((Object)partial)) {
            return true;
        }
        switch (full.getId()) {
            case 10: {
                final NBTTagCompound fullTag = (NBTTagCompound)full;
                final NBTTagCompound partialTag = (NBTTagCompound)partial;
                final Set<String> ptKeys = (Set<String>)partialTag.getKeySet();
                for (final String partialKey : ptKeys) {
                    if (!fullTag.hasKey(partialKey, (int)partialTag.getTagId(partialKey)) || !similarNBT(fullTag.getTag(partialKey), partialTag.getTag(partialKey))) {
                        return false;
                    }
                }
                return true;
            }
            case 9: {
                final NBTTagList fTagList = (NBTTagList)full;
                final NBTTagList pTagList = (NBTTagList)partial;
                if ((fTagList.isEmpty() && !pTagList.isEmpty()) || fTagList.getTagType() != pTagList.getTagType()) {
                    return false;
                }
                for (int i = 0; i < pTagList.tagCount(); ++i) {
                    final NBTBase pTag = pTagList.get(i);
                    boolean hasTag = false;
                    for (int j = 0; j < fTagList.tagCount(); ++j) {
                        if (similarNBT(fTagList.get(j), pTag)) {
                            hasTag = true;
                            break;
                        }
                    }
                    if (!hasTag) {
                        return false;
                    }
                }
                return true;
            }
            case 7: {
                final byte[] fByteArray = ((NBTTagByteArray)full).getByteArray();
                final byte[] pByteArray = ((NBTTagByteArray)partial).getByteArray();
                final List<Integer> hits = new ArrayList<Integer>();
                for (final byte pByte : pByteArray) {
                    boolean hasMatch = false;
                    for (int k = 0; k < fByteArray.length; ++k) {
                        if (!hits.contains(k) && pByte == fByteArray[k]) {
                            hits.add(k);
                            hasMatch = true;
                            break;
                        }
                    }
                    if (!hasMatch) {
                        return false;
                    }
                }
                return true;
            }
            case 11: {
                final int[] fIntArray = ((NBTTagIntArray)full).getIntArray();
                final int[] pIntArray = ((NBTTagIntArray)partial).getIntArray();
                final List<Integer> hits = new ArrayList<Integer>();
                for (final int pInt : pIntArray) {
                    boolean hasMatch2 = false;
                    for (int l = 0; l < fIntArray.length; ++l) {
                        if (!hits.contains(l) && pInt == fIntArray[l]) {
                            hits.add(l);
                            hasMatch2 = true;
                            break;
                        }
                    }
                    if (!hasMatch2) {
                        return false;
                    }
                }
                return true;
            }
            case 12: {
                final long[] fLongArray = getLongArray((NBTTagLongArray)full);
                final long[] pLongArray = getLongArray((NBTTagLongArray)partial);
                final List<Integer> hits = new ArrayList<Integer>();
                for (final long pLong : pLongArray) {
                    boolean hasMatch3 = false;
                    for (int m = 0; m < fLongArray.length; ++m) {
                        if (!hits.contains(m) && pLong == fLongArray[m]) {
                            hits.add(m);
                            hasMatch3 = true;
                            break;
                        }
                    }
                    if (!hasMatch3) {
                        return false;
                    }
                }
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    private static long[] getLongArray(final NBTTagLongArray tag) {
        final String t = tag.toString();
        final String[] entries = t.substring(3, t.length() - 1).split(",");
        final long[] data = new long[entries.length];
        for (int i = 0; i < entries.length; ++i) {
            try {
                data[i] = Long.parseLong(entries[i].substring(0, entries[i].length() - 1));
            }
            catch (Exception ex) {}
        }
        return data;
    }
    
    public NBTTagCompound getTag() {
        return this.tag;
    }
    
    @Override
    public boolean isNotFuzzy() {
        return this.tag == null;
    }
    
    @Override
    public boolean fuzzyEquals(final FuzzyLockKey other) {
        return other == this || (other instanceof NBTLockKey && similarNBT((NBTBase)this.getTag(), (NBTBase)((NBTLockKey)other).getTag()));
    }
}
