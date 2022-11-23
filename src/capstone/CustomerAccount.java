/**
 * Java Course 4
 * 
 * Norima Java Developer Course Capstone Project
 * 
 * CustomerAccount class
 */
package capstone;
import java.util.Scanner;
import java.util.regex.*;
/**
 * @author John Elli Cabuhat
 * @description Norima Java Developer Course Capstone Project
 * @CreatedDate June 27, 2022
 * @ModifiedDate 
 * @ModifiedBy
 */
public class CustomerAccount{

	private String firstName;
	private String LastName;
	private String Address;
	Scanner input = new Scanner(System.in);
	Pattern pattern = Pattern.compile("[0-9\s]");
	boolean checker = false;
	
	//Setting up data for customer account
	public void setAccount() {
		
		do {
			System.out.print("First Name: ");
			String stringInput = input.nextLine();
			if(stringInput.isBlank()) {
				System.out.println("\nInput your First Name\n");
			}
			else if(checkInput(stringInput)) {
				System.out.println("\nInvalid Input.\n");
			}
			else {
				this.firstName = stringInput;
				checker = true;
			}		
		}while(!checker);
		
		do {
			checker = false;
			System.out.print("Last Name: ");
			String stringInput = input.nextLine();
			if(stringInput.isBlank()) {
				System.out.println("\nInput your Last Name\n");
			}
			else if(checkInput(stringInput)) {
				System.out.println("\nInvalid Input.\n");
			}
			else {
				this.LastName = stringInput;
				checker = true;
			}		
		}while(!checker);
		
		do {
			checker = false;
			System.out.print("Address: ");
			String stringInput = input.nextLine();
			if(stringInput.isBlank()) {
				System.out.println("\nInput your Address\n");
			}
			else if(checkInput(stringInput)) {
				System.out.println("\nInvalid Input.\n");
			}
			else {
				this.Address = stringInput;
				checker = true;
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
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return LastName;
	}
	
	public String getAddress() {
		return Address;
	}
	
	
	
}
