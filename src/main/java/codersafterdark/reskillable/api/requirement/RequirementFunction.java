// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.api.requirement;

@FunctionalInterface
public interface RequirementFunction<T, R>
{
    R apply(final T p0) throws RequirementException;
}
