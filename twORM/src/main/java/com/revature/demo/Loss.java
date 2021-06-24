package com.revature.demo;

import java.time.LocalDateTime;

// Class for a record of product that is lost to waste or theft.
public class Loss {
	
	private long id;
	
	private long productId;
	
	private long amount;
	
	private boolean isTheft; // true indicates theft, false indicates waste
	
	private LocalDateTime dateLost;

	public Loss(long id, long productId, long amount, boolean isTheft, LocalDateTime dateLost) {
		super();
		this.id = id;
		this.productId = productId;
		this.amount = amount;
		this.isTheft = isTheft;
		this.dateLost = dateLost;
	}

	public Loss(long productId, long amount, boolean isTheft, LocalDateTime dateLost) {
		super();
		this.productId = productId;
		this.amount = amount;
		this.isTheft = isTheft;
		this.dateLost = dateLost;
	}

	public long getLossId() {
		return id;
	}

	public void setLossId(long id) {
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

	public boolean isTheft() {
		return isTheft;
	}

	public void setTheft(boolean isTheft) {
		this.isTheft = isTheft;
	}

	public LocalDateTime getDateLost() {
		return dateLost;
	}

	public void setDateLost(LocalDateTime dateLost) {
		this.dateLost = dateLost;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (amount ^ (amount >>> 32));
		result = prime * result + ((dateLost == null) ? 0 : dateLost.hashCode());
		result = prime * result + (isTheft ? 1231 : 1237);
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
		Loss other = (Loss) obj;
		if (amount != other.amount)
			return false;
		if (dateLost == null) {
			if (other.dateLost != null)
				return false;
		} else if (!dateLost.equals(other.dateLost))
			return false;
		if (isTheft != other.isTheft)
			return false;
		if (id != other.id)
			return false;
		if (productId != other.productId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Loss [id=" + id + ", productId=" + productId + ", amount=" + amount + ", isTheft=" + isTheft
				+ ", dateLost=" + dateLost + "]";
	}
	
}