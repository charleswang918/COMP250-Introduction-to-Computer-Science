
public class WorkingOwner extends Owner {

    private int targetTipPct;

    public WorkingOwner(String name, int targetTipPct) {
        /* TODO: Add your code here */
        /* TODO: Also edit the super call */
        super(name);
        this.targetTipPct = targetTipPct;
    }

    public int getTargetTipPct() {
        return targetTipPct;
    }
}
