package com.floor.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import com.floor.dto.Order;

public class FloorBusinessLogicImpl implements FloorBusinessLogic {

	@Override
	public LinkedList<Order> getAllOrdersByDate(LocalDate date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addOrder(Order order) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean editOrder(Order order) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeOrder(LocalDate date, int orderNumber) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exportAllData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> getProductTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getTaxRate(String stateAbbreviations) {
		// TODO Auto-generated method stub
		return null;
	}

}
