
public abstract class IncomeTaxPayer {

	private static int currentMaxTaxID;
	private int  taxID;
	private String  name;
	private double  income;

	public IncomeTaxPayer(String name){
		/* TODO: Add your code here */
		this.name = name;
		this.taxID = currentMaxTaxID;
		currentMaxTaxID++;
	}

	public static int getCurrentMaxTaxID() {
		return currentMaxTaxID;
	}

	public int getTaxID() {
		return taxID;
	}

	public String getName() {
		return name;
	}

	public double getIncome() {
		return this.income;
	}

	public void setIncome( double income) {
		this.income = income;
	}

	public String toString() {
		return "  " + taxID + " " + name + " income " + income ;
	}

	public boolean equals(Object obj) {
		/* TODO: Add your code here */
		/* TODO: Also remove return statement below*/
		return this == obj;
	}

	public abstract double calculateIncomeTax();
}
