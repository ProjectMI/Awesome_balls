// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.api.requirement.logic;

import javax.annotation.Nonnull;
import codersafterdark.reskillable.api.requirement.Requirement;
import java.util.List;

public interface OuterRequirement
{
    @Nonnull
    List<Class<? extends Requirement>> getInternalTypes();
}
