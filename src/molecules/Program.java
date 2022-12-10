package molecules;
import javax.swing.JFrame;

public class Program {

	public static void run() throws Exception{
        Frame frame = new Frame();
        frame.setTitle("Race");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
	
	
	public static void main(String[] args) throws Exception, java.io.IOException{
		System.out.println("Welcome to race");
		run();
	}
}