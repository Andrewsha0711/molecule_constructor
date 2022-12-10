package molecules;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class Model {
	
	ArrayList<Atom> atoms;
	ArrayList<Bond> bonds;
	ArrayList<Radical> radical;
	
	Model(){
		this.atoms = new ArrayList<Atom>();
		this.bonds = new ArrayList<Bond>();
		this.radical = new ArrayList<Radical>();
	}
		
	public void addAtom(Atom atom) {
		this.atoms.add(atom);
	}
	public void addBond(Bond bond) {
		this.bonds.add(bond);
	}
	public void addRadical(Radical rad) {
		this.radical.add(rad);
	}
	// ������� ����, ������� �������� �������� ����������
	public Atom findAtom(int xValue, int yValue) {
		for(int i = 0; i < this.atoms.size(); i++) {
			if (xValue >= (this.atoms.get(i).x - this.atoms.get(i).r - this.atoms.get(i).pointR) 
					&& xValue <= (this.atoms.get(i).x + this.atoms.get(i).r + this.atoms.get(i).pointR))
				if (yValue >= (this.atoms.get(i).y - this.atoms.get(i).r- this.atoms.get(i).pointR) 
				&& yValue <= (this.atoms.get(i).y + this.atoms.get(i).r + this.atoms.get(i).pointR)) {
        			 return this.atoms.get(i); 
        		  }
		}
		return null;
	}
	// ������� �������, ������� �������� �������� ����������
	public Radical findRadical(int xValue, int yValue) {
		for(int i = 0; i < this.radical.size(); i++) {
			if (xValue >= (this.radical.get(i).x - this.radical.get(i).height/2 - this.radical.get(i).pointR) 
					&& xValue <= (this.radical.get(i).x + this.radical.get(i).height/2 + this.radical.get(i).pointR))
				if (yValue >= (this.radical.get(i).y - this.radical.get(i).height/2- this.radical.get(i).pointR) 
				&& yValue <= (this.radical.get(i).y + this.radical.get(i).height/2 + this.radical.get(i).pointR)) {
        			 return this.radical.get(i); 
        		  }
		}
		return null;
	}
	 public boolean saveModelToFile(String FileName, boolean UpdateModel) throws java.io.IOException
	    {
		    FileWriter dataOut = null;
		    dataOut = new FileWriter(FileName, false);
		    dataOut.append("");
		    dataOut.close();
		    dataOut = new FileWriter(FileName, UpdateModel);
	    	boolean Result = false;
	    	try {
	    		dataOut.append("ATOMS\n");
	    		if (this.atoms.size() != 0)
	    			for(int i=0; i<this.atoms.size(); i++) {
	    				Atom atom = this.atoms.get(i);
	    				String str = atom.x + " " + atom.y + " " + atom.r + " " + atom.color.getRGB() + " " + atom.id + "\n";
	    				dataOut.append(str);
	    			}
	    		dataOut.append("BONDS\n");
	    		if (this.bonds.size() != 0)
	    			for(int i=0; i<this.bonds.size(); i++) {
	    				Bond bond = this.bonds.get(i);
	    				String str = bond.atom1.id + " " + bond.atom2.id + " " + bond.index1 + " " + bond.index2 + "\n";
	    				dataOut.append(str);
	    			}
	    		
	            Result = true;
	    	}
	    	catch(IOException exc) {
	    		System.err.println(exc.getMessage());
	    	}
	    	finally {
	    		dataOut.close();
	    	}
	    	return Result;
	    }
	 
	 public Atom findAtomByID(int value) {
		 for(int i = 0; i < this.atoms.size(); i++) {
			 if (this.atoms.get(i).id == value)
				 return this.atoms.get(i);
		 }
		 return null;
	 }
	 
	 public boolean readData(String fileName) throws IOException
		{
			boolean Result = false;
			BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
			String str = "";
			int typeFlag = -1; // 0 - atoms, 1 - bonds 
			try {
				while ((str = bufferedReader.readLine()) != null)
				{
					if(str.indexOf("ATOMS") >= 0){
	    				typeFlag = 0;
	    			}
					else if(str.indexOf("BONDS") >= 0){
	    				typeFlag = 1;
	    			}
					// atoms
					else if(typeFlag == 0) {
						String[] values = str.split(" ");
						Atom atom = new Atom(Integer.parseInt(values[0]),
								Integer.parseInt(values[1]),
								Integer.parseInt(values[2]),
								new Color(Integer.parseInt(values[3])),
								Integer.parseInt(values[4]));
						this.atoms.add(atom);
					}
					else if(typeFlag == 1) {
						String[] values = str.split(" ");
						Atom atom1, atom2;
						if((atom1 = this.findAtomByID(Integer.parseInt(values[0])))!=null)
							if((atom2 = this.findAtomByID(Integer.parseInt(values[1])))!=null) {
								Bond bond = new Bond(atom1,atom2,
										Integer.parseInt(values[2]),
										Integer.parseInt(values[3]));
								this.bonds.add(bond);
							}
					}
				}
				Result = true;
			}
			catch(IOException exc)
			{
				Result = false;
				System.out.println(exc.getMessage());
			}
			finally {
				bufferedReader.close();
			}
			return Result;
		}
}
