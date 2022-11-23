/**
 * Java Course 4
 * 
 * Norima Java Developer Course Capstone Project
 * 
 * Pas Driver Class
 */
package capstone;
import java.util.Scanner;
import java.sql.*;
/**
 * @author John Elli Cabuhat
 * @description Norima Java Developer Course Capstone Project
 * @CreatedDate June 27, 2022
 * @ModifiedDate July 15, 2022
 * @ModifiedBy
 */
public class PASApp{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			//establishing database connection
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/capstone_project_db", "root", "Readlord123$");
			Scanner input = new Scanner(System.in);
			CustomerAccount acc = new CustomerAccount();
			Policy policy = new Policy();
			databaseCreation createDb = new databaseCreation();
			boolean checker = false;
			
			int choice = 0;
			
			do {
				createDb.createDatabase();
				createDb.createTables();
				//menu display
				System.out.println("\nChoose an option:");
				System.out.println("[1] Create a new Customer Account");
				System.out.println("[2] Get a policy quote and buy the policy");
				System.out.println("[3] Cancel a specific policy");
				System.out.println("[4] File an accident claim against a policy.");
				System.out.println("[5] Search Customer Account");
				System.out.println("[6] Search Specific Policy");
				System.out.println("[7] Search Specific Claim");
				System.out.println("[8] Exit PAS");
				System.out.print("\nYour Choice: ");
				
				//switch case for choosing an option from 1-8
				try {
					choice = Integer.parseInt(input.nextLine());
					switch (choice) {
					
					// for creating customer account
					case 1:
						System.out.println("CUSTOMER ACCOUNT CREATION\n");
						String newAccount = "INSERT INTO customer_account(FirstName,LastName,Address) VALUES (?, ?, ?)";
						PreparedStatement st1 = con.prepareStatement(newAccount, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
						ResultSet rset1 = st1.executeQuery("SELECT * FROM customer_account");
						
							//setting up account data 
							acc.setAccount();
							if (rset1.next() == false) {
								st1.setString(1, acc.getFirstName());
								st1.setString(2, acc.getLastName());
								st1.setString(3, acc.getAddress());
								st1.executeUpdate();
								ResultSet rset = st1.executeQuery("SELECT * FROM customer_account ORDER BY AccountNo DESC LIMIT 1");
								while(rset.next()) {
									int accountNumber = rset.getInt("AccountNo");
									System.out.println("\nNew account has been created successfully.\n");
									System.out.println("Your Account Number is " + accountNumber);
								}
								
								
							}
							else {
								rset1.beforeFirst();
								boolean newAccountEntry = false;
								while (rset1.next()){
									String fullNameDb = rset1.getString("FirstName") + rset1.getString("LastName");
									String fullNameEntry = acc.getFirstName() + acc.getLastName();
									if(fullNameDb.equalsIgnoreCase(fullNameEntry)) {
										System.out.println("\nPerson already has an account. Try Again.\n");
										newAccountEntry = false;
										break;
									}
									else {
										newAccountEntry = true;
									}
								}
								
									if(newAccountEntry) {
										st1.setString(1, acc.getFirstName());
										st1.setString(2, acc.getLastName());
										st1.setString(3, acc.getAddress());
										st1.executeUpdate();
										ResultSet rset = st1.executeQuery("SELECT * FROM customer_account ORDER BY AccountNo DESC LIMIT 1");
										while(rset.next()) {
											int accountNumber = rset.getInt("AccountNo");
											System.out.println("\nNew account has been created successfully.\n");
											System.out.println("Your Account Number is " + accountNumber);
										}
									
									}
								}			
											
						break;
					
					// for creating policy; includes policy holder and vehicles
					case 2:
						System.out.println("POLICY CREATION\n");
						System.out.print("Input your account number: ");
						String accNo = input.nextLine();
						String sql = "SELECT EXISTS (SELECT * FROM customer_account WHERE AccountNo = ?)";
						PreparedStatement st2 = con.prepareStatement(sql);
						st2.setString(1, accNo);
						ResultSet rs = st2.executeQuery();
						while(rs.next()) {
							if (rs.getBoolean(1)) {
								System.out.println("For Policy\n");
								//calling methods in policy for inputting data
								policy.setPolicy();
								String sql3 = "INSERT INTO policy(EffectiveDate,ExpirationDate,PolicyPremium,AccountNo) "
										+ "VALUES (?, ?, ?, ?)";
								PreparedStatement st5 = con.prepareStatement(sql3);
								st5.setString(1, policy.getEffectiveDate());
								st5.setString(2, policy.getExpirationDate());
								st5.setDouble(3, 0.00);
								st5.setString(4, accNo);
								st5.executeUpdate();
								policy.setHolder(accNo);
								policy.setVehicle();
								
								//for display of policy number
								ResultSet rset = st5.executeQuery("SELECT PolicyNo FROM policy ORDER BY PolicyNo DESC LIMIT 1");
								while(rset.next()) {
									int policyNo = rset.getInt("PolicyNo");
									String sql4 = "UPDATE policy SET PolicyPremium = ? WHERE PolicyNo = ?";
									PreparedStatement st6 = con.prepareStatement(sql4);
									st6.setDouble(1, policy.getPolicyPremium());
									st6.setInt(2, policyNo);
									st6.executeUpdate();
									System.out.println("\nPolicy Created Successfully.\n");	
									System.out.println("Your Policy number is " + policyNo);
								}
								policy.resetTotal();
							}
							else {
								System.out.println("\nAccount Number does not exist. Try again.\n");
							}
							
						}
						break;
					
				    //For cancelling policy
					case 3:
						System.out.println("POLICY CANCELLATION\n");
						do {
							checker = false;
							System.out.print("Input a Policy Number: ");
							String stringInput = input.nextLine();
							if(stringInput.isBlank()) {
								System.out.println("\nInput your policy Number\n");
							}
							else if(policy.checkInput(stringInput)) {
								System.out.println("\nInvalid Input\n");
							}
							else {
								String polNumber = stringInput;
								checker = true;
							
						
						// setting expiration date to 1 day after effectove date
						String sql1 = "UPDATE policy SET ExpirationDate = DATE_ADD(CURDATE(), INTERVAL 1 DAY) WHERE PolicyNo = ? ";
						PreparedStatement st3 = con.prepareStatement(sql1);
						st3.setString(1, polNumber);
						st3.executeUpdate();
						System.out.println("\nPolicy Cancelled\n");
							}
							
						}while(!checker);
						break;
					
					//for filing a claim
					case 4:
						System.out.println("FILING A CLAIM\n");
						System.out.print("Input a Policy Number: ");
						String polNum = input.nextLine();
						String sql2 = "SELECT EXISTS (SELECT * FROM policy WHERE PolicyNo = ?)";
						PreparedStatement st4 = con.prepareStatement(sql2);
						st4.setString(1, polNum);
						ResultSet rs1 = st4.executeQuery();
						while(rs1.next()) {
							if(rs1.getBoolean(1)) {
								policy.setClaim(polNum);
								System.out.println("\nClaim filed to Policy Number "+ polNum);
								System.out.println("\nYour Claim Number is: "+ policy.getClaimNo()+ "\n");
							}
							else {
								System.out.println("\nPolicy Number Does Not Exist. Try Again\n");
							}
						}
						break;
					
					//for searching customer account
					case 5:
						System.out.println("CUSTOMER ACCOUNT SEARCH\n");
						System.out.print("\nInput First Name: ");
						String fName = input.nextLine();
						System.out.print("\nInput Last Name: ");
						String lName = input.nextLine();
						String sql3 = "SELECT EXISTS (SELECT * FROM customer_account WHERE FirstName = ? AND LastName = ?)";
						PreparedStatement st5 = con.prepareStatement(sql3);
						st5.setString(1, fName);
						st5.setString(2, lName);
						ResultSet rs2 = st5.executeQuery();
						while(rs2.next()) {
							if (rs2.getBoolean(1)) {
								
								//searching customer account details using firstname and lastname
								String sql4 = "SELECT * FROM customer_account WHERE FirstName = ? AND LastName = ?";
								PreparedStatement st6 = con.prepareStatement(sql4);
								st6.setString(1, fName);
								st6.setString(2, lName);
								ResultSet rs3 = st6.executeQuery();
								while(rs3.next()) {
									System.out.println("\nACCOUNT DETAILS\n");
									System.out.printf("\n%15s %15s %15s %15s\n", "Account Number", "First Name", "Last Name", "Address");
									System.out.printf("%14s %10s %19s %16s\n", rs3.getString("AccountNo"), rs3.getString("FirstName"), 
											rs3.getString("LastName"), rs3.getString("Address"));
									System.out.println("\nPOLICIES OWNED\n");
									
									//to display details of policies held by the customer
									String sql5 = "SELECT * FROM policy INNER JOIN policyholder "
											+ "ON policy.PolicyNo = policyholder.PolicyNo  WHERE AccountNo = ?;";
									PreparedStatement st7 = con.prepareStatement(sql5);
									st7.setString(1, rs3.getString("AccountNo"));
									ResultSet rs4 = st7.executeQuery();
									System.out.printf("\n%15s %15s %17s %15s %15s %9s %18s %21s\n", "Policy Number", "Effective Date", 
											 "Expiration Date","Policy Holder", "Date Of Birth", "Address", 
												"License Number", "License Date Issued");
									while(rs4.next()) {
										System.out.printf("%14s %12s %16s %19s %13s %11s %16s %15s\n", rs4.getString("PolicyNo"), 
												rs4.getString("EffectiveDate"), rs4.getString("ExpirationDate"),
												rs4.getString("FirstName")+" "+rs4.getString("LastName"), 
												rs4.getString("DateOfBirth"),rs4.getString("Address"), 
												rs4.getString("DriverLicenseNo"), rs4.getString("LicenseDateIssued"));
									}
									
								}
							}
							else {
								System.out.println("\nAccount Number does not exist. Try Again\n");
							}
						}
						break;
					
					//for policy search
					case 6:
						System.out.println("POLICY SEARCH\n");
						System.out.print("Input a Policy Number: ");
						String polNum1 = input.nextLine();
						String sql4 = "SELECT EXISTS (SELECT * FROM policy WHERE PolicyNo = ?)";
						PreparedStatement st6 = con.prepareStatement(sql4);
						st6.setString(1, polNum1);
						ResultSet rs3 = st6.executeQuery();
						while(rs3.next()) {
							if (rs3.getBoolean(1)) {
								
								//to display policy holder assigned to the policy
								String sql5 = "SELECT * FROM policy INNER JOIN policyholder "
										+ "ON policy.PolicyNo = policyholder.PolicyNo  WHERE policy.PolicyNo = ?";
								PreparedStatement st7 = con.prepareStatement(sql5);
								st7.setString(1, polNum1);
								ResultSet rs4 = st7.executeQuery();
								System.out.printf("\n%15s %15s %17s %15s %15s %9s %18s %21s\n", "Policy Number", "Effective Date", 
										"Expiration Date","Policy Holder", "Date Of Birth", "Address", 
										"License Number", "License Date Issued");
								while(rs4.next()) {
									System.out.printf("%15s %12s %16s %19s %13s %11s %16s %15s\n", rs4.getString("PolicyNo"), 
											rs4.getString("EffectiveDate"), rs4.getString("ExpirationDate"),
											rs4.getString("FirstName")+" "+rs4.getString("LastName"), 
											rs4.getString("DateOfBirth"),rs4.getString("Address"), 
											rs4.getString("DriverLicenseNo"), rs4.getString("LicenseDateIssued"));
									
									//to display all vehicles assigned to the policy 
									String sql6 = "SELECT * FROM vehicle WHERE PolicyNo = ?";
									PreparedStatement st8 = con.prepareStatement(sql6);
									st8.setString(1, polNum1);
									ResultSet rs5 = st8.executeQuery();
									System.out.println("\nList of Vehicles");
									System.out.printf("\n%15s %10s %17s %15s %15s %15s %18s %13s %21s\n", "Policy Number", "Make", "Model","Year", "Type",
											"Fuel Type", "Purchase Price", "Color", "Premium Charged");
									while(rs5.next()) {
										System.out.printf("%15s %11s %18s %13s %15s %13s %15s %19s %15s\n", rs4.getString("PolicyNo"),rs5.getString("Make"),
												rs5.getString("Model"), rs5.getString("CarYear"), rs5.getString("CarType"), rs5.getString("FuelType"), 
												rs5.getString("PurchasePrice"), rs5.getString("Color"), rs5.getString("PremiumCharged"));
									}
								}
							}
							else {
								System.out.println("\nPolicy Number does not exist. Try Again\n");
							}
						}
						break;
					
					// for claim search
					case 7:
						System.out.println("CLAIM SEARCH\n");
						System.out.print("Input a Claim Number: ");
						String claim = input.nextLine();
						String sql5 = "SELECT EXISTS (SELECT * FROM claim WHERE ClaimNo = ?)";
						PreparedStatement st7 = con.prepareStatement(sql5);
						st7.setString(1, claim);
						ResultSet rs4 = st7.executeQuery();
						while(rs4.next()) {
							if (rs4.getBoolean(1)) {
								String sql6 = "SELECT * FROM claim WHERE ClaimNo = ?";
								PreparedStatement st8 = con.prepareStatement(sql6);
								st8.setString(1, claim);
								ResultSet rs5 = st8.executeQuery();
								while(rs5.next()) {
									System.out.printf("\n%15s %20s %23s %25s %20s %22s %18s\n",  "Claim Number", "Date of Accident", 
											"Address of Accident", "Accident Description",
											"Damage Description", "Est. Cost of Repair", "Policy Number");
									System.out.printf("%15s %20s %23s %25s %20s %22s %18s\n", rs5.getString("ClaimNo"), rs5.getString("AccidentDate"), 
											rs5.getString("AddressofAccident"), rs5.getString("AccidentDescription"), rs5.getString("DamageDescription"),
											rs5.getString("EstCostofRepair"), rs5.getString("PolicyNo"));									
								}
							}
							else {
								System.out.println("\nClaim Number does not exist. Try Again\n");
							}
						}
						break;
					
					//for closing the app
					case 8:
						System.out.println("PAS will now close.");
						input.close();
						break;
						
					default:
						System.out.println("\nInvalid input. try again\n");
						break;
					}
					
				}
				catch(Exception e) {
					System.out.println("Invalid input. Try again.");
				}
				
			}while (choice != 8 ); //do-while loop for executing program until input = 8
			
		}
		catch(Exception e) {
			System.out.println("Error.Please Try Again.");
		}
		
		

	}

}
