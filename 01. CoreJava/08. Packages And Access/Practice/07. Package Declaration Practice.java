// package com.myapp.utils; 
class MathUtil {
    public static int square(int n){
        return n * n;
    }
}

// package com.myapp.main;  
class Demo { 
//    imports com.myapp.utils.MathUtil;
    public static void main (String A[]) {
        System.out.println(MathUtil.square(5));
    }
}