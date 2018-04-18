package de.bergwerklabs.jumpyjump.lobby;

import de.bergwerklabs.jumpyjump.api.Difficulty;
import de.bergwerklabs.jumpyjump.api.JumpyJumpMap;

import java.util.*;

/**
 * Created by Yannic Rieger on 06.04.2018.
 *
 * <p>
 *
 * @author Yannic Rieger
 */
public class LobbyMapManager {

  private Set<JumpyJumpMap> easyMaps;
  private Set<JumpyJumpMap> mediumMaps;
  private Set<JumpyJumpMap> hardMaps;

  public LobbyMapManager(
      Set<JumpyJumpMap> easyMaps, Set<JumpyJumpMap> mediumMaps, Set<JumpyJumpMap> hardMaps) {
    this.easyMaps = easyMaps;
    this.mediumMaps = mediumMaps;
    this.hardMaps = hardMaps;
  }

  public Set<JumpyJumpMap> getEasyMaps() {
    return Collections.singleton(
        new JumpyJumpMap(
            new HashSet<>(Arrays.asList("SirCryme", "Frakelz")),
            null,
            null,
            null,
            "EASY_MAP",
            Difficulty.EASY,
            true));
  }

  public Set<JumpyJumpMap> getMediumMaps() {
    return Collections.singleton(
        new JumpyJumpMap(
            new HashSet<>(Arrays.asList("SirCryme", "Frakelz")),
            null,
            null,
            null,
            "MEDIUM_MAP",
            Difficulty.MEDIUM,
            true));
  }

  public List<JumpyJumpMap> getHardMaps() {

    JumpyJumpMap map =
        new JumpyJumpMap(
            new HashSet<>(Arrays.asList("SirCryme", "Frakelz")),
            null,
            null,
            null,
            "HARD_MAP",
            Difficulty.HARD,
            true);

    return Arrays.asList(
        map, map, map, map, map, map, map, map, map, map, map, map, map, map, map, map, map, map,
        map, map, map, map, map);
  }
}
