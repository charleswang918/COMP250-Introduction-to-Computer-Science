
import java.util.ArrayList;
import java.util.List;

public class TaxCollector {

	private List<FoodPlace> foodPlaces = new ArrayList<>();

	private double incomeTaxCollected;
	private double salesTaxCollected;

	public TaxCollector(List<FoodPlace> foodPlaces) {
		/* TODO: Add your code here */
		this.foodPlaces = foodPlaces;
	}

	public List<FoodPlace> getFoodPlaces() {
		return foodPlaces;
	}

	public double getIncomeTaxCollected() {
		return incomeTaxCollected;
	}

	public double getSalesTaxCollected() {
		return salesTaxCollected;
	}

	public void collectTax() {
		/* TODO: Add your code here */
		double salesTax = 0;
		for (var e:this.foodPlaces){
			salesTax = salesTax + e.getTotalSalesTax();
		}
		this.salesTaxCollected = salesTax;

		double incomeTax = 0;
		for (var e:this.foodPlaces){
			List<IncomeTaxPayer> incomeTaxPayerList = e.getIncomeTaxPayers();
			for (var p:incomeTaxPayerList){
				incomeTax = incomeTax + p.calculateIncomeTax();
			}
		}
		this.incomeTaxCollected = incomeTax;
	}
	
	public String toString() {
		return "TaxCollector: income tax collected: " + incomeTaxCollected + ", sales tax collected: " + salesTaxCollected;
	}
	
}
