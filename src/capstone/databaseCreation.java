/**
 * Java Course 4
 * 
 * Norima Java Developer Course Capstone Project
 * 
 * Claim Class
 */
package capstone;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * @author John Elli Cabuhat
 * @description Norima Java Developer Course Capstone Project
 * @CreatedDate September 16, 2022
 * @ModifiedDate 
 * @ModifiedBy
 */
public class databaseCreation {

	
	public void createDatabase() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/capstone_project_db", "root", "Readlord123$");
			Statement st = con.createStatement();
			String sql = "CREATE database if not exists capstone_project_db";
			st.executeUpdate(sql);
		}
		catch(Exception e) {
			System.out.println("Error. Please Try Again.");
		}

	}
	
	public void createTables() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/capstone_project_db", "root", "Readlord123$");
			Statement st = con.createStatement();
			String sql = "Create table if not exists customer_account"
					+ "("
					+ "AccountNo int(4) unsigned zerofill primary key auto_increment,"
					+ "FirstName varchar(50) not null,"
					+ "LastName varchar(50) not null,"
					+ "Address varchar(70) not null"
					+ ")";
			st.executeUpdate(sql);
			
			String sql2 = "alter table customer_account auto_increment = 0001";
			st.executeUpdate(sql2);
			
			String sql4 = "Create table if not exists policy"
					+ "("
					+ "PolicyNo int(6) auto_increment primary key not null,"
					+ "EffectiveDate date not null,"
					+ "ExpirationDate date not null,"
					+ "PolicyPremium decimal(15,2) not null,"
					+ "AccountNo int(4) unsigned zerofill not null"
					+ ")";
			st.executeUpdate(sql4);
			
			String sql5 = "alter table policy auto_increment = 000001";
			st.executeUpdate(sql5);
			
			String sql6 = "Create table if not exists PolicyHolder"
					+ "("
					+ "PolicyHolderNo int auto_increment primary key not null,"
					+ "FirstName varchar(50) not null,"
					+ "LastName varchar(50) not null,"
					+ "DateOfBirth date not null,"
					+ "Address varchar(70) not null,"
					+ "DriverLicenseNo varchar(20) not null,"
					+ "LicenseDateIssued date not null,"
					+ "PolicyNo int(6) unsigned zerofill not null"
					+ ")";
			st.executeUpdate(sql6);
			
			String sql7 = "Create table if not exists Vehicle"
					+ "("
					+ "VehicleNo int auto_increment primary key not null,"
					+ "Make varchar(20) not null,"
					+ "Model varchar(20) not null,"
					+ "CarYear int not null,"
					+ "CarType varchar(30) not null,"
					+ "FuelType varchar(20) not null,"
					+ "PurchasePrice decimal(15,2) not null,"
					+ "Color varchar(10) not null,"
					+ "PremiumCharged decimal(15,2) not null,"
					+ "PolicyNo int(6) unsigned zerofill not null"
					+ ")";
			st.executeUpdate(sql7);
			
			String sql8 = "Create table if not exists Claim"
					+ "("
					+ "ClaimNo varchar(6) not null primary key,"
					+ "AccidentDate date not null,"
					+ "AddressofAccident varchar(70) not null,"
					+ "AccidentDescription varchar(100) not null,"
					+ "DamageDescription varchar(100) not null,"
					+ "EstCostofRepair decimal(15,2) not null,"
					+ "PolicyNo int(6) unsigned zerofill not null"
					+ ")";
			st.executeUpdate(sql8);
			
//			String sql11 = "alter table policy"
//					+ "add foreign key (AccountNo) references customer_account(AccountNo)";
//			st.executeUpdate(sql11);
//			
//			String sql12 = "alter table vehicle,"
//					+ "add foreign key (PolicyNo) references policy(PolicyNo)";
//			st.executeUpdate(sql12);
//			
//			String sql13 = "alter table policyholder,"
//					+ "add foreign key (PolicyNo) references policy(PolicyNo)";
//			st.executeUpdate(sql13);
			
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}

}
