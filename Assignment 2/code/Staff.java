
public class Staff extends IncomeTaxPayer {

	private int salaryPerHour;
	final private int incomeTaxPercentage = 25;

	public Staff(String name, boolean isCook) {
		/* TODO: Add your code here */
		/* TODO: Also edit the super call */
		super(name);
		if (isCook==true){
			this.salaryPerHour = 20;
		}
		else {
			this.salaryPerHour = 10;
		}
	}

	public int getSalaryPerHour() {
		return salaryPerHour;
	}

	public int getIncomeTaxPercentage() {
		return incomeTaxPercentage;
	}

	public double workHours(int numHours) {
		/* TODO: Add your code here */
		/* TODO: Also remove return statement below*/
		double originalIncome = this.getIncome();
		double salary = this.getSalaryPerHour() * numHours;
		double currentIncome = originalIncome + salary;
		this.setIncome(currentIncome);
		return salary;
	}

	@Override
	public double calculateIncomeTax() {
		/* TODO: Add your code here */
		/* TODO: Also remove return statement below*/
		double tax = this.getIncome() * 0.01 * this.incomeTaxPercentage;
		return tax;
	}

}
