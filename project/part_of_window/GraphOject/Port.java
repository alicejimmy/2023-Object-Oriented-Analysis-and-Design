package part_of_window.GraphOject;
import java.util.*;

public class Port{
    public int x, y, width=5, height=5; //(x,y)座標及寬高
    public BasicObject ConnectionBasicObject; //與它相連的BasicObject
    private List<ConnectionLine> ConnectionLineList = new ArrayList<ConnectionLine>(); //與它相連的ConnectionLines
    public Port(BasicObject obj){
        this.ConnectionBasicObject = obj;
    }
    //設定(x,y)座標
    public void setPort(int x, int y) {
		this.x = x;
        this.y = y;
	}
    public int[] getxy(){
        int Portxy[] = {0, 0};
        Portxy[0] = x;
        Portxy[1] = y;
        return Portxy;
    }
    //增加相連的ConnectionLine
    public void addLine(ConnectionLine line) {
		ConnectionLineList.add(line);
	}
	//移除相連的ConnectionLine
	public void removeLine(ConnectionLine line) {
		ConnectionLineList.remove(line);
	}
    //BasicObject移動後重畫與Port相連的ConnectionLine
    public void resetLines() {
		for(int i = 0; i < ConnectionLineList.size(); i++){
			ConnectionLine line = ConnectionLineList.get(i);
			line.resetLocation();
		}
	}
}
