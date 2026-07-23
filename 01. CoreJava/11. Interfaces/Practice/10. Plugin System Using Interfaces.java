interface Plugin {
    String getName();
    void execute();
}

interface Loggable {
    void log(String message);
}

abstract class BasePlugin implements Plugin, Loggable {
    public void log(String message) {
        System.out.println("[" + getName() + "] " + message);
    }
}

class DatabasePlugin extends BasePlugin {
    public String getName() {
        return "Database";
    }

    public void execute() {
        System.out.println("Connecting to DB...");
        log("Connection established");
    }
}

class CachePlugin extends BasePlugin {
    public String getName() {
        return "Cache";
    }

    public void execute() {
        System.out.println("Starting cache...");
        log("Cache warmed up");
    }
}

class Demo {
    public static void main (String A[]) {
        BasePlugin obj1 = new CachePlugin();
        DatabasePlugin obj2 = new DatabasePlugin();

        Plugin[] plugin = new Plugin[2];
        plugin[0] = obj1;
        plugin[1] = obj2;

        for (Plugin obj : plugin) {
            obj.execute();
        }
    }
}

/* 

why `log()` in `BasePlugin` can call `getName()` even though `getName()` is not yet implemented in `BasePlugin`.
log() in BasePlugin calls getName() which is declared in the Plugin interface but not implemented in BasePlugin. 
This works because at runtime, log() is always called on a concrete object (DatabasePlugin or CachePlugin) that has implemented getName(). 
Java resolves getName() dynamically at runtime — this is the template method pattern: BasePlugin defines the algorithm in log() 
and delegates one step (getName()) to the subclass.

*/