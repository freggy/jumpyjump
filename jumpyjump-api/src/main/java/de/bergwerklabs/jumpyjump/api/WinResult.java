package de.bergwerklabs.jumpyjump.api;

/**
 * Created by Yannic Rieger on 03.04.2018.
 * <p>
 *
 * @author Yannic Rieger
 */
public class WinResult {

    public long getTimeTaken() {
        return timeTaken;
    }

    private long timeTaken;

    public WinResult(long timeTaken) {
        this.timeTaken = timeTaken;
    }
}
