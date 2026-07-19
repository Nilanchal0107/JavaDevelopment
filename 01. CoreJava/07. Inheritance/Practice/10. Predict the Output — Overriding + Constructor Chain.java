class Animal {
    String type;
    Animal() {
        type = "Unknown";
        System.out.println("Animal constructor: " + type);
    }
    void speak() {
        System.out.println("Some animal sound");
    }
}

class Dog extends Animal {
    String name;
    Dog(String name) {
        // super() is called implicitly here
        this.name = name;
        System.out.println("Dog constructor: " + name);
    }
    void speak() {          // overrides Animal.speak()
        System.out.println(name + " says: Woof!");
    }
}

class GoldenRetriever extends Dog {
    GoldenRetriever(String name) {
        super(name);
        System.out.println("GoldenRetriever constructor: " + name);
    }
    void speak() {          // overrides Dog.speak()
        super.speak();
        System.out.println(name + " wags tail!");
    }
}

public class Demo {
    public static void main(String[] args) {
        GoldenRetriever g = new GoldenRetriever("Buddy");
        g.speak();
    }
}

/* 

Animal constructor: Unknown
Dog constructor: Buddy
GoldenRetriever constructor: Buddy
Buddy says: Woof!
Buddy wags tail!


1. List every constructor call in order with arrows.
GoldenRetriever("Buddy") -> super(name) -> Dog(name) -> Animal()
GoldenRetriever().speak() -> super.speak() -> Dog.speak()

2. Why does `super.speak()` inside `GoldenRetriever.speak()` call `Dog.speak()` and not `Animal.speak()`?
super in GoldenRetriever refers to the immediate parent, which is Dog. 
So super.speak() always calls Dog.speak() specifically, not whatever is highest in the chain. 
It's not about prioritization — it's about which class super resolves to.

3. What would happen if `Dog.speak()` was removed — which `speak()` would `super.speak()` call then?
Animal.speak() will get called

*/