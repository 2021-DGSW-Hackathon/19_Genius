package gui;

import java.awt.BorderLayout;
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

public class BaseJPanel extends JPanel {
	Point position;
	JFrame jf = null;
	
	public BaseJPanel(LayoutManager layout, JFrame jf) {
		this.jf = jf;
		setLayout(layout);
		setAction();
	}
	
	public BaseJPanel(JFrame jf) {
		this.jf = jf;
		setAction();
	}
	
	private void setAction() {
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				jf.setLocation(jf.getLocation().x + e.getX() - position.x,
						jf.getLocation().y + e.getY() - position.y);
			}
		});
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				position = e.getPoint();
			}
		});
	}
	
	public ImageIcon getImage(String name, int w, int h) {
		return new ImageIcon(new ImageIcon(System.getProperty("user.dir") + "\\image\\" + name + ".png").getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH));
	}
	
}
