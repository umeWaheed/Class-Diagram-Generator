import java.util.*;

public class Test{
	public int a;
	public Tool b; 
	public String s;
	private Integer i;
	
	public Test(){}
	
	public void method1(){
			System.out.println("the one method public class Haha");
		}
		public int method2(){
			return 2;
		}
	
	public static int test(int z){
		System.out.print(z+" ");
	 if (z==1){
			return 1;
		 }	
	else if (z%2==0){//even
		return test(z/2);
	}	 
	else {
		return test(3*z+1);
	}
	}
	
	public static void main(String[] args){
	    int i = Test.test(3);
		///System.out.print(i);
	}
}