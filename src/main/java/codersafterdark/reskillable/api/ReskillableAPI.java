// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.api;

import codersafterdark.reskillable.api.requirement.AdvancementRequirement;
import codersafterdark.reskillable.api.requirement.TraitRequirement;
import codersafterdark.reskillable.api.unlockable.Unlockable;
import codersafterdark.reskillable.api.requirement.UnobtainableRequirement;
import codersafterdark.reskillable.api.requirement.RequirementException;
import codersafterdark.reskillable.api.requirement.NoneRequirement;
import codersafterdark.reskillable.api.requirement.Requirement;
import org.apache.logging.log4j.Level;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.Advancement;
import codersafterdark.reskillable.api.data.PlayerData;
import net.minecraft.entity.player.EntityPlayer;
import codersafterdark.reskillable.api.unlockable.UnlockableConfig;
import codersafterdark.reskillable.api.skill.SkillConfig;
import net.minecraft.util.ResourceLocation;
import java.util.Objects;
import codersafterdark.reskillable.api.requirement.RequirementRegistry;

public class ReskillableAPI
{
    private static ReskillableAPI instance;
    private IModAccess modAccess;
    private RequirementRegistry requirementRegistry;
    
    public ReskillableAPI(final IModAccess modAccess) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokespecial   java/lang/Object.<init>:()V
        //     4: aload_0         /* this */
        //     5: aload_1         /* modAccess */
        //     6: putfield        codersafterdark/reskillable/api/ReskillableAPI.modAccess:Lcodersafterdark/reskillable/api/IModAccess;
        //     9: aload_0         /* this */
        //    10: new             Lcodersafterdark/reskillable/api/requirement/RequirementRegistry;
        //    13: dup            
        //    14: invokespecial   codersafterdark/reskillable/api/requirement/RequirementRegistry.<init>:()V
        //    17: putfield        codersafterdark/reskillable/api/ReskillableAPI.requirementRegistry:Lcodersafterdark/reskillable/api/requirement/RequirementRegistry;
        //    20: aload_0         /* this */
        //    21: getfield        codersafterdark/reskillable/api/ReskillableAPI.requirementRegistry:Lcodersafterdark/reskillable/api/requirement/RequirementRegistry;
        //    24: ldc             "adv"
        //    26: invokedynamic   BootstrapMethod #0, apply:()Lcodersafterdark/reskillable/api/requirement/RequirementFunction;
        //    31: invokevirtual   codersafterdark/reskillable/api/requirement/RequirementRegistry.addRequirementHandler:(Ljava/lang/String;Lcodersafterdark/reskillable/api/requirement/RequirementFunction;)V
        //    34: aload_0         /* this */
        //    35: getfield        codersafterdark/reskillable/api/ReskillableAPI.requirementRegistry:Lcodersafterdark/reskillable/api/requirement/RequirementRegistry;
        //    38: ldc             "trait"
        //    40: invokedynamic   BootstrapMethod #1, apply:()Lcodersafterdark/reskillable/api/requirement/RequirementFunction;
        //    45: invokevirtual   codersafterdark/reskillable/api/requirement/RequirementRegistry.addRequirementHandler:(Ljava/lang/String;Lcodersafterdark/reskillable/api/requirement/RequirementFunction;)V
        //    48: aload_0         /* this */
        //    49: getfield        codersafterdark/reskillable/api/ReskillableAPI.requirementRegistry:Lcodersafterdark/reskillable/api/requirement/RequirementRegistry;
        //    52: ldc             "unobtainable"
        //    54: invokedynamic   BootstrapMethod #2, apply:()Lcodersafterdark/reskillable/api/requirement/RequirementFunction;
        //    59: invokevirtual   codersafterdark/reskillable/api/requirement/RequirementRegistry.addRequirementHandler:(Ljava/lang/String;Lcodersafterdark/reskillable/api/requirement/RequirementFunction;)V
        //    62: aload_0         /* this */
        //    63: getfield        codersafterdark/reskillable/api/ReskillableAPI.requirementRegistry:Lcodersafterdark/reskillable/api/requirement/RequirementRegistry;
        //    66: ldc             "none"
        //    68: invokedynamic   BootstrapMethod #3, apply:()Lcodersafterdark/reskillable/api/requirement/RequirementFunction;
        //    73: invokevirtual   codersafterdark/reskillable/api/requirement/RequirementRegistry.addRequirementHandler:(Ljava/lang/String;Lcodersafterdark/reskillable/api/requirement/RequirementFunction;)V
        //    76: aload_0         /* this */
        //    77: getfield        codersafterdark/reskillable/api/ReskillableAPI.requirementRegistry:Lcodersafterdark/reskillable/api/requirement/RequirementRegistry;
        //    80: ldc             "not"
        //    82: invokedynamic   BootstrapMethod #4, apply:()Lcodersafterdark/reskillable/api/requirement/RequirementFunction;
        //    87: invokevirtual   codersafterdark/reskillable/api/requirement/RequirementRegistry.addRequirementHandler:(Ljava/lang/String;Lcodersafterdark/reskillable/api/requirement/RequirementFunction;)V
        //    90: aload_0         /* this */
        //    91: getfield        codersafterdark/reskillable/api/ReskillableAPI.requirementRegistry:Lcodersafterdark/reskillable/api/requirement/RequirementRegistry;
        //    94: ldc             "and"
        //    96: invokedynamic   BootstrapMethod #5, apply:()Lcodersafterdark/reskillable/api/requirement/RequirementFunction;
        //   101: invokevirtual   codersafterdark/reskillable/api/requirement/RequirementRegistry.addRequirementHandler:(Ljava/lang/String;Lcodersafterdark/reskillable/api/requirement/RequirementFunction;)V
        //   104: aload_0         /* this */
        //   105: getfield        codersafterdark/reskillable/api/ReskillableAPI.requirementRegistry:Lcodersafterdark/reskillable/api/requirement/RequirementRegistry;
        //   108: ldc             "nand"
        //   110: invokedynamic   BootstrapMethod #6, apply:()Lcodersafterdark/reskillable/api/requirement/RequirementFunction;
        //   115: invokevirtual   codersafterdark/reskillable/api/requirement/RequirementRegistry.addRequirementHandler:(Ljava/lang/String;Lcodersafterdark/reskillable/api/requirement/RequirementFunction;)V
        //   118: aload_0         /* this */
        //   119: getfield        codersafterdark/reskillable/api/ReskillableAPI.requirementRegistry:Lcodersafterdark/reskillable/api/requirement/RequirementRegistry;
        //   122: ldc             "or"
        //   124: invokedynamic   BootstrapMethod #7, apply:()Lcodersafterdark/reskillable/api/requirement/RequirementFunction;
        //   129: invokevirtual   codersafterdark/reskillable/api/requirement/RequirementRegistry.addRequirementHandler:(Ljava/lang/String;Lcodersafterdark/reskillable/api/requirement/RequirementFunction;)V
        //   132: aload_0         /* this */
        //   133: getfield        codersafterdark/reskillable/api/ReskillableAPI.requirementRegistry:Lcodersafterdark/reskillable/api/requirement/RequirementRegistry;
        //   136: ldc             "nor"
        //   138: invokedynamic   BootstrapMethod #8, apply:()Lcodersafterdark/reskillable/api/requirement/RequirementFunction;
        //   143: invokevirtual   codersafterdark/reskillable/api/requirement/RequirementRegistry.addRequirementHandler:(Ljava/lang/String;Lcodersafterdark/reskillable/api/requirement/RequirementFunction;)V
        //   146: aload_0         /* this */
        //   147: getfield        codersafterdark/reskillable/api/ReskillableAPI.requirementRegistry:Lcodersafterdark/reskillable/api/requirement/RequirementRegistry;
        //   150: ldc             "xor"
        //   152: invokedynamic   BootstrapMethod #9, apply:()Lcodersafterdark/reskillable/api/requirement/RequirementFunction;
        //   157: invokevirtual   codersafterdark/reskillable/api/requirement/RequirementRegistry.addRequirementHandler:(Ljava/lang/String;Lcodersafterdark/reskillable/api/requirement/RequirementFunction;)V
        //   160: aload_0         /* this */
        //   161: getfield        codersafterdark/reskillable/api/ReskillableAPI.requirementRegistry:Lcodersafterdark/reskillable/api/requirement/RequirementRegistry;
        //   164: ldc             "xnor"
        //   166: invokedynamic   BootstrapMethod #10, apply:()Lcodersafterdark/reskillable/api/requirement/RequirementFunction;
        //   171: invokevirtual   codersafterdark/reskillable/api/requirement/RequirementRegistry.addRequirementHandler:(Ljava/lang/String;Lcodersafterdark/reskillable/api/requirement/RequirementFunction;)V
        //   174: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Could not infer any expression.
        //     at com.strobel.decompiler.ast.TypeAnalysis.runInference(TypeAnalysis.java:374)
        //     at com.strobel.decompiler.ast.TypeAnalysis.run(TypeAnalysis.java:96)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:344)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:214)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createConstructor(AstBuilder.java:713)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:549)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:330)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:251)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:126)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public static ReskillableAPI getInstance() {
        return Objects.requireNonNull(ReskillableAPI.instance, "Calling Reskillable API Instance before it's creation");
    }
    
    public static void setInstance(final ReskillableAPI reskillableAPI) {
        ReskillableAPI.instance = reskillableAPI;
    }
    
    public SkillConfig getSkillConfig(final ResourceLocation name) {
        return this.modAccess.getSkillConfig(name);
    }
    
    public UnlockableConfig getTraitConfig(final ResourceLocation name, final int x, final int y, final int cost, final String[] defaultRequirements) {
        return this.modAccess.getUnlockableConfig(name, x, y, cost, defaultRequirements);
    }
    
    public void syncPlayerData(final EntityPlayer entityPlayer, final PlayerData playerData) {
        this.modAccess.syncPlayerData(entityPlayer, playerData);
    }
    
    public AdvancementProgress getAdvancementProgress(final EntityPlayer entityPlayer, final Advancement advancement) {
        return this.modAccess.getAdvancementProgress(entityPlayer, advancement);
    }
    
    public RequirementRegistry getRequirementRegistry() {
        return this.requirementRegistry;
    }
    
    public void log(final Level warn, final String s) {
        this.modAccess.log(warn, s);
    }
}
