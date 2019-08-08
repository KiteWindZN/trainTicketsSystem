package test;

/*优化点： 1.同一时间的请求过来之后对请求进行排序
 *       2. 建立以座位号为主键的索引，便于退票时的操作
 * */

import java.util.List;
import java.util.Scanner;

import process.TicketSolution;
import struct.Distance;
import struct.Node;
import struct.NodeList;
import struct.Ticket;

public class Main {
	Scanner scan=new Scanner(System.in);
	int totalStations=5;
	int totalTickets=60;
	Ticket ticket;
	public int menu(){
		int choose=0;
		
		System.out.println("-----------------------------------------");
		System.out.println("---------     欢迎登录售票系统     ---------");
		System.out.println("++++ 1.查询余票");
		System.out.println("++++ 2.购买车票");
		System.out.println("++++ 3.打印所有车票");
		System.out.println("++++ 4.退票");
		System.out.println("++++ 5.退出系统");
		System.out.println("-----------------------------------------");
		System.out.print("++++ 请输入您的选择：");
		
		choose=scan.nextInt();
		return choose;
	}
	
	
	public void solution(){
		int choose=menu();
		int start=0;
		int end=0;
		int num=0;
		TicketSolution ticketSolution=new TicketSolution(totalTickets,totalStations);
		NodeList nodeList=ticketSolution.getNodeList();
		
		boolean res=false;
		
		while(choose!=5){
			switch(choose){
			case 1://查询
				System.out.print("请输入起始地点和终点，以空格隔开：");
				start=scan.nextInt();
				end=scan.nextInt();
				res=isLegal(start,end);
				if(res==false){
					System.out.println("输入的站点不合法！！！");
					break;
				}
				List<Ticket> ticketList=ticketSolution.queryTicket(start, end);
				if(ticketList.size()==0){
					System.out.println("该区间内车票已卖完！！");
					break;
				}
				printTicketList(ticketList);
				break;
			case 2://购买
				System.out.print("请输入起始地点和终点，以空格隔开：");
				start=scan.nextInt();
				end=scan.nextInt();
				res=isLegal(start,end);
				if(res==false){
					System.out.println("输入的站点不合法！！！");
					break;
				}
				ticket=ticketSolution.bookTicket(start, end);
				if(ticket==null){
					System.out.println("该区间内车票已卖完！！");
					break;
				}
				System.out.println("车票信息如下：");
				printTicket(ticket);
				break;
				
			case 3://打印所有的车票
				printAllTicket(nodeList);
				break;
				
            case 4://退票
				
				System.out.print("请输入起始地点，终点和座位信息，以空格隔开：");
				start=scan.nextInt();
				end=scan.nextInt();
				num=scan.nextInt();
				res=isLegal(start,end);
				if(res==false){
					System.out.println("输入的站点不合法！！！");
					break;
				}
				ticket=new Ticket(num);
				ticket.setStart(start);
				ticket.setEnd(end);
				res=ticketSolution.refundTicket(ticket);
				if(res==false){
					System.out.println("退票失败！！！");
				}else{
					System.out.println("退票成功！！！");
				}
				break;
				
			default:
					System.out.println("输入字符非法！！！");
			}
			choose=menu();
		}
		System.out.println("成功退出系统！！！");
		scan.close();
	}
	
	public void printTicketList(List<Ticket> ticketList){
		if(ticketList.size()==0)
			return;
		System.out.println("一共查询到 "+ticketList.size()+" 张车票，列表如下：");
		System.out.println("    起点  终点  座位号");
		for(int i=0;i<ticketList.size();i++){
			System.out.print(i + ": ");
			printTicket(ticketList.get(i));
		}
	}
	
	public void printTicket(Ticket ticket){
		System.out.println("  "+ticket.getStart()+"    "+ticket.getEnd()+"     "+ticket.getNum());
	}
	
	public void printAllTicket(NodeList nodeList){
		List<Node> list=nodeList.getNodeList();
		
		for(int i=0;i<list.size()-1;i++){
			Node node=list.get(i);
			System.out.println("最早从站点 "+ i +"出发的车票为：");
			
			List<Distance> dis=node.getBehind();
			for(Distance d:dis){
				List<Ticket> ticketList=d.getTicketList();
				printTicketList(ticketList);
			}
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Main myObj=new Main();
		myObj.solution();
		
	}
	
	//判断站点输入是否合法
	public boolean isLegal(int start,int end){
		if(start>=end)
			return false;
		if(start<0)
			return false;
		if(end>=totalStations)
			return false;
		return true;
	}

}
