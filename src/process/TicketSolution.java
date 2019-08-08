package process;

import java.util.ArrayList;
import java.util.List;

import struct.Distance;
import struct.Node;
import struct.NodeList;
import struct.Ticket;

public class TicketSolution {
	NodeList nodeList;

	public TicketSolution(int tickets, int stations) {
		nodeList = new NodeList(tickets, stations);
	}

	public List<Ticket> getTicketList(int start, int end) {
		List<Ticket> ticketList = new ArrayList<Ticket>();
		int dis = getDistance(start, end);

		Node station = nodeList.getNodeList().get(start);

		Distance distance = station.getBehind().get(dis - 1);
		ticketList = distance.getTicketList();
		return ticketList;

	}

	public List<Ticket> queryTicket(int start, int end) {// 查询
		List<Ticket> ticketList = new ArrayList<Ticket>();

		while (ticketList.size() == 0 && (start >= 0 || end <= nodeList.getTotalStations() - 1)) {// 没有查询到票，且区间可以再扩大

			ticketList = getTicketList(start, end);
			if (ticketList.size() > 0)
				return ticketList;
			if (end < nodeList.getTotalStations() - 1) {
				ticketList = getTicketList(start, end + 1);
				if (ticketList.size() > 0)
					return ticketList;
			}
			if (start > 0) {
				ticketList = getTicketList(start - 1, end);
				if (ticketList.size() > 0)
					return ticketList;
			}

			if (start > 0 && end < nodeList.getTotalStations() - 1) {
				ticketList = getTicketList(start - 1, end + 1);
				if (ticketList.size() > 0)
					return ticketList;
			}

			if (start <= 0 && end >= nodeList.getTotalStations() - 1)
				break;
			if (start > 0)
				start--;
			if (end < nodeList.getTotalStations() - 1)
				end++;
		}

		return ticketList;
	}

	public Ticket bookTicket(int start, int end) {// 订票,查询基础上增加整个结构体的动态变化
		List<Ticket> ticketList = queryTicket(start, end);
		;
		int orgStart = start;
		int orgEnd = end;

		Ticket resTicket = null;
		if (ticketList.size() > 0) {

			resTicket = ticketList.get(0);
			start = resTicket.getStart();
			end = resTicket.getEnd();

			int ticketNum = resTicket.getNum();// 获取票的座位号
			Ticket newTicket1 = new Ticket(ticketNum);
			Ticket newTicket2 = new Ticket(ticketNum);

			// 购买的票链表减一
			ticketList.remove(0);
			// 切断的剩余的票加一
			if (start != orgStart || end != orgEnd) {
				if (start < orgStart) {
					// 以start为起点，orgStart为终点的票加1
					newTicket1.setStart(start);
					newTicket1.setEnd(orgStart);
					insert(newTicket1);
				}
				if (end > orgEnd) {
					newTicket2.setStart(orgEnd);
					newTicket2.setEnd(end);
					// 以orgEnd为起点，end为终点的票加1
					insert(newTicket2);
				}

			}
		}

		return resTicket;
	}

	public boolean refundTicket(Ticket ticket) {// 退票
		int start = ticket.getStart();
		int end = ticket.getEnd();
		int num = ticket.getNum();
		Ticket insertTicket = ticket;
		List<Node> stationList = nodeList.getNodeList();
		// 查找以start结尾的本座位的票是否存在
		// 此方法太过于麻烦，优化措施以ticket为关键字建立索引
		int flag1 = 0;
		for (int i = 0; i < start; i++) {
			Node node = stationList.get(i);
			int dis = start - i;
			List<Distance> distanceList = node.getBehind();
			Distance choose = distanceList.get(dis - 1);
			List<Ticket> ticketList = choose.getTicketList();
			for (int j = 0; j < ticketList.size(); j++) {
				Ticket t = ticketList.get(j);
				if (t.getNum() == num) {// 找到
					flag1 = 1;
					ticketList.remove(j);
					insertTicket.setStart(t.getStart()); // 设置新票的开头
					break;
				}
			}

			if (flag1 == 1)
				break;
		}

		// 查找以end开头的票，是否有此座位，如果有，继续合并
		int flag2 = 0;
		for (int h = end + 1; h < nodeList.getTotalStations(); h++) {
			int dis2 = h - end;
			Node node = stationList.get(h - 1);
			List<Distance> distanceEndList = node.getBehind();
			Distance chooseEnd = distanceEndList.get(dis2 - 1);
			List<Ticket> ticketEndList = chooseEnd.getTicketList();
			for (int g = 0; g < ticketEndList.size(); g++) {
				Ticket ticketEnd = ticketEndList.get(g);
				if (ticketEnd.getNum() == num) {
					ticketEndList.remove(g);
					flag2 = 1;
					insertTicket.setEnd(ticketEnd.getEnd()); // 设置新票的结尾

					break;
				}
			}
			if (flag2 == 1)
				break;
		}

		insert(insertTicket);
		return true; // 错误状态待添加
	}

	public int getDistance(int start, int end) {
		int distance = end - start;
		return distance;
	}

	public void insert(Ticket ticket) {
		int start = ticket.getStart();
		int end = ticket.getEnd();
		int num = ticket.getNum();
		int dis = end - start;

		List<Node> stationList = nodeList.getNodeList();
		Node node = stationList.get(start);
		Distance distance = node.getBehind().get(dis - 1);
		List<Ticket> ticketList = distance.getTicketList();
		int flag = 0;
		for (int i = 0; i < ticketList.size(); i++) {
			if (ticketList.get(i).getNum() > num) {// 找到插入点
				ticketList.add(i, ticket);
				flag = 1;
				break;
			}
		}
		if (flag == 0) {
			ticketList.add(ticketList.size(), ticket);
		}

	}

	public NodeList getNodeList() {
		return nodeList;
	}

}
