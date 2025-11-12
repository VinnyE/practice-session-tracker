
import java.util.ArrayList;

public class SessionManager {
    private ArrayList<Session> sessions;

    public SessionManager() {
        this.sessions = new ArrayList<>();
    }

    public void addSession(Session session) {
        this.sessions.add(session);
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
