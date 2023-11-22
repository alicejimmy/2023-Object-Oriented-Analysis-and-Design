package part_of_window;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

import part_of_window.GraphOject.*;
import part_of_window.GraphOject.Class;
import part_of_window.MouseEvent.*;

public class Canvas extends JPanel{
    private static Canvas instance = null;
	private int currentMode = -1; //現在工具列的功能，1:select, 2:association line, 3:generalization line, 4:composition line, 5:Class, 6:use case
	private graph SelectedObject = null; //被選到的graph
	private Rectangle SelectedArea = new Rectangle(); //滑鼠拖移的區域
	private ArrayList<graph> SelectedAreaObjectList = new ArrayList<graph>(); //SelectedArea範圍內的graph
	private ConnectionLine tmpConnectionLine = null; //暫時拖移的ConnectionLine(還沒建立的)
	private ArrayList<graph> GraphList = new ArrayList<graph>(); //已建立graph的列表
    private Canvas() {
		CanvasListener listener = new CanvasListener();
        addMouseListener(listener);
        addMouseMotionListener(listener);
	}
    public static Canvas getInstance() {
		if (instance == null) {
			instance = new Canvas();
		}
		return instance;
	}
	//設定為num的工具模式
	public void setMode(int num){
		currentMode = num;
	}
	//Group功能
	public void GroupObjects(){
		if (SelectedAreaObjectList.size() > 1){
			Group group = new Group();
			group.addGraphs(SelectedAreaObjectList);
			GraphList.add(group);
		}
		SelectedAreaObjectList.clear();
		SelectedArea.setBounds(0, 0, 0, 0);
		repaint();
	}
	//Ungroup功能
	public void UngroupObjects(){
		GraphList.remove(SelectedObject);
		ArrayList<graph> UngroupObjectslist = ((Group) SelectedObject).getGroupObjectList();
		for (int i = 0; i < UngroupObjectslist.size(); i++) {
			graph gl = UngroupObjectslist.get(i);
			gl.InGroup = false;
		}
		repaint();
	}
	//Change object name功能
	public void ChangeSelectedObjectName(String NewName){
		if (SelectedObject != null){
			((BasicObject) SelectedObject).ChangeName(NewName);
		}
		repaint();
	}
	//檢查graph是否有在SelectedArea範圍內
	private boolean CheckSelectedArea(graph g) {
		if (SelectedArea.contains(g.getx1(), g.gety1()) && SelectedArea.contains(g.getx2(), g.gety2())) {
			return true;
		}
		return false;
	}
	//畫Canvas
	public void paint(Graphics g) {
		//清除畫面上的所有graph
		Dimension dim = getSize();
		g.clearRect(0, 0, dim.width, dim.height);
		//畫所有被存在GraphList中的graph
		for (int i = 0; i <  GraphList.size(); i++) {
			graph gl = GraphList.get(i);
			g.setColor(Color.BLACK);
			gl.draw(g);
			if (!SelectedArea.isEmpty() && CheckSelectedArea(gl)) { //在SelectedArea範圍內的Object畫port
				gl.ShowPorts(g);
			}
			if (SelectedObject != null && gl == SelectedObject){ //被Select的Object畫port
				if (currentMode == 1){
					SelectedObject.ShowPorts(g);
				}
				else {
					SelectedObject = null;
				}
			}
		}
		//畫出暫時拖移的ConnectionLine
		if (tmpConnectionLine != null){
			tmpConnectionLine.draw(g);
		}
		//畫出滑鼠拖移的區域(SelectedArea)
		if (!SelectedArea.isEmpty()) {
			g.setColor(new Color(51, 153, 255, 255/4));
			g.fillRect(SelectedArea.x, SelectedArea.y, SelectedArea.width, SelectedArea.height);
			g.setColor(new Color(51, 153, 255));
			g.drawRect(SelectedArea.x, SelectedArea.y, SelectedArea.width, SelectedArea.height);
		}
	}
	//滑鼠事件
	private class CanvasListener extends MouseEventListener{
		private int start_x, start_y; //BasicObject:滑鼠當前位置, ConnectionLine:現在選到port的初始位置
		private int mouse_is_inside = 0; //滑鼠點擊到哪種graph內，1:BasicObject, 2:ConnectionLine, 3:Group
		private graph graph1 = null, graph2 = null; //ConnectionLine連到的兩個graph
		private int portnum1 = -1, portnum2 = -1; //ConnectionLine連到的兩個port的num
		//滑鼠按下時
		public void mousePressed(MouseEvent e) {
			SelectedArea.setBounds(0, 0, 0, 0);
			//Select工具
			if (currentMode==1){
				for (int i = GraphList.size()-1; i >= 0; i--) {
					graph gl = GraphList.get(i);
					mouse_is_inside = gl.inside(e.getX(), e.getY());
					if (mouse_is_inside>=1 && mouse_is_inside<=3){
						SelectedObject = gl;
						if (mouse_is_inside==1 || mouse_is_inside==3){ //如果滑鼠點擊到了BasicObject或Group就存下滑鼠當前位置
							start_x = e.getX();
							start_y = e.getY();
							break;
						}
						else if (mouse_is_inside==2){ //如果滑鼠點擊到了ConnectionLine就存下現在選到port的初始位置
							int Portxy[] = ((ConnectionLine) gl).GetInitialxy();
							start_x = Portxy[0];
							start_y = Portxy[1];
							break;
						}
						repaint();
					}
					//如果滑鼠沒點擊到任何graph就讓SelectedObject為null
					else{
						SelectedObject = null;
						SelectedAreaObjectList.clear();
						start_x = e.getX();
						start_y = e.getY();
					}
				}
			}
			//ConnectionLine工具，找要和ConnectionLine連接的第一個BasicObject
			else if (currentMode>=2 && currentMode<=4){
				FindConnetedBasicObject(e.getX(), e.getY(), 1); 
			}
			//BasicObject工具，在滑鼠點擊位置畫出所選工具的graph
			else if (currentMode>=5 && currentMode<=6){
				BasicObject obj = new BasicObject();
				if (currentMode==5){
					obj = new Class(e.getX(), e.getY());
				}
				else if (currentMode==6){
					obj = new UseCase(e.getX(), e.getY());
				}
				GraphList.add(obj);
				repaint();
			}
		}
		//滑鼠拖移時
		public void mouseDragged(MouseEvent e) {
			int moveX = e.getX() - start_x; //x拖移距離
			int moveY = e.getY() - start_y; //y拖移距離
			if (currentMode==1){
				if (SelectedObject!=null){ //如果滑鼠拖移到了任何graph則移動
					if (mouse_is_inside == 1 || mouse_is_inside == 3){
						SelectedObject.MoveLocation(moveX, moveY);
						start_x=e.getX();
						start_y=e.getY();
					}
					else if (mouse_is_inside == 2){
						SelectedObject.MoveLocation(e.getX(),e.getY());
					}
				}
				else { //如果滑鼠沒拖移到了任何graph則畫出SelectedArea
					if (e.getX() >= start_x && e.getY() >= start_y){
						SelectedArea.setBounds(start_x, start_y, Math.abs(moveX), Math.abs(moveY));
					}
					else if (e.getX() < start_x && e.getY() < start_y){
						SelectedArea.setBounds(e.getX(), e.getY(), Math.abs(moveX), Math.abs(moveY));
					}
					else if (e.getX() < start_x && e.getY() > start_y){
						SelectedArea.setBounds(e.getX(), start_y, Math.abs(moveX), Math.abs(moveY));
					}
					else if (e.getX() > start_x && e.getY() < start_y){
						SelectedArea.setBounds(start_x, e.getY(), Math.abs(moveX), Math.abs(moveY));
					}
					
				}
				repaint();
			}
			//ConnectionLine工具
			else if (currentMode>=2 && currentMode<=4){
				if (graph1!=null){ //如果有找與ConnectionLine連接的第一個BasicObject則畫出暫時的ConnectionLine
					int graph1_port[] = graph1.GetPort(portnum1).getxy();
					if (currentMode==2){
						tmpConnectionLine = new AssociationLine(graph1_port[0], graph1_port[1], graph1_port[0], graph1_port[1]);
					}
					else if (currentMode==3){
						tmpConnectionLine = new GeneralizationLine(graph1_port[0], graph1_port[1], graph1_port[0], graph1_port[1]);
					}
					else if (currentMode==4){
						tmpConnectionLine = new CompositionLine(graph1_port[0], graph1_port[1], graph1_port[0], graph1_port[1]);
					}
					tmpConnectionLine.MoveLocation(e.getX(),e.getY());
					repaint();
				}
			}
		}
		//滑鼠放開時
		public void mouseReleased(MouseEvent e) { 
			//Select工具
			if (currentMode==1){
				if (SelectedObject!=null){
					if (mouse_is_inside == 2){ //將重新和BasicObject連接
						reConnectLine(e.getX(),e.getY());
					}
				}
				else {
					if (!SelectedArea.isEmpty()){ //將選取範圍內的所有graph(ConnectionLine除外)加入SelectedAreaObjectList
						for (int i = GraphList.size()-1; i >= 0; i--) {
							graph gl = GraphList.get(i);
							if (CheckSelectedArea(gl) && gl.InGroup == false) {
								if (!ConnectionLine.class.isAssignableFrom(gl.getClass())){
									SelectedAreaObjectList.add(gl);
								}
							}
						}
						if (SelectedAreaObjectList.isEmpty()){
							SelectedArea.setBounds(0, 0, 0, 0);
						}
					}
				}
				repaint();
			}
			//ConnectionLine工具，找要和ConnectionLine連接的第二個BasicObject
			else if (currentMode>=2 && currentMode<=4){
				FindConnetedBasicObject(e.getX(), e.getY(), 2);
				if (graph1!=null && graph2!=null && graph1!=graph2){
					int graph1_port[] = graph1.GetPort(portnum1).getxy();
					int graph2_port[] = graph2.GetPort(portnum2).getxy();
					ConnectionLine obj = new ConnectionLine();
					if (currentMode==2){
						obj = new AssociationLine(graph1_port[0], graph1_port[1], graph2_port[0], graph2_port[1]);
					}
					else if (currentMode==3){
						obj = new GeneralizationLine(graph1_port[0], graph1_port[1], graph2_port[0], graph2_port[1]);
					}
					else if (currentMode==4){
						obj = new CompositionLine(graph1_port[0], graph1_port[1], graph2_port[0], graph2_port[1]);
					}
					GraphList.add(0, obj);
					obj.SetPorts(graph1.GetPort(portnum1), graph2.GetPort(portnum2));
					graph1.GetPort(portnum1).addLine(obj);
					graph2.GetPort(portnum2).addLine(obj);
					tmpConnectionLine = null;
					
				}
				else{
					if (tmpConnectionLine != null){
						tmpConnectionLine = null;
					}
				}
				repaint();
			}
		}
		//繪製ConnectionLine前，找和它連接的兩個BasicObject
		private void FindConnetedBasicObject(int mouse_x, int mouse_y, int time) { 
			for (int i = 0; i < GraphList.size(); i++) {
				graph gl = GraphList.get(i);
				int mouse_is_inside=gl.inside(mouse_x, mouse_y);
				if (mouse_is_inside==1){
					int closePortnum = ((BasicObject) gl).SwitchPort(mouse_x, mouse_y);
					if (time==1){
						graph1 = gl;
						portnum1 = closePortnum;
						break;
					}
					else if(time==2){
						graph2 = gl;
						portnum2 = closePortnum;
						break;
					}
				}
				else{
					if (time==1){
						graph1 = null;
						portnum1 = -1;
					}
					else if(time==2){
						graph2 = null;
						portnum2 = -1;
					}
				}
			}
		}
		//重新將ConnectionLine和BasicObject連接
		private void reConnectLine(int mouse_x, int mouse_y) {
			for (int i = 0; i < GraphList.size(); i++) {
				graph gl = GraphList.get(i);
				mouse_is_inside = gl.inside(mouse_x, mouse_y);
				graph Anothergl=((ConnectionLine) SelectedObject).GetAnotherPort().ConnectionBasicObject; //沒選到的Port
				if (mouse_is_inside==1 && gl!= Anothergl){
					int closePortnum = ((BasicObject) gl).SwitchPort(mouse_x, mouse_y);
					int portxy[] = gl.GetPort(closePortnum).getxy();
					SelectedObject.MoveLocation(portxy[0],portxy[1]);
					SelectedObject.GetPort().removeLine((ConnectionLine)SelectedObject);
					((ConnectionLine) SelectedObject).ResetPort(gl.GetPort(closePortnum));
					gl.GetPort(closePortnum).addLine((ConnectionLine)SelectedObject);
					break;
				}
				else{
					SelectedObject.MoveLocation(start_x,start_y);
				}
			}
	
		}
	}
	
    
}



