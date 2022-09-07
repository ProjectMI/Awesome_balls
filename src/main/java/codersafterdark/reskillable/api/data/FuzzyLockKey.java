// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.api.data;

public interface FuzzyLockKey extends LockKey
{
    boolean fuzzyEquals(final FuzzyLockKey p0);
    
    boolean isNotFuzzy();
    
    LockKey getNotFuzzy();
}
