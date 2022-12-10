package molecules;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.accessibility.Accessible;
import javax.swing.JComponent;

public class Atom extends JComponent {
	
	int x, y, r;
	int id; // id
	boolean showPoints = false;
	// ���������� ��������� ����������
	int pointsCount = 8;
	// ������ ����� ���������
	int pointR;
	Color color;
	ArrayList<Point> points;
	
    public Atom() {
    	this.x = 1;
    	this.y = 1;
    	this.r = 1;
    	this.points = new ArrayList<Point>();
    	this.pointR = this.r/this.pointsCount*2;
    	this.id = -1;
    	createPoints();
	}
	
    public Atom(int xValue, int yValue, int rValue) {
    	this.x = xValue;
    	this.y = yValue;
    	this.r = rValue;
    	this.points = new ArrayList<Point>();
    	// ������ ����� ���������
    	this.pointR = (int)(this.r/this.pointsCount*1.5);
    	this.color = Color.white;
    	createPoints();
	}
    
    public Atom(int xValue, int yValue, int rValue, Color c, int idValue) {
    	this.x = xValue;
    	this.y = yValue;
    	this.r = rValue;
    	this.color = c;
    	this.points = new ArrayList<Point>();
    	this.pointR = (int)(this.r/this.pointsCount*1.5);
    	this.id = idValue;
    	createPoints();
	}
    
    public void createPoints() {
    	for(int i = 0; i < this.pointsCount; i++) {
			double xTemp = (double)this.r * Math.cos(Math.toRadians(((double)360/(double)this.pointsCount)*i)) + (double)this.x;
			double yTemp = this.y - this.r*Math.sin(Math.toRadians((360/this.pointsCount)*i));
			this.points.add(new Point((int)xTemp,(int)yTemp));
		}
    }
	public void paint(Graphics g) {
		//this.setSize(new Dimension(1280, 720));
		g = (Graphics2D) g;
		g.setColor(this.color);
		g.fillOval(this.x - r, this.y - r, 2*r, 2*r);
		g.setColor(Color.black);
		g.drawOval(this.x - r, this.y - r, 2*r, 2*r);
		if (this.showPoints == true) {
			if(this.points.size()!=0)
				this.points.clear();
			for(int i = 0; i < this.pointsCount; i++) {
				double xTemp = (double)this.r * Math.cos(Math.toRadians(((double)360/(double)this.pointsCount)*i)) + (double)this.x;
				double yTemp = this.y - this.r*Math.sin(Math.toRadians((360/this.pointsCount)*i));
				g.setColor(Color.BLACK);
				g.drawOval((int)xTemp-this.pointR, (int)yTemp-this.pointR, 2*this.pointR, 2*this.pointR);
				g.setColor(Color.WHITE);
				g.fillOval((int)xTemp-this.pointR, (int)yTemp-this.pointR, 2*this.pointR, 2*this.pointR);
				this.points.add(new Point((int)xTemp,(int)yTemp));
			}
		}
	}
	
	public int findPoint(int xValue, int yValue) {
		for (int i = 0; i< this.pointsCount; i++) {
			if(xValue <= this.points.get(i).x + this.pointR && xValue >= this.points.get(i).x - this.pointR)
				if(yValue <= this.points.get(i).y + this.pointR && yValue >= this.points.get(i).y - this.pointR) {
					return i;
				}
		}
		return -1;
	}
	
}
