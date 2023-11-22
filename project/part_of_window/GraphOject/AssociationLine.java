package part_of_window.GraphOject;
import java.awt.*;

//繼承自ConnectionLine
public class AssociationLine extends ConnectionLine{
    private int ArrowWidth = 10, ArrowHeight = 10; //箭頭的寬高
    public AssociationLine(int x1, int y1, int x2, int y2){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
		this.y2 = y2;
    }
    //畫出AssociationLine
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        //畫線
        g.drawLine(x1, y1, x2, y2);
        //畫箭頭
        int [] Arrowx = new int[2];
        int [] Arrowy = new int[2];
        int dx = x2 - x1, dy = y2 - y1;
		double distence = Math.sqrt(dx*dx + dy*dy);
		double sin = dy/distence, cos = dx/distence;
        Arrowx[0] = (int) ((distence - ArrowHeight)*cos - ArrowWidth/2*sin + x1);
        Arrowy[0] = (int) ((distence - ArrowHeight)*sin + ArrowWidth/2*cos + y1);
        Arrowx[1] = (int) ((distence - ArrowHeight)*cos + ArrowWidth/2*sin + x1);
        Arrowy[1] = (int) ((distence - ArrowHeight)*sin - ArrowWidth/2*cos + y1);
        g.drawLine(Arrowx[0], Arrowy[0], x2, y2);
        g.drawLine(Arrowx[1], Arrowy[1], x2, y2);
	}
}
