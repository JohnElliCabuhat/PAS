/**
 * Java Course 4
 * 
 * Norima Java Developer Course Capstone Project
 * 
 * Policy Class
 */
package capstone;

import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Calendar;
import java.sql.*;

/**
 * @author John Elli Cabuhat
 * @description Norima Java Developer Course Capstone Project
 * @CreatedDate June 27, 2022
 * @ModifiedDate July 15, 2022
 * @ModifiedBy
 */
public class Policy{

	private String effectiveDate;
	private String expirationDate;
	private int carAge;
	private String licenseAge;
	private double premiumAmount;
	double policyPremium;
	private String claimNo;
	Scanner input = new Scanner(System.in);
	Calendar cal = Calendar.getInstance();
	CustomerAccount acc = new CustomerAccount();
	Vehicle vehicle = new Vehicle();
	PolicyHolder holder = new PolicyHolder();
	RatingEngine rating = new RatingEngine();
	Claim claim = new Claim();
	boolean checker = false;
	Pattern pattern = Pattern.compile("[a-zA-Z\s]");

	//for policy data input
	public void setPolicy() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		format.setLenient(false);
		do {
			System.out.print("Effective Date (YYYY-MM-DD): ");
			try {
				Date date = format.parse(input.nextLine());
				this.effectiveDate = format.format(date);
				cal.setTime(date);
				cal.add(Calendar.MONTH, 6); //to achieve expiration date after 6 months
				Date expDate = cal.getTime();
				this.expirationDate = format.format(expDate);
				System.out.println("Expiration Date: " + expirationDate);
				checker = true;
			} catch (ParseException e) {
				System.out.println("\nInvalid date.\n");
			}
		}while(!checker);
	}
	
	

	//for premium computation
	public void setPremium() {
		int yearNow = Year.now().getValue();
		this.carAge = yearNow - Integer.parseInt(vehicle.getYear()); 
		rating.setVehiclePriceFactor(carAge); //car age passed to determine vpf
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		try {
			date = format.parse(holder.getDateLicenseIssued());
			SimpleDateFormat format2 = new SimpleDateFormat("yyyy");
			this.licenseAge = format2.format(date);
			rating.setDLX(licenseAge);
		} catch (ParseException e) {
			System.out.println(e);
		}
		
		rating.computePremium(Double.parseDouble(vehicle.getPurchasePrice()));
		this.premiumAmount = rating.getPremium();
		System.out.println("Premium Charged: " + rating.getPremium());
	}

	//for policy holder data input
	public void setHolder(String accNo) {
		System.out.println("\nAssigning Policy Holder");
		System.out.println("\nChoose one option:");
		System.out.println("[1] New");
		System.out.println("[2] Account Owner");
		System.out.print("\nYour Choice: ");
		int holderChoice = Integer.parseInt(input.nextLine());
		//if-else for assigning policy holder, 1 for new policy holder, 2 to assign customer as the policy holder
		if(holderChoice == 1) {
			holder.setHolder();
		}
		else {
			Connection con;
			try {
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/capstone_project_db", "root",
						"Readlord123$");
				String sql = "SELECT * FROM customer_account WHERE AccountNo = ?";
				PreparedStatement st = con.prepareStatement(sql);
				st.setString(1, accNo);
				ResultSet rs = st.executeQuery();
				while(rs.next()) {
					String fname = rs.getString("FirstName");
					String lname = rs.getString("LastName");
					String address = rs.getString("Address");
					holder.ownerHolder(fname, lname, address);
				}
			} catch (SQLException e) {
				System.out.println(e);
			}
		}
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/capstone_project_db", "root",
					"Readlord123$");
			String sql = "SELECT MAX(PolicyNo) FROM policy";
			PreparedStatement st8 = con.prepareStatement(sql);
			ResultSet rset1 = st8.executeQuery();
			while (rset1.next()) {
				String polno = rset1.getString("MAX(PolicyNo)");
				String sql2 = "INSERT INTO policyholder(FirstName,LastName,DateOfBirth,Address,DriverLicenseNo,LicenseDateIssued,PolicyNo) "
						+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement st4 = con.prepareStatement(sql2);
				st4.setString(1, holder.getFirstName());
				st4.setString(2, holder.getLastName());
				st4.setString(3, holder.getDateofBirth());
				st4.setString(4, holder.getAddress());
				st4.setString(5, holder.getLicenseNumber());
				st4.setString(6, holder.getDateLicenseIssued());
				st4.setString(7, polno);
				st4.executeUpdate();
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	//for vehicle input
	public void setVehicle() {
		int counter = 0;
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/capstone_project_db", "root",
					"Readlord123$");
			System.out.println("\nFor Vehicle:\n");
			do {
				checker = false;
				System.out.print("\nNumber of Vehicles: ");
				String stringInput = input.nextLine();
				if(stringInput.isBlank()) {
					System.out.println("\nInput number of vehicles\n");
				}
				else if (checkInput(stringInput)) {
					System.out.println("Invalid input");
				}
				else {
					int numVehicles = Integer.parseInt(stringInput);
					checker = true;
				

			//while-loop for number of vehicle input
			while (counter < numVehicles) {
				vehicle.vehicleCheck();
				String matchCar = vehicle.getMake() + vehicle.getModel() + vehicle.getYear();
				String sql2 = "SELECT COUNT(Make) AS match_car FROM vehicle WHERE LOWER(CONCAT(Make,Model,CarYear)) = LOWER(?)";
				PreparedStatement st10 = con.prepareStatement(sql2);
				st10.setString(1, matchCar);
				ResultSet rset3 = st10.executeQuery();
				while (rset3.next()) {
					if (rset3.getInt("match_car") > 0) {
						System.out.println("Car exists in a policy.");
					}
					else {
						vehicle.SetVehicle();
						setPremium();
						rating.totalPremium(getPremiumAmount());
						counter++;
						String sql = "SELECT MAX(PolicyNo) FROM policy";
						PreparedStatement st9 = con.prepareStatement(sql);
						ResultSet rset2 = st9.executeQuery();
						while (rset2.next()) {
							String polno1 = rset2.getString("MAX(PolicyNo)");

							String sql1 = "INSERT INTO vehicle(Make,Model,CarYear,CarType,FuelType,PurchasePrice,Color,PremiumCharged,PolicyNo)"
									+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
							PreparedStatement st3 = con.prepareStatement(sql1);
							st3.setString(1, vehicle.getMake());
							st3.setString(2, vehicle.getModel());
							st3.setInt(3, Integer.parseInt(vehicle.getYear()));
							st3.setString(4, vehicle.getType());
							st3.setString(5, vehicle.getFuelType());
							st3.setDouble(6, Double.parseDouble(vehicle.getPurchasePrice()));
							st3.setString(7, vehicle.getColor());
							st3.setDouble(8, rating.getPremium());
							st3.setString(9, polno1);
							st3.executeUpdate();
							System.out.println("\nVehicle added.");
										
						}
					}

				}

			}
				}
			}while(!checker);
			System.out.println("Total Premium: " + rating.getTotalPremium());
			this.policyPremium = rating.getTotalPremium();
			
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
	
	public void resetTotal() {
		this.policyPremium = rating.resetTotalPremium();
	}

	//for setting up claim, RAND function for generating random claim number with proper format
	public void setClaim(String polNum) {
		claim.setClaim();
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/capstone_project_db", "root",
					"Readlord123$");
			String sql = "INSERT INTO claim(ClaimNo,AccidentDate,AddressofAccident,AccidentDescription,DamageDescription,EstCostofRepair,PolicyNo) "
					+ "VALUES (CONCAT(\"C\", FLOOR(RAND()*(99999-0+1)+0)), ?, ?, ?, ?, ?, ?)";
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, claim.getAccidentDate());
			st.setString(2, claim.getAddressAccident());
			st.setString(3, claim.getAccidentDesc());
			st.setString(4, claim.getDmgDesc());
			st.setDouble(5, claim.getEstCostRepair());
			st.setString(6, polNum);
			st.executeUpdate();
			ResultSet rs = st.executeQuery("select ClaimNo from claim where ClaimNo = (Select last_insert_id())");
			while(rs.next()) {
				this.claimNo = rs.getString("ClaimNo");
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
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

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public String getExpirationDate() {
		return expirationDate;
	}
	public double getPremiumAmount() {
		return premiumAmount;
	}
	public double getPolicyPremium() {
		return policyPremium;
	}
	
	public String getClaimNo() {
		return claimNo;
	}

}
