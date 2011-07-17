ImageLocator
============

## License:

Dual licensed under MIT or GPL Version 2.0 by Glenn Nelson

## Description:

A Java library that compares two images and finds all instances of the "compare" image within the "base" image.  Library can check for small variances within images such as fluxuations in RGB values.

## Requirements

	JDK (Tested on 6.24)
	JVM

## Usage:

	ImageLocator locator = new ImageLocator(base, compare);
	locator.search();			// Raw search, no tolerance for variation
	locator.search(5);			// Tolerance search, allow for RGB variance +- 5

## Functions:
	ImageLocator(BufferedImage base, BufferedImage compare); // throws ImageLocatorSizeException
	void search();							// Raw search; no tolerance
	void search(int tolerance);				// Search w/ RGB variance tolerance
	boolean isAtLocation(int x, int y);		// Check for occourrence at x,y
	Point getFirstOccourrence();			// Get Point (Object) of first match
	Point getLastOccourrence();				// Get Point (Object) of last match (null if 0/1 match)
	int numberOfOccourrences();				// Number of matches
