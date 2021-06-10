package com.revature.demo;

import java.time.LocalDateTime;
import java.util.Hashtable;

//Class for a record of each sale in the store
public class Sale {

	private long saleId;

	// the Long represents the # of units of the Product
	private Hashtable<Product, Long> order;
	
	private LocalDateTime dateSold;

	public Sale(long saleId, Hashtable<Product, Long> order, LocalDateTime dateSold) {
		super();
		this.saleId = saleId;
		this.order = order;
		this.dateSold = dateSold;
	}

	public Sale(Hashtable<Product, Long> order, LocalDateTime dateSold) {
		super();
		this.order = order;
		this.dateSold = dateSold;
	}

	public long getSaleId() {
		return saleId;
	}

	public void setSaleId(long saleId) {
		this.saleId = saleId;
	}

	public Hashtable<Product, Long> getOrder() {
		return order;
	}

	public void setOrder(Hashtable<Product, Long> order) {
		this.order = order;
	}

	public LocalDateTime getDateSold() {
		return dateSold;
	}

	public void setDateSold(LocalDateTime dateSold) {
		this.dateSold = dateSold;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateSold == null) ? 0 : dateSold.hashCode());
		result = prime * result + (int) (saleId ^ (saleId >>> 32));
		result = prime * result + ((order == null) ? 0 : order.hashCode());
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
		Sale other = (Sale) obj;
		if (dateSold == null) {
			if (other.dateSold != null)
				return false;
		} else if (!dateSold.equals(other.dateSold))
			return false;
		if (saleId != other.saleId)
			return false;
		if (order == null) {
			if (other.order != null)
				return false;
		} else if (!order.equals(other.order))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Sale [id=" + saleId + ", order=" + order + ", dateSold=" + dateSold + "]";
	}

}
