package ru.zonasumraka.maturinextensions.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import net.minecraft.launchwrapper.IClassTransformer;

public class MaturinClassTransformer implements IClassTransformer {

	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass) {
		if(!transformedName.equals("net.minecraft.entity.player.EntityPlayerMP")) return basicClass;
		boolean isObfuscated = !name.equals(transformedName);
		System.out.println("MaturinExtensions: transforming " + transformedName);
		final String ADD_STAT = isObfuscated ? "a" : "addStat";
		final String ADD_STAT_DESC = isObfuscated ? "(Lqo;I)V" : "(Lnet/minecraft/stats/StatBase;I)V";
		ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(basicClass);
        classReader.accept(classNode, 0);
        for(MethodNode method : classNode.methods) {
        	if(method.name.equals(ADD_STAT) && method.desc.equals(ADD_STAT_DESC)) {
        		System.out.println("MaturinExtensions: found addStat, injecting...");
        		final MethodNode injectedMethod = new MethodNode();
        		final Label label = new Label();
        		injectedMethod.visitLabel(label);
        		injectedMethod.visitLineNumber(666, label);
        		injectedMethod.visitVarInsn(Opcodes.ALOAD, 0);
        		injectedMethod.visitVarInsn(Opcodes.ALOAD, 1);
        		injectedMethod.visitVarInsn(Opcodes.ILOAD, 2);
	       		final String DESC = isObfuscated ? "(Laed;Lqo;I)V" : "(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/stats/StatBase;I)V";
        		injectedMethod.visitMethodInsn(Opcodes.INVOKESTATIC, "ru/zonasumraka/maturinextensions/asm/MaturinASMHooks", "emitStatEvent", DESC, false);
        		method.instructions.insertBefore(method.instructions.get(0), injectedMethod.instructions);
        	}
        }
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
	}

}
