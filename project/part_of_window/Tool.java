package part_of_window;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Tool extends JToolBar{
    private Button button_pushed = null; //被按下的button
    private Canvas canvas;
    public Tool(){
        //設定JToolBar
        setFloatable(false);
        setLayout(new GridLayout(6, 1));
        
        //工具列的6個按鈕
        Button select = new Button("select", new ImageIcon("Icon/select.png"), 1);
        add(select);
        Button association_line = new Button("association line", new ImageIcon("Icon/association_line.png"), 2);
        add(association_line);
        Button generalization_line = new Button("generalization line", new ImageIcon("Icon/generalization_line.png"), 3);
        add(generalization_line);
        Button composition_line = new Button("composition line", new ImageIcon("Icon/composition_line.png"), 4);
        add(composition_line);
        Button Class = new Button("class", new ImageIcon("Icon/class.png"), 5);
        add(Class);
        Button use_case = new Button("use case", new ImageIcon("Icon/use_case.png"), 6);
        add(use_case);
    }
    //工具列的Button
    private class Button extends JButton{
        private int Toolnum = 1; 
        public Button(String name, ImageIcon icon, int num){
            this.Toolnum = num;
			setToolTipText(name);
			setIcon(icon);
            setBackground(Color.LIGHT_GRAY);
			setFocusable(false);
            setActionCommand(name);
            addActionListener(new buttonListener());
		}
        //Button功能
        class buttonListener implements ActionListener{
            public void actionPerformed(ActionEvent e){
                if(button_pushed != null){
                    button_pushed.setBackground(Color.LIGHT_GRAY);
                }
                button_pushed = (Button) e.getSource();
                button_pushed.setBackground(Color.DARK_GRAY);
                canvas = Canvas.getInstance();
                canvas.setMode(Toolnum);
            }
        }
    }
}
