// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.api.requirement;

import org.apache.logging.log4j.Level;
import codersafterdark.reskillable.Reskillable;
import net.minecraft.util.ResourceLocation;
import codersafterdark.reskillable.api.ReskillableRegistries;
import codersafterdark.reskillable.api.skill.Skill;
import com.google.common.collect.Maps;
import java.util.Map;

public class RequirementRegistry
{
    private Map<String, RequirementFunction<String, Requirement>> requirementHandlers;
    
    public RequirementRegistry() {
        this.requirementHandlers = (Map<String, RequirementFunction<String, Requirement>>)Maps.newHashMap();
    }
    
    public Requirement getRequirement(final String requirementString) {
        final String[] requirements = requirementString.split("\\|");
        Requirement requirement = null;
        try {
            if (requirements.length == 2) {
                final String requirementType = requirements[0];
                final String requirementInputs = requirements[1];
                if (this.requirementHandlers.containsKey(requirementType)) {
                    requirement = this.requirementHandlers.get(requirementType).apply(requirementInputs);
                }
                else {
                    final Skill skill = (Skill)ReskillableRegistries.SKILLS.getValue(new ResourceLocation(requirementType));
                    if (skill == null) {
                        throw new RequirementException("Skill '" + requirementType + "' not found.");
                    }
                    try {
                        final int level = Integer.parseInt(requirementInputs);
                        if (level <= 1) {
                            throw new RequirementException("Level must be greater than 1. Found: '" + level + "'.");
                        }
                        requirement = new SkillRequirement(skill, level);
                    }
                    catch (NumberFormatException e2) {
                        throw new RequirementException("Invalid level '" + requirementInputs + "' for skill '" + skill.getName() + "'.");
                    }
                }
            }
            else if (requirements.length > 0) {
                final String requirementType = requirements[0];
                if (this.requirementHandlers.containsKey(requirementType)) {
                    final int pos = requirementType.length() + 1;
                    final String input = (pos > requirementString.length()) ? "" : requirementString.substring(pos);
                    requirement = this.requirementHandlers.get(requirementType).apply(input);
                }
            }
        }
        catch (RequirementException e) {
            Reskillable.logger.log(Level.ERROR, "Requirement Format Exception (" + requirementString + "): " + e.getMessage());
            return null;
        }
        if (requirement == null) {
            Reskillable.logger.log(Level.ERROR, "No Requirement found for Input: " + requirementString);
        }
        else if (!requirement.isEnabled()) {
            Reskillable.logger.log(Level.ERROR, "Disabled Requirement for Input: " + requirementString);
            return null;
        }
        return requirement;
    }
    
    public void addRequirementHandler(final String identity, final RequirementFunction<String, Requirement> creator) {
        this.requirementHandlers.put(identity, creator);
    }
}
