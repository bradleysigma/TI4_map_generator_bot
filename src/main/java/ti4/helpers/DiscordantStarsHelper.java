package ti4.helpers;

import ti4.generator.Mapper;
import ti4.map.Game;
import ti4.map.Planet;
import ti4.map.Player;
import ti4.map.Tile;
import ti4.map.UnitHolder;

public class DiscordantStarsHelper {
    public static void checkGardenWorlds(Game activeGame) {
        for (Player player : activeGame.getPlayers().values()) {
            if (player.hasAbility(Constants.GARDEN_WORLDS)) {
                for (Tile tile : activeGame.getTileMap().values()) {
                    for (UnitHolder unitHolder : tile.getUnitHolders().values()) {
                        if (unitHolder instanceof Planet planet) {
                            if (player.getPlanets().contains(planet.getName())) {
                                if (planet.hasGroundForces() && planet.getTokenList().contains(Constants.GARDEN_WORLDS_PNG)) {
                                    planet.removeToken(Constants.GARDEN_WORLDS_PNG);
                                } else {
                                    if(!planet.hasGroundForces()){
                                         planet.addToken(Constants.GARDEN_WORLDS_PNG);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    public static void checkSigil(Game activeGame) { //Edyn Mech adds Sigil tokens under them
        for (Player player : activeGame.getPlayers().values()) {
            if (player.ownsUnit("edyn_mech")) {
                for (Tile tile : activeGame.getTileMap().values()) {
                    if (Helper.playerHasMechInSystem(tile, activeGame, player)) {
                        tile.addToken(Constants.SIGIL, Constants.SPACE);
                    } else {
                        tile.removeToken(Constants.SIGIL, Constants.SPACE);
                    }
                }
            }
        }
    }

    public static void checkOlradinMech(Game activeMap) {
        for (Player player : activeMap.getPlayers().values()) {
            String tokenToAdd = null;
            if (player.ownsUnit("olradin_mech_positive")) {
                tokenToAdd = Constants.OLRADIN_MECH_INF_PNG;
            } else if (player.ownsUnit("olradin_mech_negative")) {
                tokenToAdd = Constants.OLRADIN_MECH_RES_PNG;
            } else {
                continue;
            }

            for (Tile tile : activeMap.getTileMap().values()) {
                for (UnitHolder unitHolder : tile.getUnitHolders().values()) {
                    if (unitHolder != null && unitHolder instanceof Planet) {
                        Planet planet = (Planet) unitHolder;
                        if (planet != null && player.getPlanets().contains(planet.getName())) {                   
                            if (!oneMechCheck(planet.getName(), activeMap, player) && ((planet.getTokenList().contains(Constants.OLRADIN_MECH_INF_PNG)) || (planet.getTokenList().contains(Constants.OLRADIN_MECH_RES_PNG)))) {
                                planet.removeToken(Constants.OLRADIN_MECH_INF_PNG);
                                planet.removeToken(Constants.OLRADIN_MECH_RES_PNG);
                            } else if (oneMechCheck(planet.getName(), activeMap, player)) {
                                planet.addToken(tokenToAdd);
                            }
                        }
                    }
                }
            }
        }
    }

    private static boolean oneMechCheck(String planetName, Game activeMap, Player player) {
        Tile tile = activeMap.getTile(AliasHandler.resolveTile(planetName));
        UnitHolder unitHolder = tile.getUnitHolders().get(planetName);
        int numMechs = 0;

        String colorID = Mapper.getColorID(player.getColor());
        String mechKey = colorID + "_mf.png";

        if (unitHolder.getUnits() != null) {
            if (unitHolder.getUnits().get(mechKey) != null) {
                numMechs = unitHolder.getUnits().get(mechKey);
            }
        }
        return numMechs == 1;
    }
}
