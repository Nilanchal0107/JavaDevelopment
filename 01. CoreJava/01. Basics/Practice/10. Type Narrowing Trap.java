class TypeNarrowing {
    public static void main (String A[]) {
        int a = 300;
        byte b = (byte) a; 

        float f = 9.99f;
        int i = (int) f;

        byte x = 10;
        byte y = 20;
        int z = x * y;

        System.out.println("byte cast of 300: " + b); // 44
        System.out.println("float to int 9.99f: " + i); // 9
        System.out.println("byte * byte stored in int: " + z); // 200 
    }
}

/*
Why is b not 300?
byte holds 8 bits. Java truncates all bits beyond the 8th. 300 in binary is 100101100 — keeping last 8 bits gives 00101100 = 44.

Why does 9.99f become 9 and not 10?
Int can only store number before decimal point.

Why can't x * y be stored in a byte variable directly?
Multiplication of two bytes can easily overflow byte capacity so we need to store it in int.
*/