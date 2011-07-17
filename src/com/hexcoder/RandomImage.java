package com.hexcoder;

import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: 67726e
 * Date: 7/15/11
 * Time: 10:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class RandomImage {
    public final int STRIPE_2_ROW = 0;
    public final int STRIPE_3_ROW = 1;
    public final int STRIPE_2_COLUMN = 2;
    public final int STRIPE_3_COLUMN = 3;
    public final int STRIPE_2_DIAGONAL = 4;
    public final int STRIPE_3_DIAGONAL = 5;
    private BufferedImage image;
    private Random random;

    /**
     * Generates a random image that can be used as a unique image for use in the LocateImage class
     * most typically as the compare image.
     *
     * @param x (int) is the horizontal size of the image to be generated
     * @param y (int) is the vertical size of the image to be generated
     * @param style (int) is the style of the image to be generated
     */
    public RandomImage(int x, int y, int style) {
        image = new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB);
        random = new Random();
        int color1 = random.nextInt(255) + 1;
        int color2 = random.nextInt(255) + 1;
        int color3 = random.nextInt(255) + 1;
        int[] colorArray1 = new int[x];
        int[] colorArray2 = new int[x];
        int[] colorArray3 = new int[x];

        for (int i = 0; i < x; i++) {
            colorArray1[i] = color1;
            colorArray2[i] = color2;
            colorArray3[i] = color3;
        }

        switch (style) {
        default:                        // Default to a 2 row striped image
        case 0:                         // STRIPE_2_ROW
            for (int i = 0; i < y; i++) {
                if (i % 2 == 0) image.setRGB(0, i, x, 1, colorArray1, 0, 1);
                else image.setRGB(0, i, x, 1, colorArray2, 0, 1);
            }
            break;
        case 1: {                       // STRIPE_3_ROW
            for (int i = 0; i < y; i++) {
                if (i % 3 == 0) image.setRGB(0, i, x, 1, colorArray1, 0, 1);
                else if ((i - 1) % 3 == 0) image.setRGB(0, i, x, 1, colorArray2, 0, 1);
                else image.setRGB(0, i, x, 1, colorArray3, 0, 1);
            }
            break;
            }
        case 2:                         // STRIPE_2_COLUMN
            for (int i = 0; i < x; i++) {
                if (i % 2 == 0) image.setRGB(i, 0, 1, 100, colorArray1, 0, 1);
                else image.setRGB(i, 0, 1, 100, colorArray2, 0, 1);
            }
            break;
        case 3: {                       // STRIPE_3_COLUMN
            for (int i = 0; i < x; i++) {
                if (i % 3 == 0) image.setRGB(i, 0, 1, 100, colorArray1, 0, 1);
                else if ((i - 1) % 3 == 0) image.setRGB(i, 0, 1, 100, colorArray2, 0, 1);
                else image.setRGB(i, 0, 1, 100, colorArray3, 0, 1);
            }
            break;
            }
        case 4:                         // STRIPE_2_DIAGONAL
            for (int i = 0; i < y; i++) {
                if (i % 2 == 0) {
                    int x2 = 0;
                    int y2 = i;

                    while (x2 < x && y2 < y) {
                        image.setRGB(x2++, y2++, color1);
                    }
                } else {
                    int x2 = 0;
                    int y2 = i;

                    while (x2 < x && y2 < y) {
                        image.setRGB(x2++, y2++, color2);
                    }
                }
            }

            for (int i = 0; i < x; i++) {
                if (i % 2 == 0) {
                    int x2 = i;
                    int y2 = 0;

                    while (x2 < x && y2 < y) {
                        image.setRGB(x2++, y2++, color1);
                    }
                } else {
                    int x2 = i;
                    int y2 = 0;

                    while (x2 < x && y2 < y) {
                        image.setRGB(x2++, y2++, color2);
                    }
                }
            }
            break;
        case 5: {                       // STRIPE_3_DIAGONAL
            for (int i = 0; i < y; i++) {
                if (i % 3 == 0) {
                    int x2 = 0;
                    int y2 = i;

                    while (x2 < x && y2 < y) {
                        image.setRGB(x2++, y2++, color1);
                    }
                } else if ((i - 1) % 3 == 0) {
                    int x2 = 0;
                    int y2 = i;

                    while (x2 < x && y2 < y) {
                        image.setRGB(x2++, y2++, color2);
                    }
                } else {
                    int x2 = 0;
                    int y2 = i;

                    while (x2 < x && y2 < y) {
                        image.setRGB(x2++, y2++, color3);
                    }
                }
            }

            for (int i = 0; i < x; i++) {
                if (i % 3 == 0) {
                    int x2 = i;
                    int y2 = 0;

                    while (x2 < x && y2 < y) {
                        image.setRGB(x2++, y2++, color1);
                    }
                } else if ((i - 1) % 3 == 0) {
                    int x2 = i;
                    int y2 = 0;

                    while (x2 < x && y2 < y) {
                        image.setRGB(x2++, y2++, color2);
                    }
                } else {
                    int x2 = i;
                    int y2 = 0;

                    while (x2 < x && y2 < y) {
                        image.setRGB(x2++, y2++, color3);
                    }
                }
            }
            break;
            }
        }
    }

    /**
     * Getter for the random image generated in the constructor
     *
     * @return BufferedImage that is the generated random image
     */
    public BufferedImage getImage() { return this.image; }

}
