package org.tondo.hibernate.mappings.otm.uni;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 
 * @author TondoDev
 *
 */
@Entity
public class UniTargetJoinT {
	
	
	@Id
	//@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	private String data;
	
	@Column(name = "source")
	private Long src;
	
//	public long getSrc() {
//		return src;
//	}
//	
//	public void setSrc(long src) {
//		this.src = src;
//	}
	
	
	public String getData() {
		return data;
	}
	
	public void setData(String data) {
		this.data = data;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
}
