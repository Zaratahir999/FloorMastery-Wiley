package com.floor.presentation;
import java.util.List;
import java.nio.file.*;
import java.io.*;

import java.util.*;
import com.floor.service.FloorBusinessLogic;
import com.floor.service.FloorBusinessLogicImpl;
import com.floor.dto.Order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.stream.Stream;

public class FloorUserInterfaceImpl implements FloorUserInterface {
    private FloorBusinessLogic businessLogic = new FloorBusinessLogicImpl();
    private Scanner scanner = new Scanner(System.in);

    @Override
    public void displayMenu() {
        System.out.println("1. Display Orders");
        System.out.println("2. Add an Order");
        System.out.println("3. Edit an Order");
        System.out.println("4. Remove an Order");
        System.out.println("5. Export All Data");
        System.out.println("6. Quit");
    }

    @Override
    public void performMenu(int choice) {
        switch (choice) {
            case 1:
                displayOrders();
                break;
            case 2:
                addOrder();
                break;
            case 3:
                editOrder();
                break;
            case 4:
                removeOrder();
                break;
            case 5:
                exportAllData();
                break;
            case 6:
                System.out.println("Exiting...");
                scanner.close();
                System.exit(0);
                break;
            default:
                System.out.println("Invalid Choice");
        }
        
        displayMenu();

    }

    private void displayOrders() {
        System.out.println("Enter a date (MM/dd/yyyy): ");
        String dateInput = scanner.nextLine();

        // Remove "/" from the entered date to match the filename format
        String formattedDateInput = dateInput.replace("/", "");

        // Construct the filename
        String fileName = "Orders_" + formattedDateInput + ".txt";

        // Construct the path to the file
        Path filePath = Paths.get("Orders", fileName);

        // Check if the file exists
        if (Files.exists(filePath)) {
            // If the file exists, read and display its contents
            try (Stream<String> lines = Files.lines(filePath)) {
                lines.forEach(System.out::println);
            } catch (IOException e) {
                System.out.println("Error reading file: " + e.getMessage());
            }
        } else {
            System.out.println("No orders found for the entered date.");
        }
    }

       
    
   

    private void addOrder() {
        Order order = getInputOrder();

        System.out.println("Order Summary:");
        System.out.println(order);

        System.out.println("Add this order? (Y/N)");
        String addChoice = scanner.nextLine();
        
        if (addChoice.equalsIgnoreCase("Y")) {
            if (businessLogic.addOrder(order))
            	businessLogic.saveOrder();
           
                System.out.println("Order Added!");
            } else {
                System.out.println("Order Not Added!");
            }
        }
    



    private Order getInputOrder() {
        Order order = new Order();

        System.out.println("Enter order date (MM/dd/yyyy): ");
        String dateInput = scanner.nextLine();
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate orderDate = LocalDate.parse(dateInput, formatter);
        order.setDate(orderDate);

        System.out.println("Enter Customer Name: ");
        String customerName = scanner.nextLine();
        order.setCustomerName(customerName);

        System.out.println("Enter State: ");
        String state = scanner.nextLine();
        order.setState(state);

        System.out.println("Enter Product Type: ");
        String productType = scanner.nextLine();
        order.setProductType(productType);

        System.out.println("Enter Area: ");
        BigDecimal area = scanner.nextBigDecimal();
        order.setArea(area);
        scanner.nextLine(); // Consume the newline character

        businessLogic.calculateOrder(order);
        BigDecimal taxRate = businessLogic.getTaxRate(state);
        order.setTaxRate(taxRate);
        
        return order;
    }

    private void editOrder() {
        System.out.println("Enter order date (MM/dd/yyyy) to edit: ");
        String dateInput = scanner.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate orderDate = LocalDate.parse(dateInput, formatter);

        // Display the orders for the given date
        List<Order> orders = businessLogic.getOrdersByDate(orderDate);
        for (Order order : orders) {
            System.out.println(order);
        }

        System.out.println("Enter order number to edit: ");
        int orderNumber = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        Order newOrder = getInputOrder();

        System.out.println("Edit this order? (Y/N)");
        String editChoice = scanner.nextLine();

        if (editChoice.equalsIgnoreCase("Y")) {
            if (businessLogic.editOrder(orderDate, orderNumber, newOrder)) {
                System.out.println("Order Edited!");
            } else {
                System.out.println("Order Not Found!");
            }
        }
    }
    private void removeOrder() {
        System.out.println("Enter order date (MM/dd/yyyy) to remove: ");
        String dateInput = scanner.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate orderDate = LocalDate.parse(dateInput, formatter);

        // Display the orders for the given date
        List<Order> orders = businessLogic.getOrdersByDate(orderDate);
        for (Order order : orders) {
            System.out.println(order);
        }

        System.out.println("Enter order number to remove: ");
        int orderNumber = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        System.out.println("Remove this order? (Y/N)");
        String removeChoice = scanner.nextLine();

        if (removeChoice.equalsIgnoreCase("Y")) {
            if (businessLogic.removeOrder(orderDate, orderNumber)) {
                System.out.println("Order Removed!");
            } else {
                System.out.println("Order Not Found!");
            }
        }
    }

    private void exportAllData() {
    	businessLogic.exportAllData();
        System.out.println("All orders exported!");
    }
}