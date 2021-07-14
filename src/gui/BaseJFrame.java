package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BaseJFrame extends JFrame {

	Point position;
	
	protected void setFrame(int w, int h) {
		setSize(w, h);
		setUndecorated(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setIconImage(getImage("icon", 10, 10).getImage());
	}
	
	public ImageIcon getImage(String name, int w, int h) {
		return new ImageIcon(new ImageIcon(System.getProperty("user.dir") + "\\image\\" + name + ".png").getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH));
	}
}
