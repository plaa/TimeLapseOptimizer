package com.github.timelapseoptimizer;

import java.awt.image.BufferedImage;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.ToolFactory;


public class App {
	
	public App(String filename) {
		
		IMediaReader reader = ToolFactory.makeReader(filename);
		reader.setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);
		
		ImageComparator comparator = new RGBDistanceComparator();
		//		FrameDiscrepancyAnalyzer analyzer = new FrameDiscrepancyAnalyzer(0, new RGBDistanceComparator());
		FrameSelector selector = new FrameSelector(1000000, 4000000, comparator);
		reader.addListener(selector);
		
		while (reader.readPacket() == null) {
			// No-op
		}
	}
	
	
	
	public static void main(String[] args) {
		if (args.length <= 0) {
			throw new IllegalArgumentException("must pass in a filename as the first argument");
		}
		
		new App(args[0]);
	}
}
