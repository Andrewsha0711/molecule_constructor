package molecules;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JComponent;

public class Palette extends JComponent{
	// ���������� ��������� � �������
	int componentsCount;
	// ���������� �������
	int xLeft, xRight, yUpper, yLower;
	// ������ ������
	int r = 30;
	// ������� ������� ������
	ArrayList<Point> positions;
	// ��������������� �����, ����������� � �������
	ArrayList<Color> colors;
	ArrayList<String> symbols;
	ArrayList<Integer> sizes;
	// �����
	Font font;
	
	
	public Palette(int x0, int y0, int x1, int y1) {
		this.xLeft = x0;
		this.xRight = x1;
		this.yLower = y1;
		this.yUpper = y0;
		
		this.positions = new ArrayList<Point>();
		this.colors = new ArrayList<Color>();
		this.symbols = new ArrayList<String>();
		this.sizes = new ArrayList<Integer>();
		this.colors.add(Color.cyan);
		this.symbols.add("H");
		this.sizes.add(25);
		this.colors.add(Color.pink);
		this.symbols.add("N");
		this.sizes.add(40);
		this.colors.add(Color.yellow);
		this.symbols.add("C");
		this.sizes.add(40);
		this.colors.add(Color.blue);
		this.symbols.add("O");
		this.sizes.add(50);
		
		this.font = new Font("TimesRoman", Font.BOLD, 22);
	}
	
	public void paint(Graphics g) {
		g.setFont(this.font);
		g = (Graphics2D) g;
		g.drawLine(this.xLeft, this.yUpper, this.xLeft, this.yLower);
		g.drawLine(this.xLeft, this.yUpper, this.xRight, this.yUpper);
		g.drawLine(this.xRight, this.yUpper, this.xRight, this.yLower);
		g.drawLine(this.xLeft, this.yLower, this.xRight, this.yLower);
		
		int stepY = (this.yLower - this.yUpper)/5;
		int xOval = this.xLeft + (this.xRight - this.xLeft)/4;
		int xSymbol = this.xLeft + (this.xRight - this.xLeft)/4*3;
		int yOval = this.yUpper + stepY/2;
		for(int i = 0; i < 5; i++) {
			g.drawLine(this.xLeft, this.yUpper + stepY*(i+1), this.xRight, this.yUpper + stepY*(i+1));
		}
		if(this.positions.size()!=0)
			this.positions.clear();
		for(int i = 0; i < 4; i++) {
			g.setColor(this.colors.get(i));
			g.fillOval(xOval-this.r, yOval-this.r, 2*r, 2*r);
			g.setColor(Color.black);
			g.drawOval(xOval-this.r, yOval-this.r, 2*r, 2*r);
			g.drawChars(this.symbols.get(i).toCharArray(), 0, this.symbols.get(i).length(), xSymbol, yOval);
			this.positions.add(new Point(xOval,yOval));
			yOval += stepY;
		}
		this.positions.add(new Point(xOval,yOval));
		new Radical(xOval,yOval,50).paint(g);
	}
	
	public JComponent tryToCreateAtom(int x, int y) {
		int i = 0;
		for(i = 0; i < (this.positions.size()-1); i++) {
			if(x >= (this.positions.get(i).x - this.r) && x <= (this.positions.get(i).x + this.r))
				if(y >= (this.positions.get(i).y - this.r) && y <= (this.positions.get(i).y + this.r)) {
					return new Atom(x, y, this.sizes.get(i), this.colors.get(i), -1);
				}
		}
		if(x >= (this.positions.get(i).x - this.r) && x <= (this.positions.get(i).x + this.r))
			if(y >= (this.positions.get(i).y - this.r) && y <= (this.positions.get(i).y + this.r)) {
				return new Radical(x, y, 50);
			}
		return null;
	}
}
