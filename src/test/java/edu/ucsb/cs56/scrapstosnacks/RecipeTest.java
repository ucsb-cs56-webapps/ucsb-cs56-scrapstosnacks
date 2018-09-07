package edu.ucsb.cs56.scrapstosnacks;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class RecipeTest {


	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	private final PrintStream originalErr = System.err;
	
	@Before
	public void setUpStreams() {
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
	/*	Recipe chickenandrice = new chickenandrice();
		Ingredient chicken = new Ingredient(1.0, "chicken");
		Ingredient rice = new Ingredient(1.0, "cup of rice");
		chickenandrice.add(chicken);
	
		chickenandaice.add(rice);
	*/
	}


	@After
	public void restoreStreams() {
		System.setOut(originalOut);
		System.setErr(originalErr);
	}

/*	@Test
	public void test_list_ingrdients() {
		String expected = "Chickencup of rice";
		assertEquals(expected, chickenandrice.list_ingredients() );
	}
*/	
}
