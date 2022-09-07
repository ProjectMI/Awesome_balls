//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.network;

import codersafterdark.reskillable.api.toast.ToastHelper;
import codersafterdark.reskillable.api.ReskillableRegistries;
import codersafterdark.reskillable.api.skill.Skill;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class SkillToastPacket implements IMessage, IMessageHandler<SkillToastPacket, IMessage>
{
    private ResourceLocation skillName;
    private int level;
    
    public SkillToastPacket() {
    }
    
    public SkillToastPacket(final ResourceLocation skillName, final int level) {
        this.skillName = skillName;
        this.level = level;
    }
    
    public void fromBytes(final ByteBuf buf) {
        this.skillName = new ResourceLocation(ByteBufUtils.readUTF8String(buf));
        this.level = buf.readInt();
    }
    
    public void toBytes(final ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.skillName.toString());
        buf.writeInt(this.level);
    }
    
    public IMessage onMessage(final SkillToastPacket message, final MessageContext ctx) {
        Minecraft.getMinecraft().addScheduledTask(() -> this.handleMessage(message, ctx));
        return null;
    }
    
    public IMessage handleMessage(final SkillToastPacket message, final MessageContext ctx) {
        ToastHelper.sendSkillToast((Skill)ReskillableRegistries.SKILLS.getValue(message.skillName), message.level);
        return null;
    }
}
