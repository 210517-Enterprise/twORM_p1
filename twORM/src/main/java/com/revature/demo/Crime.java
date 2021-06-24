package com.revature.demo;

import com.revature.annotations.Entity;
import com.revature.annotations.Getter;
import com.revature.annotations.PrimaryKey;
import com.revature.annotations.Setter;

@Entity(name = "crime")
public class Crime {
	
	@PrimaryKey(name="offence", isSerial=false)
	private String offence;
	
	private String description;
	
	private int sentence;
	
	@Getter(name="offence")
	public String getOffence() {
		return offence;
	}

	@Setter(name="offence")
	public void setOffence(String name) {
		this.offence = name;
	}
	
	@Getter(name="description")
	public String getDescription() {
		return description;
	}

	@Setter(name="description")
	public void setDescription(String description) {
		this.description = description;
	}

	@Getter(name="sentence")
	public int getSentence() {
		return sentence;
	}

	@Setter(name="sentence")
	public void setSentence(int sentence) {
		this.sentence = sentence;
	}

	public Crime(String name, String description, int sentence) {
		super();
		this.offence = name;
		this.description = description;
		this.sentence = sentence;
	}

	public Crime() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((offence == null) ? 0 : offence.hashCode());
		result = prime * result + sentence;
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
		Crime other = (Crime) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (offence == null) {
			if (other.offence != null)
				return false;
		} else if (!offence.equals(other.offence))
			return false;
		if (sentence != other.sentence)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Crime [name=" + offence + ", description=" + description + ", sentence=" + sentence + "]";
	}

}
