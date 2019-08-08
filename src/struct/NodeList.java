package struct;

import java.util.ArrayList;
import java.util.List;

public class NodeList {
	List<Node> nodeList=new ArrayList<Node>();
	int totalTickets;
	int totalStations;
	
	public NodeList(int tickets,int stations){
		this.totalTickets=tickets;
		this.totalStations=stations;
		init();
	}
	
	public void init(){
		for(int i=0;i<this.totalStations;i++){
			Node node=new Node(i);
			this.nodeList.add(node);
			List<Distance> behind=node.behind;
			for(int j=1;j<this.totalStations-i;j++){
				Distance bNode=new Distance(j);
				behind.add(bNode);
			}
		}
		
		Node node=nodeList.get(0);
		List<Distance> behind=node.getBehind();
		Distance chooseDistance=behind.get(totalStations-1-1);//初始状态所有的票都是从起始点到终点的
		for(int i=0;i<this.totalTickets;i++){ //i代表车票额座位号
			Ticket ticket=new Ticket(i);//创建一个新的票节点,设置车票座位号
			ticket.start=0;
			ticket.end=totalStations-1;
			chooseDistance.getTicketList().add(ticket);
		}
	}

	public List<Node> getNodeList() {
		return nodeList;
	}

	public void setNodeList(List<Node> nodeList) {
		this.nodeList = nodeList;
	}

	public int getTotalTickets() {
		return totalTickets;
	}

	public void setTotalTickets(int totalTickets) {
		this.totalTickets = totalTickets;
	}

	public int getTotalStations() {
		return totalStations;
	}

	public void setTotalStations(int totalStations) {
		this.totalStations = totalStations;
	}
	
}
