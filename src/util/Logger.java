package util;

public class Logger {

	// Properties

	public static boolean enableAppLog = true;
	public static boolean enableEngineLog = true;
	public static boolean enableGuiLog = true;
	public static boolean enableIaLog = false;

	// Logs

	public static void logApp(String str) {
		if (Logger.enableAppLog) {
			System.out.println(str);
		}
	}

	public static void logEngine(String str) {
		if (Logger.enableEngineLog) {
			System.out.println("ENGINE DEBUG : " + str);
		}
	}

	public static void logGui(String str) {
		if (Logger.enableGuiLog) {
			System.out.println("GUI DEBUG : " + str);
		}
	}

	public static void logIa(String str) {
		if (Logger.enableIaLog) {
			System.out.println("IA DEBUG : " + str);
		}
	}

}
