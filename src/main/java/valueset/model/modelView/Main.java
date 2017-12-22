package valueset.model.modelView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

public class Main {

	public static void main(String[] args) {
		popTest();
		//第几张图，有两张(0,1)，起点序号(0-6)，终点序号(0-6)  
        AF operation = new AF(new RxnormGraph(0), 3, 6);  
        operation.getResult(); 
	}
	
	public static void popTest() {
		
		List<List<String>> sortTests = new ArrayList<List<String>>();
		List<String> sortTest = new ArrayList<String>();
		sortTest.add("ad");
		sortTest.add("cd");
		sortTests.add(sortTest);
		List<String> sortTest1 = new ArrayList<String>();
		sortTest1.add("d");
		sortTest1.add("e");
		sortTest1.add("f");
		sortTests.add(sortTest1);
		List<String> sortTest2 = new ArrayList<String>();
		sortTest2.add("ddf");
		sortTests.add(sortTest2);
		
		Collections.sort(sortTests,new Comparator<List<String>>(){  
            public int compare(List<String> arg0, List<String> arg1) {
            	Integer size0 = new Integer(arg0.size());
            	Integer size1 = new Integer(arg1.size());
                return size0.compareTo(size1);  
            }  
        });
		System.out.println(sortTests);

		
	}
}
