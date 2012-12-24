package com.github.timelapseoptimizer;

import java.awt.image.BufferedImage;

/**
 * Interface for comparing two images for similarity.
 */
public interface ImageComparator {

	/**
	 * Return a value indicating the similarity of the two images.
	 * A value of zero indicates most similarity (identical) and positive values
	 * indicate increasing dissimilarity.
	 */
	public double getSimilarity(BufferedImage reference, BufferedImage image);
	
}
