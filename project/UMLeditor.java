import javax.swing.*;
import java.awt.BorderLayout;

import part_of_window.*;

public class UMLeditor extends JFrame{
	private Menu menu;
	private Tool tool;
	private Canvas canvas;
	private UMLeditor() {
		getContentPane().setLayout(new BorderLayout());
		//視窗中的菜單列
		menu = new Menu();
		getContentPane().add(menu, BorderLayout.NORTH);
		//視窗中的工具列
		tool = new Tool();
		getContentPane().add(tool, BorderLayout.WEST);
		//視窗中的畫布區
		canvas = Canvas.getInstance();
		getContentPane().add(canvas, BorderLayout.CENTER);
	}
	//main fucion
    public static void main(String[] args) {
        UMLeditor window = new UMLeditor();
		window.setTitle("UML editor");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(800, 500);
		window.setVisible(true);
    }
}