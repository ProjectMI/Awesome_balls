//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.advancement.skilllevel;

import net.minecraft.advancements.ICriterionInstance;
import com.google.gson.JsonParseException;
import codersafterdark.reskillable.api.ReskillableRegistries;
import net.minecraft.util.JsonUtils;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import codersafterdark.reskillable.api.skill.Skill;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import mcp.MethodsReturnNonnullByDefault;
import javax.annotation.ParametersAreNonnullByDefault;
import codersafterdark.reskillable.advancement.CriterionTrigger;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SkillLevelTrigger extends CriterionTrigger<SkillLevelListeners, SkillLevelCriterionInstance>
{
    public SkillLevelTrigger() {
        super(new ResourceLocation("reskillable", "skill_level"), SkillLevelListeners::new);
    }
    
    public void trigger(final EntityPlayerMP player, final Skill skill, final int level) {
        final SkillLevelListeners listeners = ((CriterionTrigger<SkillLevelListeners, U>)this).getListeners(player.getAdvancements());
        if (listeners != null) {
            listeners.trigger(skill, level);
        }
    }
    
    public SkillLevelCriterionInstance deserializeInstance(final JsonObject json, final JsonDeserializationContext context) {
        final int level = JsonUtils.getInt(json, "level");
        if (!json.has("skill")) {
            return new SkillLevelCriterionInstance(null, level);
        }
        final ResourceLocation skillName = new ResourceLocation(JsonUtils.getString(json, "skill"));
        final Skill skill = (Skill)ReskillableRegistries.SKILLS.getValue(skillName);
        if (skill != null) {
            return new SkillLevelCriterionInstance(skill, level);
        }
        throw new JsonParseException("Failed to find Matching Skill for Name: " + skillName);
    }
}
