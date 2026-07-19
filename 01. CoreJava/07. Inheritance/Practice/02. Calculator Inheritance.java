class Calc {

    int add(int n1, int n2) {
        return n1 + n2;
    }

    int sub(int n1, int n2) {
        return n1 - n2;
    }
}

class AdvCalc extends Calc {
    
    int multi(int n1, int n2) {
        return n1 * n2;
    }

    int div(int n1, int n2) {
        return n1 / n2;
    }
}

class Test {
    public static void main (String A[]) {
        AdvCalc obj = new AdvCalc();
        System.out.println(obj.add(4,5));
        System.out.println(obj.sub(5,1));
        System.out.println(obj.multi(3,5));
        System.out.println(obj.div(12,3));
    }
}