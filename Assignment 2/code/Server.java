
public class Server extends Staff {
	
	private int targetTipPct;

	public Server(String name, int targetTipPct){
		/* TODO: Add your code here */
		/* TODO: Also edit the super call */
		super(name, false);
		this.targetTipPct = targetTipPct;
	}

	public int getTargetTipPct() {
		return targetTipPct;
	}
}
