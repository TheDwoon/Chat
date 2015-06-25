package edu.kit.fsmi.schnupperstudium.chat.common.network;

/**
 * 
 * @author Daniel Wieland
 *
 */
public interface ChannelInitializer {
	/**
	 * Called when a channel is accepted by a network.
	 * 
	 * @param channel channel to be initialzed.
	 */
	void initChannel(NetworkChannel channel);
}
