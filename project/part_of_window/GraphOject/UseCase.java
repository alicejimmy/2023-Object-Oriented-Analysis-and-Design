package part_of_window.GraphOject;
import java.awt.*;

//繼承自BasicObject
public class UseCase extends BasicObject{
    public UseCase(int x, int y){
        this.width = 120;
		this.height = 60;
        this.x1=x;
        this.y1=y;
        this.x2 = x1 + width;
		this.y2 = y1 + height;
        CreatePorts();
    }
    //畫出UseCase
    public void draw(Graphics g) {
        // g.setColor(new Color(204, 204, 204, 255/6));
        g.setColor(Color.LIGHT_GRAY);
        g.fillOval(x1, y1, width, height);
        g.setColor(Color.BLACK);
        g.drawOval(x1, y1, width, height);
        int stringWidth = g.getFontMetrics().stringWidth(ObjectName);
        g.drawString(ObjectName, x1 + (width-stringWidth)/2, y1 + 35);
	}
}
