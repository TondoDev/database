package org.tondo.hibernate.mappings;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class OneToOneFirst {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String firstProp;
	
	@OneToOne(mappedBy = "first")
	private OneToOneSecond second;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstProp() {
		return firstProp;
	}

	public void setFirstProp(String firstProp) {
		this.firstProp = firstProp;
	}

	public OneToOneSecond getSecond() {
		return second;
	}

	public void setSecond(OneToOneSecond second) {
		this.second = second;
	}
}
