package part_of_window.GraphOject;
import java.awt.*;
import java.util.*;

//繼承自graph
public class Group extends graph{
    private int width, height; //寬高
    private int bound = 10; //留邊
    private ArrayList<graph> GroupObjectList = new ArrayList<graph>(); //在此Group範圍內的graphs

    //設定Group範圍
    public void setBound(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
		this.y2 = y2;
        this.width = x2-x1;
		this.height = y2-y1;
    }
    //增加在此Group範圍內的graphs
    public void addGraphs(ArrayList<graph> AreaObjectList) {
        int x1_min = Integer.MAX_VALUE, y1_min = Integer.MAX_VALUE, x2_max = Integer.MIN_VALUE, y2_max = Integer.MIN_VALUE;
        for (int i = 0; i < AreaObjectList.size(); i++) {
			graph gl = AreaObjectList.get(i);
            GroupObjectList.add(gl);
            gl.InGroup = true;
            if (gl.getx1() < x1_min) {
				x1_min = gl.getx1();
			}
            if (gl.gety1() < y1_min) {
				y1_min = gl.gety1();
			}
			if (gl.getx2() > x2_max) {
				x2_max = gl.getx2();
			}
			if (gl.gety2() > y2_max) {
				y2_max = gl.gety2();
			}
		}
        setBound(x1_min-bound, y1_min-bound, x2_max+bound, y2_max+bound);
	}
    //畫出Group
    public void draw(Graphics g){
        g.setColor(new Color(0, 204, 0, 255/4));
        g.fillRect(x1, y1, width, height);
        g.setColor(new Color(0, 204, 0));
        g.drawRect(x1, y1, width, height);
    }
    //(mouse_x,mouse_y)是否在BasicObject的範圍內
    public int inside(int mouse_x, int mouse_y){
        if (mouse_x >= x1 && mouse_x <= x2 && mouse_y >= y1 && mouse_y <= y2){
            return 3;
        }
        else{
            return 0;
        }
    }
    //移動
    public void MoveLocation(int moveX, int moveY) {
        this.x1 = x1 + moveX;
        this.y1 = y1 + moveY;
        this.x2 = x1 + width;
		this.y2 = y1 + height;
        for (int i = 0; i < GroupObjectList.size(); i++) {
			graph gl = GroupObjectList.get(i);
			gl.MoveLocation(moveX, moveY);
		}
    }
    //回傳在此Group範圍內的graphs
    public ArrayList<graph> getGroupObjectList(){
        return GroupObjectList;
    }
}
