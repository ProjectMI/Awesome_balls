package com.example.test;


public class CAPCustomInventory implements ICAPCustomInventory {
	
	//Реализуем методы, что определены в интерфейсе ICAPCustomInventory

	//Создаем обьект нашего инвентаря. Он будет храниться в этой КАП'е
	public final CustomInventory inventory = new CustomInventory();
		
	/**
	 * Метод, который возвращает обьект инвентаря inventory
	 */
	public CustomInventory getInventory(){
		
		return this.inventory;
	}
	
	/**
	 * Метод, для копировании информации из другого инвентаря, например при клонировании
	 */
	@Override
	public void copyInventory(ICAPCustomInventory inventory) {
		
		this.inventory.copy(inventory.getInventory());
	}

}
