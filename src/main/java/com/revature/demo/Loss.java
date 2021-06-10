package com.revature.demo;

import java.time.LocalDateTime;

//Class for a record of a unit or units of product that are lost to waste or theft.
public class Loss {
	
	private long lossId;
	
	private Product product;
	
	private long amount;
	
	private LocalDateTime dateLost;

	public Loss(long lossId, Product product, long amount, LocalDateTime dateLost) {
		super();
		this.lossId = lossId;
		this.product = product;
		this.amount = amount;
		this.dateLost = dateLost;
	}

	public Loss(Product product, long amount, LocalDateTime dateLost) {
		super();
		this.product = product;
		this.amount = amount;
		this.dateLost = dateLost;
	}

	public long getLossId() {
		return lossId;
	}

	public void setLossId(long lossId) {
		this.lossId = lossId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
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
		result = prime * result + (int) (lossId ^ (lossId >>> 32));
		result = prime * result + ((product == null) ? 0 : product.hashCode());
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
		if (lossId != other.lossId)
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Loss [id=" + lossId + ", product=" + product + ", amount=" + amount + ", dateLost=" + dateLost
				+ "]";
	}

}
