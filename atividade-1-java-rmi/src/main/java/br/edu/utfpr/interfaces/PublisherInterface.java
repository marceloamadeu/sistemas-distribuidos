package br.edu.utfpr.interfaces;

import br.edu.utfpr.entity.Enquete;

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
	 * @param enquete to be published
	 */
	public void publish(Enquete enquete);

	/**
	 * Advertise new topic for others to subscribe to
	 * 
	 * @param enquete
	 */
	public void advertise(Enquete enquete);

}
