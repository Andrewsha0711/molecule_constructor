package molecules;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import javax.accessibility.Accessible;
import javax.swing.JComponent;

public class Bond extends JComponent implements Accessible{
	
	Atom atom1;
	Atom atom2;
	int index1 = 0;
	int index2 = 0;
	

    public Bond(Atom a, Atom b, int indx1, int indx2) {
    	this.atom1 = a;
    	this.atom2 = b;
    	this.index1 = indx1;
    	this.index2 = indx2;
	}
	public void paint(Graphics g) {
		//this.setSize(new Dimension(1280, 720));
		g = (Graphics2D) g;
		g.setColor(Color.blue);
		g.drawLine(this.atom1.points.get(index1).x,
				this.atom1.points.get(index1).y,
				this.atom2.points.get(index2).x,
				this.atom2.points.get(index2).y);
	}
}