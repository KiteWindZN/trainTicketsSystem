package struct;

import java.util.ArrayList;
import java.util.List;

public class Distance {
	int Num; //距离
	List<Ticket> ticketList;
	
	public Distance(int num){
		this.Num=num;
		this.ticketList=new ArrayList<Ticket>();
	}

	public int getNum() {
		return Num;
	}

	public void setNum(int num) {
		Num = num;
	}

	public List<Ticket> getTicketList() {
		return ticketList;
	}

	public void setTicketList(List<Ticket> ticketList) {
		this.ticketList = ticketList;
	}
	
	
}
