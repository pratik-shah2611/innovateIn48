package TestNG;

import org.testng.TestListenerAdapter;
import org.testng.TestNG;

public class StudClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		TestListenerAdapter tla = new TestListenerAdapter();
		TestNG testng = new TestNG();
		testng.setTestClasses(new Class[] { TestNGStudClass.class });
		testng.addListener(tla);
		testng.run();
	}
}
