package com.floor.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import com.floor.dto.Order;

public interface FloorBusinessLogic {

	LinkedList<Order> getAllOrdersByDate(LocalDate date);
	boolean addOrder (Order order);
	boolean editOrder (Order order);
	void removeOrder (LocalDate date, int orderNumber);
	void exportAllData();
	List<String> getProductTypes();
	BigDecimal getTaxRate(String stateAbbreviations);
	
	
}
