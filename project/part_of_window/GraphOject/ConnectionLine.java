package part_of_window.GraphOject;
import java.awt.geom.*;

//繼承自graph，AssociationLine, GeneralizationLine, CompositionLine將會繼承它
public class ConnectionLine extends graph{
    private int HeadorTail = 1; //0:head(start), 1:tail(end)
    private Port[] ObjectPorts = new Port[2]; //與它相連的Ports

    //(mouse_x,mouse_y)是否在BasicObject的範圍內
    public int inside(int mouse_x, int mouse_y){
        Line2D line = new Line2D.Double(x1, y1, x2, y2);
        double distance = line.ptLineDist(mouse_x, mouse_y); //(mouse_x,mouse_y)與ConnectionLine的距離
        if (distance <= 3){
            double distToStart = Math.sqrt(Math.pow((mouse_x - x1),2) + Math.pow((mouse_y - y1), 2)); //(mouse_x,mouse_y)與head Port的距離
			double distToEnd = Math.sqrt(Math.pow((mouse_x - x2),2) + Math.pow((mouse_y - y2), 2));//(mouse_x,mouse_y)與tail Port的距離
			if(distToStart <= distToEnd) {
				HeadorTail = 0;
			}
			else{
				HeadorTail = 1;
			}
            return 2;
        }
        else{
            return 0;
        }
    }
    //改變線當前head或tail的位置
    public void MoveLocation(int mouse_x, int mouse_y) {
        if (HeadorTail == 0){
            this.x1 = mouse_x;
			this.y1 = mouse_y;
        }
        else if (HeadorTail == 1){
            this.x2 = mouse_x;
			this.y2 = mouse_y;
        }
    }
    //設定與它相連的Ports
    public void SetPorts(Port port1, Port port2) {
		this.ObjectPorts[0] = port1;
		this.ObjectPorts[1] = port2;
	}
    //重設相連的Port
    public void ResetPort(Port port) {
        if (HeadorTail == 0){
            this.ObjectPorts[0] = port;
        }
        else if (HeadorTail == 1){
            this.ObjectPorts[1] = port;
        }
	}
    //回傳當前選到的Port
    public Port GetPort() {
		return ObjectPorts[HeadorTail];
	}
    //回傳當前沒選到的Port
    public Port GetAnotherPort() {
		return ObjectPorts[(1-HeadorTail)];
	}
    //回傳當前選到的Port的初始座標(x,y)
    public int[] GetInitialxy(){
        int Portxy[] = {0, 0};
        if (HeadorTail == 0){
            Portxy[0] = x1;
            Portxy[1] = y1;
        }
        else if (HeadorTail == 1){
            Portxy[0] = x2;
            Portxy[1] = y2;
        }
        return Portxy;
    }
    //BasicObject移動後重畫相連的ConnectionLine
    public void resetLocation(){
        this.x1 = ObjectPorts[0].x;
		this.y1 = ObjectPorts[0].y;
		this.x2 = ObjectPorts[1].x;
		this.y2 = ObjectPorts[1].y;
    }
}
