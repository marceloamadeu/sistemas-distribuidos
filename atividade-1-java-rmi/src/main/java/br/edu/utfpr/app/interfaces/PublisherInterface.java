package br.edu.utfpr.app.interfaces;

import br.edu.utfpr.app.entity.Enquete;

/**
 * This interface is the methods available to a Publisher within the pub-sub system
 * 
 * @author rob mccartney
 *
 */
public interface PublisherInterface {
	
	/**
	 * Publish an event of a specific topic with title, content, and optional keywords for content filtering
	 * 
	 * @param event to be published
	 */
	public void publish(Enquete enquete);

	/**
	 * Advertise new topic for others to subscribe to
	 * 
	 * @param newTopic
	 */
	public void advertise(Enquete enquete);

}
