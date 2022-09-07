// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.api.data;

public final class GenericLockKey implements LockKey
{
    private Class<? extends LockKey> type;
    
    public GenericLockKey(final Class<? extends LockKey> type) {
        this.type = type;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o == this || (o instanceof GenericLockKey && ((this.type != null) ? this.type.equals(((GenericLockKey)o).type) : (((GenericLockKey)o).type == null)));
    }
    
    @Override
    public int hashCode() {
        return (this.type == null) ? super.hashCode() : this.type.hashCode();
    }
}
