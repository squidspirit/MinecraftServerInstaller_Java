package application;

import java.util.Vector;

import javafx.util.Pair;

public class Information {
    public static final String NAME = "Minecraft Server Installer";
    public static final String VERSION = "4.0.1.0";
    public static final String AUTHOR = "SquidSpirit";
    public static final String EMAIL = "service@squidspirit.ddns.net";
    public static final String TUTORIAL = "https://youtu.be/yNis5vcueQY";
    public static final String WEBSITE = "https://squidspirit.ddns.net/tutorial/minecraft-server-installer/";
    
    public class Url {
        public static final String GAME_VERSION = "https://www.dropbox.com/s/mtz3moc9dpjtz7s/GameVersions.txt?dl=1";
    }

    public class VersionList {
        public static Vector<Pair<String, String>> gameVersion = new Vector<>();
    }
}
