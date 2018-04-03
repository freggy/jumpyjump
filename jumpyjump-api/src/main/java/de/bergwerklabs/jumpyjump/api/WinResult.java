package de.bergwerklabs.jumpyjump.api;

/**
 * Created by Yannic Rieger on 03.04.2018.
 * <p>
 * Contains win data.
 *
 * @author Yannic Rieger
 */
public class WinResult {

    /**
     * Time in milliseconds the round lasted.
     */
    public long getTimeTaken() {
        return timeTaken;
    }

    private long timeTaken;

    /**
     * @param timeTaken time in milliseconds the round lasted.
     */
    public WinResult(long timeTaken) {
        this.timeTaken = timeTaken;
    }
}
