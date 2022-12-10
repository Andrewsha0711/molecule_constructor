package molecules;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class Frame extends JFrame{
    public static final int DEF_Width = 1280;
    public static final int DEF_Height = 720;
    
    WorkingSpaceComponent mainField;
        
    public Frame() throws Exception{
    	this.mainField = new WorkingSpaceComponent();
    	
    	 JMenuBar menuBar = new JMenuBar();
         // ���������� � ������� ���� ���������� ������� ����  
    	 JMenu file = new JMenu("����");
         // ����� ���� "�������" � ������������
         JMenuItem open = new JMenuItem("�������");
         JMenuItem save = new JMenuItem("���������");
         file.add(open);
         file.addSeparator();
         file.add(save);
         // ���������� �����������
         menuBar.add(file);
         // ���������� ���� � ���������� ����������
         setJMenuBar(menuBar);
         // �������� ����
         setVisible(true);
    	
    	open.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("�������� �����");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int result = fileChooser.showOpenDialog(Frame.this);   
                if (result == JFileChooser.APPROVE_OPTION) {
                	System.out.println(fileChooser.getSelectedFile());
                	Model model = new Model();
                	try {
						if(model.readData(fileChooser.getSelectedFile().getAbsolutePath())==true) {
							mainField.model.atoms.clear();
							mainField.model.bonds.clear();
							for(int i = 0; i<model.atoms.size(); i++) {
								mainField.model.atoms.add(model.atoms.get(i));
								mainField.counter += 1;
							}
							for(int i = 0; i<model.bonds.size(); i++) {
								mainField.model.bonds.add(model.bonds.get(i));
							}
							System.out.println(model.atoms.size());
							System.out.println(mainField.model.atoms.size());
							//System.out.println(mainField.bonds.size());
							mainField.repaint();
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                }
            }
        });
    	
    	save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("���������� �����");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int result = fileChooser.showSaveDialog(Frame.this);   
                if (result == JFileChooser.APPROVE_OPTION) {
                	System.out.println(fileChooser.getSelectedFile());
                	try {
						mainField.model.saveModelToFile(fileChooser.getSelectedFile().getAbsolutePath(), true);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                }
            }
        });
    	this.getContentPane().add(BorderLayout.WEST, this.mainField);
    	pack();
    }
    
    public void createAddOptionBox(JPanel panel) {
    }
    
    public void createColorBoxes(JPanel panel) {
    }
}
