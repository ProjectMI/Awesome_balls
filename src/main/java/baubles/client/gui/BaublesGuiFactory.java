// 
// Decompiled by Procyon v0.5.36
// 

package baubles.client.gui;

import java.util.Collection;
import net.minecraftforge.common.config.ConfigElement;
import java.util.ArrayList;
import net.minecraftforge.fml.client.config.IConfigElement;
import java.util.List;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.config.GuiConfig;
import baubles.common.Config;
import net.minecraftforge.fml.client.DefaultGuiFactory;

public class BaublesGuiFactory extends DefaultGuiFactory
{
    public BaublesGuiFactory() {
        super("baubles", GuiConfig.getAbridgedConfigPath(Config.config.toString()));
    }
    
    public GuiScreen createConfigGui(final GuiScreen parent) {
        return (GuiScreen)new GuiConfig(parent, (List)getConfigElements(), this.modid, false, false, this.title);
    }
    
    private static List<IConfigElement> getConfigElements() {
        final List<IConfigElement> list = new ArrayList<IConfigElement>();
        list.addAll(new ConfigElement(Config.config.getCategory("general")).getChildElements());
        list.addAll(new ConfigElement(Config.config.getCategory("client")).getChildElements());
        return list;
    }
}
