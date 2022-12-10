package molecules;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JComponent;

public class Radical extends JComponent{
	int height;
	// ���������� ������
	int x,y;
	int id;
	// ����� ��������
	ArrayList<Point> points;
	boolean showPoints = false;
	// ���������� ����� ���������
	int pointsCount = 4;
	// ������ ����� ���������
	int pointR;
	
	 public Radical() {
		 this.height = 2;
		 this.x = 0;
		 this.y = 0;
		 this.points = new ArrayList<Point>();
	     this.pointR = 1;
	     createPoints();
	 }
	
	 public Radical(int xCenter, int yCenter, int h) {
		 this.height = h;
		 this.x = xCenter;
		 this.y = yCenter;
		 this.points = new ArrayList<Point>();
	     this.pointR = (int)((this.height/2)/this.pointsCount);
	     createPoints();
	 }
	 
	 public void createPoints() {
		 if(this.pointsCount == 4) {
			 this.points.add(new Point(this.x - this.height/2,this.y));
			 this.points.add(new Point(this.x, this.y - this.height/2));
			 this.points.add(new Point(this.x + this.height/2,this.y));
			 this.points.add(new Point(this.x,this.y + this.height/2));
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
	 
	 public void paint(Graphics g) {
			g = (Graphics2D) g;
			g.setColor(Color.red);
			g.drawRect(x - this.height/2, y-this.height/2, height, height);
			g.drawRect(x - this.height/2+1, y-this.height/2+1, height-2, height-2);
			g.setColor(Color.black);
			g.drawRect(x - this.height/2+2, y-this.height/2+2, height-4, height-4);
			g.drawChars("R".toCharArray(), 0, 1, this.x, this.y);
			if(this.showPoints) {
				for(int i = 0; i< this.points.size(); i++) {
					g.drawOval(this.points.get(i).x - this.pointR, this.points.get(i).y-this.pointR, this.pointR*2, this.pointR*2);
				}
			}
		}
}
