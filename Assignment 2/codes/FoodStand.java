
import java.util.ArrayList;
import java.util.List;

public class FoodStand extends FoodPlace {

    public FoodStand(String name, double fixedCosts, WorkingOwner owner) {
        /* TODO: Add your code here */
        /* TODO: Also edit the super call */
        super(name, fixedCosts, owner);
        owner.setFoodPlace(this);
    }

    @Override
    public String toString() {
        return "Name of FoodStand: " + this.getName() +
                "\n" + "Owner: " + this.getOwner();
    }

    @Override
    public void workShift(int hours) {
        // no salaried workers so do nothing
    }

    @Override
    public List<IncomeTaxPayer> getIncomeTaxPayers() {
        /* TODO: Add your code here */
        /* TODO: Also remove return statement below*/
        ArrayList<IncomeTaxPayer> taxPayerList = new ArrayList<IncomeTaxPayer>();
        taxPayerList.add(this.getOwner());
        return taxPayerList;

    }

    @Override
    public void distributeIncomeAndSalesTax(Check check) {
        /* TODO: Add your code here */
        double ownerOriginalIncome = this.getOwner().getIncome();
        double ownerCurrentIncome = check.getMenuPrice() + check.getTip();
        double ownerUpdateIncome = ownerOriginalIncome + ownerCurrentIncome;
        this.getOwner().setIncome(ownerUpdateIncome);

        double originalSalesTax = this.getTotalSalesTax();
        double currentSalesTax = check.getSalesTax();
        double updateSalesTax = originalSalesTax + currentSalesTax;
        this.setTotalSalesTax(updateSalesTax);
    }

    @Override
    public double getTipPercentage() {
        /* TODO: Add your code here */
        /* TODO: Also remove return statement below*/
        double targetTipPct = ((WorkingOwner)this.getOwner()).getTargetTipPct();
        return targetTipPct;
    }
}
