
import java.util.ArrayList;
import java.util.List;

public class Restaurant extends FoodPlace {
	private Staff cook;
	private Server server;

	public Restaurant(String name, double fixedCosts, Owner owner, Staff cook, Server server) {
		/* TODO: Add your code here */
		/* TODO: Also edit the super call */
		super(name, fixedCosts, owner);
		this.cook = cook;
		this.server = server;
		owner.setFoodPlace(this);
	}

	public Staff getCook() {
		return cook;
	}

	public Server getServer() {
		return server;
	}

	@Override
	public String toString() {
		return "Name of restaurant: " + this.getName() +
				"\n" + "Owner: " + this.getOwner() +
				"\n" + "Cook: " + cook +
				"\n" + "Server: " + server;
	}

	@Override
	public void workShift(int hours) {
		/* TODO: Add your code here */
		double cookOriginalIncome = this.cook.getIncome();
		double cookCurrentIncome = this.cook.workHours(hours);
		double cookUpdateIncome = cookOriginalIncome + cookCurrentIncome;
		this.cook.setIncome(cookUpdateIncome);

		double serverOriginalIncome = this.server.getIncome();
		double serverCurrentIncome = this.server.workHours(hours);
		double serverUpdateIncome = serverOriginalIncome + serverCurrentIncome;
		this.server.setIncome(serverUpdateIncome);

		double ownerOriginalExpense = this.getOwner().getSalaryExpenses();
		double ownerCurrentExpense = cookCurrentIncome + serverCurrentIncome;
		double ownerUpdateExpense = ownerOriginalExpense + ownerCurrentExpense;
		this.getOwner().setSalaryExpenses(ownerUpdateExpense);
	}

	@Override
	public List<IncomeTaxPayer> getIncomeTaxPayers() {
		/* TODO: Add your code here */
		/* TODO: Also remove return statement below*/
		ArrayList<IncomeTaxPayer> taxPayersList = new ArrayList<IncomeTaxPayer>();
		taxPayersList.add(this.cook);
		taxPayersList.add(this.server);
		taxPayersList.add(this.getOwner());
		return taxPayersList;
	}

	@Override
	public void distributeIncomeAndSalesTax(Check check) {
		/* TODO: Add your code here */
		double ownerOriginalIncome = this.getOwner().getIncome();
		double ownerCurrentIncome = check.getMenuPrice();
		double ownerUpdateIncome = ownerOriginalIncome + ownerCurrentIncome;
		this.getOwner().setIncome(ownerUpdateIncome);

		double cookOriginalIncome = this.cook.getIncome();
		double cookCurrentIncome = check.getTip() * 0.2;
		double cookUpdateIncome = cookOriginalIncome + cookCurrentIncome;
		this.cook.setIncome(cookUpdateIncome);

		double serverOriginalIncome = this.server.getIncome();
		double serverCurrentIncome = check.getTip() * 0.8;
		double serverUpdateIncome = serverOriginalIncome + serverCurrentIncome;
		this.server.setIncome(serverUpdateIncome);

		double originalSalesTax = this.getTotalSalesTax();
		double currentSalesTax = check.getSalesTax();
		double updateSalesTax = originalSalesTax + currentSalesTax;
		this.setTotalSalesTax(updateSalesTax);
	}

	@Override
	public double getTipPercentage() {
		/* TODO: Add your code here */
		/* TODO: Also remove return statement below*/
		return server.getTargetTipPct();
	}

}
