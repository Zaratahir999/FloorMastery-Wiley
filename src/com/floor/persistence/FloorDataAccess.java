package com.floor.persistence;


import java.util.LinkedList;
import java.util.List;

import com.floor.dto.Order;
import com.floor.dto.Product;
import com.floor.dto.Tax;

public interface FloorDataAccess {

	public boolean writeOrderFiles(LinkedList<Order> orders);
	
	public boolean writeProductFiles(LinkedList<Product> products);
	
	public boolean writeTaxFiles(LinkedList<Tax> taxes);
	public LinkedList<Order> readOrderFile(String orderFile);
	LinkedList<Product> readProductFile();
    LinkedList<Tax> readTaxFile();
	
    //public LinkedList<Order> readOrderFiles(List<String> filenames);
    public boolean exportAllOrders(List<Order> orders);
    public int getMaxOrderNumber();
    
    List<String> getAllOrderFiles();
}