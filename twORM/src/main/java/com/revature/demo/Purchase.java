package com.revature.demo;

import java.time.LocalDateTime;

import com.revature.annotations.Column;
import com.revature.annotations.Getter;
import com.revature.annotations.PrimaryKey;
import com.revature.annotations.Setter;


// Class for a record of each purchase order of inventory for the store
public class Purchase {
	
	@PrimaryKey(name = "sale_id", isSerial = true)
	private long id;
	
	@Column(name = "product_id")
	private long productId;
	
	@Column(name = "amount")
	private long amount;
	
	@Column(name = "ordered_date")
	private LocalDateTime dateOrdered;
	
	@Column(name = "delivery_status")
	private boolean isDelivered;
	
	@Column(name = "delivered_date")
	private LocalDateTime dateDelivered;
	
	public Purchase() {
		super();
	}

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
	
	@Getter(name = "getId")
	public long getId() {
		return id;
	}

	@Setter(name = "setId")
	public void setId(long id) {
		this.id = id;
	}

	@Getter(name = "getProductId")
	public long getProductId() {
		return productId;
	}

	@Setter(name = "setProductId")
	public void setProductId(long productId) {
		this.productId = productId;
	}

	@Getter(name = "getAmount")
	public long getAmount() {
		return amount;
	}

	@Setter(name = "setAmount")
	public void setAmount(long amount) {
		this.amount = amount;
	}

	@Getter(name = "getDateOrdered")
	public LocalDateTime getDateOrdered() {
		return dateOrdered;
	}

	@Setter(name = "setDateOrdered")
	public void setDateOrdered(LocalDateTime dateOrdered) {
		this.dateOrdered = dateOrdered;
	}

	@Getter(name = "isDelivered")
	public boolean isDelivered() {
		return isDelivered;
	}

	@Setter(name = "setDelivered")
	public void setDelivered(boolean isDelivered) {
		this.isDelivered = isDelivered;
	}

	
	@Getter(name = "getDateDelivered")
	public LocalDateTime getDateDelivered() {
		return dateDelivered;
	}

	@Setter(name = "setDateDelivered")
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