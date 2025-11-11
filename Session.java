import java.time.LocalDate;

public class Session {
    private LocalDate date;
    private int duration;

    public Session(LocalDate date, int duration) {
        this.date = date;
        this.duration = duration;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public int getDuration() {
        return this.duration;
    }

    @Override
    public String toString() {
        String message = "Session{date=" + this.getDate() + ", duration="+this.getDuration() + "}";
        
        return message;
    }
} 
