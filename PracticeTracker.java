public class PracticeTracker {
    public static void main(String[] args) {
        if (args.length == 2) {
            System.out.println("Command: " + args[0]);
            System.out.println("Argument: " + args[1]);
        } else if (args.length == 1) {
            System.out.println("Command: " + args[0]);
        } else {
            System.out.println("Cannot run CLI with zero or more than two args");
        }

    }
}
