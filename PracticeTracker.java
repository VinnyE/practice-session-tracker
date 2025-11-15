import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class PracticeTracker {
    public static void main(String[] args) throws IOException {
        if (args.length >= 1 && args.length < 3) {
            SessionManager manager = new SessionManager();

            switch (args[0]) {
                case "add":
                    try {
                        if (args.length < 2) {
                            System.out.println("A valid duration argument is needed.");
                            return;
                        }

                        int duration = Integer.parseInt(args[1]);

                        if (duration < 0) {
                            System.out.println("Duration must be positive.");
                            return;
                        }

                        Session session = new Session(LocalDate.now(), duration);
                        manager.addSession(session);

                        System.out.println(String.format("Added session:  %d minutes", session.getDuration()));
                    } catch (NumberFormatException e) {
                        System.err.println("Error parsing duration param: " + e.getMessage());
                    }
                    break;
                case "list":
                        ArrayList<Session> sessions = manager.listSessions();

                        if (sessions.size() == 0) {
                            System.out.println("No sessions added yet.");
                        }

                        for (Session session : sessions) {
                            System.out.println(session.getDate() + "  " + session.getDuration() + " minutes");
                        }
                    break;
                case "total":
                    int total = manager.getTotalMinutes();
                    int hours = total / 60;
                    int minutes = total % 60;

                    System.out.printf("Total practice time: %d hours and %d minutes%n", hours, minutes);

                    break;

                default:
                    System.out.println("Invalid CLI argument. Valid arguments are: 'add', 'list', and 'total'.");
                    break;
            }
        } else {
            System.out.println("Cannot run CLI with zero or more than two args. Received " + args.length + " arguments.");
        }
    }
}
