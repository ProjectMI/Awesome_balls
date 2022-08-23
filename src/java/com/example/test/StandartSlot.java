package com.example.test;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class StandartSlot extends Slot {
	
	//��� �� ���� ������ �������� ���������� �����
	
    private final EntityPlayer thePlayer;
    private int removeCount;
    
    public StandartSlot(EntityPlayer player, IInventory inventoryIn, int slotIndex, int xPosition, int yPosition){
    	
        super(inventoryIn, slotIndex, xPosition, yPosition);
        this.thePlayer = player;
    }

    /*	����� �� ����� ���� ���� ������� � ���� ����. ����� ����� ���� ��������, �������� ���� �� ������
    	���� � ���� ������ ���� �������� ������, ���������� ����� �� ������� � ����� ������, ���� ��, ��
    	���������� false
    
    */
    public boolean isItemValid(@Nullable ItemStack stack){
    	
    	//���� ������ ����� 0 � �������� ���� - ���� � ��������, �� �� ��������� ������ � ����.
    	if(this.getSlotIndex() == 0 && stack.getItem().equals(Items.APPLE)) return false;
        return true;
    }

    public ItemStack decrStackSize(int amount){
    	
        if (this.getHasStack()){
            this.removeCount += Math.min(amount, this.getStack().getCount());
        }

        return super.decrStackSize(amount);
    }

    //��� ����������, ���� ������� ������� �� �����
    public ItemStack onTake(EntityPlayer player, ItemStack stack){
    	
        this.onCrafting(stack);
        super.onTake(player, stack);
        return stack;
    }

    protected void onCrafting(ItemStack stack, int amount){
    	
        this.removeCount += amount;
        this.onCrafting(stack);
    }

    protected void onCrafting(ItemStack stack){
    	
        stack.onCrafting(this.thePlayer.world, this.thePlayer, this.removeCount);
        this.removeCount = 0;    
    }
}
