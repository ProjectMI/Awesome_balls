//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.advancement.trait;

import net.minecraft.advancements.ICriterionInstance;
import codersafterdark.reskillable.api.ReskillableRegistries;
import com.google.gson.JsonParseException;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import codersafterdark.reskillable.api.unlockable.Unlockable;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import mcp.MethodsReturnNonnullByDefault;
import javax.annotation.ParametersAreNonnullByDefault;
import codersafterdark.reskillable.advancement.CriterionTrigger;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class UnlockUnlockableTrigger extends CriterionTrigger<UnlockUnlockableListeners, UnlockUnlockableCriterionInstance>
{
    public UnlockUnlockableTrigger() {
        super(new ResourceLocation("reskillable", "unlockable"), UnlockUnlockableListeners::new);
    }
    
    public void trigger(final EntityPlayerMP entityPlayer, final Unlockable unlockable) {
        final UnlockUnlockableListeners listeners = ((CriterionTrigger<UnlockUnlockableListeners, U>)this).getListeners(entityPlayer.getAdvancements());
        if (listeners != null) {
            listeners.trigger(unlockable);
        }
    }
    
    public UnlockUnlockableCriterionInstance deserializeInstance(final JsonObject json, final JsonDeserializationContext context) {
        if (!json.has("unlockable_name")) {
            throw new JsonParseException("Field 'unlockable_name' not found");
        }
        final String unlockableName = json.get("unlockable_name").getAsString();
        final Unlockable unlockable = (Unlockable)ReskillableRegistries.UNLOCKABLES.getValue(new ResourceLocation(unlockableName));
        if (unlockable != null) {
            return new UnlockUnlockableCriterionInstance(unlockable);
        }
        throw new JsonParseException("No Unlockable found for name " + unlockableName);
    }
}
