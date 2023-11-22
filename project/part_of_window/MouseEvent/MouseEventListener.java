package part_of_window.MouseEvent;
import java.awt.event.*;

//滑鼠事件(預設)
public class MouseEventListener implements MouseListener, MouseMotionListener{
    //MouseListener
    public void mouseClicked(MouseEvent e) {
        // System.out.println("mouseClicked");
    }
    public void mousePressed(MouseEvent e) {
        // System.out.println("mousePressed");
    }
    public void mouseReleased(MouseEvent e) {
        // System.out.println("mouseReleased");
    }
    public void mouseEntered(MouseEvent e) {
        // System.out.println("mouseEntered");
    }
    public void mouseExited(MouseEvent e) {
        // System.out.println("mouseExited");
    }
    //MouseMotionListener
    public void mouseDragged(MouseEvent e) {
        // System.out.println("mouseDragged");
    }
    public void mouseMoved(MouseEvent e) {
        // System.out.println("mouseMoved");
    }
}
