package molecules;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

public class CustomMouseListener implements MouseListener {

	int stepX = 0; // ������� ����� ����������� ���� �� ������ � �� ����������
	int stepY = 0; // ������� ����� ����������� ���� �� ������ � �� ����������

	Thread mouseT;
	Thread mouseT1;

	int tempIndex;
	Atom tempAtom;
	boolean doubleClick = false;

	public void mouseClicked(MouseEvent e) {
		WorkingSpaceComponent comp = (WorkingSpaceComponent) e.getSource();
	}

	// ���� � ������� ����
	public void mouseEntered(MouseEvent e) {
		Point location = MouseInfo.getPointerInfo().getLocation();
		// ���������� �� ������
		double x = location.getX();
		double y = location.getY();
		// ������� ����� ������������ �� ������ � �� ���������� � ������ ���������
		this.stepX = (int) x - e.getX();
		this.stepY = (int) y - e.getY();
		WorkingSpaceComponent comp = (WorkingSpaceComponent) e.getSource();
		// ����� ��� ������������ ��������� �� ����������
		this.mouseT1 = new MouseThread1(comp, stepX, stepY);
		this.mouseT1.start();
	}

	// ���� �� � ������� ����
	public void mouseExited(MouseEvent e) {
		WorkingSpaceComponent comp = (WorkingSpaceComponent) e.getSource();
		if (this.mouseT1 != null) {
			if (this.mouseT1.isAlive()) {
				this.mouseT1.interrupt();
			}
		}
	}

	// ����������� ��� ������ ������
	public void mousePressed(MouseEvent e) {
		WorkingSpaceComponent comp = (WorkingSpaceComponent) e.getSource();
		JComponent element;
		if ((element = comp.findElement(e.getX(), e.getY())) != null) {
			int index = -1;
			// � ������ ������� �� ������ ��� ����������
			Atom atomTemp = null;
			Radical radicalTemp = null;
			if (element instanceof Atom) {
				atomTemp = (Atom) element;
			}
			if (element instanceof Radical) {
				radicalTemp = (Radical) element;
			}
			if ((atomTemp != null && (index = (atomTemp).findPoint(e.getX(), e.getY())) != -1)
					|| (radicalTemp != null && (index = (radicalTemp).findPoint(e.getX(), e.getY())) != -1)) {
				if ((index = (atomTemp).findPoint(e.getX(), e.getY())) != -1) {
					if (this.doubleClick == true) {
						// this.lines.put(this.startPoint, point);
						int bondIndex = -1;
						if ((bondIndex = comp.isBondExists((Atom) element, this.tempAtom, index,
								this.tempIndex)) != -1) {
							comp.model.bonds.remove(bondIndex);
						} else {
							comp.model.bonds.add(new Bond((Atom) element, this.tempAtom, index, this.tempIndex));
						}
						this.doubleClick = false;
					} else {
						this.tempAtom = (Atom) element;
						this.tempIndex = index;
						this.doubleClick = true;
					}
				}
				if (radicalTemp != null && (index = (radicalTemp).findPoint(e.getX(), e.getY())) != -1) {
				}
			}
			// ���� ������� ��������� � ���� ����� �� ���������� ��������������
			else {
				System.out.println("���� ��������");
				if (e.getButton() == MouseEvent.BUTTON2) {
					comp.deleteAtom((Atom) element);
				} else {
					// ����� ��� ������������ ��������������
					this.mouseT = new MouseThread(element, comp, stepX, stepY);
					this.mouseT.start();
				}
			}
		}
		if (comp.isInPalette(e.getX(), e.getY())) {
			System.out.println("�� �������");
			JComponent tempEl;
			if (comp.tryToGetElement(e.getX(), e.getY())) {
				if ((tempEl = comp.findElement(e.getX(), e.getY())) != null) {
					this.mouseT = new MouseThread(tempEl, comp, stepX, stepY);
					this.mouseT.start();
				}
			}
		}
		// System.out.println("������ ������");
	}

	// ����������� ����� ���������� ������
	public void mouseReleased(MouseEvent e) {
		WorkingSpaceComponent comp = (WorkingSpaceComponent) e.getSource();
		if (this.mouseT != null) {
			if (this.mouseT.isAlive())
				this.mouseT.interrupt();
			// System.out.println("������ ��������");
		}
	}

	public class MouseThread extends Thread {

		private volatile boolean canRun = true;
		JComponent element;
		WorkingSpaceComponent comp;
		// ���������� ��������� ���� ������ �� ����������
		int dx, dy;

		MouseThread(JComponent value, WorkingSpaceComponent compValue, int a, int b) {
			this.element = value;
			this.comp = compValue;
			this.dx = a;
			this.dy = b;
		}

		public void changeAction() {
			canRun = !canRun;
		}

		public void run() {
			System.out.println("�����");
			if (!Thread.interrupted()) {
				while (true) {
					try {
						Point location = MouseInfo.getPointerInfo().getLocation();
						if (this.element instanceof Atom) {
							System.out.println("����");
							((Atom) element).x = (int) location.getX() - this.dx;
							((Atom) element).y = (int) location.getY() - this.dy;
						} else {
							System.out.println("�������");
							// System.out.println(((Radical)element).x);
							(comp.model.radical.get(0)).x = (int) location.getX() - this.dx;
							(comp.model.radical.get(0)).y = (int) location.getY() - this.dy;
						}
						comp.repaint();
						sleep(20);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						return;
						// e.printStackTrace();
					}
				}
			}
		}
	}

	public class MouseThread1 extends Thread {

		private volatile boolean canRun = true;
		Atom atom;
		WorkingSpaceComponent comp;
		int dx;
		int dy;

		MouseThread1(WorkingSpaceComponent compValue, int a, int b) {
			this.comp = compValue;
			this.dx = a;
			this.dy = b;
		}

		public void changeAction() {
			canRun = !canRun;
		}

		public void run() {
			if (!Thread.interrupted()) {
				while (true) {
					try {
						Point location = MouseInfo.getPointerInfo().getLocation();
						double x = location.getX() - dx;
						double y = location.getY() - dy;

						if ((atom = comp.findHighlightedAtom((int) x, (int) y)) != null) {
							atom.showPoints = true;
							atom.repaint();
							comp.repaint();
						} else {
							for (int i = 0; i < comp.model.atoms.size(); i++) {
								comp.model.atoms.get(i).showPoints = false;
								comp.model.atoms.get(i).repaint();
							}
							comp.repaint();
						}
						sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						return;
						// e.printStackTrace();
					}
				}
			}
		}
	}
}
