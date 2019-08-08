package struct;

import java.util.ArrayList;
import java.util.List;

public class Node {

	int Num;
	Node next;
	List<Distance> behind;

	
	public Node(int num){
		this.Num=num;
		this.next=null;
		this.behind=new ArrayList<Distance>();
		
	}
	
	public int getNumber() {
		return Num;
	}
	public void setNumber(int Num) {
		this.Num = Num;
	}
	public Node getNext() {
		return next;
	}
	public void setNext(Node next) {
		this.next = next;
	}

	public List<Distance> getBehind() {
		return behind;
	}

	public void setBehind(List<Distance> behind) {
		this.behind = behind;
	}
	
	
	
}
