// 
// Decompiled by Procyon v0.5.36
// 

package baubles.client.gui;

import java.util.Collection;
import net.minecraftforge.common.config.ConfigElement;
import java.util.ArrayList;
import net.minecraftforge.fml.client.config.IConfigElement;
import java.util.List;
import baubles.common.Config;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.config.GuiConfig;

public class BaublesGuiConfig extends GuiConfig
{
    public BaublesGuiConfig(final GuiScreen parent) {
        super(parent, (List)getConfigElements(), "baubles", false, false, GuiConfig.getAbridgedConfigPath(Config.config.toString()));
    }
    
    private static List<IConfigElement> getConfigElements() {
        final List<IConfigElement> list = new ArrayList<IConfigElement>();
        list.addAll(new ConfigElement(Config.config.getCategory("general")).getChildElements());
        list.addAll(new ConfigElement(Config.config.getCategory("client")).getChildElements());
        return list;
    }
}
