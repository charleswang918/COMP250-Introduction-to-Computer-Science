import java.util.ArrayList;
import java.util.HashMap;

public class ERPriorityQueue{

	public ArrayList<Patient>  patients;
	public HashMap<String,Integer>  nameToIndex;

	public ERPriorityQueue(){

		//  use a dummy node so that indexing starts at 1, not 0

		patients = new ArrayList<Patient>();
		patients.add( new Patient("dummy", 0.0) );

		nameToIndex  = new HashMap<String,Integer>();
	}

	private int parent(int i){
		return i/2;
	}

	private int leftChild(int i){
	    return 2*i;
	}

	private int rightChild(int i){
	    return 2*i+1;
	}

    /*
    TODO: OPTIONAL
    TODO: Additional helper methods such as isLeaf(int i), isEmpty(), swap(int i, int j) could be useful for this assignment
     */
	public void swap(int i, int j){
		Patient patienti = this.patients.get(i);
		Patient patientj = this.patients.get(j);
		this.patients.set(i, patientj);
		this.patients.set(j,patienti);
		int temi = this.nameToIndex.get(patienti.name);
		int temj = this.nameToIndex.get(patientj.name);
		this.nameToIndex.replace(patienti.name, temj);
		this.nameToIndex.replace(patientj.name, temi);

	}

	public boolean isLeaf(int i){
		boolean hasLeft = this.leftChild(i) < this.patients.size();
		boolean hasRight = this.rightChild(i) < this.patients.size();
		if (hasLeft || hasRight){
			return false;
		}
		else {
			return true;
		}
	}

	public boolean isEmpty(){
		return this.patients.size() == 1;
	}

	public void upHeap(int i){
		// TODO: Implement your code here
		while (i >= 1) {
			double newPriority = this.patients.get(i).getPriority();
			double ParentPriority = this.patients.get(this.parent(i)).getPriority();
			if (ParentPriority > newPriority) {
				swap(i, this.parent(i));
				i = this.parent(i);
			} else {
				break;
			}
		}
	}

	public void downHeap(int i) {
		// TODO: Implement your code here
		int size = this.patients.size()-1;
		while (this.leftChild(i) <= size) {
			int child = this.leftChild(i);
			if (child < size){
				double leftChildPriority = this.patients.get(this.leftChild(i)).getPriority();
				double rightChildPriority = this.patients.get(this.rightChild(i)).getPriority();
				if (rightChildPriority < leftChildPriority){
					child = this.rightChild(i);
				}
			}
			if (this.patients.get(child).getPriority() <= this.patients.get(i).getPriority()){
				this.swap(child, i);
				i = child;
			}
			else return;
		}
	}

	public boolean contains(String name){
        // TODO: Implement your code here & remove return statement
		boolean isContain = false;
		for (var p:this.patients){
			if (p.getName().equals(name)){
				isContain = true;
				break;
			}
		}
        return isContain;
	}

	public double getPriority(String name){
        // TODO: Implement your code here & remove return statement
		double priority = -1;
		for (var p:this.patients){
			if (p.getName().equals(name)){
				priority = p.getPriority();
			}
		}
        return priority;
	}

	public double getMinPriority(){
        // TODO: Implement your code here & remove return statement
		if (this.patients.size() > 1){
			return this.patients.get(1).getPriority();
		}
		else return -1;
	}

	public String removeMin(){
        // TODO: Implement your code here & remove return statement
		if (this.isEmpty()){
			return null;
		}
		else {
			int size = this.patients.size();
			this.swap(1, size-1);
			Patient minPatient = this.patients.get(size-1);
			nameToIndex.remove(minPatient.name);
			Patient lastPatient = this.patients.remove(size-1);
			downHeap(1);
			return lastPatient.getName();
		}
	}

	public String peekMin(){
        // TODO: Implement your code here & remove return statement
		if (this.patients.size() > 1){
			return this.patients.get(1).getName();
		}
        else return null;
	}

	/*
	 * There are two add methods.  The first assumes a specific priority.
	 * The second gives a default priority of Double.POSITIVE_INFINITY
	 *
	 * If the name is already there, then return false.
	 */

	public boolean add(String name, double priority){
        // TODO: Implement your code here & remove return statement
		if (this.contains(name)){
			return false;
		}
		else {
			Patient newPatient = new Patient(name, priority);
			this.patients.add(newPatient);
			nameToIndex.put(name, this.patients.indexOf(newPatient));
			upHeap(this.patients.indexOf(newPatient));
			return true;
		}
	}

	public boolean add(String name){
        // TODO: Implement your code here
		if (this.contains(name)){
			return false;
		}
		else {
			Patient newPatient = new Patient(name, Double.POSITIVE_INFINITY);
			this.patients.add(newPatient);
			upHeap(this.patients.indexOf(newPatient));
			nameToIndex.put(name, this.patients.indexOf(newPatient));
			return true;
		}
	}

	public boolean remove(String name){
        // TODO: Implement your code here
		if (this.nameToIndex.get(name) == null){
			return false;
		}
		else {
			int indexRemoved = this.nameToIndex.get(name);
			int size = this.patients.size();
			this.swap(indexRemoved, size-1);
			Patient minPatient = this.patients.get(size-1);
			nameToIndex.remove(minPatient.name);
			this.patients.remove(size-1);
			downHeap(indexRemoved);
			return true;
		}
	}

	/*
	 *   If new priority is different from the current priority then change the priority
	 *   (and possibly modify the heap).
	 *   If the name is not there, return false
	 */

	public boolean changePriority(String name, double priority){
        // TODO: Implement your code here & remove return statement
		if (this.nameToIndex.containsKey(name)){
			int index = this.nameToIndex.get(name);
			double prePriority = this.patients.get(index).priority;
			this.patients.get(index).priority = priority;

			if (prePriority < priority){
				this.downHeap(index);
			}
			else if (prePriority > priority){
				this.upHeap(index);
			}
			return true;
		}
		else return false;
	}

	public ArrayList<Patient> removeUrgentPatients(double threshold){
        // TODO: Implement your code here & remove return statement
		ArrayList<Patient> urgentPatients = new ArrayList<Patient>();
		for (int i = 1; i < this.patients.size(); i++){
			Patient p = this.patients.get(i);

			if (p.getPriority() <= threshold){
				urgentPatients.add(p);
				this.remove(p.name);
				i = 0;
			}
		}
        return urgentPatients;
	}

	public ArrayList<Patient> removeNonUrgentPatients(double threshold){
        // TODO: Implement your code here & remove return statement
		ArrayList<Patient> nonUrgentPatients = new ArrayList<Patient>();

		for (int i = 0; i < this.patients.size(); i++){
			Patient p = this.patients.get(i);
			if (p.getPriority() >= threshold){
				nonUrgentPatients.add(p);
				this.remove(p.name);
				i = 0;
			}
		}
        return nonUrgentPatients;
	}

	static class Patient{
		private String name;
		private double priority;

		Patient(String name,  double priority){
			this.name = name;
			this.priority = priority;
		}

		Patient(Patient otherPatient){
			this.name = otherPatient.name;
			this.priority = otherPatient.priority;
		}

		double getPriority() {
			return this.priority;
		}

		void setPriority(double priority) {
			this.priority = priority;
		}

		String getName() {
			return this.name;
		}

		@Override
		public String toString(){
			return this.name + " - " + this.priority;
		}

		public boolean equals(Object obj){
			if (!(obj instanceof  ERPriorityQueue.Patient)) return false;
			Patient otherPatient = (Patient) obj;
			return this.name.equals(otherPatient.name) && this.priority == otherPatient.priority;
		}

	}
}
