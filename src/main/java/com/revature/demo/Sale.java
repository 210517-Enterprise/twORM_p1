package com.revature.demo;

import java.time.LocalDateTime;

//Class for a record of each sale in the store
public class Sale {

	private long id;

	private long productId;
	
	private long amount;
	
	private LocalDateTime dateSold;

	public Sale(long id, long productId, long amount, LocalDateTime dateSold) {
		super();
		this.id = id;
		this.productId = productId;
		this.amount = amount;
		this.dateSold = dateSold;
	}

	public Sale(long productId, long amount, LocalDateTime dateSold) {
		super();
		this.productId = productId;
		this.amount = amount;
		this.dateSold = dateSold;
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
		result = prime * result + (int) (amount ^ (amount >>> 32));
		result = prime * result + ((dateSold == null) ? 0 : dateSold.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
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
		Sale other = (Sale) obj;
		if (amount != other.amount)
			return false;
		if (dateSold == null) {
			if (other.dateSold != null)
				return false;
		} else if (!dateSold.equals(other.dateSold))
			return false;
		if (id != other.id)
			return false;
		if (productId != other.productId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Sale [id=" + id + ", productId=" + productId + ", amount=" + amount + ", dateSold=" + dateSold + "]";
	}

}
