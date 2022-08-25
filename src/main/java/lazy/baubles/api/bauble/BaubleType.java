package lazy.baubles.api.bauble;

import java.util.Arrays;

public enum BaubleType {
    AMULET(new int[]{0}),
    RING(new int[]{1, 2}),
    BELT(new int[]{3}),
    TRINKET(new int[]{0, 1, 2, 3, 4, 5, 6}),
    HEAD(new int[]{4}),
    BODY(new int[]{5}),
    CHARM(new int[]{6});

    int[] validSlots;

    private BaubleType(int... validSlots) {
        this.validSlots = validSlots;
    }

    public boolean hasSlot(int slot) {
        return Arrays.stream(this.validSlots).anyMatch((s) -> {
            return s == slot;
        });
    }

    public int[] getValidSlots() {
        return this.validSlots;
    }

    public static BaubleType getFromString(String type) {
        BaubleType var10000;
        switch (type) {
            case "ring":
                var10000 = RING;
                break;
            case "amulet":
                var10000 = AMULET;
                break;
            case "belt":
                var10000 = BELT;
                break;
            case "head":
                var10000 = HEAD;
                break;
            case "body":
                var10000 = BODY;
                break;
            case "charm":
                var10000 = CHARM;
                break;
            default:
                var10000 = TRINKET;
        }

        return var10000;
    }
}