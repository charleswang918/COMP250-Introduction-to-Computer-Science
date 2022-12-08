
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FoodPlaceCopy extends FoodPlace {
    private String flag;
    public FoodPlaceCopy(String name, double fixedCosts, Owner owner, String flag){
        super(name,fixedCosts,owner);
        this.flag = flag;
    }
    public void workShift(int hours){

    };

    public List<IncomeTaxPayer> getIncomeTaxPayers(){
        return null;
    }

    public void distributeIncomeAndSalesTax(Check check){

    }

    public double getTipPercentage(){
        return 0.0;
    };
}

class IncomeTaxPlayerCopy extends IncomeTaxPayer{
    public IncomeTaxPlayerCopy(String name){
        super(name);
    };

    public  double calculateIncomeTax(){
        return 0.0;
    };
}



@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestMain {


    @Test
    @Tag("score:4")
    @Order(1)
    @DisplayName("Restaurant Constructor Test")
    void restaurantConstructor(){
        assertEquals("Mama Africa", MamaAfrica.getName());
        assertEquals(1200, MamaAfrica.getFixedCosts());
        assertEquals(Tina, MamaAfrica.getOwner());
        assertEquals(Sade, MamaAfrica.getCook());
        assertEquals(Nia, MamaAfrica.getServer()); 
    }

    @Test
    @Tag("score:2")
    @Order(2)
    @DisplayName("Restaurant workShift Test1")
    void workShiftTest1() {
        MamaAfrica.workShift(8);
        assertEquals(160, Tina.getSalaryExpenses());

    }

    @Test
    @Tag("score:2")
    @Order(3)
    @DisplayName("Restaurant workShift Test2")
    void workShiftTest2(){
        MamaAfrica.workShift(8);
        assertEquals(80, Sade.getIncome());
        assertEquals(80, Nia.getIncome());
    }

    @Test
    @Tag("score:4")
    @Order(4)
    @DisplayName("Restaurant getIncomeTaxPayers Test1")
    void getIncomeTaxPayersTest() {
        List<IncomeTaxPayer> payers = List.of(Sade, Tina, Nia);
        boolean result = new HashSet<>(payers).equals(new HashSet<>(MamaAfrica.getIncomeTaxPayers()));
        assertEquals(true, result);
    }

    @Test
    @Tag("score:1")
    @Order(5)
    @DisplayName("Restaurant distributeIncomeAndSalesTax Test1")
    void distributeIncomeAndSalesTaxTest1() {
        Check check = new Check(180.0);
        check.setTipByPct(20);
        MamaAfrica.distributeIncomeAndSalesTax(check);
        double ownerIncome = MamaAfrica.getOwner().getIncome();
//        double serverIncome = MamaAfrica.getServer().getIncome();
//        double cookIncome = MamaAfrica.getCook().getIncome();
//        double salesTax = MamaAfrica.getTotalSalesTax();
        assertEquals(180, ownerIncome);
//        assertEquals(28.8,serverIncome);
//        assertEquals(7.2, cookIncome);
//        assertEquals(27.0, salesTax);
    }
    @Test
    @Tag("score:1")
    @Order(6)
    @DisplayName("Restaurant distributeIncomeAndSalesTax Test2")
    void distributeIncomeAndSalesTaxTest2() {
        Check check = new Check(180.0);
        check.setTipByPct(20);
        MamaAfrica.distributeIncomeAndSalesTax(check);
        double serverIncome = MamaAfrica.getServer().getIncome();
        assertEquals(28.8,serverIncome);
    }

    @Test
    @Tag("score:1")
    @Order(7)
    @DisplayName("Restaurant distributeIncomeAndSalesTax Test3")
    void distributeIncomeAndSalesTaxTest3() {
        Check check = new Check(180.0);
        check.setTipByPct(20);
        MamaAfrica.distributeIncomeAndSalesTax(check);
        double cookIncome = MamaAfrica.getCook().getIncome();
        assertEquals(7.2, cookIncome);
    }

    @Test
    @Tag("score:1")
    @Order(8)
    @DisplayName("Restaurant distributeIncomeAndSalesTax Test4")
    void distributeIncomeAndSalesTaxTest4() {
        Check check = new Check(180.0);
        check.setTipByPct(20);
        MamaAfrica.distributeIncomeAndSalesTax(check);
        double salesTax = MamaAfrica.getTotalSalesTax();
        assertEquals(27.0, salesTax);
    }
    
    @Test
    @Tag("score:2")
    @Order(13)
    @DisplayName("Restaurant getTipPercentage Test")
    void getTipPercentageTest() {
        int serverTip = MamaAfrica.getServer().getTargetTipPct();
        assertEquals(20, serverTip);
    }

    ////ok

    @Test
    @Tag("hidden")
    @Tag("score:2")
    @Order(14)
    @DisplayName("FoodStand Constructor Test1")
    void foodStandCon(){
        WorkingOwner tom = new WorkingOwner("Tom", 10);
        FoodStand fs = new FoodStand("FoodStand", 100.0 , tom);
        FoodStand fs1 = new FoodStand("FoodStand2", 100.0 , tom);
        assertEquals("FoodStand", fs.getName());
        assertEquals(100.0, fs.getFixedCosts());
        assertEquals(1,(fs1.getFoodPlaceID() - fs.getFoodPlaceID()));
    }


    @Test
    @Tag("score:2")
    @Order(15)
    @DisplayName("FoodStand getIncomeTaxPayers Test1")
    void foodstandgetIncomeTaxPayers() {
        WorkingOwner tom = new WorkingOwner("Tom", 10);
        FoodStand fs = new FoodStand("FoodStand", 100.0 , tom);
        List<IncomeTaxPayer> owners = fs.getIncomeTaxPayers();
        assertEquals(1, owners.size()); //contains only one argument
        assertEquals(tom, owners.get(0));
    }

    @Test
    @Tag("score:2")
    @Order(16)
    @DisplayName("FoodStand distributeIncomeAndSalesTax Test1")
    void foodstanddistributeIncomeAndSalesTax1() {
        WorkingOwner tom = new WorkingOwner("Tom", 10);
        FoodStand fs = new FoodStand("FoodStand", 100.0 , tom);
        Check check = new Check(180.0);
        check.setTipByPct(20);
        fs.distributeIncomeAndSalesTax(check);
        assertEquals(216,tom.getIncome() );
    }

    @Test
    @Tag("hidden")
    @Tag("score:2")
    @Order(17)
    @DisplayName("FoodStand distributeIncomeAndSalesTax Test2")
    void foodstanddistributeIncomeAndSalesTax2() {
        WorkingOwner tom = new WorkingOwner("Tom", 10);
        FoodStand fs = new FoodStand("FoodStand", 100.0 , tom);
        Check check = new Check(180.0);
        check.setTipByPct(20);
        fs.distributeIncomeAndSalesTax(check);
        assertEquals(27,fs.getTotalSalesTax() );
    }

    @Test
    @Tag("score:2")
    @Order(18)
    @DisplayName("FoodStand getTipPercentage Test")
    void foodstandgetTipPercentage() {
        WorkingOwner tom = new WorkingOwner("Tom", 10);
        FoodStand fs = new FoodStand("FoodStand", 100.0 , tom);
        assertEquals(10, fs.getTipPercentage());
    }
    //ok

    Owner Ricardo = new Owner("Ricardo");
    Staff Andrew = new Staff("Andrew", false);
    Staff Alphonse = new Staff("Alphonse", false);
    Staff Rissah = new Staff("Rissah", true);
    Staff Yung = new Staff("Yung", false);
    List<Staff> staff1 = Arrays.asList(Andrew, Alphonse, Rissah, Yung);
    FastFood McDonald1 = new FastFood("McDonald", 730, Ricardo, staff1);

    @Test
    @Tag("score:2")
    @Order(19)
    @DisplayName("FastFood Constructor Test1")
    void fastfoodCon1(){
        assertEquals("McDonald", McDonald1.getName());
        assertEquals(730, McDonald1.getFixedCosts());
        assertEquals(Ricardo, McDonald1.getOwner());
        //assertEquals(0,McDonald.getFoodPlaceID());

    }
    @Test
    @Tag("score:2")
    @Order(20)
    @DisplayName("FastFood Constructor Test2")
    void fastfoodCon2(){
        assertEquals(staff1, McDonald1.getStaff());
    }

    @Test
    @Tag("score:2")
    @Order(21)
    @DisplayName("FastFood workshift Test1")
    void fastworkshiftTest1(){
        McDonald1.workShift(8);
        assertEquals(400.0, Ricardo.getSalaryExpenses());

    }
    @Test
    @Tag("score:2")
    @Order(22)
    @DisplayName("FastFood workshift Test2")
    void fastworkshiftTest2(){
        McDonald1.workShift(8);
        assertEquals(80.0, Andrew.getIncome());
        assertEquals(80.0, Alphonse.getIncome());
        assertEquals(80.0, Yung.getIncome());
        assertEquals(160.0, Rissah.getIncome());

    }

    @Test
    @Tag("score:2")
    @Order(23)
    @DisplayName("FastFood getIncomeTaxPayers Test1")
    void fastgetIncomeTaxPayers1() {
        List<IncomeTaxPayer> payers = List.of(Andrew, Yung, Alphonse, Rissah, Ricardo );
        boolean result = new HashSet<>(payers).equals(new HashSet<>(McDonald1.getIncomeTaxPayers()));
        assertEquals(true, result);
    }

    @Test
    @Tag("score:2")
    @Order(24)
    @DisplayName("FastFood distributeIncomeAndSalesTax Test1")
    void fastdistributeIncomeAndSalesTax1() {
        Check check = new Check(180.0);
        check.setTipByPct(20);
        McDonald1.distributeIncomeAndSalesTax(check);
        assertEquals(180.0, Ricardo.getIncome());
        assertEquals(9.0, Yung.getIncome());
    }

    @Test
    @Tag("hidden")
    @Tag("score:2")
    @Order(25)
    @DisplayName("FastFood distributeIncomeAndSalesTax Test2")
    void fastdistributeIncomeAndSalesTax2() {
        Check check = new Check(180.0);
        check.setTipByPct(20);
        McDonald1.distributeIncomeAndSalesTax(check);
        assertEquals(27.0, McDonald1.getTotalSalesTax());


    }

    
    @Test
    @Tag("hidden")
    @Tag("score:2")
    @Order(26)
    @DisplayName("IncomeTaxPayer Constructor Test 1")
    void incomeTaxPayerConstructor1() {
        IncomeTaxPlayerCopy a = new IncomeTaxPlayerCopy("alice");
        assertEquals("alice", a.getName());

    }

    @Test
    @Tag("hidden")
    @Tag("score:2")
    @Order(27)
    @DisplayName("IncomeTaxPayer Constructor Test 2")
    void incomeTaxPayerConstructor2() {
        IncomeTaxPlayerCopy b = new IncomeTaxPlayerCopy("bob");
        IncomeTaxPlayerCopy d = new IncomeTaxPlayerCopy("david");
        assertEquals(-1, (b.getTaxID() - d.getTaxID() ));

    }

    @Test
    @Tag("score:2")
    @Order(28)
    @DisplayName("IncomeTaxPayer Equals Test 1")
    void incomeTaxPayerEquals() {
        IncomeTaxPlayerCopy c = new IncomeTaxPlayerCopy("celia");
        IncomeTaxPlayerCopy d = new IncomeTaxPlayerCopy("celia"); //same names
        assertEquals(false, c.equals(d));
    }



    @Test
    @Tag("score:2")
    @Order(29)
    @DisplayName("Staff Constructor Test1")
    void staffCon(){
        assertEquals(20, ann.getSalaryPerHour());
        assertEquals(10, tina.getSalaryPerHour());
    }

    @Test
    @Tag("score:2")
    @Order(30)
    @DisplayName("Staff workhours Test1")
    void workHours1() {
        double salary = ann.workHours(8);
        assertEquals(160, salary);
        double salary2 = ann.workHours(8);
        assertEquals(160, salary2);

    }
    @Test
    @Tag("hidden")
    @Tag("score:2")
    @Order(31)
    @DisplayName("Staff workhours Test2")
    void workHours2() {
        double salary = ann.workHours(8);
        double salary2 = ann.workHours(8);
        assertEquals(320,ann.getIncome() );
    }

    @Test
    @Tag("score:2")
    @Order(32)
    @DisplayName("Staff calculateIncomeTax Test1")
    void calculateIncomeTax() {
        double salary = ann.workHours(8);
        double salary2 = ann.workHours(8);
        double tax = ann.calculateIncomeTax();
        assertEquals(80, tax);

    }


    @Test
    @Tag("score:2")
    @Order(33)
    @DisplayName("Server Constructor Test")
    void server(){
        Server tod = new Server("Tod", 15);
        assertEquals("Tod", tod.getName());
        assertEquals(10, tod.getSalaryPerHour());
    }
    //


    Staff ann = new Staff("Ann", true);
    Staff tina = new Staff("Tina", false);
    
    @Test
    @Tag("hidden")
    @Tag("score:2")
    @Order(34)
    @DisplayName("Owner Constructor Test")
    void ownerCon(){
        Owner jack = new Owner("Jack");
        assertEquals("Jack", jack.getName());
    }


    List<Staff> staff2 = Arrays.asList(Andrew, Alphonse);

    @Test
    @Tag("score:2")
    @Order(35)
    @DisplayName("Owner calculateIncomeTax Test 1")
    void calculateIncomeTax1() {
        FastFood McDonald2 = new FastFood("McDonald2", 230, Ricardo, staff2);
        Check check = new Check(480.0);
        check.setTipByPct(20);
        McDonald2.workShift(8);
        McDonald2.distributeIncomeAndSalesTax(check);
        double tax = Ricardo.calculateIncomeTax();
        //double result = Ricardo.getIncome() -Ricardo.getSalaryExpenses() - McDonald2.getFixedCosts();
        // System.out.println(result * Ricardo.getIncomeTaxPct() / 100.0);
        assertEquals(9.0, tax);
    }

    @Test
    @Tag("score:2")
    @Order(36)
    @DisplayName("Owner calculateIncomeTax Test 2")
    void calculateIncomeTax2() {
        FastFood McDonald2 = new FastFood("McDonald2", 230, Ricardo, staff2);
        Check check = new Check(180.0);
        check.setTipByPct(20);
        McDonald2.workShift(8);
        McDonald2.distributeIncomeAndSalesTax(check);
        double tax = Ricardo.calculateIncomeTax();
        assertEquals(0.0, tax);


    }
    //

    Owner Tina = new Owner("Tina");
    Staff Sade = new Staff("Sade", false);
    Server Nia = new Server("Nia", 20);
    Restaurant MamaAfrica = new Restaurant("Mama Africa", 1200, Tina, Sade, Nia);


    WorkingOwner tom = new WorkingOwner("Tom", 10);
    FoodStand fs = new FoodStand("FoodStand", 100.0 , tom);


    List<Staff> staff3 = Arrays.asList(Andrew, Alphonse, Rissah, Yung);
    FastFood McDonald5 = new FastFood("McDonald", 730, Ricardo, staff3);

    List<FoodPlace> fpList = List.of(MamaAfrica, McDonald5,fs);
    TaxCollector tc = new TaxCollector(fpList);


    @Test
    @Tag("hidden")
    @Tag("score:2")
    @Order(37)
    @DisplayName("Check Constructor Test")
    void checkConstructor(){
        Check ch  = new Check(100.0);
        assertEquals(100.0, ch.getMenuPrice());
        assertEquals(15.0, ch.getSalesTax());
    }

    @Test
    @Tag("score:2")
    @Order(38)
    @DisplayName("Check setTipByPct Test")
    void setTipByPctTest() {
        Check ch  = new Check(200.0);
        ch.setTipByPct(0.16);
        assertEquals(0.32, ch.getTip());
    }

//

    Customer tedd = new Customer("Tedd", 12);
    @Test
    @Tag("hidden")
    @Tag("score:4")
    @Order(39)
    @DisplayName("Customer Constructor Test1")
    void constructor(){

        assertEquals("Tedd", tedd.getName());
        assertEquals(12, tedd.getTargetTipPct());
    }


    @Test
    @Tag("score:4")
    @Order(40)
    @DisplayName("Customer dineAndPayCheck Test1")
    void dineAndPayCheck() {
        Owner Tina = new Owner("Tina");
        Staff Sade = new Staff("Sade", false);
        Server Nia = new Server("Nia", 20);
        Restaurant MamaAfrica = new Restaurant("Mama Africa", 1200, Tina, Sade, Nia);
        tedd.dineAndPayCheck(MamaAfrica, 200);
        assertEquals(30.0,MamaAfrica.getTotalSalesTax() );


    }

    @Test
    @Tag("hidden")
    @Tag("score:4")
    @Order(41)
    @DisplayName("Customer dineAndPayCheck Test2")
    void dineAndPayCheck2() {
        Owner Ricardo = new Owner("Ricardo");
        Staff Andrew = new Staff("Andrew", false);
        Staff Alphonse = new Staff("Alphonse", false);
        Staff Rissah = new Staff("Rissah", true);
        Staff Yung = new Staff("Yung", false);
        List<Staff> staff = Arrays.asList(Andrew, Alphonse, Rissah, Yung);
        FastFood McDonald = new FastFood("McDonald", 730, Ricardo, staff);

        tedd.dineAndPayCheck(McDonald, 170);
        assertEquals(25.5,McDonald.getTotalSalesTax() );


    }





    @Test
    @Tag("hidden")
    @Tag("score:4")
    @Order(42)
    @DisplayName("TaxCollector Constructor Test")
    void taxCollectorconstructor(){
        assertEquals(fpList, tc.getFoodPlaces());
    }




    @Test
    @Tag("score:2")
    @Order(43)
    @DisplayName("TaxCollector collectTax SalesTax Test 1")
    void collectTax1() {
        Check check1 = new Check(300);
        check1.setTipByPct(15);

        Check check2 = new Check(110);
        check1.setTipByPct(18);

        MamaAfrica.workShift(5);
        MamaAfrica.distributeIncomeAndSalesTax(check1);
        McDonald5.workShift(8);
        McDonald5.distributeIncomeAndSalesTax(check2);
        fs.workShift(4);
        fs.distributeIncomeAndSalesTax(check1);
        tc.collectTax();

        assertEquals(106.5, tc.getSalesTaxCollected());

    }
    @Test
    @Tag("score:2")
    @Order(44)
    @DisplayName("TaxCollector collectTax IncomeTax Test 1")
    void collectTax2() {
        Check check1 = new Check(300);
        check1.setTipByPct(15);

        Check check2 = new Check(110);
        check1.setTipByPct(18);

        MamaAfrica.workShift(5);
        MamaAfrica.distributeIncomeAndSalesTax(check1);
        McDonald5.workShift(8);
        McDonald5.distributeIncomeAndSalesTax(check2);
        fs.workShift(4);
        fs.distributeIncomeAndSalesTax(check1);
        tc.collectTax(); ///////

        assertEquals(163.9, tc.getIncomeTaxCollected());

    }
//
    @Test
    @Tag("hidden")
    @Tag("score:4")
    @Order(45)
    @DisplayName("WorkingOwner Constructor Test1")
    void workingownerCons(){
        WorkingOwner stella = new WorkingOwner("Stella", 15);
        assertEquals(15,stella.getTargetTipPct());

    }
    



    @Test
    @Tag("hidden")
    @Tag("score:4")
    @Order(46)
    @DisplayName("FoodPlace Constructor Test")
    void foodplaceConstructor(){
        Owner kate = new Owner("Kate");
        FoodPlaceCopy fpc = new FoodPlaceCopy("BurgerHouse", 100.0, kate, "grade");
        FoodPlaceCopy fpc9 = new FoodPlaceCopy("BurgerHousePro", 200.0, kate, "grade");

        assertEquals("BurgerHouse", fpc.getName());
        assertEquals(100.0, fpc.getFixedCosts());
        assertEquals(1,(fpc9.getFoodPlaceID() - fpc.getFoodPlaceID())); //?
        assertEquals(kate, fpc.getOwner());
        assertEquals(fpc9, kate.getFoodPlace());
    }

    @Test
    @Tag("hidden")
    @Tag("score:2")
    @Order(46)
    @DisplayName("FoodPlace Equals Test 1")
    void foodplaceEquals1() {
        Owner jack = new Owner("jack");
        FoodPlaceCopy fpc1 = new FoodPlaceCopy("BurgerHouse", 200.0, jack, "grade");
        FoodPlaceCopy fpc2 = new FoodPlaceCopy("BurgerHouse", 200.0, jack, "grade");
        assertEquals(false, fpc1.equals(fpc2));
        //equals
    }

    @Test
    @Tag("hidden")
    @Tag("score:2")
    @Order(48)
    @DisplayName("FoodPlace Equals Test 2")
    void foodplaceEquals2() {
        Owner jack = new Owner("jack");
        FoodPlaceCopy fpc1 = new FoodPlaceCopy("BurgerHouse", 200.0, jack, "grade");
        FoodPlaceCopy fpc11 = fpc1;
        assertEquals(true, fpc1.equals(fpc11));
    }


}
