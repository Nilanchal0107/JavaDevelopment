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

class VeryAdvCalc extends AdvCalc {

    double power(int n1, int n2){
        return Math.pow(n1, n2);
    }
}

class Test {
    public static void main (String A[]) {
        VeryAdvCalc obj = new VeryAdvCalc();
        System.out.print(obj.add(4,5) + " ");
        System.out.print(obj.sub(7,3) + " ");
        System.out.print(obj.multi(5,3) + " ");
        System.out.print(obj.div(15,4) + " ");
        System.out.println(obj.power(4,2));
    }
}