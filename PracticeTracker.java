import java.time.LocalDate;
import java.util.ArrayList;

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

        SessionManager manager = new SessionManager();
        manager.addSession(new Session(LocalDate.now(), 45));
        manager.addSession(new Session(LocalDate.of(2025, 11, 10), 60));
        manager.addSession(new Session(LocalDate.of(2025, 11, 9), 30));

        ArrayList<Session> sessions = manager.listSessions();
        System.out.println("All sessions:");
        for (Session s : sessions) {
            System.out.println(s);
        }

        // Test total
        int total = manager.getTotalMinutes();
        System.out.println("Total minutes: " + total);
    }
}
