/**
 * Java Course 4
 * 
 * Norima Java Developer Course Capstone Project
 * 
 * Rating Engine Class
 */
package capstone;
import java.util.Calendar;
/**
 * @author John Elli Cabuhat
 * @description Norima Java Developer Course Capstone Project
 * @CreatedDate June 27, 2022
 * @ModifiedDate 
 * @ModifiedBy
 */
public class RatingEngine {

	private double p;
	private double vp;
	private double vpf;
	private int dlx;
	private double totalPremium;
	
	Calendar cal = Calendar.getInstance();
	
	// to set vehicle price factor
	public void setVehiclePriceFactor(int carAge) {
		
		if(carAge < 1) {
			this.vpf = 0.01;
		}
		else if(carAge >= 1 && carAge < 3) {
			this.vpf = 0.008;
		}
		else if(carAge >= 3 && carAge < 5) {
			this.vpf = 0.007;
		}
		else if(carAge >= 5 && carAge < 10) {
			this.vpf = 0.006;
		}
		else if(carAge >= 10 && carAge < 15) {
			this.vpf = 0.004;
		}
		else if(carAge >= 15 && carAge < 20) {
			this.vpf = 0.002;
		}
		else if(carAge >= 20 && carAge < 40) {
			this.vpf = 0.001;
		}
		else {
			this.vpf = 0;
		}
	}
	
	//setting up number of years since driver license was first issued 
	public void setDLX(String licenseAge) {
			this.dlx = cal.get(Calendar.YEAR)- Integer.parseInt(licenseAge);
	}
	
	//premium computation
	public void computePremium(double PurchasePrice) {
		this.vp = PurchasePrice;
		this.p = (vp*vpf) + ((vp/100)/dlx);
	}
	
	public double getPremium() {
		return p;
	}
	
	//to get the total premium
	public void totalPremium(double premium) {
		this.totalPremium += premium;
	}
	
	public double getTotalPremium() {
		return totalPremium;
	}
	
	//to reset total premium to 0
	public double resetTotalPremium(){
		this.totalPremium -= totalPremium;
		return totalPremium;
	}
}
