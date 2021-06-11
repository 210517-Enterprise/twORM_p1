package com.revature.demo;

import java.math.BigDecimal;

public class Product {
	
	private long id;
	
	private String name;
	
	private BigDecimal cost;
	
	private BigDecimal price;
	
	private long stock;

	public Product(long id, String name, BigDecimal cost, BigDecimal price, long stock) {
		super();
		this.id = id;
		this.name = name;
		this.cost = cost;
		this.price = price;
		this.stock = stock;
	}

	public Product(String name, BigDecimal cost, BigDecimal price, long stock) {
		super();
		this.name = name;
		this.cost = cost;
		this.price = price;
		this.stock = stock;
	}

	public Product(String name, BigDecimal cost, BigDecimal price) {
		super();
		this.name = name;
		this.cost = cost;
		this.price = price;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public long getStock() {
		return stock;
	}

	public void setStock(long stock) {
		this.stock = stock;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cost == null) ? 0 : cost.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + (int) (stock ^ (stock >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (cost == null) {
			if (other.cost != null)
				return false;
		} else if (!cost.equals(other.cost))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (stock != other.stock)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", cost=$" + cost + ", price=$" + price + ", stock=" + stock
				+ "]";
	}

}