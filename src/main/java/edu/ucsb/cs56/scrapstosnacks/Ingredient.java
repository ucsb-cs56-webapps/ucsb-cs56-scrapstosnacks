package edu.ucsb.cs56.scrapstosnacks;

public class Ingredient{
	private double amount;
	private String name;
	public Ingredient(double a,String n)
	{
		amount = a;
		name = n;
	}
	public double get_amount()
	{
		return amount;
	}
	public String get_name()
	{
		return name;
	}
	public void set_amount(double a)
	{
		amount = a;
	}
	public void set_name(String n)
	{
		name = n;
	}
}	
