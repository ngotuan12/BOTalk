package com.bigsoft.camerasdk.util;

import android.graphics.Bitmap;
import android.graphics.Color;

public class ImageFilter {
	
	
	   public static final double PI = 3.14159d;
	    public static final double FULL_CIRCLE_DEGREE = 360d;
	    public static final double HALF_CIRCLE_DEGREE = 180d;
	    public static final double RANGE = 256d;
	    
	    
	    
	    /**
	     * 컬러 필터 
	     * @param src
	     * @param degree
	     * @return
	     */
	 public static Bitmap tintImage(Bitmap src, int degree) {
		 
	        int width = src.getWidth();
	        int height = src.getHeight();
	 
	        int[] pix = new int[width * height];
	        src.getPixels(pix, 0, width, 0, 0, width, height);
	 
	        int RY, GY, BY, RYY, GYY, BYY, R, G, B, Y;
	        double angle = (PI * (double)degree) / HALF_CIRCLE_DEGREE;
	        
	        int S = (int)(RANGE * Math.sin(angle));
	        int C = (int)(RANGE * Math.cos(angle));
	 
	        for (int y = 0; y < height; y++)
	            for (int x = 0; x < width; x++) {
	                int index = y * width + x;
	                int r = ( pix[index] >> 16 ) & 0xff;
	                int g = ( pix[index] >> 8 ) & 0xff;
	                int b = pix[index] & 0xff;
	                RY = ( 70 * r - 59 * g - 11 * b ) / 100;
	                GY = (-30 * r + 41 * g - 11 * b ) / 100;
	                BY = (-30 * r - 59 * g + 89 * b ) / 100;
	                Y  = ( 30 * r + 59 * g + 11 * b ) / 100;
	                RYY = ( S * BY + C * RY ) / 256;
	                BYY = ( C * BY - S * RY ) / 256;
	                GYY = (-51 * RYY - 19 * BYY ) / 100;
	                R = Y + RYY;
	                R = ( R < 0 ) ? 0 : (( R > 255 ) ? 255 : R );
	                G = Y + GYY;
	                G = ( G < 0 ) ? 0 : (( G > 255 ) ? 255 : G );
	                B = Y + BYY;
	                B = ( B < 0 ) ? 0 : (( B > 255 ) ? 255 : B );
	                pix[index] = 0xff000000 | (R << 16) | (G << 8 ) | B;
	            }
	         
	        Bitmap outBitmap = Bitmap.createBitmap(width, height, src.getConfig());    
	        outBitmap.setPixels(pix, 0, width, 0, 0, width, height);
	        
	        pix = null;
	        
	        return outBitmap;
	    }
	 
	
	/**
	 * 컬러 필터
	 * 
	 * @param src
	 * @param depth
	 * @param red
	 * @param green
	 * @param blue
	 * @return
	 */
	public static Bitmap createSepiaToningEffect(Bitmap src, int depth, double red, double green, double blue) {
	    // image size
	    int width = src.getWidth();
	    int height = src.getHeight();
	    // create output bitmap
	    Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
	    // constant grayscale
	    final double GS_RED = 0.3;
	    final double GS_GREEN = 0.59;
	    final double GS_BLUE = 0.11;
	    // color information
	    int A, R, G, B;
	    int pixel;
	 
	    // scan through all pixels
	    for(int x = 0; x < width; ++x) {
	        for(int y = 0; y < height; ++y) {
	            // get pixel color
	            pixel = src.getPixel(x, y);
	            // get color on each channel
	            A = Color.alpha(pixel);
	            R = Color.red(pixel);
	            G = Color.green(pixel);
	            B = Color.blue(pixel);
	            // apply grayscale sample
	            B = G = R = (int)(GS_RED * R + GS_GREEN * G + GS_BLUE * B);
	 
	            // apply intensity level for sepid-toning on each channel
	            R += (depth * red);
	            if(R > 255) { R = 255; }
	 
	            G += (depth * green);
	            if(G > 255) { G = 255; }
	 
	            B += (depth * blue);
	            if(B > 255) { B = 255; }
	 
	            // set new pixel color to output image
	            bmOut.setPixel(x, y, Color.argb(A, R, G, B));
	        }
	    }
	 
	    // return final image
	    return bmOut;
	}
	
	
	
	/**
	 * 흑백처리 
	 * @param src
	 * @return
	 */
	public static Bitmap doGreyscale(Bitmap src) {
	    // constant factors
	    final double GS_RED = 0.299;
	    final double GS_GREEN = 0.587;
	    final double GS_BLUE = 0.114;
	 
	    // create output bitmap
	    Bitmap bmOut = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
	    // pixel information
	    int A, R, G, B;
	    int pixel;
	 
	    // get image size
	    int width = src.getWidth();
	    int height = src.getHeight();
	 
	    // scan through every single pixel
	    for(int x = 0; x < width; ++x) {
	        for(int y = 0; y < height; ++y) {
	            // get one pixel color
	            pixel = src.getPixel(x, y);
	            // retrieve color of all channels
	            A = Color.alpha(pixel);
	            R = Color.red(pixel);
	            G = Color.green(pixel);
	            B = Color.blue(pixel);
	            // take conversion up to one single value
	            R = G = B = (int)(GS_RED * R + GS_GREEN * G + GS_BLUE * B);
	            // set new pixel color to output bitmap
	            bmOut.setPixel(x, y, Color.argb(A, R, G, B));
	        }
	    }
	 
	    // return final image
	    return bmOut;
	}
	
	
	/*
	 * 감마처리 ( 어둡게, 밝게 )
	 */
	public static Bitmap doGamma(Bitmap src, double red, double green, double blue) {
	    // create output image
	    Bitmap bmOut = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
	    // get image size
	    int width = src.getWidth();
	    int height = src.getHeight();
	    // color information
	    int A, R, G, B;
	    int pixel;
	    // constant value curve
	    final int    MAX_SIZE = 256;
	    final double MAX_VALUE_DBL = 255.0;
	    final int    MAX_VALUE_INT = 255;
	    final double REVERSE = 1.0;
	 
	    // gamma arrays
	    int[] gammaR = new int[MAX_SIZE];
	    int[] gammaG = new int[MAX_SIZE];
	    int[] gammaB = new int[MAX_SIZE];
	 
	    // setting values for every gamma channels
	    for(int i = 0; i < MAX_SIZE; ++i) {
	        gammaR[i] = (int)Math.min(MAX_VALUE_INT,
	                (int)((MAX_VALUE_DBL * Math.pow(i / MAX_VALUE_DBL, REVERSE / red)) + 0.5));
	        gammaG[i] = (int)Math.min(MAX_VALUE_INT,
	                (int)((MAX_VALUE_DBL * Math.pow(i / MAX_VALUE_DBL, REVERSE / green)) + 0.5));
	        gammaB[i] = (int)Math.min(MAX_VALUE_INT,
	                (int)((MAX_VALUE_DBL * Math.pow(i / MAX_VALUE_DBL, REVERSE / blue)) + 0.5));
	    }
	 
	    // apply gamma table
	    for(int x = 0; x < width; ++x) {
	        for(int y = 0; y < height; ++y) {
	            // get pixel color
	            pixel = src.getPixel(x, y);
	            A = Color.alpha(pixel);
	            // look up gamma
	            R = gammaR[Color.red(pixel)];
	            G = gammaG[Color.green(pixel)];
	            B = gammaB[Color.blue(pixel)];
	            // set new color to output bitmap
	            bmOut.setPixel(x, y, Color.argb(A, R, G, B));
	        }
	    }
	 
	    // return final image
	    return bmOut;
	}
	
	
	
	public static Bitmap doBrightness(Bitmap src, int value) {
	    // image size
	    int width = src.getWidth();
	    int height = src.getHeight();
	    // create output bitmap
	    Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
	    // color information
	    int A, R, G, B;
	    int pixel;
	 
	    // scan through all pixels
	    for(int x = 0; x < width; ++x) {
	        for(int y = 0; y < height; ++y) {
	            // get pixel color
	            pixel = src.getPixel(x, y);
	            A = Color.alpha(pixel);
	            R = Color.red(pixel);
	            G = Color.green(pixel);
	            B = Color.blue(pixel);
	 
	            // increase/decrease each channel
	            R += value;
	            if(R > 255) { R = 255; }
	            else if(R < 0) { R = 0; }
	 
	            G += value;
	            if(G > 255) { G = 255; }
	            else if(G < 0) { G = 0; }
	 
	            B += value;
	            if(B > 255) { B = 255; }
	            else if(B < 0) { B = 0; }
	 
	            // apply new pixel color to output bitmap
	            bmOut.setPixel(x, y, Color.argb(A, R, G, B));
	        }
	    }
	 
	    // return final image
	    return bmOut;
	}


	/**
	 * 이미지 blur 처리 
	 * @param sentBitmap
	 * @param radius
	 * @return
	 */
	public static Bitmap fastblur(Bitmap sentBitmap, int radius) {

		//if(sentBitmap==null) return null;
		
        Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);

        if (radius < 1) {
            return (null);
        }

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int[] pix = new int[w * h];
   
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;

        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int vmin[] = new int[Math.max(w, h)];

        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int dv[] = new int[256 * divsum];
        for (i = 0; i < 256 * divsum; i++) {
            dv[i] = (i / divsum);
        }

        yw = yi = 0;

        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;

        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = radius;

            for (x = 0; x < w; x++) {

                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vmin[x]];

                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;

                sir = stack[i + radius];

                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];

                rbs = r1 - Math.abs(i);

                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;

                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }

                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] = ( 0xff000000 & pix[yi] ) | ( dv[rsum] << 16 ) | ( dv[gsum] << 8 ) | dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];

                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi += w;
            }
        }

       
        bitmap.setPixels(pix, 0, w, 0, 0, w, h);

        return (bitmap);
    }
	
	
    
	
	public static class ConvolutionMatrix
	{
	    public static final int SIZE = 3;
	 
	    public double[][] Matrix;
	    public double Factor = 1;
	    public double Offset = 1;
	 
	    public ConvolutionMatrix(int size) {
	        Matrix = new double[size][size];
	    }
	 
	    public void setAll(double value) {
	        for (int x = 0; x < SIZE; ++x) {
	            for (int y = 0; y < SIZE; ++y) {
	                Matrix[x][y] = value;
	            }
	        }
	    }
	 
	    public void applyConfig(double[][] config) {
	        for(int x = 0; x < SIZE; ++x) {
	            for(int y = 0; y < SIZE; ++y) {
	                Matrix[x][y] = config[x][y];
	            }
	        }
	    }
	 
	    public static  Bitmap computeConvolution3x3(Bitmap src, ConvolutionMatrix matrix) {
	        int width = src.getWidth();
	        int height = src.getHeight();
	        Bitmap result = Bitmap.createBitmap(width, height, src.getConfig());
	 
	        int A, R, G, B;
	        int sumR, sumG, sumB;
	        int[][] pixels = new int[SIZE][SIZE];
	 
	        for(int y = 0; y < height - 2; ++y) {
	            for(int x = 0; x < width - 2; ++x) {
	 
	                // get pixel matrix
	                for(int i = 0; i < SIZE; ++i) {
	                    for(int j = 0; j < SIZE; ++j) {
	                        pixels[i][j] = src.getPixel(x + i, y + j);
	                    }
	                }
	 
	                // get alpha of center pixel
	                A = Color.alpha(pixels[1][1]);
	 
	                // init color sum
	                sumR = sumG = sumB = 0;
	 
	                // get sum of RGB on matrix
	                for(int i = 0; i < SIZE; ++i) {
	                    for(int j = 0; j < SIZE; ++j) {
	                        sumR += (Color.red(pixels[i][j]) * matrix.Matrix[i][j]);
	                        sumG += (Color.green(pixels[i][j]) * matrix.Matrix[i][j]);
	                        sumB += (Color.blue(pixels[i][j]) * matrix.Matrix[i][j]);
	                    }
	                }
	 
	                // get final Red
	                R = (int)(sumR / matrix.Factor + matrix.Offset);
	                if(R < 0) { R = 0; }
	                else if(R > 255) { R = 255; }
	 
	                // get final Green
	                G = (int)(sumG / matrix.Factor + matrix.Offset);
	                if(G < 0) { G = 0; }
	                else if(G > 255) { G = 255; }
	 
	                // get final Blue
	                B = (int)(sumB / matrix.Factor + matrix.Offset);
	                if(B < 0) { B = 0; }
	                else if(B > 255) { B = 255; }
	 
	                // apply new pixel
	                result.setPixel(x + 1, y + 1, Color.argb(A, R, G, B));
	            }
	        }
	 
	        // final image
	        return result;
	    }
	}
	
	
}
