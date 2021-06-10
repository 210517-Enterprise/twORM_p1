package com.revature.demo;

import java.time.LocalDateTime;
import java.util.Hashtable;

// Class for a record of each purchase order of inventory for the store
public class Purchase {
	
	private long purchaseId;
	
	// the Long represents the # of units of the product
	private Hashtable<Product, Long> order;
	
	private LocalDateTime dateOrdered;
	
	private boolean isDelivered;
	
	private LocalDateTime dateDelivered;
	
	public Purchase(long purchaseId, Hashtable<Product, Long> order, LocalDateTime dateOrdered, boolean isDelivered,
			LocalDateTime dateDelivered) {
		super();
		this.purchaseId = purchaseId;
		this.order = order;
		this.dateOrdered = dateOrdered;
		this.isDelivered = isDelivered;
		this.dateDelivered = dateDelivered;
	}

	public Purchase(Hashtable<Product, Long> order, LocalDateTime dateOrdered, boolean isDelivered,
			LocalDateTime dateDelivered) {
		super();
		this.order = order;
		this.dateOrdered = dateOrdered;
		this.isDelivered = isDelivered;
		this.dateDelivered = dateDelivered;
	}

	public Purchase(Hashtable<Product, Long> order, LocalDateTime dateOrdered) {
		super();
		this.order = order;
		this.dateOrdered = dateOrdered;
	}

	public long getId() {
		return purchaseId;
	}

	public void setId(long purchaseId) {
		this.purchaseId = purchaseId;
	}

	public Hashtable<Product, Long> getOrder() {
		return order;
	}

	public void setOrder(Hashtable<Product, Long> order) {
		this.order = order;
	}

	public LocalDateTime getDateOrdered() {
		return dateOrdered;
	}

	public void setDateOrdered(LocalDateTime dateOrdered) {
		this.dateOrdered = dateOrdered;
	}

	public boolean isDelivered() {
		return isDelivered;
	}

	public void setDelivered(boolean isDelivered) {
		this.isDelivered = isDelivered;
	}

	public LocalDateTime getDateDelivered() {
		return dateDelivered;
	}

	public void setDateDelivered(LocalDateTime dateDelivered) {
		this.dateDelivered = dateDelivered;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateDelivered == null) ? 0 : dateDelivered.hashCode());
		result = prime * result + ((dateOrdered == null) ? 0 : dateOrdered.hashCode());
		result = prime * result + (int) (purchaseId ^ (purchaseId >>> 32));
		result = prime * result + (isDelivered ? 1231 : 1237);
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
		Purchase other = (Purchase) obj;
		if (dateDelivered == null) {
			if (other.dateDelivered != null)
				return false;
		} else if (!dateDelivered.equals(other.dateDelivered))
			return false;
		if (dateOrdered == null) {
			if (other.dateOrdered != null)
				return false;
		} else if (!dateOrdered.equals(other.dateOrdered))
			return false;
		if (purchaseId != other.purchaseId)
			return false;
		if (isDelivered != other.isDelivered)
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
		return "Purchase [id=" + purchaseId + ", order=" + order + ", dateOrdered=" + dateOrdered + ", isDelivered="
				+ isDelivered + ", dateDelivered=" + dateDelivered + "]";
	}

}
