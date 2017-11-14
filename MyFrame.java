import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
public class MyFrame{
    
    public void iniGui() {
        JFrame frame =new JFrame("Class Diagram Tool");
        frame.setLayout(new BorderLayout());        
        
        JPanel panel1 = new JPanel();
		panel1.setPreferredSize(new Dimension(600,70));
		
		panel1.setBackground(new Color(196, 108, 60));
		JButton aButton = new JButton("Choose File");
		aButton.setPreferredSize(new Dimension(100,50));
		aButton.setBackground(new Color(242, 189, 142));
        panel1.add(aButton);
        Border t1=new TitledBorder("Relationships");
        JPanel panel2 = new JPanel();
		panel2.setBackground(new Color(247, 149, 96));
		panel2.setPreferredSize(new Dimension(600,150));
        panel2.setBorder(t1);
         Border t2=new TitledBorder("Class Diagram");
        JPanel panel3 = new JPanel();
		panel3.setBackground(new Color(244, 176, 139));
		panel3.setPreferredSize(new Dimension(600,400));
		panel3.setBorder(t2);
        
      
	  frame.add(panel1,BorderLayout.NORTH);
	  frame.add(panel2,BorderLayout.SOUTH);
	  frame.add(panel3,BorderLayout.CENTER);
	  
        
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
frame.setSize(600,600);
frame.setLocationRelativeTo(null);
frame.setVisible(true);	  
    }
	public static void main(String[] args)
	{
		MyFrame f=new MyFrame();
		f.iniGui();
	
}
}

