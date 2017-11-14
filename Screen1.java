import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.ArrayList;
import java.lang.reflect.*;
import javax.swing.border.*;
import javax.swing.table.*;

public class Screen1 extends JComponent implements ActionListener{
	static JFrame frame;
	static JButton aButton,b;
	static JPanel panel1;
	static String[] cname = new String[10];
	static ArrayList<ArrayList<String>> attr= new ArrayList<ArrayList<String>>();
	static ArrayList<ArrayList<String>> meth= new ArrayList<ArrayList<String>>();
	static JPanel panel2 = new JPanel();
	static JPanel panel3 = new JPanel();
	static JPanel[] p = new JPanel[10]; 
	static Point[][] points = new Point[10][2]; //starting and ending points
	static int numPanel=-1;
	
	public void actionPerformed(ActionEvent e){
		
		if (e.getSource()==aButton){
		String extension="";
		JFileChooser chooser = new JFileChooser();
	int returnVal = chooser.showOpenDialog(new JFrame());
		
	if (returnVal == JFileChooser.APPROVE_OPTION) {
        File file = chooser.getSelectedFile();
		String path = file.getPath().substring(0,file.getPath().lastIndexOf('\\'));
        System.out.println(file.getName()+" "+path);
		int i = file.getName().lastIndexOf('.');
		if (i > 0) {
		extension = Tool.getExt(file);
		}
			if (extension.equals("java")){
			Tool.getFiles(file ,new File(path));
			}
			else if (extension.equals("zip")){
				String folder = JOptionPane.showInputDialog("Enter folder name for extraction: ");
				Tool.unzip(file,folder);
			}
			else{
				JOptionPane.showMessageDialog(null,"Choose .java or .zip file","Error",JOptionPane.ERROR_MESSAGE);
			}
	}			
	else {
		JOptionPane.showMessageDialog(null,"No file selected","Error",JOptionPane.ERROR_MESSAGE);
		}
		}
		else{
			JFrame detail = new JFrame("Class Details");
			//System.out.print(numPanel);
			String[] row = {"class name","number of attributes","attributes","number of methods","methods"};
			TableModel model = new DefaultTableModel(row,numPanel+2);
      JTable table = new JTable(model);
      JScrollPane scrollpane = new JScrollPane(table);
	 
	   for (int p=0 ; p<=numPanel ; p++){
	   model.setValueAt(cname[p],p+1,0);
	   model.setValueAt(attr.get(p).size(),p+1,1);
	   model.setValueAt(meth.get(p).size(),p+1,3);
	   String  s="<html><body>";  
	   for (int i=0 ; i<attr.get(p).size() ; i++){
		s=s+attr.get(p).get(i)+"<br>";
	   }
	    s+="</body></html>";
	    model.setValueAt(s,p+1,2);
	   
	   s="<html><body>";
	   for (int i=0 ; i<meth.get(p).size() ; i++){
		s=s+meth.get(p).get(i)+"<br>";
	   }
	   s+="</body></html>";
	    model.setValueAt(s,p+1,4);
	  
        int rowHeight = table.getRowHeight();

        for (int column = 0; column < table.getColumnCount(); column++)
        {
            Component comp = table.prepareRenderer(table.getCellRenderer(p+1, column), p+1, column);
            rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
        }

        table.setRowHeight(p+1, rowHeight);
	   }
	   
	 // row={"a","b","c"};
	 // model.addRow(row1);
	  detail.add(scrollpane);
	  detail.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		detail.setSize(600,600);
		detail.setLocationRelativeTo(null);  
		detail.setVisible(true);
		}
	}
	
	public void draw(){
		frame =new JFrame("Class Diagram Tool");
        setLayout(new BorderLayout());  
		JPanel panel1 = new JPanel();
		panel1.setPreferredSize(new Dimension(600,70));
		panel1.setBackground(new Color(196, 108, 60));
		aButton = new JButton("Choose File");
		b = new JButton("Details");
		aButton.setPreferredSize(new Dimension(100,50));
		aButton.setBackground(new Color(242, 189, 142));
		b.setPreferredSize(new Dimension(100,50));
		b.setBackground(new Color(242, 189, 142));
        panel1.add(aButton);
		panel1.add(b);
        
		TitledBorder t1=new TitledBorder("Relationships");
		t1.setTitleFont(new Font("Arial", Font.BOLD, 20));
		panel2.setFont(new Font("Arial", Font.BOLD, 20));
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
		panel2.setBackground(new Color(247, 149, 96));
		panel2.setPreferredSize(new Dimension(600,150));
        panel2.setBorder(t1);
        TitledBorder t2=new TitledBorder("Class Diagram");
		t2.setTitleFont(new Font("Arial", Font.BOLD, 20));

		panel3.setBackground(new Color(244, 176, 139));
		panel3.setPreferredSize(new Dimension(600,400));
		panel3.setBorder(t2);
		
		b.addActionListener(this);
		aButton.addActionListener(this);
	    frame.add(panel1,BorderLayout.NORTH);
		frame.add(panel2,BorderLayout.SOUTH);
		frame.add(panel3,BorderLayout.CENTER);
	  
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600,600);
		frame.setLocationRelativeTo(null);  
		frame.setVisible(true);
	}
	
	public static void newPanel(String c, String[] a, String[] m){
		++numPanel;
		cname[numPanel]=c;
		ArrayList<String> a1 = new ArrayList<String>();
		ArrayList<String> m1 = new ArrayList<String>();
		
		p[numPanel] = new JPanel();
		p[numPanel].setBorder(BorderFactory.createLineBorder(Color.black));
	
		String data = c+"\n----------------------------------------------\n";
		int rows=1+a.length+m.length;
		   for (int i=0 ; i<a.length ; i++)
			{ data = data+a[i]+"\n";		// data = data+a[i].toString()+"\n";
				a1.add(i,a[i]);
			}
			attr.add(numPanel,a1);
		   
		   data=data+"----------------------------------------------\n";
			 
			for (int i=0 ; i<m.length ; i++){
				data = data+m[i]+"\n";		
				m1.add(i,m[i]);
			}
			meth.add(numPanel,m1);
			   
		p[numPanel].add(new JTextArea(data,rows,10));	
		panel3.add(p[numPanel]);
		frame.add(panel3,BorderLayout.CENTER);
		frame.repaint();
	    frame.revalidate();
	}
	
	public static void insertRelations(ArrayList<String> r){
		JLabel[] lbl = new JLabel[r.size()];
		
		for (int k=0 ; k<=numPanel ; k++){
		points[k][0] = new Point(p[k].getX(),p[k].getY()); //top left
		points[k][1] = new Point(p[k].getX(),p[k].getY()+p[k].getHeight()); //bottom left
		System.out.print(points[k][0]+" "+points[k][1]);
		}
		
		for (int i=0 ; i<r.size() ; i++){
			    lbl[i] = new JLabel(r.get(i));
				lbl[i].setFont(null);
				panel2.add(lbl[i]);
		}
		frame.add(panel2,BorderLayout.SOUTH);
		frame.repaint();
	    frame.revalidate();
	}
	
	public static void main(String[]args){
		Screen1 s = new Screen1();
		s.draw();
	}
}