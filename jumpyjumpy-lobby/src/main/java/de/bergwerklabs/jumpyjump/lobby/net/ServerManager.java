package de.bergwerklabs.jumpyjump.lobby.net;

import de.bergwerklabs.jumpyjump.lobby.MapSession;
import de.bergwerklabs.jumpyjump.lobby.QuickJoinSession;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by Yannic Rieger on 08.04.2018.
 * <p>
 *
 * @author Yannic Rieger
 */
public class ServerManager {

    private Queue<MapSession> mapSessionQueue = new PriorityQueue<>();
    private Queue<QuickJoinSession> quickJoinSessionQueue = new PriorityQueue<>();

    public void addToQueue(QuickJoinSession session) {
        this.quickJoinSessionQueue.add(session);
    }

    public void addToQueue(MapSession session) {
        this.mapSessionQueue.add(session);
    }

    public void removeFromQueue(QuickJoinSession session) {
        this.quickJoinSessionQueue.remove(session);
    }

    public void removeFromQueue(MapSession session) {
        this.mapSessionQueue.remove(session);
    }
}
