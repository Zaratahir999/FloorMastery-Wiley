package com.floor.persistence;

import java.util.LinkedList;
import java.util.List;

import com.floor.dto.Order;
import com.floor.dto.Product;
import com.floor.dto.Tax;

public interface FloorDataAccess {

	public boolean writeOrderFiles(LinkedList<Order> orders);
	LinkedList<Order> readOrderFile(); //might need to remove to make this code static
	
	public boolean writeProductFiles(LinkedList<Product> products);
	LinkedList<Product> readProductFile(); //might need to remove to make this code static
	
	public boolean writeTaxFiles(LinkedList<Tax> taxes);
	LinkedList<Tax> readTaxFile(); //might need to remove to make this code static
	public void writeOrderFiles(List<Order> testOrders, String filePath);
	
	
	
	
}
