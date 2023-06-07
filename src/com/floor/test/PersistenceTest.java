package com.floor.test;

import static org.junit.jupiter.api.Assertions.*;



import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;

import com.floor.dto.Order;
import com.floor.persistence.FloorDataAccess;
import com.floor.persistence.FloorDataAccessImpl;

class PersistenceTest {

	
	
	private FloorDataAccess floorDataAccess = new FloorDataAccessImpl();
	private String filePath = "testOrders.txt";
	private File file1 = new File(filePath);
	
	@BeforeEach
	public void setUp() {
	
		
		
	//	testOrder = new LinkedList<>();
	//	testOrder.add(new Order(LocalDate.now(), 111, "Alice", "California", new BigDecimal("99"), "Tile", new BigDecimal("234"),
			//	new BigDecimal("3.20"), new BigDecimal("4.22"), new BigDecimal("345.55"), new BigDecimal("234.44"),
			//	new BigDecimal("23.33"), new BigDecimal("2344.44")));
		 //testOrder.add(new Order(LocalDate.now(), 222, "Steven", "New Yord", new BigDecimal("99"), "Tile", new BigDecimal("34.22"),
			//	new BigDecimal("4.20"), new BigDecimal("9.22"), new BigDecimal("343.55"), new BigDecimal("134.44"),
			//	new BigDecimal("13.33"), new BigDecimal("3344.44")));
		
	}
	
	@Test
	public void testWriteOrderFile() {
		List<Order>  testOrders= new ArrayList<>();
		Order testOrder1 = new Order(LocalDate.now(), 111, "Alice", "California", new BigDecimal("99"), "Tile", new BigDecimal("234"),
			new BigDecimal("3.20"), new BigDecimal("4.22"), new BigDecimal("345.55"), new BigDecimal("234.44"),
		new BigDecimal("23.33"), new BigDecimal("2344.44"));
		
		testOrders.add(testOrder1);
		
		floorDataAccess.writeOrderFiles(testOrders, filePath);
		assertTrue(file1.exists());
		
		
		
		
	}
	@Test void testReadOrderFile() {
	//	floorDataAccess.writeOrderFiles(testOrder)
		//LinkedList<Order> result1 = floorDataAccess.readOrderFile();
		//assertEquals(testOrder, result1);
	}
	@Test
	public void testWriteProductFile() {
		
	}
	@Test void testReadProductFile() {
		
	}
	@Test
	public void testWriteTaxFile() {
		
	}
	@Test void testReadTaxFile() {
		
	}
	
	
}
