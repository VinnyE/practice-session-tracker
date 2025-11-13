
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class SessionManager {
    private ArrayList<Session> sessions;

    private void saveToFile() throws IOException {
        Path filePath = Paths.get("sessions.txt");
        List<String> lines = new ArrayList<>();

        for (Session session : this.sessions) {
            lines.add(session.getDate() + "," + session.getDuration());
        }

        Files.write(filePath, lines);
    }

    public SessionManager() {
        this.sessions = new ArrayList<>();
    }

    public void addSession(Session session) throws IOException {
        this.sessions.add(session);
        saveToFile();
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
