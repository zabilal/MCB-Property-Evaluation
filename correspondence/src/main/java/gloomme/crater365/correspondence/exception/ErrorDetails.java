package gloomme.crater365.correspondence.exception;

import lombok.Data;

@Data
public class ErrorDetails implements java.io.Serializable {

    private final String message;

    private final long timestamp;

    public ErrorDetails(final String message) {
        this.timestamp = System.currentTimeMillis();
        this.message = message;
    }

}
