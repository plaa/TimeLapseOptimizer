package com.github.timelapseoptimizer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

import com.xuggle.mediatool.event.IVideoPictureEvent;

/**
 * A class that selects suitable frames from a video sequence for
 * a time lapse.
 */
public class FrameSelector extends FrameProcessor {
	
	private final long minT, maxT;
	private final ImageComparator comparator;
	
	private IVideoPictureEvent keyFrame;
	private List<IVideoPictureEvent> frames = new ArrayList<IVideoPictureEvent>();
	
	private int outputFile = 0;
	
	
	/**
	 * Sole constructor.
	 * 
	 * @param minT	minimum number of microseconds between selected frames.
	 * @param maxT	maximum number of microseconds between selected frames.
	 */
	public FrameSelector(long minT, long maxT, ImageComparator comparator) {
		this.minT = minT;
		this.maxT = maxT;
		this.comparator = comparator;
	}
	
	
	
	@Override
	protected void process(long timestamp, BufferedImage image, IVideoPictureEvent event) {
		if (keyFrame == null) {
			keyFrame = event;
			select(event);
			return;
		}
		
		// Check for frames within minT .. maxT
		if (timestamp < keyFrame.getTimeStamp() + minT) {
			return;
		}
		if (timestamp <= keyFrame.getTimeStamp() + maxT) {
			frames.add(event);
			return;
		}
		if (frames.isEmpty()) {
			throw new IllegalStateException("Frame list is empty, cannot choose next frame");
		}
		
		// Check which frame is best from the range
		double bestDiff = Double.MAX_VALUE;
		IVideoPictureEvent bestFrame = null;
		
		for (IVideoPictureEvent frame : frames) {
			double diff = comparator.getSimilarity(keyFrame.getImage(), frame.getImage());
			if (diff < bestDiff) {
				bestDiff = diff;
				bestFrame = frame;
			}
		}
		
		select(bestFrame);
		
	}
	
	
	private void select(IVideoPictureEvent event) {
		String filename = String.format("frame%04d.jpg", outputFile);
		outputFile++;
		
		System.out.println("Selecting frame at t=" + event.getTimeStamp() + "  file=" + filename);
		
		keyFrame = event;
		
		try {
			ImageIO.write(event.getImage(), "jpg", new File(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Remove obsolete frames from list
		Iterator<IVideoPictureEvent> iterator = frames.iterator();
		while (iterator.hasNext()) {
			IVideoPictureEvent frame = iterator.next();
			if (frame.getTimeStamp() < keyFrame.getTimeStamp() + minT) {
				iterator.remove();
			} else {
				break;
			}
		}
	}
	
}
