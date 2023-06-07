package com.floor.persistence;

import java.util.LinkedList;
import java.util.List;

import com.floor.dto.Order;
import com.floor.dto.Product;
import com.floor.dto.Tax;

public class FloorDataAccessImpl implements FloorDataAccess {

	@Override
	public boolean writeOrderFiles(LinkedList<Order> orders) {
//		FileWriter fileWriter = new FileWriter ("")
		return false;
	}

	@Override
	public LinkedList<Order> readOrderFile() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean writeProductFiles(LinkedList<Product> products) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public LinkedList<Product> readProductFile() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean writeTaxFiles(LinkedList<Tax> taxes) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public LinkedList<Tax> readTaxFile() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void writeOrderFiles(List<Order> testOrders, String filePath) {
		// TODO Auto-generated method stub
		
	}

}
