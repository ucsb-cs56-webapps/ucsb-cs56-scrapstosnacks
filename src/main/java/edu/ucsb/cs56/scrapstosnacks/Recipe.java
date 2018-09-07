package edu.ucsb.cs56.scrapstosnacks;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Recipe{
	private ArrayList<Ingredient> re;
	private String name;
	private String instruction;

	//no argument constructor
	public Recipe() 
	{
		re = new ArrayList<Ingredient>();
		name = "";
		instruction = "";
	}

	public Recipe(ArrayList<Ingredient> r,String n,String i)
	{
		name = n;
		if(r.size()>0)
		{
			for(int j=0;j<r.size();j++)
			{
				re.add(r.get(j));
			}	
		}
		else
		{
			System.out.print("there is no ingredient"); // temporarily using this to debug
		}
		instruction = i;
	}


	public String get_name()
	{
		return name;
	}


	public String get_instruction()
	{
		return instruction;
	}


	public ArrayList<Ingredient> get_list()
	{
		return re;
	}


	public String list_ingredients()
	{
		String result="";
		for(int i=0;i<re.size();i++)
		{
			result += re.get(i).get_name();
		}
		return result;
	}


	public void set_name(String n)
	{
		name = n;
	}


	public void set_instruction(String i)
	{
		instruction = i;
	}


	public void add(Ingredient a)
	{
		re.add(a);//not handling duplicate situation
	}


	public int search(Ingredient a)
	{
		for(int i=0;i<re.size();i++)
		{
			if(re.get(i).get_name()==a.get_name())
			{
				return i;
			}
		}
		return -1;
	}


	// call search first to find index then call delete
	public boolean delete(int index)
	{
		if(index<re.size()&&index>=0)
		{
			re.remove(index);
			return true;
		}
		else
			return false;
	}


	public Ingredient get(Ingredient a) throws NoSuchElementException
	{
			if(this.search(a)!=-1)
			{
				return re.get(this.search(a));
			}
			else
				throw new NoSuchElementException();
	}
}

