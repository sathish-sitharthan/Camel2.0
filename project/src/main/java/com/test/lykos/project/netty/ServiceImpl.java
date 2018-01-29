package com.test.lykos.project.netty;

import com.lykos.model.Product;

public class ServiceImpl implements Service {

	public void nettyMethod(Product value) {
	System.out.println("Hey your netty connect is working : Product Id::"+ value.getProductId()+"-------- Product Name::"+value.getProductName());

	}



}
