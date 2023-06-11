package com.floor.service;
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

public class OrderData {
	
	
	    private List<Order> orders;
	    private int maxOrderNumber;

	    public OrderData(List<Order> orders, int maxOrderNumber) {
	        this.orders = orders;
	        this.maxOrderNumber = maxOrderNumber;
	    }

	    public List<Order> getOrders() {
	        return orders;
	    }

	    public int getMaxOrderNumber() {
	        return maxOrderNumber;
	    }
	}


