// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.base.asm;

import java.util.Map;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

public class LoadingPlugin implements IFMLLoadingPlugin
{
    public static boolean runtimeDeobfEnabled;
    
    public String[] getASMTransformerClass() {
        return new String[] { "codersafterdark.reskillable.base.asm.ClassTransformer" };
    }
    
    public String getModContainerClass() {
        return null;
    }
    
    public String getSetupClass() {
        return null;
    }
    
    public void injectData(final Map<String, Object> data) {
        LoadingPlugin.runtimeDeobfEnabled = data.get("runtimeDeobfuscationEnabled");
    }
    
    public String getAccessTransformerClass() {
        return null;
    }
}
