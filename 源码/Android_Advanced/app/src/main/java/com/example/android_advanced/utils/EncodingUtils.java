package com.example.android_advanced.utils;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.HashMap;

public class EncodingUtils {

    public static HashMap hashMap; //用hasmap放置二维码的参数
    public static Bitmap bitmap;//声明一个bitmap对象用于放置图片;
    //生成二维码
    public static Bitmap createQRCode(String contents) {
        hashMap = new HashMap();
        hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        //定义二维码的纠错级别，为L
        hashMap.put(EncodeHintType.CHARACTER_SET, "utf-8");
        //设置字符编码为utf-8
        hashMap.put(EncodeHintType.MARGIN, 2);
        //设置margin属性为2,也可以不设置
        BitMatrix bitMatrix = null;   //这个类是用来描述二维码的,可以看做是个布尔类型的数组
        try {
            bitMatrix = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, 250, 250, hashMap);
            //调用encode()方法,第一次参数是二维码的内容，第二个参数是生二维码的类型，第三个参数是width，第四个参数是height，最后一个参数是hints属性
        } catch (WriterException e) {
            e.printStackTrace();
        }

        int width = bitMatrix.getWidth();//获取width
        int height = bitMatrix.getHeight();//获取height
        int[] pixels = new int[width * height]; //创建一个新的数组,大小是width*height
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                //通过两层循环,为二维码设置颜色
                if (bitMatrix.get(i, j)) {
                    pixels[i * width + j] = Color.BLACK;  //设置为黑色

                } else {
                    pixels[i * width + j] = Color.WHITE; //设置为白色
                }
            }
        }

        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        //调用Bitmap的createBitmap()，第一个参数是width,第二个参数是height,最后一个是config配置，可以设置成RGB_565
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        //调用setPixels(),第一个参数就是上面的那个数组，偏移为0，x,y也都可为0，根据实际需求来,最后是width ,和height
        return bitmap;
        //调用setImageBitmap()方法，将二维码设置到imageview控件里

    }
    //识别码
    public static String  getCode(Bitmap bitmap) {
        hashMap = new HashMap();
        hashMap.put(DecodeHintType.CHARACTER_SET, "utf-8");//设置解码的字符，为utf-8
        int width = bitmap.getWidth();//现在是从那个bitmap中得到width和height
        int height = bitmap.getHeight();
        int[] pixels = new int[width * height];//新建数组，大小为width*height
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height); //和什么的setPixels()方法对应
        Result result = null;//Result类主要是用于保存展示二维码的内容的
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new RGBLuminanceSource(width, height, pixels)));
        //BinaryBitmap这个类是用于反转二维码的，HybridBinarizer这个类是zxing在对图像进行二值化算法的一个类
        try {
            result = new MultiFormatReader().decode(binaryBitmap);//调用MultiFormatReader()方法的decode()，传入参数就是上面用的反转二维码的
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 2、生成条形码
     *
     * @param contents      需要生成的内容
     * @param desiredWidth  生成条形码的宽带
     * @param desiredHeight 生成条形码的高度
     * @return
     */
    public static Bitmap creatBarcode(String contents, int desiredWidth, int desiredHeight) {
        try {
            Bitmap ruseltBitmap = null;
            /**
             * 图片两端所保留的空白的宽度
             */
            int marginW = 20;
            /**
             * 条形码的编码类型
             */
            BarcodeFormat barcodeFormat = BarcodeFormat.CODE_128;
            ruseltBitmap = encodeAsBitmap(contents, barcodeFormat,
                    desiredWidth, desiredHeight);
            return ruseltBitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /*以下的为条形码生成辅助工具方法*/

    /**
     * 生成条形码的Bitmap
     *
     * @param contents      需要生成的内容
     * @param format        编码格式
     * @param desiredWidth
     * @param desiredHeight
     * @return
     */
    protected static Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int desiredWidth, int desiredHeight) {
        final int WHITE = 0xFFFFFFFF;
        final int BLACK = 0xFF000000;

        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result = null;
        try {
            result = writer.encode(contents, format, desiredWidth,
                    desiredHeight, null);
        } catch (WriterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        // All are 0, or black, by default
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

}