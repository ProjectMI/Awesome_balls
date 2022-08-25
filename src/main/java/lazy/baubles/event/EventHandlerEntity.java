package lazy.baubles.event;

import net.minecraft.nbt.CompoundTag;
import lazy.baubles.capability.BaublesContainer;
import net.minecraft.world.phys.Vec3;
import java.util.Iterator;
import net.minecraftforge.network.PacketDistributor;
import lazy.baubles.network.PacketHandler;
import lazy.baubles.network.msg.SyncPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import lazy.baubles.api.BaublesAPI;
import lazy.baubles.api.bauble.IBauble;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import lazy.baubles.api.bauble.IBaublesItemHandler;
import net.minecraftforge.event.TickEvent;
import java.util.Collection;
import java.util.Collections;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import lazy.baubles.capability.BaublesContainerProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.common.capabilities.Capability;
import lazy.baubles.api.cap.CapabilityBaubles;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventHandlerEntity
{
    @SubscribeEvent
    public static void cloneCapabilitiesEvent(final PlayerEvent.Clone event) {
        try {
            event.getOriginal().getCapability((Capability)CapabilityBaubles.BAUBLES).ifPresent(bco -> {
                final CompoundTag nbt = ((BaublesContainer)bco).serializeNBT();
                event.getOriginal().getCapability((Capability)CapabilityBaubles.BAUBLES).ifPresent(bcn -> ((BaublesContainer)bcn).deserializeNBT(nbt));
            });
        }
        catch (Exception e) {
            System.out.println(invokedynamic(makeConcatWithConstants:(Lnet/minecraft/network/chat/Component;)Ljava/lang/String;, event.getOriginal().m_7755_()));
        }
    }
    
    @SubscribeEvent
    public static void attachCapabilitiesPlayer(final AttachCapabilitiesEvent<Entity> event) {
        final Object object = event.getObject();
        if (object instanceof Player) {
            final Player player = (Player)object;
            event.addCapability(new ResourceLocation("baubles", "container"), (ICapabilityProvider)new BaublesContainerProvider(player));
        }
    }
    
    @SubscribeEvent
    public static void playerJoin(final EntityJoinWorldEvent event) {
        final Entity entity = event.getEntity();
        if (entity instanceof ServerPlayer) {
            final ServerPlayer serverPlayer = (ServerPlayer)entity;
            syncSlots((Player)serverPlayer, (Collection<? extends Player>)Collections.singletonList(serverPlayer));
        }
    }
    
    @SubscribeEvent
    public static void onStartTracking(final PlayerEvent.StartTracking event) {
        final Entity target = event.getTarget();
        if (target instanceof ServerPlayer) {
            final ServerPlayer serverPlayer = (ServerPlayer)target;
            syncSlots((Player)serverPlayer, Collections.singletonList(event.getPlayer()));
        }
    }
    
    @SubscribeEvent
    public static void playerTick(final TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            final Player player = event.player;
            player.getCapability((Capability)CapabilityBaubles.BAUBLES).ifPresent(IBaublesItemHandler::tick);
        }
    }
    
    @SubscribeEvent
    public static void rightClickItem(final PlayerInteractEvent.RightClickItem event) {
        final ItemStack itemStack = event.getPlayer().m_21120_(event.getHand());
        final Item 41720_ = itemStack.m_41720_();
        if (41720_ instanceof IBauble) {
            final IBauble bauble = (IBauble)41720_;
            final IBaublesItemHandler itemHandler = (IBaublesItemHandler)BaublesAPI.getBaublesHandler(event.getPlayer()).orElseThrow(NullPointerException::new);
            final int emptySlot = BaublesAPI.getEmptySlotForBaubleType(event.getPlayer(), bauble.getBaubleType(itemStack));
            if (emptySlot != -1) {
                itemHandler.setStackInSlot(emptySlot, itemStack.m_41777_());
                itemStack.m_41764_(0);
            }
        }
        else if (itemStack.getCapability((Capability)CapabilityBaubles.ITEM_BAUBLE).isPresent()) {
            final IBauble bauble2 = (IBauble)itemStack.getCapability((Capability)CapabilityBaubles.ITEM_BAUBLE).orElseThrow(NullPointerException::new);
            final IBaublesItemHandler itemHandler2 = (IBaublesItemHandler)BaublesAPI.getBaublesHandler(event.getPlayer()).orElseThrow(NullPointerException::new);
            final int emptySlot2 = BaublesAPI.getEmptySlotForBaubleType(event.getPlayer(), bauble2.getBaubleType(itemStack));
            if (emptySlot2 != -1) {
                itemHandler2.setStackInSlot(emptySlot2, itemStack.m_41777_());
                itemStack.m_41764_(0);
            }
        }
    }
    
    @SubscribeEvent
    public static void playerDeath(final LivingDropsEvent event) {
        final Level level = event.getEntity().f_19853_;
        final Entity entity = event.getEntity();
        if (entity instanceof Player) {
            final Player p = (Player)entity;
            if (!level.f_46443_ && !level.m_46469_().m_46207_(GameRules.f_46133_)) {
                dropItemsAt(p, event.getDrops());
            }
        }
    }
    
    private static void syncSlots(final Player player, final Collection<? extends Player> receivers) {
        player.getCapability((Capability)CapabilityBaubles.BAUBLES).ifPresent(baubles -> {
            for (byte i = 0; i < baubles.getSlots(); ++i) {
                syncSlot(player, i, baubles.getStackInSlot((int)i), receivers);
            }
        });
    }
    
    public static void syncSlot(final Player player, final byte slot, final ItemStack stack, final Collection<? extends Player> receivers) {
        final SyncPacket pkt = new SyncPacket(player.m_142049_(), slot, stack);
        for (final Player receiver : receivers) {
            if (receiver instanceof ServerPlayer) {
                final ServerPlayer s = (ServerPlayer)receiver;
                PacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> s), (Object)pkt);
            }
        }
    }
    
    private static void dropItemsAt(final Player player, final Collection<ItemEntity> drops) {
        player.getCapability((Capability)CapabilityBaubles.BAUBLES).ifPresent(baubles -> {
            for (int i = 0; i < baubles.getSlots(); ++i) {
                if (!baubles.getStackInSlot(i).m_41619_()) {
                    final Vec3 pos = player.m_20182_();
                    final ItemEntity bauble = new ItemEntity(player.f_19853_, pos.f_82479_, pos.f_82480_ + player.m_20192_(), pos.f_82481_, baubles.getStackInSlot(i).m_41777_());
                    bauble.m_32010_(40);
                    drops.add(bauble);
                    baubles.setStackInSlot(i, ItemStack.f_41583_);
                }
            }
        });
    }
}
