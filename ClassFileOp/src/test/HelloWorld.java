package test;

import java.util.List;
import java.util.ArrayList;

public class HelloWorld implements Comparable<Integer> {
    private int i;
    protected String[] ss;
    
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        
        List<String> strList = new ArrayList<String>();
        strList.add("abc");
        
    }

    @Override
    public int compareTo(Integer o) {
        return 0;
    }
    
    public void foo() {}
}