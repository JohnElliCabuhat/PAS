/**
 * Java Course 4
 * 
 * Norima Java Developer Course Capstone Project
 * 
 * Claim Class
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
public class Claim {

	private String accidentDate;
	private String addressAccident;
	private String accidentDesc;
	private String dmgDesc;
	private double EstCostRepair;
	Scanner input = new Scanner(System.in);
	Calendar cal = Calendar.getInstance();
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	Pattern pattern = Pattern.compile("[a-zA-Z\s]");
	boolean checker = false;
	
	//to set-up claim data ,do while loops for validation purposes
	public void setClaim() {
		do {
			System.out.print("Date of Accident(YYYY-MM-DD): ");
			try {
				Date date = format.parse(input.nextLine());
				this.accidentDate = format.format(date);
				checker = true;
			} catch (ParseException e) {
				System.out.println("\nInvalid date.\n");
			}
		}while(!checker);
		
		do {
			checker = false;
			System.out.print("Address where accident happened: ");
			String inputString = input.nextLine();
			if(inputString.isBlank()) {
				System.out.println("\nInput the address\n");
			}
			else {
				this.addressAccident = inputString;
				checker = true;
			}
		}while(!checker);
		
		do {
			checker = false;
			System.out.print("Description of Accident: ");
			String inputString = input.nextLine();
			if(inputString.isBlank()) {
				System.out.println("\nInput Description\n");
			}
			else {
				this.accidentDesc = inputString;
				checker = true;
			}
		}while(!checker);
		
		do {
			checker = false;
			System.out.print("Description of damage to vehicle: ");
			String inputString = input.nextLine();
			if(inputString.isBlank()) {
				System.out.println("\nInput Description\n");
			}
			else {
				this.dmgDesc = inputString;
				checker = true;
			}
		}while(!checker);
		
		do {
			checker = false;
			System.out.print("Estimated cost of repairs: ");
			String inputString = input.nextLine();
			if(inputString.isBlank()) {
				System.out.println("\nInput Estimate Cost.\n");
			}
			else if(checkInput(inputString)) {
				System.out.println("\nInvalid input.\n");
			}
			else {
				this.EstCostRepair = Double.parseDouble(inputString);
				checker = true;
			}
		}while(!checker);
	}
	
	//to check string input
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

	public String getAccidentDate() {
		return accidentDate;
	}

	public String getAddressAccident() {
		return addressAccident;
	}

	public String getAccidentDesc() {
		return accidentDesc;
	}

	public String getDmgDesc() {
		return dmgDesc;
	}

	public double getEstCostRepair() {
		return EstCostRepair;
	}
	
	
	
}
