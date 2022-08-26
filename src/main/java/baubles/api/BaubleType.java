// 
// Decompiled by Procyon v0.5.36
// 

package baubles.api;

public enum BaubleType
{
    AMULET(new int[] { 0 }), 
    RING(new int[] { 1, 2 }), 
    BELT(new int[] { 3 }), 
    TRINKET(new int[] { 0, 1, 2, 3, 4, 5, 6 }), 
    HEAD(new int[] { 4 }), 
    BODY(new int[] { 5 }), 
    CHARM(new int[] { 6 });
    
    int[] validSlots;
    
    private BaubleType(final int[] validSlots) {
        this.validSlots = validSlots;
    }
    
    public boolean hasSlot(final int slot) {
        for (final int s : this.validSlots) {
            if (s == slot) {
                return true;
            }
        }
        return false;
    }
    
    public int[] getValidSlots() {
        return this.validSlots;
    }
}
