import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.lang.reflect.*;

public class ClassPanel extends JPanel{
		String classname;
		Field[] attrib;
		Method[] methods;
		
	public ClassPanel(){
		add(new JLabel("test"));
	}	
	  public ClassPanel(String s, Field[] a, Method[] m){
		   classname = s;
		   attrib = a;
		   methods = m;
		   add(new JLabel(classname));
		   for (int i=0 ; i<a.length ; i++)
			   add(new JLabel(a[i].toString()));
			for (int i=0 ; i<m.length ; i++)
			   add(new JLabel(m[i].toString()));
	   }
}
