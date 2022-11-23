/**
 * Java Course 4
 * 
 * Norima Java Developer Course Capstone Project
 * 
 * Vehicle Class
 */
package capstone;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author John Elli Cabuhat
 * @description Norima Java Developer Course Capstone Project
 * @CreatedDate June 27, 2022
 * @ModifiedDate July 15, 2022
 * @ModifiedBy
 */
public class Vehicle{

	private String make;
	private String model;
	private String year;
	private String type;
	private String fuelType;
	private String purchasePrice;
	private String color;
	Scanner input = new Scanner(System.in);
	Pattern pattern = Pattern.compile("[a-zA-Z\s]");
	Pattern pattern2 = Pattern.compile("[0-9\s]");
	boolean checker = false;
	
	//setting up vehicle data 
	public void SetVehicle() {
		
		do {
			System.out.print("Type (1)4-door Sedan,(2)2-door Sports Car,(3)SUV,(4)Truck: ");
			try {
				int typeInput = Integer.parseInt(input.nextLine());
				//switch case for vehicle type choices
				switch (typeInput) {
				case 1:
					this.type = "4-door Sedan";
					checker = true;
					break;
				case 2:
					this.type = "2-door Sports Car";
					checker = true;
					break;
				case 3:
					this.type = "SUV";
					checker = true;
					break;
				case 4:
					this.type = "Truck";
					checker = true;
					break;
				default:
					System.out.println("\nInvalid input. Try Again.\n");
				}
			}catch(Exception e) {
				System.out.println("\nInvalid Input. Try Again.\n");
			}
		}while(!checker);
		
		
		do {
			checker = false;
			System.out.print("FuelType (1)Diesel,(2)Electric,(3)Petrol: ");
			try {
				int fuelInput = Integer.parseInt(input.nextLine());
				//switch case for fuel type choices
				switch (fuelInput) {
				case 1:
					this.fuelType = "Diesel";
					checker = true;
					break;
				case 2:
					this.fuelType = "Electric";
					checker = true;
					break;
				case 3:
					this.fuelType = "Petrol";
					checker = true;
					break;
				default:
					System.out.println("Invalid input. Try Again.");
				}
			}catch(Exception e) {
				System.out.println("Invalid Input. Try Again.");
			}
		}while(!checker);
		
		do {
			checker = false;
			System.out.print("Purchase Price: ");
			String stringInput = input.nextLine();
			if(stringInput.isBlank()) {
				System.out.println("\nInput the price\n");
			}
			else if(checkInput(stringInput)) {
				System.out.println("\nInvalid input\n");
			}
			else {
				this.purchasePrice = stringInput;
				checker = true;
			}
		}while(!checker);
		
		do {
			checker = false;
			System.out.print("Color: ");
			String stringInput = input.nextLine();
			if(stringInput.isBlank()) {
				System.out.println("\nInput color\n");
			}
			else if(checkInput2(stringInput)) {
				System.out.println("\nInvalid input\n");
			}
			else {
				this.color = stringInput;
				checker = true;
			}
		}while(!checker);
		
	}
	
	// These data is for checking if a car already exists
	public void vehicleCheck() {
		
		do {
			checker = false;
			System.out.print("Make: ");
			String stringInput = input.nextLine();
			if(stringInput.isBlank()) {
				System.out.println("\nInput make\n");
			}
			else if(checkInput2(stringInput)) {
				System.out.println("\nInvalid input\n");
			}
			else {
				this.make = stringInput;
				checker = true;
			}
		}while(!checker);
		
		do {
			checker = false;
			System.out.print("Model: ");
			String stringInput = input.nextLine();
			if(stringInput.isBlank()) {
				System.out.println("\nInput model\n");
			}
			else if(checkInput2(stringInput)) {
				System.out.println("\nInvalid input\n");
			}
			else {
				this.model = stringInput;
				checker = true;
			}
		}while(!checker);
		
		do {
			checker = false;
			System.out.print("Year: ");
			String stringInput = input.nextLine();
			if(stringInput.isBlank()) {
				System.out.println("\nInput year\n");
			}
			else if(checkInput(stringInput)) {
				System.out.println("\nInvalid input\n");
			}
			else {
				this.year = stringInput;
				checker = true;
			}
		}while(!checker);
		
	}
	
	//for checking if there are letters
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
	
	//for checking if there are numbers
	public boolean checkInput2(String input) {
		Matcher matcher = pattern2.matcher(input);
		boolean matchFound = matcher.find();
		if(matchFound) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public String getYear() {
		return year;
	}

	public String getMake() {
		return make;
	}

	public String getModel() {
		return model;
	}

	public String getType() {
		return type;
	}

	public String getFuelType() {
		return fuelType;
	}

	public String getPurchasePrice() {
		return purchasePrice;
	}

	public String getColor() {
		return color;
	}
	
	
	


	
}
