package com.example.test;

public class ClientProxy extends ServerProxy {

	@Override
	public void preInit() {
		
	    super.preInit();
	}

	@Override
	public void init() {
		
		KeyHandler.register();
		super.init();
	}

	@Override
	public void postInit() {
	   
		super.postInit();
	}
}
