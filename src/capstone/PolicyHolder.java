/**
 * Java Course 4
 * 
 * Norima Java Developer Course Capstone Project
 * 
 * Policy Holder Class
 */
package capstone;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author John Elli Cabuhat
 * @description Norima Java Developer Course Capstone Project
 * @CreatedDate June 27, 2022
 * @ModifiedDate 
 * @ModifiedBy
 */
public class PolicyHolder{

	private String FirstName;
	private String LastName;
	private String DateofBirth;
	private String Address;
	private String LicenseNumber;
	private String DateLicenseIssued;
	Scanner input = new Scanner(System.in);
	Calendar cal = Calendar.getInstance();
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	Pattern pattern = Pattern.compile("[0-9\s]");
	
	
	//for setting up policy holder data
	public void setHolder() {
		format.setLenient(false);
		boolean checker = false;
		
		do {
			System.out.print("First Name: ");
			String inputString = input.nextLine();
			if(inputString.isBlank()) {
				System.out.println("\nInput first name\n");
			}
			else if(checkInput(inputString)) {
				System.out.println("\nInvalid Input.\n");
			}
			else {
				this.FirstName = inputString;
				checker = true;
			}
			
		}while(!checker);
		
		do {
			checker = false;
			System.out.print("Last Name: ");
			String inputString = input.nextLine();
			if(inputString.isBlank()) {
				System.out.println("\nInput last name\n");
			}
			else if(checkInput(inputString)) {
				System.out.println("\nInvalid Input.\n");
			}
			else {
				this.LastName = inputString;
				checker = true;
			}
			
		}while(!checker);
		
		do {
			checker = false;
			System.out.print("Date of Birth (YYYY-MM-DD): ");
			try {
				Date date = format.parse(input.nextLine());
				this.DateofBirth = format.format(date);
				checker = true;
			} catch (ParseException e) {
				System.out.println("\nInvalid date.\n");
			}
		}while(!checker);
		
		do {
			checker = false;
			System.out.print("Address: ");
			String inputString = input.nextLine();
			if(inputString.isBlank()) {
				System.out.println("\nInput Address\n");
			}
			else if(checkInput(inputString)) {
				System.out.println("\nInvalid Input.\n");
			}
			else {
				this.Address = inputString;
				checker = true;
			}
			
		}while(!checker);
		
		do {
			checker = false;
			System.out.print("License Number: ");
			String inputString = input.nextLine();
			if(inputString.isBlank()) {
				System.out.println("\nInput License Number\n");
			}
			else {
				this.LicenseNumber = inputString;
				checker = true;
			}
			
		}while(!checker);
		
		
		
		do {
			checker = false;
			System.out.print("Date of Driver's License, 1st Issued (YYYY-MM-DD): ");
			try {
				Date date = format.parse(input.nextLine());
				this.DateLicenseIssued = format.format(date);
				checker = true;
			} catch (ParseException e) {
				System.out.println("\nInvalid date.\n");
			}
		}while(!checker);

	}
	
	public boolean checkInput(String input) {
		Matcher matcher = pattern.matcher(input);
		boolean matchFound = matcher.find();
		if(matchFound) {
			return true;
		}
		else {
			return false;
		}
	}
	
	//to set-up data as default ( customer account details)  
	public void ownerHolder(String firstName, String lastName, String Address) {
		format.setLenient(false);
		boolean checker = false;
		System.out.println("First Name: " + firstName);
		this.FirstName = firstName;
		System.out.println("\nLast Name: " + lastName);
		this.LastName = lastName;
		System.out.println("\nAddress: " + Address);
		this.Address = Address;
		
		do {
			System.out.print("Date of Birth (YYYY-MM-DD): ");
			try {
				Date date = format.parse(input.nextLine());
				this.DateofBirth = format.format(date);
				checker = true;
			} catch (ParseException e) {
				System.out.println("\nInvalid date.\n");
			}
		}while(!checker);
		
		do {
			checker = false;
			System.out.print("License Number: ");
			String inputString = input.nextLine();
			if(inputString.isBlank()) {
				System.out.println("\nInput License Number\n");
			}
			else {
				this.LicenseNumber = inputString;
				checker = true;
			}
			
		}while(!checker);
		
		do {
			checker = false;
			System.out.print("Date of Driver's License, 1st Issued (YYYY-MM-DD): ");
			try {
				Date date = format.parse(input.nextLine());
				this.DateLicenseIssued = format.format(date);
				checker = true;
			} catch (ParseException e) {
				System.out.println("\nInvalid date.\n");
			}
		}while(!checker);
		
	}

	public String getDateLicenseIssued() {
		return DateLicenseIssued;
	}

	public String getFirstName() {
		return FirstName;
	}

	public String getLastName() {
		return LastName;
	}

	public String getDateofBirth() {
		return DateofBirth;
	}

	public String getAddress() {
		return Address;
	}

	public String getLicenseNumber() {
		return LicenseNumber;
	}
}
