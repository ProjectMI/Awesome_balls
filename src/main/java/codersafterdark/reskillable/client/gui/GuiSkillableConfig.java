// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.client.gui;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.DummyConfigElement;
import net.minecraftforge.fml.client.config.IConfigElement;
import java.util.List;
import codersafterdark.reskillable.base.ConfigHandler;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.config.GuiConfig;

public class GuiSkillableConfig extends GuiConfig
{
    public GuiSkillableConfig(final GuiScreen parentScreen) {
        super(parentScreen, (List)getAllElements(), "reskillable", false, false, GuiConfig.getAbridgedConfigPath(ConfigHandler.config.toString()));
    }
    
    public static List<IConfigElement> getAllElements() {
        final DummyConfigElement.DummyCategoryElement dummyCategoryElement;
        return (List<IConfigElement>)ConfigHandler.config.getCategoryNames().stream().filter(s -> !s.contains(".")).map(s -> {
            new DummyConfigElement.DummyCategoryElement(s, s, new ConfigElement(ConfigHandler.config.getCategory(s)).getChildElements());
            return dummyCategoryElement;
        }).collect(Collectors.toList());
    }
}
