package molecules;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JComponent;

public class WorkingSpaceComponent extends JComponent{
	    private static final int DEF_WIDTH = 1280;
	    private static final int DEF_HEIGHT = 720;
	    
	    Model model;
		//ArrayList<Atom> atoms;
		//ArrayList<Bond> bonds;
		Palette palette;
		int counter;
		
		
	    public Dimension getPreferredSize() {
	        return new Dimension(DEF_WIDTH, DEF_HEIGHT);
	    }
		
		public WorkingSpaceComponent() {	
			this.counter = 0;
			this.model = new Model();
			//this.atoms = new ArrayList<Atom>();
			//this.bonds = new ArrayList<Bond>();
			int step = 100;			
			palette = new Palette(DEF_WIDTH-DEF_WIDTH/7,step,DEF_WIDTH,DEF_HEIGHT-step);
			this.addMouseListener(new CustomMouseListener());
		}
		
		public void paint(Graphics g) {
			this.setSize(new Dimension(1280, 720));
			g = (Graphics2D) g;
			
			// ���������� "�������"
			palette.paint(g);
			
			// ���������� ������������ ���������
			// �����
			for(int i = 0; i<this.model.atoms.size(); i++) {
				this.model.atoms.get(i).paint(g);
			}
			// �����
			for(int i = 0; i<this.model.bonds.size(); i++) {
				this.model.bonds.get(i).paint(g);
			}
			// ��������
			for(int i = 0; i<this.model.radical.size(); i++) {
				this.model.radical.get(i).paint(g);
			}
		}
		
		public JComponent findElement(int xValue, int yValue) {
			if(this.model.findAtom(xValue, yValue) != null)
				return this.model.findAtom(xValue, yValue);
			if(this.model.findRadical(xValue, yValue) != null)
				return this.model.findRadical(xValue, yValue);
			return null;
		}
		
		public Atom findHighlightedAtom(int xValue, int yValue) {
			for(int i = 0; i < this.model.atoms.size(); i++) {
				if (xValue >= (this.model.atoms.get(i).x - this.model.atoms.get(i).r - 4) && xValue <= (this.model.atoms.get(i).x + this.model.atoms.get(i).r + 4))
					if (yValue >= (this.model.atoms.get(i).y - this.model.atoms.get(i).r - 4) && yValue <= (this.model.atoms.get(i).y + this.model.atoms.get(i).r + 4)) {
	        			 return this.model.atoms.get(i); 
	        		  }
			}
			return null;
		}
		
		public boolean isInPalette(int x, int y) {
			if(x >= this.palette.xLeft &&
			   x <= this.palette.xRight &&
			   y >= this.palette.yUpper &&
			   y <= this.palette.yLower)
				return true;
			return false;
		}
		public boolean tryToGetElement(int x, int y) {
			Atom newAtom;
			Radical newRadical;
			JComponent element;
			if((element = this.palette.tryToCreateAtom(x, y))!=null) {
				if(element instanceof Atom) {
					newAtom = (Atom)element;
					newAtom.id = this.counter;
					this.counter += 1;
					this.model.addAtom(newAtom);
				}
				if(element instanceof Radical) {
					newRadical = (Radical)element;
					newRadical.id = this.counter;
					this.counter += 1;
					this.model.addRadical(newRadical);
				}
				return true;
			}
			return false;
		}
		
		public int isBondExists(Atom a1, Atom a2, int ind1, int ind2) {
			for(int i = 0; i<this.model.bonds.size(); i++) {
				if(this.model.bonds.get(i).atom1.id == a1.id &&
				   this.model.bonds.get(i).atom2.id == a2.id &&
				   this.model.bonds.get(i).index1 == ind1 &&
				   this.model.bonds.get(i).index2 == ind2)
					return i;
				if(this.model.bonds.get(i).atom2.id == a1.id &&
						   this.model.bonds.get(i).atom1.id == a2.id &&
						   this.model.bonds.get(i).index1 == ind2 &&
						   this.model.bonds.get(i).index2 == ind1)
							return i;

			}
			return -1;
		}
		public void deleteAtom(Atom a) {
			
			for (int i = 0; i< a.points.size(); i++) {
				for (int j = 0; j< this.model.bonds.size(); j++) {
					if(this.model.bonds.get(j).atom1 == a || this.model.bonds.get(j).atom2 == a)
						if(this.model.bonds.get(j).index1 == i || this.model.bonds.get(j).index2 == i)
							this.model.bonds.remove(j);
				}
			}
			this.model.atoms.remove(a);
		}
}
