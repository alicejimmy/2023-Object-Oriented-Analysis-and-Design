package part_of_window.GraphOject;
import java.awt.*;

//BasicObject, ConnectionLine, Group將會繼承它
public class graph{
    protected int x1, y1, x2, y2; //左上(x1,y1)及右下(x2,y2)座標
    public boolean InGroup = false; //是否已被Group過
    //取得左上(x1,y1)及右下(x2,y2)座標
    public int getx1(){
        return x1;
    }
    public int gety1(){
        return y1;
    }
    public int getx2(){
        return x2;
    }
    public int gety2(){
        return y2;
    }
    public void draw(Graphics g){} //畫出graph
    public int inside(int mouse_x, int mouse_y){return 0;} //是否在graph的範圍內，0:無, 1:BasicObject, 2:ConnectionLine, 3:Group
    public void MoveLocation(int moveX, int moveY) {} //移動graph
    public Port GetPort() {return null;} //回傳graph的Port(ConnectionLine用)
    public Port GetPort(int portnum) {return null;} //回傳graph的Port(BasicObject用)
    public void ShowPorts(Graphics g) {} //BasicObject用
}
