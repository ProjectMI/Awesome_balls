//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.api.requirement;

import java.util.Objects;
import java.util.Iterator;
import java.util.ArrayList;
import codersafterdark.reskillable.api.requirement.logic.OuterRequirement;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.List;
import net.minecraftforge.fml.common.eventhandler.Event;
import codersafterdark.reskillable.api.event.CacheInvalidatedEvent;
import net.minecraftforge.common.MinecraftForge;
import javax.annotation.Nullable;
import net.minecraft.world.WorldServer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import codersafterdark.reskillable.api.event.UnlockUnlockableEvent;
import codersafterdark.reskillable.api.event.LockUnlockableEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import codersafterdark.reskillable.api.event.LevelUpEvent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraft.entity.player.EntityPlayerMP;
import codersafterdark.reskillable.network.InvalidateRequirementPacket;
import codersafterdark.reskillable.network.PacketHandler;
import java.util.Collection;
import java.util.Arrays;
import codersafterdark.reskillable.api.requirement.logic.DoubleRequirement;
import codersafterdark.reskillable.api.requirement.logic.impl.NOTRequirement;
import java.util.HashSet;
import java.util.HashMap;
import javax.annotation.Nonnull;
import net.minecraft.entity.player.EntityPlayer;
import java.util.UUID;
import java.util.Map;
import java.util.Set;

public class RequirementCache
{
    private static Set<Class<? extends Requirement>> dirtyCacheTypes;
    private static Map<SidedUUID, RequirementCache> cacheMap;
    private Map<Class<? extends Requirement>, Map<Requirement, Boolean>> requirementCache;
    private Set<Class<? extends Requirement>> recentlyInvalidated;
    private boolean valid;
    private boolean isRemote;
    private boolean dirtyCache;
    private UUID uuid;
    
    @Deprecated
    public RequirementCache(@Nonnull final EntityPlayer player) {
        this(player.getUniqueID(), player.getEntityWorld().isRemote);
        RequirementCache.cacheMap.put(new SidedUUID(this.uuid, this.isRemote), this);
    }
    
    private RequirementCache(final UUID uuid, final boolean isClientPlayer) {
        this.requirementCache = new HashMap<Class<? extends Requirement>, Map<Requirement, Boolean>>();
        this.recentlyInvalidated = new HashSet<Class<? extends Requirement>>();
        this.valid = true;
        this.uuid = uuid;
        this.isRemote = isClientPlayer;
    }
    
    public static RequirementCache getCache(@Nonnull final EntityPlayer player) {
        return getCache(player.getUniqueID(), player.getEntityWorld().isRemote);
    }
    
    public static RequirementCache getCache(final UUID uuid, final boolean isRemote) {
        final SidedUUID sidedUUID = new SidedUUID(uuid, isRemote);
        if (RequirementCache.cacheMap.containsKey(sidedUUID)) {
            return RequirementCache.cacheMap.get(sidedUUID);
        }
        final RequirementCache cache = new RequirementCache(uuid, isRemote);
        RequirementCache.cacheMap.put(sidedUUID, cache);
        return cache;
    }
    
    public static boolean hasCache(@Nonnull final EntityPlayer player) {
        return hasCache(player.getUniqueID(), player.getEntityWorld().isRemote);
    }
    
    public static boolean hasCache(final UUID uuid, final boolean isRemote) {
        return RequirementCache.cacheMap.containsKey(new SidedUUID(uuid, isRemote));
    }
    
    public static void registerDirtyTypes() {
        registerRequirementType(NOTRequirement.class, DoubleRequirement.class);
    }
    
    public static void registerRequirementType(final Class<? extends Requirement>... requirementClasses) {
        RequirementCache.dirtyCacheTypes.addAll(Arrays.asList(requirementClasses));
    }
    
    public static void invalidateCache(final EntityPlayer player, final Class<? extends Requirement>... cacheTypes) {
        if (player != null) {
            invalidateCache(player.getUniqueID(), cacheTypes);
        }
    }
    
    public static void invalidateCache(final UUID uuid, final Class<? extends Requirement>... cacheTypes) {
        final boolean hasServer = hasCache(uuid, false);
        final boolean hasClient = hasCache(uuid, true);
        if (hasServer) {
            invalidateCacheNoPacket(uuid, false, cacheTypes);
            if (!hasClient) {
                final RequirementCache cache = getCache(uuid, false);
                if (cache != null) {
                    final EntityPlayer player = cache.getPlayer();
                    if (player != null) {
                        PacketHandler.INSTANCE.sendTo((IMessage)new InvalidateRequirementPacket(uuid, cacheTypes), (EntityPlayerMP)player);
                    }
                }
            }
        }
        if (hasClient) {
            invalidateCacheNoPacket(uuid, true, cacheTypes);
            if (!hasServer) {
                PacketHandler.INSTANCE.sendToServer((IMessage)new InvalidateRequirementPacket(uuid, cacheTypes));
            }
        }
    }
    
    @Deprecated
    public static void invalidateCacheNoPacket(final UUID uuid, final Class<? extends Requirement>... cacheTypes) {
        invalidateCacheNoPacket(uuid, true, cacheTypes);
        invalidateCacheNoPacket(uuid, false, cacheTypes);
    }
    
    public static void invalidateCacheNoPacket(final UUID uuid, final boolean isRemote, final Class<? extends Requirement>... cacheTypes) {
        getCache(uuid, isRemote).invalidateCache(cacheTypes);
    }
    
    public static boolean requirementAchieved(final EntityPlayer player, final Requirement requirement) {
        return player != null && requirementAchieved(player.getUniqueID(), player.getEntityWorld().isRemote, requirement);
    }
    
    @Deprecated
    public static boolean requirementAchieved(final UUID uuid, final Requirement requirement) {
        return requirementAchieved(uuid, true, requirement) || requirementAchieved(uuid, false, requirement);
    }
    
    public static boolean requirementAchieved(final UUID uuid, final boolean isRemote, final Requirement requirement) {
        return getCache(uuid, isRemote).requirementAchieved(requirement);
    }
    
    @SubscribeEvent
    public static void onLevelChange(final LevelUpEvent.Post event) {
        invalidateCache(event.getEntityPlayer().getUniqueID(), SkillRequirement.class);
    }
    
    @SubscribeEvent
    public static void onUnlockableLocked(final LockUnlockableEvent.Post event) {
        invalidateCache(event.getEntityPlayer().getUniqueID(), TraitRequirement.class);
    }
    
    @SubscribeEvent
    public static void onUnlockableUnlocked(final UnlockUnlockableEvent.Post event) {
        invalidateCache(event.getEntityPlayer().getUniqueID(), TraitRequirement.class);
    }
    
    @SubscribeEvent
    public static void onAdvancement(final AdvancementEvent event) {
        invalidateCache(event.getEntityPlayer().getUniqueID(), AdvancementRequirement.class);
    }
    
    @SubscribeEvent
    public static void onDisconnect(final PlayerEvent.PlayerLoggedOutEvent event) {
        removeCache(event.player.getUniqueID(), true);
        removeCache(event.player.getUniqueID(), false);
    }
    
    public static void removeCache(final EntityPlayer player) {
        removeCache(player.getUniqueID(), player.getEntityWorld().isRemote);
    }
    
    public static void removeCache(final UUID uuid, final boolean isRemote) {
        final SidedUUID sidedUUID = new SidedUUID(uuid, isRemote);
        if (RequirementCache.cacheMap.containsKey(sidedUUID)) {
            RequirementCache.cacheMap.get(sidedUUID).valid = false;
            RequirementCache.cacheMap.remove(sidedUUID);
        }
    }
    
    @Nullable
    private EntityPlayer getPlayer() {
        if (this.isRemote) {
            final WorldClient world = Minecraft.getMinecraft().world;
            return (world == null) ? null : world.getPlayerEntityByUUID(this.uuid);
        }
        final MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
        if (server != null) {
            for (final WorldServer world2 : server.worlds) {
                final EntityPlayer player = world2.getPlayerEntityByUUID(this.uuid);
                if (player != null) {
                    return player;
                }
            }
        }
        return null;
    }
    
    public boolean isValid() {
        return this.valid;
    }
    
    public void forceClear() {
        if (this.isValid()) {
            final EntityPlayer player = this.getPlayer();
            if (player != null) {
                this.requirementCache.clear();
                this.recentlyInvalidated.clear();
                this.dirtyCache = false;
                MinecraftForge.EVENT_BUS.post((Event)new CacheInvalidatedEvent(player, true));
                if (!this.isRemote) {
                    PacketHandler.INSTANCE.sendTo((IMessage)new InvalidateRequirementPacket(this.uuid, (Class<? extends Requirement>[])new Class[0]), (EntityPlayerMP)player);
                }
            }
        }
    }
    
    public boolean requirementAchieved(final Requirement requirement) {
        if (requirement == null || !this.isValid()) {
            return false;
        }
        final EntityPlayer player = this.getPlayer();
        if (player == null) {
            return false;
        }
        if (!requirement.isCacheable()) {
            return requirement.achievedByPlayer(player);
        }
        final Class<? extends Requirement> clazz = requirement.getClass();
        Map<Requirement, Boolean> cache;
        if (this.requirementCache.containsKey(clazz)) {
            cache = this.requirementCache.get(clazz);
            if (cache.containsKey(requirement)) {
                return cache.get(requirement);
            }
        }
        else {
            this.requirementCache.put(clazz, cache = new HashMap<Requirement, Boolean>());
        }
        final boolean achieved = requirement.achievedByPlayer(player);
        cache.put(requirement, achieved);
        if (!this.dirtyCache && RequirementCache.dirtyCacheTypes.stream().anyMatch(dirtyType -> dirtyType.isInstance(requirement))) {
            this.dirtyCache = true;
        }
        this.recentlyInvalidated.removeAll(this.recentlyInvalidated.stream().filter(type -> type.isInstance(requirement)).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()));
        if (requirement instanceof OuterRequirement) {
            this.recentlyInvalidated.removeAll(((OuterRequirement)requirement).getInternalTypes());
        }
        return achieved;
    }
    
    public void invalidateCache(final Class<? extends Requirement>... cacheType) {
        final EntityPlayer player = this.getPlayer();
        if (player == null) {
            return;
        }
        final List<Class<? extends Requirement>> dirtyTypes = this.dirtyCache ? new ArrayList<Class<? extends Requirement>>(RequirementCache.dirtyCacheTypes) : new ArrayList<Class<? extends Requirement>>();
        if (cacheType != null) {
            for (final Class<? extends Requirement> type : cacheType) {
                if (!this.recentlyInvalidated.contains(type)) {
                    dirtyTypes.add(type);
                    this.recentlyInvalidated.add(type);
                }
            }
            if (dirtyTypes.size() == RequirementCache.dirtyCacheTypes.size()) {
                MinecraftForge.EVENT_BUS.post((Event)new CacheInvalidatedEvent(player, false));
                return;
            }
        }
        if (dirtyTypes.isEmpty()) {
            MinecraftForge.EVENT_BUS.post((Event)new CacheInvalidatedEvent(player, false));
            return;
        }
        final Set<Class<? extends Requirement>> requirements = this.requirementCache.keySet();
        final List<Class<? extends Requirement>> toRemove = new ArrayList<Class<? extends Requirement>>();
        for (final Class<? extends Requirement> requirement2 : requirements) {
            for (final Class<? extends Requirement> dirtyType : dirtyTypes) {
                if (dirtyType.isAssignableFrom(requirement2)) {
                    toRemove.add(requirement2);
                }
            }
        }
        final Map<Requirement, Boolean> map;
        toRemove.forEach(requirement -> map = this.requirementCache.remove(requirement));
        MinecraftForge.EVENT_BUS.post((Event)new CacheInvalidatedEvent(player, !toRemove.isEmpty()));
    }
    
    static {
        RequirementCache.dirtyCacheTypes = new HashSet<Class<? extends Requirement>>();
        RequirementCache.cacheMap = new HashMap<SidedUUID, RequirementCache>();
    }
    
    private static class SidedUUID
    {
        private final UUID uuid;
        private final boolean isRemote;
        
        private SidedUUID(final UUID uuid, final boolean isRemote) {
            this.uuid = uuid;
            this.isRemote = isRemote;
        }
        
        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof SidedUUID) {
                final SidedUUID other = (SidedUUID)obj;
                return this.isRemote == other.isRemote && this.uuid.equals(other.uuid);
            }
            return false;
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(this.isRemote, this.uuid);
        }
    }
}
