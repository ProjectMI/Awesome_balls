//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.loot;

import javax.annotation.Nonnull;
import java.util.List;
import com.google.gson.JsonParseException;
import java.util.ArrayList;
import net.minecraft.util.JsonUtils;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonArray;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonObject;
import net.minecraft.util.ResourceLocation;
import javax.annotation.ParametersAreNonnullByDefault;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.storage.loot.LootContext;
import java.util.Random;
import codersafterdark.reskillable.api.data.RequirementHolder;
import net.minecraft.world.storage.loot.conditions.LootCondition;

public class LootConditionRequirement implements LootCondition
{
    private final RequirementHolder requirementHolder;
    private final boolean requiresPlayer;
    private final String[] requirements;
    
    public LootConditionRequirement(final boolean requiresPlayer, final String[] requirements) {
        this.requiresPlayer = requiresPlayer;
        this.requirementHolder = ((requirements.length > 0) ? RequirementHolder.fromStringList(requirements) : RequirementHolder.realEmpty());
        this.requirements = requirements;
    }
    
    @ParametersAreNonnullByDefault
    public boolean testCondition(final Random rand, final LootContext context) {
        return (context.getKillerPlayer() instanceof EntityPlayer) ? PlayerDataHandler.get((EntityPlayer)context.getKillerPlayer()).matchStats(this.requirementHolder) : (!this.requiresPlayer);
    }
    
    public static class Serializer extends LootCondition.Serializer<LootConditionRequirement>
    {
        public Serializer() {
            super(new ResourceLocation("reskillable", "requirement"), (Class)LootConditionRequirement.class);
        }
        
        @ParametersAreNonnullByDefault
        public void serialize(final JsonObject json, final LootConditionRequirement value, final JsonSerializationContext context) {
            json.addProperty("requiresPlayer", Boolean.valueOf(value.requiresPlayer));
            if (value.requirements.length > 1) {
                final JsonArray requirementsJson = new JsonArray();
                for (final String requirement : value.requirements) {
                    requirementsJson.add(requirement);
                }
                json.add("requirements", (JsonElement)requirementsJson);
            }
            else {
                json.addProperty("requirements", value.requirements[0]);
            }
        }
        
        @Nonnull
        @ParametersAreNonnullByDefault
        public LootConditionRequirement deserialize(final JsonObject json, final JsonDeserializationContext context) {
            final boolean requiresPlayer = JsonUtils.getBoolean(json, "requiresPlayer", false);
            String[] requirements = new String[0];
            final JsonElement requirementsJson = json.get("requirements");
            if (requirementsJson != null) {
                if (requirementsJson.isJsonArray()) {
                    final List<String> reqs = new ArrayList<String>();
                    requirementsJson.getAsJsonArray().forEach(req -> reqs.add(req.getAsString()));
                    requirements = reqs.toArray(new String[0]);
                }
                else {
                    if (!requirementsJson.isJsonPrimitive() || !requirementsJson.getAsJsonPrimitive().isString()) {
                        throw new JsonParseException("Failed to find Requirements");
                    }
                    requirements = new String[] { requirementsJson.getAsJsonPrimitive().getAsString() };
                }
            }
            return new LootConditionRequirement(requiresPlayer, requirements);
        }
    }
}
