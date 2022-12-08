
public class Owner extends IncomeTaxPayer {

	final private int incomeTaxPct = 10;
	private double salaryExpenses;

	private FoodPlace foodPlace;

	public Owner(String name) {
		/* TODO: Add your code here */
		/* TODO: Also edit the super call */
		super(name);
	}

	public FoodPlace getFoodPlace(){
		return foodPlace;
	}

	public int getIncomeTaxPct() {
		return incomeTaxPct;
	}

	public double getSalaryExpenses() {
		return salaryExpenses;
	}

	public void setSalaryExpenses(double salaryExpenses) {
		this.salaryExpenses = salaryExpenses;
	}

	public void setFoodPlace(FoodPlace foodPlace) {
		this.foodPlace = foodPlace;
	}

	@Override
	public double calculateIncomeTax() {
		/* TODO: Add your code here */
		/* TODO: Also remove return statement below*/
		double income = this.getIncome();
		double expense = this.getSalaryExpenses() + this.getFoodPlace().getFixedCosts();
		double tax;
		if (income - expense >= 0){
			tax = (income - expense) * 0.1;
		} else {
			tax = 0;
		}
		return tax;
	}
}
