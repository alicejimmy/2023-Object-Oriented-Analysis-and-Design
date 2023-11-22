package part_of_window.GraphOject;
import java.awt.*;

//繼承自ConnectionLine
public class CompositionLine extends ConnectionLine{
    private int ArrowWidth = 10, ArrowHeight = 10; //箭頭的寬高
    public CompositionLine(int x1, int y1, int x2, int y2){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
		this.y2 = y2;
    }
    //畫出CompositionLine
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        //畫線
        g.drawLine(x1, y1, x2, y2);
        //畫箭頭
        int [] Arrowx = new int[4];
        int [] Arrowy = new int[4];
        Arrowx[0] = x2;
        Arrowy[0] = y2;
        int dx = x2 - x1, dy = y2 - y1;
		double distence = Math.sqrt(dx*dx + dy*dy);
		double sin = dy/distence, cos = dx/distence;
        Arrowx[1] = (int) ((distence - ArrowHeight)*cos - ArrowWidth/2*sin + x1);
        Arrowy[1] = (int) ((distence - ArrowHeight)*sin + ArrowWidth/2*cos + y1);
        Arrowx[3] = (int) ((distence - ArrowHeight)*cos + ArrowWidth/2*sin + x1);
        Arrowy[3] = (int) ((distence - ArrowHeight)*sin - ArrowWidth/2*cos + y1);
        Arrowx[2] = (int) ((distence - ArrowHeight)*cos - ArrowHeight*cos + x1);
        Arrowy[2] = (int) ((distence - ArrowHeight)*sin - ArrowHeight*sin + y1);
        g.fillPolygon(Arrowx, Arrowy, 4);
	}
}
