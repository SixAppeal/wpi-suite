package edu.wpi.cs.wpisuitetng.modules.taskmanager.util;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.util.TaskUtil;;

/**
 * 
 * @author Thhughes
 * @author jrhennessy
 *
 */
public class TestTaskUtil {
	
	
	/**
	 * tests that the sanatizeinput function will sanatize the leading 
	 * zeros in an input
	 */
	@Test 
	public void testSanitizeInput_leading(){
		String test1 = new String("     input");
		test1 = TaskUtil.sanitizeInput(test1);
		assertEquals(test1, "input");
	}
	
	/**
	 * tests that the sanatizeinput function will sanatize the trailing 
	 * zeros in an input
	 */
	@Test 
	public void testSanitizeInput_trailing(){
		String test2 = new String("input     ");
		test2 = TaskUtil.sanitizeInput(test2);
		assertEquals(test2, "input");
	}
	
	/**
	 * tests that the sanatizeinput function will sanatize both the leading and trailing  
	 * zeros in an input
	 */
	@Test 
	public void testSanitizeInput_both(){
		String test3 = new String("      input     ");
		test3 = TaskUtil.sanitizeInput(test3);
		assertEquals(test3, "input");
	}
	
	@Test 
	public void testSanitizeInput_tab(){
		String test3 = new String("\t input     ");
		test3 = TaskUtil.sanitizeInput(test3);
		assertEquals(test3, "input");
	}
	
	@Test 
	public void testSanitizeInput_newLine(){
		String test3 = new String("\n input     ");
		test3 = TaskUtil.sanitizeInput(test3);
		assertEquals(test3, "input");
	}
	
	@Test 
	public void testSanitizeInput_return(){
		String test3 = new String("\r input     ");
		test3 = TaskUtil.sanitizeInput(test3);
		assertEquals(test3, "input");
	}
	
	@Test 
	public void testSanitizeInput_break(){
		String test3 = new String("\b  input     ");
		test3 = TaskUtil.sanitizeInput(test3);
		assertEquals(test3, "input");
	}
	
	@Test 
	public void testSanitizeInput_front(){
		String test3 = new String("\f  input     ");
		test3 = TaskUtil.sanitizeInput(test3);
		assertEquals(test3, "input");
	}
	
	
	
	/**
	 * Tests if the title in putted is validated 
	 * given a normal title
	 */
	@Test
	public void testvalidateTitle_validTitle(){
		String test = new String();
		test = TaskUtil.validateTitle("Title Title Title");
		assertEquals(test, "Title Title Title");
	}
	
	@Test
	public void testValidtateTitle_validTitle_2(){
		String test = new String();
		test = TaskUtil.validateTitle("  Title  ");
		assertEquals(test, "Title");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testvalidateTitle_invalidTitle()throws IllegalArgumentException
	{
		String test = new String();
		test = TaskUtil.validateTitle(" ");
		
	}
	
	/**
	 * Tests if the title inputed is validated 
	 * given a normal title
	 */
	@Test
	public void testvalidateDescription_validDescription(){
		String test = new String();
		test = TaskUtil.validateDescription("description description description");
		assertEquals(test, "description description description");
	}
	
	@Test
	public void testvalidateDescription_validDescription_2(){
		String test = new String();
		test = TaskUtil.validateDescription("  Description  ");
		assertEquals(test, "Description");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testvalidateDescription_invalidDesription()throws IllegalArgumentException
	{
		String test = new String();
		test = TaskUtil.validateDescription(" ");
		
	}
	
	
	
	
	@Test(expected=IllegalArgumentException.class)
	public void testValidateEffort_invalidInput_neg() throws IllegalArgumentException
	{
		TaskUtil.validateEffort(-666);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testValidateEffort_invalidInput_zero() throws IllegalArgumentException
	{
		TaskUtil.validateEffort(0);
	}
	
	@Test
	public void testValidateEffort_ValidInput(){
		Integer testInt = new Integer(0);
		Integer answer = new Integer(666);
		
		testInt = TaskUtil.validateEffort(666);
		
		assertEquals(testInt, answer);
	}
	
	
	
	
	

}
