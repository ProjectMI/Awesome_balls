//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package baubles.common.event;

import net.minecraft.util.math.MathHelper;
import net.minecraft.entity.item.EntityItem;
import java.util.List;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import java.util.Iterator;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import baubles.common.network.PacketHandler;
import baubles.common.network.PacketSync;
import java.util.Set;
import java.util.HashSet;
import net.minecraft.world.WorldServer;
import java.util.Arrays;
import baubles.api.cap.IBaublesItemHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import baubles.api.cap.BaublesCapabilities;
import baubles.api.IBauble;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import java.util.Collection;
import java.util.Collections;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import baubles.api.cap.BaublesContainerProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.nbt.NBTTagCompound;
import baubles.common.Baubles;
import baubles.api.BaublesApi;
import baubles.api.cap.BaublesContainer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraft.item.ItemStack;
import java.util.UUID;
import java.util.HashMap;

public class EventHandlerEntity
{
    private HashMap<UUID, ItemStack[]> baublesSync;
    
    public EventHandlerEntity() {
        this.baublesSync = new HashMap<UUID, ItemStack[]>();
    }
    
    @SubscribeEvent
    public void cloneCapabilitiesEvent(final PlayerEvent.Clone event) {
        try {
            final BaublesContainer bco = (BaublesContainer)BaublesApi.getBaublesHandler(event.getOriginal());
            final NBTTagCompound nbt = bco.serializeNBT();
            final BaublesContainer bcn = (BaublesContainer)BaublesApi.getBaublesHandler(event.getEntityPlayer());
            bcn.deserializeNBT(nbt);
        }
        catch (Exception e) {
            Baubles.log.error("Could not clone player [" + event.getOriginal().getName() + "] baubles when changing dimensions");
        }
    }
    
    @SubscribeEvent
    public void attachCapabilitiesPlayer(final AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityPlayer) {
            event.addCapability(new ResourceLocation("baubles", "container"), (ICapabilityProvider)new BaublesContainerProvider(new BaublesContainer()));
        }
    }
    
    @SubscribeEvent
    public void playerJoin(final EntityJoinWorldEvent event) {
        final Entity entity = event.getEntity();
        if (entity instanceof EntityPlayerMP) {
            final EntityPlayerMP player = (EntityPlayerMP)entity;
            this.syncSlots((EntityPlayer)player, (Collection<? extends EntityPlayer>)Collections.singletonList(player));
        }
    }
    
    @SubscribeEvent
    public void onStartTracking(final PlayerEvent.StartTracking event) {
        final Entity target = event.getTarget();
        if (target instanceof EntityPlayerMP) {
            this.syncSlots((EntityPlayer)target, Collections.singletonList(event.getEntityPlayer()));
        }
    }
    
    @SubscribeEvent
    public void onPlayerLoggedOut(final net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent event) {
        this.baublesSync.remove(event.player.getUniqueID());
    }
    
    @SubscribeEvent
    public void playerTick(final TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            final EntityPlayer player = event.player;
            final IBaublesItemHandler baubles = BaublesApi.getBaublesHandler(player);
            for (int i = 0; i < baubles.getSlots(); ++i) {
                final ItemStack stack = baubles.getStackInSlot(i);
                final IBauble bauble = (IBauble)stack.getCapability((Capability)BaublesCapabilities.CAPABILITY_ITEM_BAUBLE, (EnumFacing)null);
                if (bauble != null) {
                    bauble.onWornTick(stack, (EntityLivingBase)player);
                }
            }
            if (!player.world.isRemote) {
                this.syncBaubles(player, baubles);
            }
        }
    }
    
    private void syncBaubles(final EntityPlayer player, final IBaublesItemHandler baubles) {
        ItemStack[] items = this.baublesSync.get(player.getUniqueID());
        if (items == null) {
            items = new ItemStack[baubles.getSlots()];
            Arrays.fill(items, ItemStack.EMPTY);
            this.baublesSync.put(player.getUniqueID(), items);
        }
        if (items.length != baubles.getSlots()) {
            final ItemStack[] old = items;
            items = new ItemStack[baubles.getSlots()];
            System.arraycopy(old, 0, items, 0, Math.min(old.length, items.length));
            this.baublesSync.put(player.getUniqueID(), items);
        }
        Set<EntityPlayer> receivers = null;
        for (int i = 0; i < baubles.getSlots(); ++i) {
            final ItemStack stack = baubles.getStackInSlot(i);
            final IBauble bauble = (IBauble)stack.getCapability((Capability)BaublesCapabilities.CAPABILITY_ITEM_BAUBLE, (EnumFacing)null);
            if (baubles.isChanged(i) || (bauble != null && bauble.willAutoSync(stack, (EntityLivingBase)player) && !ItemStack.areItemStacksEqual(stack, items[i]))) {
                if (receivers == null) {
                    receivers = new HashSet<EntityPlayer>(((WorldServer)player.world).getEntityTracker().getTrackingPlayers((Entity)player));
                    receivers.add(player);
                }
                this.syncSlot(player, i, stack, receivers);
                baubles.setChanged(i, false);
                items[i] = ((stack == null) ? ItemStack.EMPTY : stack.copy());
            }
        }
    }
    
    private void syncSlots(final EntityPlayer player, final Collection<? extends EntityPlayer> receivers) {
        final IBaublesItemHandler baubles = BaublesApi.getBaublesHandler(player);
        for (int i = 0; i < baubles.getSlots(); ++i) {
            this.syncSlot(player, i, baubles.getStackInSlot(i), receivers);
        }
    }
    
    private void syncSlot(final EntityPlayer player, final int slot, final ItemStack stack, final Collection<? extends EntityPlayer> receivers) {
        final PacketSync pkt = new PacketSync(player, slot, stack);
        for (final EntityPlayer receiver : receivers) {
            PacketHandler.INSTANCE.sendTo((IMessage)pkt, (EntityPlayerMP)receiver);
        }
    }
    
    @SubscribeEvent
    public void playerDeath(final PlayerDropsEvent event) {
        if (event.getEntity() instanceof EntityPlayer && !event.getEntity().world.isRemote && !event.getEntity().world.getGameRules().getBoolean("keepInventory")) {
            this.dropItemsAt(event.getEntityPlayer(), event.getDrops(), (Entity)event.getEntityPlayer());
        }
    }
    
    public void dropItemsAt(final EntityPlayer player, final List<EntityItem> drops, final Entity e) {
        final IBaublesItemHandler baubles = BaublesApi.getBaublesHandler(player);
        for (int i = 0; i < baubles.getSlots(); ++i) {
            if (baubles.getStackInSlot(i) != null && !baubles.getStackInSlot(i).isEmpty()) {
                final EntityItem ei = new EntityItem(e.world, e.posX, e.posY + e.getEyeHeight(), e.posZ, baubles.getStackInSlot(i).copy());
                ei.setPickupDelay(40);
                final float f1 = e.world.rand.nextFloat() * 0.5f;
                final float f2 = e.world.rand.nextFloat() * 3.1415927f * 2.0f;
                ei.motionX = -MathHelper.sin(f2) * f1;
                ei.motionZ = MathHelper.cos(f2) * f1;
                ei.motionY = 0.20000000298023224;
                drops.add(ei);
                baubles.setStackInSlot(i, ItemStack.EMPTY);
            }
        }
    }
}
