package pistonmc.techtree.mc7;

import pistonmc.techtree.adapter.ILocaleLoader;

public class ServerLocaleLoader implements ILocaleLoader {

    @Override
    public String getCurrentLocale() {
        // on server we just use the default locale
        return "en_US";
    }
}
