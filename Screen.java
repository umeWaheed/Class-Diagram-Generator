import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.lang.reflect.*;

public class Screen extends JComponent implements ActionListener{
	static JFrame frame;
	static JPanel[] p = new JPanel[10]; 
	static int numPanel=-1;
	
	public void actionPerformed(ActionEvent e){
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
				Tool.unzip(file);
				//Tool.getRelationships();
			}
			else{
				System.out.println("choose .java or .zip file");
			}
	}			
	else {
        System.out.println("no file selected");
		}
	}
	
	public void draw(){
		frame  = new JFrame();
	    frame.setLayout(new FlowLayout(FlowLayout.LEFT,20,20));
	    JButton b = new JButton("choose file");
		
	    b.addActionListener(this);
	    frame.add(b);
		frame.setVisible(true);
		frame.setSize(500,500);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
	}
	
	public static void newPanel(String c, Field[] a, Method[] m){
		++numPanel;
		p[numPanel] = new JPanel();
		p[numPanel].setBorder(BorderFactory.createLineBorder(Color.black));
		//p[numPanel].add(new JLabel(c));
		String data = c+"\n----------------------------------------------\n";
		int rows=1+a.length+m.length;
		   for (int i=0 ; i<a.length ; i++)
			   data = data+a[i].toString()+"\n";
		   data=data+"----------------------------------------------\n";
			 // p[numPanel].add(new JLabel(a[i].toString()));
			for (int i=0 ; i<m.length ; i++)
				data = data+m[i].toString()+"\n";
			   //p[numPanel].add(new JLabel(m[i].toString()));
		p[numPanel].add(new JTextArea(data,rows,10));	   
		frame.add(p[numPanel]);
		frame.repaint();
	    frame.validate();
	}
	
	/*public void drawPanel(){
		Graphics g = p[numPanel].getGraphics();
		System.out.print(g);
		g.fillRect(10,20,p[numPanel].getWidth(),p[numPanel].getHeight());
		frame.repaint();
	    frame.validate();
	}*/
	
	public static void main(String[]args){
		Screen s = new Screen();
		s.draw();
	}
}