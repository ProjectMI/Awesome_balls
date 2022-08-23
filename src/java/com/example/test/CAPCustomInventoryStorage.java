package com.example.test;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class CAPCustomInventoryStorage implements IStorage<ICAPCustomInventory> {

	//���������� ���������� (�.�. ���������, ��� ������ � ���������) � ������� writeToNBT
	
	@Override
	public NBTBase writeNBT(Capability<ICAPCustomInventory> capability, ICAPCustomInventory instance, EnumFacing side) {
		
		NBTTagCompound properties = new NBTTagCompound();
		//�������� ����� writeToNBT �� ��������� � ���������� ���� � ��������� � ��� ������� � ���
		instance.getInventory().writeToNBT(properties);
		return properties;
	}

	//������ ���������� (�.�. ���������, ��� ������ � ���������) � ���������� �� � ��������� � ������� readFromNBT
	@Override
	public void readNBT(Capability<ICAPCustomInventory> capability, ICAPCustomInventory instance, EnumFacing side, NBTBase nbt) {
		
		NBTTagCompound properties = (NBTTagCompound)nbt;
		//�������� ����� readFromNBT �� ��������� � ������ � ����(���.) ����� ������� ��������� � ����
		instance.getInventory().readFromNBT(properties);
	}

}
