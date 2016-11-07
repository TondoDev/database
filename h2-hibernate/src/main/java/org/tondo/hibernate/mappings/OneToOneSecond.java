package org.tondo.hibernate.mappings;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class OneToOneSecond {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private BigDecimal secondProp;
	
	@OneToOne
	private OneToOneFirst first;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public BigDecimal getSecondProp() {
		return secondProp;
	}


	public void setSecondProp(BigDecimal secondProp) {
		this.secondProp = secondProp;
	}


	public OneToOneFirst getFirst() {
		return first;
	}


	public void setFirst(OneToOneFirst first) {
		this.first = first;
	}
}
