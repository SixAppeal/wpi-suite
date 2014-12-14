package edu.wpi.cs.wpisuitetng.network;


public class TrelloNetwork extends Network {
	private static TrelloNetwork instance = null;

	protected TrelloNetwork() {
	}

	public static void setInstance(TrelloNetwork network) {
		instance = network;
	}

	/**
	 * Returns the singleton instance of TrelloNetwork.
	 * 
	 * @return The singleton instance of TrelloNetwork.
	 */
	public static TrelloNetwork getInstance() {
		if (instance == null) {
			instance = new TrelloNetwork();
		}
		return instance;
	}
}