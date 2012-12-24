package com.github.timelapseoptimizer;

import java.awt.image.BufferedImage;

/**
 * An image comparator that computes a squared RGB distance of each pixel
 * and returns the average distance of the pixels.
 */
public class RGBDistanceComparator implements ImageComparator {
	
	public double getSimilarity(BufferedImage reference, BufferedImage image) {
		int width = reference.getWidth();
		int height = reference.getHeight();

		if (image.getWidth() != width || image.getHeight() != height) {
			throw new IllegalArgumentException("Image sizes don't match,"
					+ " reference=" + reference.getWidth() + "x" + reference.getHeight() +
					" image=" + image.getWidth() + "x" + image.getHeight());
		}
		
		int[] ref = new int[width*height];
		int[] img = new int[width*height];
		
		reference.getRGB(0, 0, width, height, ref, 0, width);
		image.getRGB(0, 0, width, height, img, 0, width);
		
		double difference = 0;
		for (int i=0; i<ref.length; i++) {
			int px1 = ref[i];
			int px2 = img[i];
			
			int r1 = (px1 >> 16) & 0xFF;
			int g1 = (px1 >> 8) & 0xFF;
			int b1 = (px1) & 0xFF;
			int r2 = (px2 >> 16) & 0xFF;
			int g2 = (px2 >> 8) & 0xFF;
			int b2 = (px2) & 0xFF;
			
			difference += pow2(r1-r2) + pow2(g1-g2) + pow2(b1-b2);
		}
		
		difference /= (width*height);
		
		return difference;
	}
	
	
	private int pow2(int x) {
		return x*x;
	}
	
}
