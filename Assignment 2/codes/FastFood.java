
import java.util.ArrayList;
import java.util.List;

public class FastFood extends FoodPlace {

    private List<Staff> staff = new ArrayList<>();

    public FastFood(String name, double fixedCosts, Owner owner, List<Staff> staff) {
        /* TODO: Add your code here */
        /* TODO: Also edit the super call */
        super(name, fixedCosts, owner);
        owner.setFoodPlace(this);
        for (var p:staff){
            this.staff.add(p);
        }
    }

    public List<Staff> getStaff() {
        return staff;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Name of FastFood: " + this.getName() +
                "\n" + "Owner: " + this.getOwner());
        int index = 1;
        for (Staff staff: staff) {
            builder.append("\n" + "Staff " + index++ + " : " + staff );
        }
        return builder.toString();
    }

    @Override
    public void workShift(int hours) {
        /* TODO: Add your code here */
        double ownerExpense = this.getOwner().getSalaryExpenses();
        for (var p:this.staff){
            double originalIncome = p.getIncome();
            double currentIncome = p.workHours(hours);
            double updateIncome = originalIncome + currentIncome;
            p.setIncome(updateIncome);
            ownerExpense = ownerExpense + currentIncome;
        }
        this.getOwner().setSalaryExpenses(ownerExpense);
    }

    @Override
    public List<IncomeTaxPayer> getIncomeTaxPayers() {
        /* TODO: Add your code here */
        /* TODO: Also remove return statement below*/
        ArrayList<IncomeTaxPayer> taxPayersList = new ArrayList<IncomeTaxPayer>();
        for (var p:this.staff){
            taxPayersList.add(p);
        }
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

        double tip = check.getTip();
        int n = this.staff.size();
        if (tip > 0){
            double tipPerStaff = tip / n;
            for (var p:this.staff){
                double originalIncome = p.getIncome();
                double currentIncome = tipPerStaff;
                double updateIncome = originalIncome + currentIncome;
                p.setIncome(updateIncome);
            }
        }

        double originalSalesTax = this.getTotalSalesTax();
        double currentSalesTax = check.getSalesTax();
        double updateSalesTax = originalSalesTax + currentSalesTax;
        this.setTotalSalesTax(updateSalesTax);
    }

    @Override
    public double getTipPercentage() {
        return 0;
    }
}
