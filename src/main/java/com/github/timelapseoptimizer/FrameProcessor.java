package com.github.timelapseoptimizer;

import java.awt.image.BufferedImage;

import com.xuggle.mediatool.MediaListenerAdapter;
import com.xuggle.mediatool.event.IVideoPictureEvent;

/**
 * An abstract class that can be extended to consume individual
 * video frames.  This filters out unnecessary cruft from the Xuggle
 * API.
 */
public abstract class FrameProcessor extends MediaListenerAdapter {

	private Integer videoStreamIndex = -1;

	public void onVideoPicture(IVideoPictureEvent event) {
		// Verify we have the correct video stream.
		// If none selected yet, select this one.
		if (event.getStreamIndex() != videoStreamIndex) {
			if (-1 == videoStreamIndex) {
				videoStreamIndex = event.getStreamIndex();
			} else {
				return;
			}
		}
		
		// Verify we have the necessary content in the event.
		Long timestamp = event.getTimeStamp();
		if (timestamp == null) {
			return;
		}
		long t = timestamp;
		
		BufferedImage image = event.getImage();
		if (image == null) {
			return;
		}
		
		process(t, image, event);
	}
	
	
	/**
	 * Process the video frame.
	 * 
	 * @param timestamp		microsecond timestamp for the video frame.
	 * @param image			the video frame image.
	 * @param event			the original picture event.
	 */
	protected abstract void process(long timestamp, BufferedImage image, IVideoPictureEvent event);
	
}
