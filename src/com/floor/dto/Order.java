package com.floor.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Order {

	private final LocalDate date;
	private int orderNumber;
	private String customerName;
	private String state;
	private BigDecimal taxRate;
	private String productType;
	private BigDecimal area;
	private BigDecimal costPerSquareFoot;
	private BigDecimal labourCostPerSquareFoot;
	private BigDecimal materialCost;
	private BigDecimal labourCost;
	private BigDecimal tax;
	private BigDecimal total;
	
	
	public Order(LocalDate date, int orderNumber, String customerName, String state, BigDecimal taxRate,
			String productType, BigDecimal area, BigDecimal costPerSquareFoot, BigDecimal labourCostPerSquareFoot,
			BigDecimal materialCost, BigDecimal labourCost, BigDecimal tax, BigDecimal total) {
		super();
		this.date = date;
		this.orderNumber = orderNumber;
		this.customerName = customerName;
		this.state = state;
		this.taxRate = taxRate;
		this.productType = productType;
		this.area = area;
		this.costPerSquareFoot = costPerSquareFoot;
		this.labourCostPerSquareFoot = labourCostPerSquareFoot;
		this.materialCost = materialCost;
		this.labourCost = labourCost;
		this.tax = tax;
		this.total = total;
	}
	
	
	public int getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public BigDecimal getTaxRate() {
		return taxRate;
	}
	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public BigDecimal getArea() {
		return area;
	}
	public void setArea(BigDecimal area) {
		this.area = area;
	}
	public BigDecimal getCostPerSquareFoot() {
		return costPerSquareFoot;
	}
	public void setCostPerSquareFoot(BigDecimal costPerSquareFoot) {
		this.costPerSquareFoot = costPerSquareFoot;
	}
	public BigDecimal getLabourCostPerSquareFoot() {
		return labourCostPerSquareFoot;
	}
	public void setLabourCostPerSquareFoot(BigDecimal labourCostPerSquareFoot) {
		this.labourCostPerSquareFoot = labourCostPerSquareFoot;
	}
	public BigDecimal getMaterialCost() {
		return materialCost;
	}
	public void setMaterialCost(BigDecimal materialCost) {
		this.materialCost = materialCost;
	}
	public BigDecimal getLabourCost() {
		return labourCost;
	}
	public void setLabourCost(BigDecimal labourCost) {
		this.labourCost = labourCost;
	}
	public BigDecimal getTax() {
		return tax;
	}
	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public LocalDate getDate() {
		return date;
	}


	@Override
	public String toString() {
		return "Order [date=" + date + ", orderNumber=" + orderNumber + ", customerName=" + customerName + ", state="
				+ state + ", taxRate=" + taxRate + ", productType=" + productType + ", area=" + area
				+ ", costPerSquareFoot=" + costPerSquareFoot + ", labourCostPerSquareFoot=" + labourCostPerSquareFoot
				+ ", materialCost=" + materialCost + ", labourCost=" + labourCost + ", tax=" + tax + ", total=" + total
				+ "]";
	}

	
	
	
	
}
