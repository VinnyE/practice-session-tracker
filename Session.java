import java.time.LocalDate;

public class Session {
    private LocalDate date;
    private int duration;

    public Session(LocalDate date, int duration) {
        if (date == null) {
            throw new IllegalArgumentException("Error: Date cannot be null.");
        }

        if (duration <= 0 || duration > 1440) {
            throw new IllegalArgumentException("Error: Duration must be between 1 and 1440 minutes.");
        }

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
