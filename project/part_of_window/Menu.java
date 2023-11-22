package part_of_window;
import javax.swing.*;
import java.awt.event.*;

public class Menu extends JMenuBar{
    private Canvas canvas;
    public Menu() {
        //菜單列的File選項
        JMenu file = new JMenu("File");
        add(file);
        //菜單列的Edit選項
        JMenu edit = new JMenu("Edit");
        add(edit);
        //Edit選項中的Group功能
        JMenuItem group= new JMenuItem("Group");
        group.addActionListener(new GroupListener());
        edit.add(group);
        //Edit選項中的Ungroup功能
        JMenuItem ungroup= new JMenuItem("Ungroup");
        ungroup.addActionListener(new UngroupListener());
        edit.add(ungroup);
        //Edit選項中的Change object name功能
        JMenuItem change_object_name= new JMenuItem("Change object name");
        change_object_name.addActionListener(new ChangeObjectNameListener());
        edit.add(change_object_name);
    }
    //Group功能
    class GroupListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
            canvas = Canvas.getInstance();
			canvas.GroupObjects();
		}
	}
    //Ungroup功能
    class UngroupListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
            canvas = Canvas.getInstance();
			canvas.UngroupObjects();
		}
	}
    //Change object name功能
    class ChangeObjectNameListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JFrame ChangeNameWindow = new JFrame();
            String NewName = JOptionPane.showInputDialog(ChangeNameWindow, "Enter your message");
            canvas = Canvas.getInstance();
            canvas.ChangeSelectedObjectName(NewName);
	    }
    }
}