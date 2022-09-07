//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.api.unlockable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import codersafterdark.reskillable.api.data.RequirementHolder;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.logging.log4j.Level;
import codersafterdark.reskillable.Reskillable;
import java.util.Objects;
import codersafterdark.reskillable.api.ReskillableRegistries;
import javax.annotation.Nonnull;
import codersafterdark.reskillable.api.ReskillableAPI;
import codersafterdark.reskillable.api.skill.Skill;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

public abstract class Unlockable extends IForgeRegistryEntry.Impl<Unlockable> implements Comparable<Unlockable>, IAbilityEventHandler {
    private final String name;
    protected UnlockableConfig unlockableConfig;
    private ResourceLocation icon;
    private Skill parentSkill;
    
    public Unlockable(final ResourceLocation name, final int x, final int y, final ResourceLocation skillName, final int cost, final String... defaultRequirements) {
        this.name = name.toString().replace(":", ".");
        this.setRegistryName(name);
        this.setIcon(new ResourceLocation(name.getNamespace(), "textures/unlockables/" + name.getPath() + ".png"));
        this.unlockableConfig = ReskillableAPI.getInstance().getTraitConfig(name, x, y, cost, defaultRequirements);
        this.setParentSkill(skillName);
    }
    
    @Nonnull
    public Skill getParentSkill() {
        return this.parentSkill;
    }
    
    protected void setParentSkill(final ResourceLocation skillName) {
        if (this.parentSkill != null) {
            if (skillName != null && skillName.equals((Object)this.parentSkill.getRegistryName())) {
                return;
            }
            this.parentSkill.getUnlockables().remove(this);
        }
        this.parentSkill = Objects.requireNonNull((Skill)ReskillableRegistries.SKILLS.getValue(skillName));
        if (this.isEnabled()) {
            if (this.parentSkill.isEnabled()) {
                this.parentSkill.addUnlockable(this);
            }
            else {
                Reskillable.logger.log(Level.ERROR, this.getName() + " is enabled but the parent skill: " + this.parentSkill.getName() + " is disabled. Disabling: " + this.getName());
                this.unlockableConfig.setEnabled(false);
            }
        }
    }
    
    public RequirementHolder getRequirements() {
        return this.unlockableConfig.getRequirementHolder();
    }
    
    public String getKey() {
        return this.name;
    }
    
    public String getName() {
        return new TextComponentTranslation("reskillable.unlock." + this.getKey(), new Object[0]).getUnformattedComponentText();
    }
    
    public String getDescription() {
        return new TextComponentTranslation("reskillable.unlock." + this.getKey() + ".desc", new Object[0]).getUnformattedComponentText();
    }
    
    public ResourceLocation getIcon() {
        return this.icon;
    }
    
    protected void setIcon(final ResourceLocation newIcon) {
        this.icon = newIcon;
    }
    
    public void onUnlock(final EntityPlayer player) {
    }
    
    public void onLock(final EntityPlayer player) {
    }
    
    public boolean hasSpikes() {
        return false;
    }
    
    public boolean isEnabled() {
        return this.unlockableConfig.isEnabled();
    }
    
    public int compareTo(@Nonnull final Unlockable o) {
        final int skillCmp = this.getParentSkill().compareTo(o.getParentSkill());
        if (skillCmp == 0) {
            return this.getName().compareTo(o.getName());
        }
        return skillCmp;
    }
    
    public int getCost() {
        return this.unlockableConfig.getCost();
    }
    
    public int getX() {
        return this.unlockableConfig.getX();
    }
    
    public int getY() {
        return this.unlockableConfig.getY();
    }
    
    public final UnlockableConfig getUnlockableConfig() {
        return this.unlockableConfig;
    }

    @Override
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        IAbilityEventHandler.super.onPlayerTick(event);
    }

    @Override
    public void onBlockDrops(BlockEvent.HarvestDropsEvent event) {
        IAbilityEventHandler.super.onBlockDrops(event);
    }

    @Override
    public void getBreakSpeed(PlayerEvent.BreakSpeed event) {
        IAbilityEventHandler.super.getBreakSpeed(event);
    }

    @Override
    public void onMobDrops(LivingDropsEvent event) {
        IAbilityEventHandler.super.onMobDrops(event);
    }

    @Override
    public void onAttackMob(LivingHurtEvent event) {
        IAbilityEventHandler.super.onAttackMob(event);
    }

    @Override
    public void onHurt(LivingHurtEvent event) {
        IAbilityEventHandler.super.onHurt(event);
    }

    @Override
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        IAbilityEventHandler.super.onRightClickBlock(event);
    }

    @Override
    public void onEnderTeleport(EnderTeleportEvent event) {
        IAbilityEventHandler.super.onEnderTeleport(event);
    }

    @Override
    public void onKillMob(LivingDeathEvent event) {
        IAbilityEventHandler.super.onKillMob(event);
    }
}
