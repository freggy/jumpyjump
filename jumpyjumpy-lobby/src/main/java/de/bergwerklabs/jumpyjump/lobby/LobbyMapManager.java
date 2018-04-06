package de.bergwerklabs.jumpyjump.lobby;

import de.bergwerklabs.jumpyjump.api.JumpyJumpMap;

import java.util.Set;

/**
 * Created by Yannic Rieger on 06.04.2018.
 * <p>
 *
 * @author Yannic Rieger
 */
public class LobbyMapManager {


    private Set<JumpyJumpMap> easyMaps;
    private Set<JumpyJumpMap> mediumMaps;
    private Set<JumpyJumpMap> hardMaps;


    public LobbyMapManager(
            Set<JumpyJumpMap> easyMaps, Set<JumpyJumpMap> mediumMaps, Set<JumpyJumpMap> hardMaps
    ) {
        this.easyMaps = easyMaps;
        this.mediumMaps = mediumMaps;
        this.hardMaps = hardMaps;
    }

    public Set<JumpyJumpMap> getEasyMaps() {
        return easyMaps;
    }

    public Set<JumpyJumpMap> getMediumMaps() {
        return mediumMaps;
    }

    public Set<JumpyJumpMap> getHardMaps() {
        return hardMaps;
    }
}
