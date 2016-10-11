package org.tondo.hibernate.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	private int height;
	private double salary;
	private Date birthtDate;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public double getSalary() {
		return salary;
	}
	
	public void setSalary(double salary) {
		this.salary = salary;
	}
	
	public Date getBirthtDate() {
		return birthtDate;
	}
	
	public void setBirthtDate(Date birthtDate) {
		this.birthtDate = birthtDate;
	}
	
	public static Person create(String name, int height, double salary, Date birth) {
		Person p = new Person();
		p.setName(name);
		p.setHeight(height);
		p.setSalary(salary);
		p.setBirthtDate(birth);
		return p;
	}
}
