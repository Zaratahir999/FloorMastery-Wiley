package com.floor.persistence;
import java.util.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;

import java.time.LocalDate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;

import java.time.LocalDate;

import com.floor.dto.Order;
import com.floor.dto.Product;
import com.floor.dto.Tax;
import java.util.*;
public class FloorDataAccessImpl implements FloorDataAccess {

    private static final String ORDER_FOLDER_PATH = "Orders/";
    private static final String ORDER_FILE_PATH = "Orders/";


   
    @Override
    public LinkedList<Order> readOrderFile(String orderFile) {
        LinkedList<Order> orders = new LinkedList<>();

        File file = new File(ORDER_FOLDER_PATH + orderFile);

        if (file.isFile()) {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
                bufferedReader.readLine(); // Skip header line

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    String[] values = line.split(",");
                    if (values.length == 12) {
                        int orderNumber = Integer.parseInt(values[0]);
                        String customerName = values[1];
                        String state = values[2];
                        String productType = values[4]; // Added this line
                        BigDecimal taxRate = null;
                        BigDecimal area = null;
                        BigDecimal costPerSquareFoot = null;
                        BigDecimal laborCostPerSquareFoot = null;
                        BigDecimal materialCost = null;
                        BigDecimal laborCost = null;
                        BigDecimal tax = null;
                        BigDecimal total = null;
                        try {
                            taxRate = new BigDecimal(values[3]);
                            area = new BigDecimal(values[5]);
                            costPerSquareFoot = new BigDecimal(values[6]);
                            laborCostPerSquareFoot = new BigDecimal(values[7]);
                            materialCost = new BigDecimal(values[8]);
                            laborCost = new BigDecimal(values[9]);
                            tax = new BigDecimal(values[10]);
                            total = new BigDecimal(values[11]);
                        } catch (NumberFormatException e) {
                            System.err.println("Invalid number format in order: " + line);
                            continue;
                        }

                        Order order = new Order(orderNumber, customerName, state, taxRate, productType, area,
                                costPerSquareFoot, laborCostPerSquareFoot, materialCost, laborCost, tax, total);
                        orders.add(order);
                    } else {
                        System.err.println("Invalid order format: " + line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return orders;
    }


    @Override
    public boolean writeOrderFiles(LinkedList<Order> orders) {
        try {
            for (Order order : orders) {
                String fileName = generateOrderFileName(order.getDate());
                FileWriter fileWriter = new FileWriter(fileName, true);
                PrintWriter printWriter = new PrintWriter(fileWriter);

                printWriter.println(order.getOrderNumber() + ","
                        + order.getCustomerName() + ","
                        + order.getState() + ","
                        + order.getTaxRate() + ","
                        + order.getProductType() + ","
                        + order.getArea() + ","
                        + order.getCostPerSquareFoot() + ","
                        + order.getLabourCostPerSquareFoot() + ","
                        + order.getMaterialCost() + ","
                        + order.getLabourCost() + ","
                        + order.getTax() + ","
                        + order.getTotal());

                printWriter.flush();
                printWriter.close();
                fileWriter.close();
            }

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    
    
    

    private String generateOrderFileName(LocalDate date) {
        String formattedDate = date.format(DateTimeFormatter.ofPattern("MMddyyyy"));
        return ORDER_FILE_PATH + "Orders_" + formattedDate + ".txt";
    }



    @Override
    public boolean writeProductFiles(LinkedList<Product> products) {
        try {
            FileWriter fileWriter = new FileWriter("Products.txt");
            PrintWriter printWriter = new PrintWriter(fileWriter);

            for (Product product : products) {
                String productString = product.getProductType() + "," + product.getCostPerSquareFoot() + ","
                        + product.getLabourCostPerSquareFoot();
                printWriter.println(productString);
            }
            printWriter.flush();
            printWriter.close();
            fileWriter.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
	public LinkedList<Product> readProductFile() {
	    LinkedList<Product> products = new LinkedList<>();

	    try {
	        FileReader fileReader = new FileReader("Products.txt");
	        BufferedReader bufferedReader = new BufferedReader(fileReader);
	        Scanner scanner = new Scanner(bufferedReader);
	        
	        if (scanner.hasNextLine()) {
	            scanner.nextLine();
	        }

	        int lineNumber = 1; 

	        while (scanner.hasNext()) {
	            String currentLine = scanner.nextLine();

	            if (currentLine != null) {
	                String values[] = currentLine.split(",");
	                if (values.length >= 3) {
	                    try {
	                        Product product = new Product(values[0], new BigDecimal(values[1]), new BigDecimal(values[2]));
	                        products.add(product);
	                    } catch (NumberFormatException e) {
	        
	                        System.err.println("Error parsing BigDecimal values on line " + lineNumber + ": " + currentLine);
	                    }
	                } else {
	                    System.err.println("Invalid line format: " + currentLine);
	                }
	            }

	            lineNumber++;
	        }

	        fileReader.close();
	        bufferedReader.close();

	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    return products;
	}

    @Override
	public boolean writeTaxFiles(LinkedList<Tax> taxes) {
		try {
			FileWriter fileWriter = new FileWriter("Taxes.txt");
			PrintWriter printWriter= new PrintWriter(fileWriter);
		
		for(Tax tax:taxes) {
			String taxString=tax.getStateAbbreviation()+","+tax.getStateName()+","+tax.getTaxRate();
			printWriter.println(taxString);
		}
		printWriter.flush();
		printWriter.close();
		fileWriter.close();
		
		return true;
		
	} catch (IOException e) {
		e.printStackTrace();
		return false;
	}

}
	@Override
	public LinkedList<Tax> readTaxFile() {
	    LinkedList<Tax> taxes = new LinkedList<>();

	    try {
	        FileReader fileReader = new FileReader("Taxes.txt");
	        BufferedReader bufferedReader = new BufferedReader(fileReader);
	        Scanner scanner = new Scanner(bufferedReader);

	        if (scanner.hasNextLine()) {
	            scanner.nextLine();
	        }

	        int lineNumber = 1;

	        while (scanner.hasNext()) {
	            String currentLine = scanner.nextLine();

	            if (currentLine != null) {
	                String values[] = currentLine.split(",");
	                if (values.length >= 3) {
	                    try {
	                    	String stateAbbreviations= values[0];
	                        String stateName = values[1];
	                        BigDecimal taxRate = new BigDecimal(values[2]);

	                        Tax tax = new Tax(stateAbbreviations, stateName, taxRate);
	                        taxes.add(tax);
	                    } catch (NumberFormatException e) {
	                  
	                        System.err.println("Error parsing BigDecimal values on line " + lineNumber + ": " + currentLine);
	                    }
	                } else {
	                    System.err.println("Invalid line format: " + currentLine);
	                }
	            }

	            lineNumber++;
	        }

	        fileReader.close();
	        bufferedReader.close();

	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    return taxes;
	}

//	@Override
//    public LinkedList<Order> readOrderFileForEdit(List<String> filenames) {
//        LinkedList<Order> allOrders = new LinkedList<>();
//        for (String filename : filenames) {
//            LinkedList<Order> orders = readOrderFile();
//            allOrders.addAll(orders);
//        }
//        return allOrders;
//    }

	
	@Override
	public List<String> getAllOrderFiles() {
	final String ORDER_FOLDER_PATH = "Orders/";
	List<String> orderFiles = new ArrayList<>();
	
	File directory = new File(ORDER_FOLDER_PATH);
    File[] files = directory.listFiles();

    if (files != null) {
        for (File file : files) {
            if (file.isFile()) {
                orderFiles.add(file.getName());
            }
        }
    }
    return orderFiles;

	}
	
	@Override
	public int getMaxOrderNumber() {
		 File folder = new File("Orders");
		    File[] listOfFiles = folder.listFiles();

		    int maxOrderNumber = 0;

		    for (File file : listOfFiles) {
		        if (file.isFile()) {
		            String fileName = file.getName();
		            String[] parts = fileName.split("_");
		            if (parts.length == 2) {
		                String orderNumberString = parts[1].substring(0, parts[1].indexOf('.'));
		                int orderNumber = Integer.parseInt(orderNumberString);
		                if (orderNumber > maxOrderNumber) {
		                    maxOrderNumber = orderNumber;
		                }
		            }
		        }
		    }

		    return maxOrderNumber;
	}

	@Override
	public boolean exportAllOrders(List<Order> orders) {
	    final String BACKUP_FOLDER_PATH = "Backup/";
	    String fileName = BACKUP_FOLDER_PATH + "AllOrders.txt";

	    try (PrintWriter printWriter = new PrintWriter(new FileWriter(fileName, false))) {
	        for (Order order : orders) {
	            printWriter.println(order);
	        }

	        return true;
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	
}

	
			
	