package com.revature.demo;

import java.time.LocalDateTime;

// Class for a record of each purchase order of inventory for the store
public class Purchase {
	
	private long id;
	
	private long productId;
	
	private long amount;
	
	private LocalDateTime dateOrdered;
	
	private boolean isDelivered;
	
	private LocalDateTime dateDelivered;

	public Purchase(long id, long productId, long amount, LocalDateTime dateOrdered, boolean isDelivered,
			LocalDateTime dateDelivered) {
		super();
		this.id = id;
		this.productId = productId;
		this.amount = amount;
		this.dateOrdered = dateOrdered;
		this.isDelivered = isDelivered;
		this.dateDelivered = dateDelivered;
	}

	public Purchase(long productId, long amount, LocalDateTime dateOrdered, boolean isDelivered,
			LocalDateTime dateDelivered) {
		super();
		this.productId = productId;
		this.amount = amount;
		this.dateOrdered = dateOrdered;
		this.isDelivered = isDelivered;
		this.dateDelivered = dateDelivered;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
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
		result = prime * result + (int) (amount ^ (amount >>> 32));
		result = prime * result + ((dateDelivered == null) ? 0 : dateDelivered.hashCode());
		result = prime * result + ((dateOrdered == null) ? 0 : dateOrdered.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (isDelivered ? 1231 : 1237);
		result = prime * result + (int) (productId ^ (productId >>> 32));
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
		if (amount != other.amount)
			return false;
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
		if (id != other.id)
			return false;
		if (isDelivered != other.isDelivered)
			return false;
		if (productId != other.productId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Purchase [id=" + id + ", productId=" + productId + ", amount=" + amount + ", dateOrdered=" + dateOrdered
				+ ", isDelivered=" + isDelivered + ", dateDelivered=" + dateDelivered + "]";
	}
	
}