package com.github.timelapseoptimizer;

import java.awt.image.BufferedImage;

import com.xuggle.mediatool.event.IVideoPictureEvent;

public class FrameDiscrepancyAnalyzer extends FrameProcessor {
	
	private final long keyFramePosition;
	private final ImageComparator comparator;
	
	private BufferedImage keyFrame;
	
	public FrameDiscrepancyAnalyzer(long keyFramePosition, ImageComparator comparator) {
		this.keyFramePosition = keyFramePosition;
		this.comparator = comparator;
	}
	
	
	@Override
	protected void process(long t, BufferedImage image, IVideoPictureEvent event) {
		
		// Check for keyframe
		if (keyFrame == null && t >= keyFramePosition) {
			keyFrame = image;
			return;
		}
		
		double diff = comparator.getSimilarity(keyFrame, image);
		int sqrt = (int) Math.sqrt(diff);
		//		System.out.printf("t=%5.2f \tdiff=%d \tsqrt=%d\n", t/1000000.0, (int)diff, (int)Math.sqrt(diff));
		System.out.printf("t=%5.2f \t%d\t", t / 1000000.0, sqrt);
		for (int i = 0; i < sqrt / 2; i++) {
			System.out.print('*');
		}
		System.out.println();
		
	}
}
