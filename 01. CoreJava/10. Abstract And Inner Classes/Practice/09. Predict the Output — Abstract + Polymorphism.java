abstract class Appliance {
    String brand = "Generic";

    Appliance() {
        System.out.println("Appliance created: " + brand);
    }

    public abstract void operate();

    public void powerOn() {
        System.out.println("Powering on: " + brand);
        operate();
    }
}

class WashingMachine extends Appliance {
    WashingMachine() {
        brand = "Samsung";
        System.out.println("WashingMachine created: " + brand);
    }

    public void operate() {
        System.out.println("Washing clothes with " + brand);
    }
}

public class Demo {
    public static void main(String[] args) {
        Appliance a = new WashingMachine();
        a.powerOn();
    }
}

/* 

Appliance created: Generic
WashingMachine created: Samsung
Powering on: Samsung
Washing clothes with Samsung

1. Why does `Appliance created: Generic` print even though we only called `new WashingMachine()`?
Before creating new WashingMachine(), Java automatically calls Parent class with its constructor.
And in Appliance class's constructor we had printed `Appliance created: Generic`

2. In `powerOn()`, which version of `operate()` is called — `Appliance`'s or `WashingMachine`'s? Why?
powerOn() is defined in Appliance and calls operate() — but because the actual object at runtime is a WashingMachine, 
dynamic dispatch kicks in and calls WashingMachine.operate(), not Appliance.operate() 
(which doesn't even have a body since it's abstract). 
That's the key insight — the method is resolved at runtime based on the actual object type, not the reference type.

3. What happens at the line `brand = "Samsung"` — is this a new variable or the inherited one?
brand = "Samsung" is a inherited ones.
That is brand had change its refernce point.
Brand has been inherited from Appliance class.

*/