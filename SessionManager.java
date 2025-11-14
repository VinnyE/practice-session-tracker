
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;


public class SessionManager {
    private ArrayList<Session> sessions;

    public SessionManager() throws IOException {
        this.sessions = new ArrayList<>();
        this.loadFromFile();
    }

    private void saveToFile() throws IOException {
        Path filePath = Paths.get("sessions.txt");
        List<String> lines = new ArrayList<>();

        for (Session session : this.sessions) {
            lines.add(session.getDate() + "," + session.getDuration());
        }

        Files.write(filePath, lines);
    }

    private void loadFromFile() throws IOException {
        Path filePath = Paths.get("sessions.txt");

        if (Files.exists(filePath)) {
            List<String> lines = Files.readAllLines(filePath);

            for (String line : lines) {
                String[] parts = line.split(",");
                LocalDate date = LocalDate.parse(parts[0]);
                int duration = Integer.parseInt(parts[1]);
                this.sessions.add(new Session(date, duration));
            }
        }
    }

    public void addSession(Session session) throws IOException {
        this.sessions.add(session);
        this.saveToFile();
    }

    public ArrayList<Session> listSessions() {
        return new ArrayList<>(this.sessions);
    }

    public int getTotalMinutes() {
        int accumulatedTime = 0;

        for (Session session : this.sessions) {
            accumulatedTime += session.getDuration();
        }

        return accumulatedTime;
    }
}
