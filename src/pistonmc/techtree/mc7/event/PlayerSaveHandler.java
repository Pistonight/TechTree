package pistonmc.techtree.mc7.event;

import java.io.File;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import pistonmc.techtree.adapter.IPlayerData;
import pistonmc.techtree.adapter.IPlayerServerSide;
import pistonmc.techtree.mc7.ModServer;
import pistonmc.techtree.mc7.PlayerServerSide;

public class PlayerSaveHandler {
    private ModServer server;

    public PlayerSaveHandler(ModServer server) {
        this.server = server;
    }

    static class DataHandler implements IPlayerData {

        public String playerName;
        public File playerDir;

        public DataHandler(String playerName, File playerDir) {
            this.playerName = playerName;
            this.playerDir = playerDir;
        }

        @Override
        public File getDataFile(String name) {
            return playerDir.toPath().resolve(this.playerName + "_" + name + ".txt").toFile();
        }
    }

    // this event is only fired on the server side
    @SubscribeEvent
    public void onPlayerLoad(PlayerEvent.LoadFromFile event) {
        IPlayerServerSide player = new PlayerServerSide(event.entityPlayer);
        String name = player.getClientPlayerName();
        File dir = event.playerDirectory;
        IPlayerData data = new DataHandler(name, dir);
        this.server.getProgress().onPlayerLoad(player, data);
    }

    // this event is only fired on the server side
    @SubscribeEvent
    public void onPlayerSave(PlayerEvent.SaveToFile event) {
        IPlayerServerSide player = new PlayerServerSide(event.entityPlayer);
        String name = player.getClientPlayerName();
        File dir = event.playerDirectory;
        IPlayerData data = new DataHandler(name, dir);
        this.server.getProgress().onPlayerSave(player, data);
    }
}
