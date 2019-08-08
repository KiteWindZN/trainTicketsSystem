package struct;

public class Ticket {
	int Num;//座位号码
	int start;
	int end;

	public Ticket(int num){
		this.Num=num;
	}
	public int getNum() {
		return Num;
	}

	public void setNum(int num) {
		Num = num;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}

	
}
