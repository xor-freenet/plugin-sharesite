package plugins.ShareLink;

import plugins.ShareLink.common.Logger;
import plugins.ShareLink.webui.WebInterface;
import freenet.l10n.BaseL10n;
import freenet.l10n.PluginL10n;
import freenet.l10n.BaseL10n.LANGUAGE;
import freenet.pluginmanager.FredPlugin;
import freenet.pluginmanager.FredPluginBaseL10n;
import freenet.pluginmanager.FredPluginL10n;
import freenet.pluginmanager.FredPluginRealVersioned;
import freenet.pluginmanager.FredPluginThreadless;
import freenet.pluginmanager.FredPluginVersioned;
import freenet.pluginmanager.PluginRespirator;

/**
 * This is the main class, the first one executed by Freenet.
 * Only one instance exists, and can be accessed from anywhere using
 * the global variable. The most logical way to reach different things.
 */
public class Plugin implements FredPlugin, FredPluginVersioned, FredPluginRealVersioned, FredPluginL10n, FredPluginBaseL10n, FredPluginThreadless {
	public static final String version = "0.1.5";
	public static final long realVersion = 6;
	public static final String freesite = "USK@9aOIc6GxaELlIj8550zM7uaOXgPhqk5ZQS0fzJzrJ78,bhzvffR2i36fTnHzHJFKGVwFLEeSYoRI4DiSBmqb-9E,AQACAAE/site/10/";
	public static boolean isPreRelease = false;
	
	private PluginL10n plugL10n;
	
	public static Plugin instance;
	public PluginRespirator pluginRespirator;
	public BaseL10n l10n;
	
	public Logger logger;
	public Inserter inserter;
	public Database database;
	public WebInterface webInterface;
	
	public Plugin() {
		instance = this;
	}
	
	@Override
	public void runPlugin(PluginRespirator pr) {
		pluginRespirator = pr;
		
		logger = new Logger("ShareLink.log");
		logger.putstr("Loading of ShareLink " + version + " begins!");
		
		logger.putstr("Preparing the inserter ...");
		inserter = new Inserter();
		inserter.start();
		
		logger.putstr("Loading the database ...");
		database = new Database();

		logger.putstr("Registering the web interface ...");
		webInterface = new WebInterface();
		webInterface.createInterface();
		
		logger.putstr("Successfully started!");
	}

	@Override
	public void terminate() {
		logger.putstr("Terminating ...");
		webInterface.removeInterface();
		inserter.terminate();
		logger.close();
	}

	@Override
	public String getVersion() {
		return version;
	}

	@Override
	public long getRealVersion() {
		return realVersion;
	}

	@Override
	public String getString(String key) {
		return l10n.getString(key);
	}

	@Override
	public void setLanguage(LANGUAGE newLanguage) {
		plugL10n = new PluginL10n(this, newLanguage);
		l10n = plugL10n.getBase();
	}

	@Override
	public String getL10nFilesBasePath() {
		return "plugins/ShareLink/l10n/";
	}

	@Override
	public String getL10nFilesMask() {
		return "lang_${lang}.l10n";
	}

	@Override
	public String getL10nOverrideFilesMask() {
		return "ShareLink_lang_${lang}.override.l10n";
	}

	@Override
	public ClassLoader getPluginClassLoader() {
		return Plugin.class.getClassLoader();
	}
}
