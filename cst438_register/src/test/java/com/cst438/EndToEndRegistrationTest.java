package com.cst438;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cst438.domain.Student;
import com.cst438.domain.StudentRepository;

@SpringBootTest
public class EndToEndRegistrationTest {

	public static final String CHROME_DRIVER_FILE_LOCATION = "C:/Users/elkev/Downloads/chromedriver_win32/chromedriver.exe";

	public static final String URL = "http://localhost:3000";
	

	public static final String TEST_USER_EMAIL = "test10@csumb.edu";

	public static final String TEST_USER_NAME = "test 10";

	public static final int SLEEP_DURATION = 2000; // 1 second.


	@Autowired
	StudentRepository studentRepository;

	@Test
	public void addStudentTest() throws Exception {

		Student s = null;
		do {
			s = studentRepository.findByEmail(TEST_USER_EMAIL);
			if (s != null)
				studentRepository.delete(s);
		} while (s != null);

		// set the driver location and start driver
		//@formatter:off
		// browser	property name 				Java Driver Class
		// edge 	webdriver.edge.driver 		EdgeDriver
		// FireFox 	webdriver.firefox.driver 	FirefoxDriver
		// IE 		webdriver.ie.driver 		InternetExplorerDriver
		//@formatter:on

		System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_FILE_LOCATION);
		WebDriver driver = new ChromeDriver();
		// Puts an Implicit wait for 10 seconds before throwing exception
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		try {

			driver.get(URL);
			Thread.sleep(SLEEP_DURATION);

			 //Locate and click "Add Student" button
//			driver.findElement(By.xpath("//button[@id='AddStudent']")).click();
			driver.findElement(By.xpath("//*[@id='AddStudent']")).click();
			Thread.sleep(SLEEP_DURATION);

			// enter test email, test name, and click "Add"
			
			driver.findElement(By.xpath("//input[@id='name']")).sendKeys(TEST_USER_NAME);
			driver.findElement(By.xpath("//input[@id='email']")).sendKeys(TEST_USER_EMAIL);
			

			driver.findElement(By.xpath("//button[@id='Add']")).click();
			Thread.sleep(SLEEP_DURATION);

			// verify that new student has been added
			Student student = studentRepository.findByEmail(TEST_USER_EMAIL);
			System.out.println("/created student "+student.getName()+" "+student.getEmail());
			assertNotNull(student, "Added student is not present.");

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			throw ex;
			
		} finally {

			// clean up database.
			Student st = studentRepository.findByEmail(TEST_USER_EMAIL);
			if (st != null)
				studentRepository.delete(st);
			driver.quit();
		}

	}

}