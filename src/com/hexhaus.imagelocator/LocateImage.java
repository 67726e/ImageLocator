package com.hexcoder.imagelocator;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by IntelliJ IDEA.
 * User: 67726e
 * Date: 7/12/11
 * Time: 9:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class LocateImage {
    private int compareX, compareY;
    private int firstX, firstY, lastX, lastY;
    private Point[] occurrences;
    private BufferedImage base, compare;
    private int[][] baseRGB;
    private int[][] compareRGB;

    public LocateImage(BufferedImage base, BufferedImage compare) throws ImageSizeException {
        this.base = base;
        this.compare = compare;

        firstX = -1;
        firstY = -1;
        lastX = -1;
        lastY = -1;
        occurrences = new Point[0];

        if (base.getHeight() < compare.getHeight()
                || base.getWidth() < compare.getWidth()) {
            throw new ImageSizeException();
        }

        baseRGB = new int[base.getHeight()][base.getWidth()];
        compareRGB = new int[compare.getHeight()][compare.getWidth()];

        for (int y = 0; y < baseRGB.length; y++) {
            for (int x = 0; x < baseRGB[0].length; x++) {
                baseRGB[y][x] = base.getRGB(x, y);
            }
        }

        for (int y = 0; y < compareRGB.length; y++) {
            for (int x = 0; x < compareRGB.length; x++) {
                compareRGB[y][x] = compare.getRGB(x, y);
            }
        }
    }

    /**
     * Receives x/y coordinates and check if there is an occurrence that starts at that coordinate
     *
     * @param x is the horizontal coordinate
     * @param y is the vertical coordinate
     * @return true if there is a location at the given coordinate, otherwise false
     */
    public boolean isAtLocation(int x, int y) {
        for (int i = 0; i < occurrences.length; i++) {
            if (occurrences[i].getX() == x
                    && occurrences[i].getY() == y) return true;
        }
        return false;
    }

    /**
     * Returns a Point object containing the coordinates of the first match of the compare image within the base image
     *
     * @return Point object with the first coordinate
     */
    public Point getFirstOccurrence() {
        Point coord = new Point(firstX, firstY);
        return coord;
    }

    /**
     * Returns a Point object containing the coordinates of the last match of the compare image within the base image
     *
     * @return Point object with the last coordinate
     */
    public Point getLastOccurrence() {
        Point coord = new Point(firstX, firstY);
        return coord;
    }

    /**
     * Gives the amount of occurrences of the compare image within the base image
     *
     * @return int that is the length of the array containing all matches
     */
    public int numberOfOccurrences() { return this.occurrences.length; }

    /**
     * Gives a Point array with all the occurrences of the compare image within the base image
     *
     * @return Returns an array of Point objects each containing a coordinate match of the compare image within the base image
     */
    public Point[] getOccurrences() { return this.occurrences; }

    /**
     * Searches through the base image and records any instance of the compare image within
     *
     */
    public void search() {
        boolean match = false;

        for (int baseY = 0; baseY < baseRGB.length; baseY++) {
			for (int baseX = 0; baseX < baseRGB[0].length; baseX++) {
				if (baseRGB[baseY][baseX] == compareRGB[0][0]) {
					match = true;
					int x = baseX;
					int y = baseY;

					for (; y < (baseY + compareRGB.length); y++) {
						for (;x < (baseX + compareRGB[0].length); x++) {
							if (y >= baseRGB.length) { match = false; }									// Check if we have reached the vertical end of the image
							else if (x >= baseRGB[0].length) { match = false; }							// Check if we have reached the horizontal end of the image
							else if (baseRGB[y][x] != compareRGB[y - baseY][x - baseX]) { match = false; }	// Check if the pixels for the given coordinates mathc
						}
						x = baseX;																		// Reset the x location for the next iteration
					}

					if (match == true) {
						addOccurrence(baseX, baseY);													// Add the occourrence to the array of occourrences

						if (firstX == -1 && firstY == -1) {												// Check if this is the first match
							firstX = baseX;																// Set the x coordinate of the first occourrence
							firstY = baseY;																// Set the y coordinate of the first occourrence
						} else {																		// Otherwise this is the latest occourrence
							lastX = baseX;																// Set the x coordinate of the last occourrence
							lastY = baseY;																// Set the y coordinate of the last occourrence
						}
					}
				}
			}
		}
    }

    /**
     * Function searches through the base image for all instances of the compare image
     * while calculating for subtle variations in the RGB values.
     *
     * @param tolerance is the allowable amount of variance of a given R, G, or B value of a given pixel in the compare image
     */
    public void search(int tolerance) {
        boolean match = false;

		for (int baseY = 0; baseY < baseRGB.length; baseY++) {
			for (int baseX = 0; baseX < baseRGB[0].length; baseX++) {
				int bRGB = baseRGB[baseY][baseX];										// Get "base" RGB value
				int bR = bRGB & 0x00FF0000;											// Zero-Out all but the R bits
				bR = bR >> 16;														// Shift over 16 bits to get value
				int bG = bRGB & 0x0000FF00;											// Zero-Out all but the G bits
				bG = bG >> 8;														// Shift over 8 bits to get value
				int bB = bRGB & 0x000000FF;											// Zero-Out all but the B bits (no shifting needed)

				int cRGB = compareRGB[0][0];											// Get "compare" RGB value
				int cR = cRGB & 0x00FF0000;											// Zero-Out all but the R bits
				cR = cR >> 16;														// Shift over 16 bits to get value
				int cG = cRGB & 0x0000FF00;											// Zero-Out all but the G bits
				cG = cG >> 8;														// Shift over 8 bits to get value
				int cB = cRGB & 0x000000FF;											// Zero-Out all but the B bits (no shifting needed)

				boolean rgbMatch = false;
				if ((cR - bR) <= tolerance && (cR - bR) >= -tolerance &&			// Check if 'R' falls within tolerance levels
					(cG - bG) <= tolerance && (cG - bG) >= -tolerance &&			// Check if 'G' falls within tolerance levels
					(cB - bB) <= tolerance && (cB - bB) >= -tolerance)				// Check if 'B' falls within tolerance levels
					rgbMatch = true;

				if (rgbMatch) {
					match = true;
					int x = baseX;
					int y = baseY;

					for (; y < (baseY + compareRGB.length); y++) {
						for (;x < (baseX + compareRGB[0].length); x++) {
							if (y >= baseRGB.length) { match = false; }							// Check if we have reached the vertical end of the image
							else if (x >= baseRGB[0].length) { match = false; }					// Check if we have reached the horizontal end of the image
							else {
								bRGB = baseRGB[y][x];												// Get "base" RGB value
								bR = bRGB & 0x00FF0000;											// Zero-Out all but the R bits
								bR = bR >> 16;													// Shift over 16 bits to get value
								bG = bRGB & 0x0000FF00;											// Zero-Out all but the G bits
								bG = bG >> 8;													// Shift over 8 bits to get value
								bB = bRGB & 0x000000FF;											// Zero-Out all but the B bits (no shifting needed)

								cRGB = compareRGB[y - baseY][x - baseX];							// Get "compare" RGB value
								cR = cRGB & 0x00FF0000;											// Zero-Out all but the R bits
								cR = cR >> 16;													// Shift over 16 bits to get value
								cG = cRGB & 0x0000FF00;											// Zero-Out all but the G bits
								cG = cG >> 8;													// Shift over 8 bits to get value
								cB = cRGB & 0x000000FF;											// Zero-Out all but the B bits (no shifting needed)

								if (!((cR - bR) <= tolerance && (cR - bR) >= -tolerance &&		// Check if 'R' falls within tolerance limits
									(cG - bG) <= tolerance && (cG - bG) >= -tolerance &&		// Check if 'G' falls within tolerance limits
									(cB - bB) <= tolerance && (cB - bB) >= -tolerance)) 		// Check if 'B' falls within tolerance limits
									match = false;
							}
						}
						x = baseX;																// Reset the x location for the next iteration
					}

					if (match == true) {
						addOccurrence(baseX, baseY);											// Add the occourrence to the array of occourrences

						if (firstX == -1 && firstY == -1) {										// Check if this is the first match
							firstX = baseX;														// Set the x coordinate of the first occourrence
							firstY = baseY;														// Set the y coordinate of the first occourrence
						} else {																// Otherwise this is the latest occourrence
							lastX = baseX;														// Set the x coordinate of the last occourrence
							lastY = baseY;														// Set the y coordinate of the last occourrence
						}
					}
				}
			}
		}
    }

    /**
     * Takes x/y coordinate of an occurrence and adds it to the array of occurrences
     *
     * @param x is the horizontal coordinate
     * @param y is the vertical coordinate
     */
    private void addOccurrence(int x, int y) {
        Point[] temp = occurrences;
        occurrences = new Point[occurrences.length + 1];
        for (int i = 0; i < temp.length; i++) {
            occurrences[i] = temp[i];
        }
        occurrences[temp.length] = new Point(x, y);
    }
}
