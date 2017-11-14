import java.io.*;
import java.lang.Exception;
import java.util.zip.*;
import java.util.*;
import java.lang.reflect.*;
import java.util.Scanner;
import java.net.URLClassLoader;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.lang.Class;

public class Tool{
	
	public static ArrayList<String> getRelationships(ArrayList<Class> c){
		//for each class scan other classes has-a relationship
		ArrayList<String> r = new ArrayList<String>();
		for (int i=0 ; i<c.size() ; i++)
		{
			//compare with java built-in classes 
			Field[] f1 = c.get(i).getDeclaredFields(); //class i itself
			for (int k=0 ; k<f1.length ; k++){
				if (f1[k].toString().contains("java")){
					System.out.print(f1[k].getType().getSimpleName());
					r.add("Has a relationship: "+c.get(i).toString()+" has an instance of "+f1[k].getType().getSimpleName());
				}
			}
			
			//compare with other classes  class i with others
			for (int j=0 ; j<c.size() ; j++){
				if (i!=j){											//don't compare file with itself
					Field[] f = c.get(j).getDeclaredFields();
																		// scan all fields for the given class name
					for (int k=0 ; k<f.length ; k++){
						if (f[k].toString().contains(c.get(i).getName())){
							r.add("Has a relationship: "+c.get(j).getName()+" has an instance of "+c.get(i).getName());//System.out.println(c.get(j).getName()+" Has a relationship: "+c.get(i).getName());
						}
					}
				}
			}
			
			// is-a relationship
			String parent = c.get(i).getSuperclass().getName();
			if (!parent.startsWith("java")){
			r.add("Inheritence : "+c.get(i).getName()+" extends "+parent);//System.out.println(c.get(i).getName()+" has Super class "+parent);}
			}
		}
		return r;
	}
	
	public static void unzip(File file, String folder){
		//zip file
		try{
			FileInputStream f = new FileInputStream(file);
			ZipInputStream z = new ZipInputStream(f);
			ZipEntry entry = z.getNextEntry();
			
			String path = file.getPath().substring(0,file.getPath().lastIndexOf("\\")+1); //path of file-file name
			
			//System.out.println(file.getPath());
			File dir = new File(path+File.separator+folder);
			
			if (!dir.exists()){
			dir.mkdir();
			}
	
			while (entry!=null){
				Pattern p = java.util.regex.Pattern.compile("^[/\\\\]?(?:.+[/\\\\]+?)?(.+?)[/\\\\]?$"); //name of the file
                Matcher matcher = p.matcher(entry.getName());

				String entryName="";
                if ( matcher.find() ) {
                  entryName = matcher.group(1);
                 }
				else{
					entryName = entry.getName();
				}
				 
				System.out.print(entryName);
				String dest = path+folder+File.separator+entryName;							//path of zip file+folder to extract into+entryname
				//System.out.println(entry.getName()+" "+dest);
				if (!entry.isDirectory()){
					BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(dest));
                    byte[] bytesIn = new byte[4096];
                    int read = 0;
					while ((read = z.read(bytesIn)) != -1) {
						bos.write(bytesIn, 0, read);
					}
					bos.close();
				}
				z.closeEntry();
				entry = z.getNextEntry();
			}
			z.close();
			getFiles(null, new File(path+folder));
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		
		}
	}
	
	public static void getFiles(File file, File folder){
		ArrayList<Class> classes = new ArrayList<Class>();	//holds all the classes in a zip file
		
		try{
			File path = new File(folder+"\\"); 
			URL u = path.toURI().toURL();
			URL[] urls = new URL[]{u};
			ClassLoader loader = new URLClassLoader(urls);
			
			if (file!=null){	//for a single .java file
				Class c = loader.loadClass(removeExt(file));
				//System.out.println("file");
				classes.add(c);	
				Screen1.newPanel(c.getName(),getAttributes(c),getMethods(c));
			}
			else{				// for an unzipped folder
				for (File fileEntry : folder.listFiles()) {	
					if (fileEntry.isDirectory()) {
						getFiles(null ,fileEntry);
					} else {
						if (!getExt(fileEntry).equals("class")){
						Class c = loader.loadClass(removeExt(fileEntry));
						classes.add(c);							// add extracted classes to find relationships
						//System.out.println(fileEntry+" "+u+" "+c);
						Screen1.newPanel(c.getName(),getAttributes(c),getMethods(c));
						}
					}
				}
			}
			Screen1.insertRelations(getRelationships(classes));	//pass list of classes to relation method
		}
		catch(Exception ex){
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public static String[] getAttributes(Class c){
		Field[] f = c.getDeclaredFields();
		String []a = new String[f.length];
		 for (int j=0 ; j<f.length ; j++){
			 System.out.println(f[j].getType());
		   a[j] = standardize(f[j].toString(),f[j].getType().toString());
		}
		return a;
	}
	
	public static String standardize(String line,String type){
		char modifier;
		String name;
		
		if (line.startsWith("public")){
			modifier='+';
		}
		else if (line.startsWith("private")){
			modifier='-';
		}
		else
		{
			modifier=' ';
		}
		
		if (type.startsWith("class")){
			type = type.replace("class","");
		}
		if (type.contains("."))
			type = type.substring(type.lastIndexOf('.')+1);
		
		if (!line.contains("(")) //attribute
		name = line.substring(line.lastIndexOf('.')+1);
		else{
			name = line.substring(line.indexOf('.')+1);
		}
		
		return modifier+" "+name+" : "+type;
	}
	
	public static String[] getMethods(Class c){
		Method[] m = c.getDeclaredMethods();
		String[] mthd = new String[m.length];
		for (int i = 0; i < m.length; i++){
            //System.out.println(m[i].toString()+" "+m[i].getReturnType());
			mthd[i] = standardize(m[i].toString(),m[i].getReturnType().toString());
		}
		return mthd;
	}
	
	public static String getExt(File f){
	    int i = f.getName().indexOf('.');
		return f.getName().substring(i+1);
	}
	
	public static String removeExt(File f){
		int i = f.getName().indexOf('.');
		return f.getName().substring(0,i);
	}
	
	
}