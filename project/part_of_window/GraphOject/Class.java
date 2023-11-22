package part_of_window.GraphOject;
import java.awt.*;

//繼承自BasicObject
public class Class extends BasicObject{
    public Class(int x, int y){
        this.width = 120;
		this.height = 90;
        this.x1=x;
        this.y1=y;
        this.x2 = x1 + width;
		this.y2 = y1 + height;
        CreatePorts();
    }
    //畫出Class
    public void draw(Graphics g) {
        // g.setColor(new Color(204, 204, 204, 255/6));
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(x1, y1, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(x1, y1, width, height);
		g.drawLine(x1, y1 + height/3, x2, y1 + height/3);
		g.drawLine(x1, y2 - height/3, x2, y2 - height/3);
        int stringWidth = g.getFontMetrics().stringWidth(ObjectName);
        g.drawString(ObjectName, x1 + (width-stringWidth)/2, y1 + 20);
	}
}
