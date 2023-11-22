package part_of_window.GraphOject;
import java.awt.*;

//繼承自graph，Class, UseCase將會繼承它
public class BasicObject extends graph{
    protected int width, height; //寬、高
    protected String ObjectName = "Object Name"; //名字
    private Port[] ObjectPorts = new Port[4]; //四個Prots

    //建立ObjectPorts
    protected void CreatePorts() {
        int[] Portsx = {x1, x1 + width/2, x2, x1 + width/2};
		int[] Portsy = {y1 + height/2, y1,  y1 + height/2, y2};
        for(int i = 0; i < ObjectPorts.length; i++) {
            Port port = new Port(this);
            port.setPort(Portsx[i], Portsy[i]);
            ObjectPorts[i] = port;
		}
    }
    //畫出ObjectPorts
    public void ShowPorts(Graphics g) {
		for(int i = 0; i < ObjectPorts.length; i++) {
            if (i==0){
                g.fillRect(ObjectPorts[i].x-ObjectPorts[i].width, ObjectPorts[i].y, ObjectPorts[i].width, ObjectPorts[i].height);
            }
            else if (i==1){
                g.fillRect(ObjectPorts[i].x, ObjectPorts[i].y-ObjectPorts[i].height, ObjectPorts[i].width, ObjectPorts[i].height);
            }
            else{
                g.fillRect(ObjectPorts[i].x, ObjectPorts[i].y, ObjectPorts[i].width, ObjectPorts[i].height);
            }
		}
	}
    //(mouse_x,mouse_y)是否在BasicObject的範圍內
    public int inside(int mouse_x, int mouse_y){
        if (mouse_x >= x1 && mouse_x <= x2 && mouse_y >= y1 && mouse_y <= y2){
            return 1;
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
        int[] Portsx = {x1, x1 + width/2, x2, x1 + width/2};
		int[] Portsy = {y1 + height/2, y1,  y1 + height/2, y2};
        for(int i = 0; i < ObjectPorts.length; i++) {
            ObjectPorts[i].setPort(Portsx[i], Portsy[i]);
			ObjectPorts[i].resetLines();
		}
    }
    //回傳指定portnum的ObjectPorts
    public Port GetPort(int portnum) {
		return ObjectPorts[portnum];
	}
    //回傳離(mouse_x,mouse_y)最近的ObjectPorts的num
    public int SwitchPort (int mouse_x, int mouse_y){ 
        int closePortnum = -1; //離(mouse_x,mouse_y)最近的Port的num
        double shortdistance = Float.POSITIVE_INFINITY; //ObjectPorts中離(mouse_x,mouse_y)的最短距離
        for (int i = 0; i < ObjectPorts.length; i++){
            double distance = Math.sqrt(Math.pow((ObjectPorts[i].x-mouse_x), 2) + Math.pow((ObjectPorts[i].y-mouse_y), 2));
            if (distance < shortdistance){
                shortdistance=distance;
                closePortnum=i;
            }
        }
        return closePortnum;
    }
    //改名字
    public void ChangeName(String name){
		this.ObjectName = name;
	}
}
