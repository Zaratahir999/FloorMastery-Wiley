package com.floor.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.nio.file.*;
import java.time.format.DateTimeParseException;
import java.io.*;
import java.util.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.util.stream.Collectors;

import com.floor.dto.Order;
import com.floor.dto.Product;
import com.floor.dto.Tax;
import com.floor.persistence.FloorDataAccess;
import com.floor.persistence.FloorDataAccessImpl;
import java.util.ArrayList;

public class FloorBusinessLogicImpl implements FloorBusinessLogic {
    private LinkedList<Order> ordersList = new LinkedList<>();
  

	
    private FloorDataAccess dataAccess;

    public FloorBusinessLogicImpl() {
        this.dataAccess = new FloorDataAccessImpl();
    }
    
    
    
   //getting the files from data access layer, get unique number and pass to UI 
    @Override
    public boolean addOrder(Order order) {
    	//load the files and add the file to that list
    	List<String> orderFiles = dataAccess.getAllOrderFiles();
        // Generate order number based on the next available order #
    	int nextOrderNumber = dataAccess.getMaxOrderNumber() +1;
        order.setOrderNumber(nextOrderNumber);

        // Perform calculations
        calculateOrder(order);

        return ordersList.add(order);
        }
    
    
    @Override
    public void saveOrder() {

        dataAccess.writeOrderFiles(ordersList);
    }
  
    


    @Override
    public List<String> getAllOrderFiles() {
        return dataAccess.getAllOrderFiles();
    }

    
    
    @Override
    public boolean editOrder(LocalDate orderDate, int orderNumber, Order newOrder) {
        // Get all order files
        List<String> orderFiles = dataAccess.getAllOrderFiles();

        // Find the order file for the given date
        String orderFile = null;
        String targetFile = "Orders_" + orderDate.format(DateTimeFormatter.ofPattern("MMddyyyy")) + ".txt";
        for (String file : orderFiles) {
            if (file.equals(targetFile)) {
                orderFile = file;
                break;
            }
        }

        if (orderFile == null) {
            return false;
        }

        // Load the orders from the file
        LinkedList<Order> orders = dataAccess.readOrderFile(orderFile);

        // Find the order in the list
        for (Order order : orders) {
            if (order.getOrderNumber() == orderNumber) {
                // Update the order details
                order.setDate(newOrder.getDate());
                order.setCustomerName(newOrder.getCustomerName());
                order.setState(newOrder.getState());
                order.setProductType(newOrder.getProductType());
                order.setArea(newOrder.getArea());
                order.setTaxRate(newOrder.getTaxRate());

                // Perform calculations
                calculateOrder(order);

                // Save the orders back to the file
                dataAccess.writeOrderFiles(orders);

                return true;
            }
        }

        return false;
    }


    @Override
    public boolean removeOrder(LocalDate orderDate, int orderNumber) {
        // Get all order files
        List<String> orderFiles = dataAccess.getAllOrderFiles();

        // Find the order file for the given date
        String orderFile = null;
        String targetFile = "Orders_" + orderDate.format(DateTimeFormatter.ofPattern("MMddyyyy")) + ".txt";
        for (String file : orderFiles) {
            if (file.equals(targetFile)) {
                orderFile = file;
                break;
            }
        }

        if (orderFile == null) {
            return false;
        }

        // Load the orders from the file
        LinkedList<Order> orders = dataAccess.readOrderFile(orderFile);

        // Find the order in the list
        Order orderToRemove = null;
        for (Order order : orders) {
            if (order.getOrderNumber() == orderNumber) {
                orderToRemove = order;
                break;
            }
        }

        if (orderToRemove == null) {
            return false;
        }

        // Remove the order from the list
        orders.remove(orderToRemove);

        // Save the orders back to the file
        dataAccess.writeOrderFiles(orders);

        return true;
    }

    
    
    
    
    
    @Override
    public void calculateOrder(Order order) {
        // Fetch product and tax data
        LinkedList<Product> products = getAllProducts();
        LinkedList<Tax> taxes = getAllTaxes();

        // Calculate material cost
        Product product = getProductByType(products, order.getProductType());
        if (product != null) {
            BigDecimal costPerSquareFoot = product.getCostPerSquareFoot();
            BigDecimal labourCostPerSquareFoot = product.getLabourCostPerSquareFoot();
            order.setCostPerSquareFoot(costPerSquareFoot);
            order.setLabourCostPerSquareFoot(labourCostPerSquareFoot);
            
            
            
            BigDecimal area = order.getArea();
            BigDecimal materialCost = costPerSquareFoot.multiply(area);
            BigDecimal labourCost = labourCostPerSquareFoot.multiply(area);
            order.setMaterialCost(materialCost);
            order.setLabourCost(labourCost);
        } else {
        	 order.setCostPerSquareFoot(BigDecimal.ZERO);
             order.setLabourCostPerSquareFoot(BigDecimal.ZERO);
             order.setMaterialCost(BigDecimal.ZERO);
             order.setLabourCost(BigDecimal.ZERO);
        }
     // Calculate tax
        String state = order.getState();
        Tax tax = getTaxByState(taxes, state);
        if (tax != null) {
            BigDecimal taxRate = tax.getTaxRate();
            BigDecimal subtotal = order.getMaterialCost().add(order.getLabourCost());
            BigDecimal taxAmount = subtotal.multiply(taxRate.divide(BigDecimal.valueOf(100)));
            order.setTax(taxAmount);
        } else {
            order.setTax(BigDecimal.ZERO); // Set to zero if tax not found
        }

        // Calculate total
        BigDecimal total = order.getMaterialCost().add(order.getLabourCost()).add(order.getTax());
        order.setTotal(total);
    }


    @Override
    public LinkedList<Product> getAllProducts() {
        return dataAccess.readProductFile();
    }

    @Override
    public LinkedList<Tax> getAllTaxes() {
        return dataAccess.readTaxFile();
    }

    @Override
    public List<String> getProductTypes() {
        LinkedList<Product> products = getAllProducts();
        return products.stream().map(Product::getProductType).collect(Collectors.toList());
    }

    @Override
    public BigDecimal getTaxRate(String stateAbbreviation) {
        LinkedList<Tax> taxes = getAllTaxes();
        Tax tax = getTaxByStateAbbreviation(taxes, stateAbbreviation);
        if (tax != null) {
            return tax.getTaxRate();
        }
        // Default return, might not be accurate
        return BigDecimal.ZERO;
    }

    
    @Override
    public void exportAllData() {
        List<String> orderFiles = dataAccess.getAllOrderFiles();
        List<Order> allOrders = new ArrayList<>();

        for (String orderFile : orderFiles) {
            List<Order> orders = dataAccess.readOrderFile(orderFile);
            allOrders.addAll(orders);
        }

        dataAccess.exportAllOrders(allOrders);
    }



    private Product getProductByType(LinkedList<Product> products, String productType) {
        for (Product product : products) {
            if (product.getProductType().equalsIgnoreCase(productType)) {
                return product;
            }
        }
        return null;
    }

    private Tax getTaxByState(LinkedList<Tax> taxes, String state) {
        for (Tax tax : taxes) {
            if (tax.getStateName().equalsIgnoreCase(state)) {
                return tax;
            }
        }
        return null;
    }

    private Tax getTaxByStateAbbreviation(LinkedList<Tax> taxes, String stateAbbreviation) {
        for (Tax tax : taxes) {
            if (tax.getStateAbbreviation().equalsIgnoreCase(stateAbbreviation)) {
                return tax;
            }
        }
        return null;
    }
    
    @Override
    public LinkedList<Order> getAllOrdersByDate(LocalDate date) {
        LinkedList<Order> ordersByDate = new LinkedList<>();
        for (Order order : ordersList) {
            if (order.getDate().equals(date)) {
                ordersByDate.add(order);
            }
        }
        return ordersByDate;
    }



	
	@Override
	public LinkedList<Order> getOrdersByDate(LocalDate orderDate) {
	    // Get all order files
	    List<String> orderFiles = dataAccess.getAllOrderFiles();

	    // Find the order file for the given date
	    String orderFile = null;
	    String targetFile = "Orders_" + orderDate.format(DateTimeFormatter.ofPattern("MMddyyyy")) + ".txt";
	    for (String file : orderFiles) {
	        if (file.equals(targetFile)) {
	            orderFile = file;
	            break;
	        }
	    }

	    if (orderFile == null) {
	        return new LinkedList<>();
	    }

	    // Load the orders from the file
	    return dataAccess.readOrderFile(orderFile);
	}


	
}

