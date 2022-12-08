
public class Check {
	private double menuPrice;
	private double salesTax;
	private double tip;

	public Check(double menuPrice) {
		/* TODO: Add your code here */
		this.menuPrice = menuPrice;
		this.salesTax = menuPrice * 0.15;
	}

	public double getMenuPrice() {
		return this.menuPrice;
	}

	public double getSalesTax() {
		return this.salesTax;
	}

	public double getTip() {
		return this.tip;
	}

	public void setTipByPct(double tipPct ) {
		/* TODO: Add your code here */
		if (tipPct >= 0 && tipPct <= 100){
			this.tip = tipPct * 0.01 * this.menuPrice;
		}
	}
}
