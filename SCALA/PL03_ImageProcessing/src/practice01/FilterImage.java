/**
 * NOTE! THIS FILE IS NO LONGER IN USE [OBSOLETE].
 * Check:
 * https://github.com/yusufshakeel/Java-Image-Processing-Project/blob/master/src/Obsolete.txt
 * 
 * for more details.
 * 
 */


package practice01;

/**
 * File: FilterImage.java
 * 
 * Description:
 * This file contains different filtering methods.
 * 
 * @author Yusuf Shakeel
 * @version 1.0
 * Date: 26-01-2014 sun
 * 
 * www.github.com/yusufshakeel/Java-Image-Processing-Project
 * 
 * The MIT License (MIT)
 * Copyright (c) 2014 Yusuf Shakeel
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
public class FilterImage {
    
    /**
     * This method is used to perform median filtering on the image object passed.
     * 
     * @param img The image object passed on which median filtering is performed.
     * @param maskSize - The size of the mask is an odd integer like 3, 5, 7 … etc.
     */
    public static void medianFilter(MyImage img, int maskSize){
        
        /** 
         * This array will store the output of the median filter operation which will
         * be later written back to the original image pixels.
         */
        int outputPixels[] = new int[img.getImageTotalPixels()];

        /**
         * Buff is a 2D square of odd size like 3x3, 5x5, 7x7, ...
         * For simplicity storing it into 1D array.
         */
        int buff[];
        
        /** Median Filter operation */
        for(int y = 0; y < img.getImageHeight(); y++){
            for(int x = 0; x < img.getImageWidth(); x++){
                buff = new int[maskSize * maskSize];
                int count = 0;
                for(int r = y - (maskSize / 2); r <= y + (maskSize / 2); r++){
                    for(int c = x - (maskSize / 2); c <= x + (maskSize / 2); c++){
                        if(r < 0 || r >= img.getImageHeight() || c < 0 || c >= img.getImageWidth()){
                            /** Some portion of the mask is outside the image. */
                            continue;
                        }else{
                            buff[count] = img.getPixel(c, r);
                            count++;
                        }
                    }
                }
                
                /** sort buff array */
                java.util.Arrays.sort(buff);
                
                /** save median value in outputPixels array */
                outputPixels[x+(y*img.getImageWidth())] = buff[count/2];
            }
        }
        /** Write the output pixels to the image pixels */
        for(int y = 0; y < img.getImageHeight(); y++){
            for(int x = 0; x < img.getImageWidth(); x++){
                img.setPixelToValue(x, y, outputPixels[x+(y*img.getImageWidth())]);
            }
        }
    }
    
    /**
     * This method is used to perform median filtering on the image object passed.
     * 
     * @param img The image object passed on which median filtering is performed.
     * @param maskSize - The size of the mask is an odd integer like 3, 5, 7 … etc.
     */
    public static void medianFilter_RGB(MyImage img, int maskSize){
        
        /** 
         * This array will store the output of the median filter operation which will
         * be later written back to the original image pixels.
         */
        int outputPixels[] = new int[img.getImageTotalPixels()];

        /**
         * red, green and blue are a 2D square of odd size like 3x3, 5x5, 7x7, ...
         * For simplicity storing it into 1D array.
         */
        int red[], green[], blue[];
        
        /** Median Filter operation */
        for(int y = 0; y < img.getImageHeight(); y++){
            for(int x = 0; x < img.getImageWidth(); x++){
                red = new int[maskSize * maskSize];
                green = new int [maskSize * maskSize];
                blue = new int [maskSize * maskSize];
                int count = 0;
                for(int r = y - (maskSize / 2); r <= y + (maskSize / 2); r++){
                    for(int c = x - (maskSize / 2); c <= x + (maskSize / 2); c++){
                        if(r < 0 || r >= img.getImageHeight() || c < 0 || c >= img.getImageWidth()){
                            /** Some portion of the mask is outside the image. */
                            continue;
                        }else{
                            red[count] = img.getRed(c, r);
                            green[count] = img.getGreen(c, r);
                            blue[count] = img.getBlue(c, r);
                            count++;
                        }
                    }
                }
                
                /** sort red, green, blue array */
                java.util.Arrays.sort(red);
                java.util.Arrays.sort(green);
                java.util.Arrays.sort(blue);
                
                /** save median value in outputPixels array */
                int p = ImageFX.getPixelValueFromARGBValue(255, red[count/2], green[count/2], blue[count/2]);
                outputPixels[x+(y*img.getImageWidth())] = p;
            }
        }
        /** Write the output pixels to the image pixels */
        for(int y = 0; y < img.getImageHeight(); y++){
            for(int x = 0; x < img.getImageWidth(); x++){
                img.setPixelToValue(x, y, outputPixels[x+(y*img.getImageWidth())]);
            }
        }
    }
    
    /**
     * This method is used to perform median filtering on the image object passed.
     * This method will fill the cells of the mask not over the pixels of the image with 0.
     * 
     * Mask 3x3
     *  |
     *  V         Image Pixels 3x5
     * +-+-+-+    |
     * |0|0|0|    V
     * +-+-+-+-+-+-+
     * |0|1|2|3|2|1|
     * +-+-+-+-+-+-+
     * |0|4|5|6|5|4|
     * +-+-+-+-+-+-+
     *   |7|8|9|8|7|
     *   +-+-+-+-+-+
     * 
     * @param img The image object passed on which median filtering is performed.
     * @param maskSize - The size of the mask is an odd integer like 3, 5, 7 … etc.
     */
    public static void medianFilter_ZeroFill(MyImage img, int maskSize){
        
        /** 
         * This array will store the output of the median filter operation which will
         * be later written back to the original image pixels.
         */
        int outputPixels[] = new int[img.getImageTotalPixels()];

        /**
         * Buff is a 2D square of odd size like 3x3, 5x5, 7x7, ...
         * For simplicity storing it into 1D array.
         */
        int buff[];
        
        /** Median Filter operation */
        for(int y = 0; y < img.getImageHeight(); y++){
            for(int x = 0; x < img.getImageWidth(); x++){
                buff = new int[maskSize * maskSize];
                int i = 0;
                for(int r = y - (maskSize / 2); r <= y + (maskSize / 2); r++){
                    for(int c = x - (maskSize / 2); c <= x + (maskSize / 2); c++){
                        if(r < 0 || r >= img.getImageHeight() || c < 0 || c >= img.getImageWidth()){
                            /** Some portion of the mask is outside the image. */
                            buff[i] = 0;
                        }else{
                            buff[i] = img.getPixel(c, r);
                        }
                        i++;
                    }
                }
                java.util.Arrays.sort(buff);
                outputPixels[x+(y*img.getImageWidth())] = buff[(maskSize*maskSize)/2];
            }
        }
        /** Write the output pixels to the image pixels */
        for(int y = 0; y < img.getImageHeight(); y++){
            for(int x = 0; x < img.getImageWidth(); x++){
                img.setPixelToValue(x, y, outputPixels[x+(y*img.getImageWidth())]);
            }
        }
    }
    
    /**
     * This method is used to perform median filtering on the image object passed.
     * This method will fill the cells of the mask not over the pixels of the image
     * with the value of the pixels near to the cell.
     *
     * Mask 3x3
     *  |
     *  V         Image Pixels 3x5
     * +-+-+-+    |
     * |1|1|2|    V
     * +-+-+-+-+-+-+
     * |1|1|2|3|2|1|
     * +-+-+-+-+-+-+
     * |4|4|5|6|5|4|
     * +-+-+-+-+-+-+
     *   |7|8|9|8|7|
     *   +-+-+-+-+-+
     * 
     * @param img The image object passed on which median filtering is performed.
     * @param maskSize - The size of the mask is an odd integer like 3, 5, 7 … etc.
     */
    public static void medianFilter_ValueFill(MyImage img, int maskSize){
        
        /** 
         * This array will store the output of the median filter operation which will
         * be later written back to the original image pixels.
         */
        int outputPixels[] = new int[img.getImageTotalPixels()];

        /**
         * Buff is a 2D square of odd size like 3x3, 5x5, 7x7, ...
         * For simplicity storing it into 1D array.
         */
        int buff[];
        
        /** Median Filter operation */
        for(int y = 0; y < img.getImageHeight(); y++){
            for(int x = 0; x < img.getImageWidth(); x++){
                buff = new int[maskSize * maskSize];
                int i = 0;
                for(int r = y - (maskSize / 2); r <= y + (maskSize / 2); r++){
                    for(int c = x - (maskSize / 2); c <= x + (maskSize / 2); c++){
                        if(r < 0 || r >= img.getImageHeight() || c < 0 || c >= img.getImageWidth()){
                            /** Some portion of the mask is outside the image. */
                            int tr = r, tc = c;
                            if(r < 0){
                                tr = r+1;
                            }else if(r == img.getImageHeight()){
                                tr = r-1;
                            }
                            if(c < 0){
                                tc = c+1;
                            }else if(c == img.getImageWidth()){
                                tc = c-1;
                            }
                            buff[i] = img.getPixel(tc, tr);
                        }else{
                            buff[i] = img.getPixel(c, r);
                        }
                        i++;
                    }
                }
                java.util.Arrays.sort(buff);
                outputPixels[x+(y*img.getImageWidth())] = buff[(maskSize*maskSize)/2];
            }
        }
        /** Write the output pixels to the image pixels */
        for(int y = 0; y < img.getImageHeight(); y++){
            for(int x = 0; x < img.getImageWidth(); x++){
                img.setPixelToValue(x, y, outputPixels[x+(y*img.getImageWidth())]);
            }
        }
    }
    
    /**
     * Common names: Mean Filtering, Smoothing, Averaging, Box Filtering.
     * This method is used to remove noise from image by averaging the neighbouring
     * pixels under the mask. Note! details from the image is also removed in this
     * process. Hence bigger the mask size more the detail lost.
     * This method will fill the pixels outside the mask with 0 value.
     *
     * Mask 3x3
     *  |
     *  V         Image Pixels 3x5
     * +-+-+-+    |
     * |0|0|0|    V
     * +-+-+-+-+-+-+
     * |0|1|2|3|2|1|
     * +-+-+-+-+-+-+
     * |0|4|5|6|5|4|
     * +-+-+-+-+-+-+
     *   |7|8|9|8|7|
     *   +-+-+-+-+-+
     * 
     * @param img The image object passed on which median filtering is performed.
     * @param maskSize - The size of the mask is an odd integer like 3, 5, 7 … etc.
     */
    public static void meanFilter_ZeroFill(MyImage img, int maskSize){
        
        /** 
         * This array will store the output of the median filter operation which will
         * be later written back to the original image pixels.
         */
        int outputPixels[] = new int[img.getImageTotalPixels()];

        /**
         * Buff is a 2D square of odd size like 3x3, 5x5, 7x7, ...
         * For simplicity storing it into 1D array.
         */
        int buff[];
        
        /** Median Filter operation */
        for(int y = 0; y < img.getImageHeight(); y++){
            for(int x = 0; x < img.getImageWidth(); x++){
                buff = new int[maskSize * maskSize];
                int i = 0;
                for(int r = y - (maskSize / 2); r <= y + (maskSize / 2); r++){
                    for(int c = x - (maskSize / 2); c <= x + (maskSize / 2); c++){
                        if(r < 0 || r >= img.getImageHeight() || c < 0 || c >= img.getImageWidth()){
                            /** Some portion of the mask is outside the image. */
                            buff[i] = 0;
                        }else{
                            buff[i] = img.getPixel(c, r);
                        }
                        i++;
                    }
                }
                
                /** Find mean of the buff array value */
                int sa=0, sr=0, sg=0, sb=0;
                for(i = 0; i < 9; i++){
                    sa += ImageFX.getAlphaValueFromPixelValue(buff[i]);
                    sr += ImageFX.getRedValueFromPixelValue(buff[i]);
                    sg += ImageFX.getGreenValueFromPixelValue(buff[i]);
                    sb += ImageFX.getBlueValueFromPixelValue(buff[i]);
                }
                
                /** Save pixel value to outputPixels array */
                int p = ImageFX.getPixelValueFromARGBValue(sa/9, sr/9, sg/9, sb/9);
                outputPixels[x+(y*img.getImageWidth())] = p;
            }
        }
        /** Write the output pixels to the image pixels */
        for(int y = 0; y < img.getImageHeight(); y++){
            for(int x = 0; x < img.getImageWidth(); x++){
                img.setPixelToValue(x, y, outputPixels[x+(y*img.getImageWidth())]);
            }
        }
    }
    
    /**
     * Common names: Mean Filtering, Smoothing, Averaging, Box Filtering.
     * This method is used to remove noise from image by averaging the neighbouring
     * pixels under the mask. Note! details from the image is also removed in this
     * process. Hence bigger the mask size more the detail lost.
     * This method fill the pixels outside the mask with values near to the pixels.
     *
     * Mask 3x3
     *  |
     *  V         Image Pixels 3x5
     * +-+-+-+    |
     * |1|1|2|    V
     * +-+-+-+-+-+-+
     * |1|1|2|3|2|1|
     * +-+-+-+-+-+-+
     * |4|4|5|6|5|4|
     * +-+-+-+-+-+-+
     *   |7|8|9|8|7|
     *   +-+-+-+-+-+
     * 
     * @param img The image object passed on which median filtering is performed.
     * @param maskSize - The size of the mask is an odd integer like 3, 5, 7 … etc.
     */
    public static void meanFilter_ValueFill(MyImage img, int maskSize){
        
        /** 
         * This array will store the output of the median filter operation which will
         * be later written back to the original image pixels.
         */
        int outputPixels[] = new int[img.getImageTotalPixels()];

        /**
         * Buff is a 2D square of odd size like 3x3, 5x5, 7x7, ...
         * For simplicity storing it into 1D array.
         */
        int buff[];
        
        /** Median Filter operation */
        for(int y = 0; y < img.getImageHeight(); y++){
            for(int x = 0; x < img.getImageWidth(); x++){
                buff = new int[maskSize * maskSize];
                int i = 0;
                for(int r = y - (maskSize / 2); r <= y + (maskSize / 2); r++){
                    for(int c = x - (maskSize / 2); c <= x + (maskSize / 2); c++){
                        if(r < 0 || r >= img.getImageHeight() || c < 0 || c >= img.getImageWidth()){
                            /** Some portion of the mask is outside the image. */
                            int tr = r, tc = c;
                            if(r < 0){
                                tr = r+1;
                            }else if(r == img.getImageHeight()){
                                tr = r-1;
                            }
                            if(c < 0){
                                tc = c+1;
                            }else if(c == img.getImageWidth()){
                                tc = c-1;
                            }
                            buff[i] = img.getPixel(tc, tr);
                        }else{
                            buff[i] = img.getPixel(c, r);
                        }
                        i++;
                    }
                }
                
                /** Find mean of the buff array value */
                int sa=0, sr=0, sg=0, sb=0;
                for(i = 0; i < 9; i++){
                    sa += ImageFX.getAlphaValueFromPixelValue(buff[i]);
                    sr += ImageFX.getRedValueFromPixelValue(buff[i]);
                    sg += ImageFX.getGreenValueFromPixelValue(buff[i]);
                    sb += ImageFX.getBlueValueFromPixelValue(buff[i]);
                }
                
                /** Save pixel value to outputPixels array */
                int p = ImageFX.getPixelValueFromARGBValue(sa/9, sr/9, sg/9, sb/9);
                outputPixels[x+(y*img.getImageWidth())] = p;
            }
        }
        /** Write the output pixels to the image pixels */
        for(int y = 0; y < img.getImageHeight(); y++){
            for(int x = 0; x < img.getImageWidth(); x++){
                img.setPixelToValue(x, y, outputPixels[x+(y*img.getImageWidth())]);
            }
        }
    }
}//class FilterImage ends here