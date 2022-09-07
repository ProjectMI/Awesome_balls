// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.base.asm;

import java.util.Iterator;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.ClassReader;
import net.minecraft.launchwrapper.IClassTransformer;

public class ClassTransformer implements IClassTransformer
{
    public byte[] transform(final String name, final String transformedName, final byte[] basicClass) {
        if (transformedName.equals("net.minecraft.client.renderer.InventoryEffectRenderer")) {
            final ClassReader reader = new ClassReader(basicClass);
            final ClassNode node = new ClassNode();
            reader.accept((ClassVisitor)node, 0);
            final String funcName = "drawActivePotionEffects";
            final String obfName = "f";
            final String desc = "()V";
            for (final MethodNode method : node.methods) {
                if ((method.name.equals(funcName) || method.name.equals(obfName)) && method.desc.equals(desc)) {
                    for (final AbstractInsnNode itrNode : method.instructions) {
                        if (itrNode.getOpcode() == 16) {
                            final IntInsnNode intNode = (IntInsnNode)itrNode;
                            if (intNode.operand == 124) {
                                final MethodInsnNode newNode = new MethodInsnNode(184, "codersafterdark/reskillable/client/gui/handler/InventoryTabHandler", "getPotionOffset", "()I", false);
                                method.instructions.insert((AbstractInsnNode)intNode, (AbstractInsnNode)newNode);
                                method.instructions.remove((AbstractInsnNode)intNode);
                                break;
                            }
                            continue;
                        }
                    }
                    final ClassWriter writer = new ClassWriter(3);
                    node.accept((ClassVisitor)writer);
                    return writer.toByteArray();
                }
            }
        }
        return basicClass;
    }
}
