enum TrafficLight {
    Red, Yellow, Green;

    public TrafficLight nextLight(TrafficLight current) {
        switch(current) {
            case Red:
                return Yellow;
            case Yellow:
                return Green;
            default:
                return Red;
        }
    }
}

class Demo {
    public static void main (String A[]) {
        TrafficLight current = TrafficLight.Red;

        for (int i = 0; i < 4; i++) {
            System.out.println(current);
            current = current.nextLight(current);
        }
    }
}

