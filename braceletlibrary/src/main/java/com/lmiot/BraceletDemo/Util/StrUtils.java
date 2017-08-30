/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lmiot.BraceletDemo.Util;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class includes a small subset of standard GATT attributes for demonstration purposes.
 */
public class StrUtils {

    private static HashMap<Integer, String> serviceTypes = new HashMap();
    static {
        // Sample Services.
    	serviceTypes.put(BluetoothGattService.SERVICE_TYPE_PRIMARY, "PRIMARY");
    	serviceTypes.put(BluetoothGattService.SERVICE_TYPE_SECONDARY, "SECONDARY");
    }
    
    public static String getServiceType(int type){
    	return serviceTypes.get(type);
    }
    

    //-------------------------------------------    
    private static HashMap<Integer, String> charPermissions = new HashMap();
    static {
    	charPermissions.put(0, "UNKNOW");
    	charPermissions.put(BluetoothGattCharacteristic.PERMISSION_READ, "READ");
    	charPermissions.put(BluetoothGattCharacteristic.PERMISSION_READ_ENCRYPTED, "READ_ENCRYPTED");
    	charPermissions.put(BluetoothGattCharacteristic.PERMISSION_READ_ENCRYPTED_MITM, "READ_ENCRYPTED_MITM");
    	charPermissions.put(BluetoothGattCharacteristic.PERMISSION_WRITE, "WRITE");
    	charPermissions.put(BluetoothGattCharacteristic.PERMISSION_WRITE_ENCRYPTED, "WRITE_ENCRYPTED");
    	charPermissions.put(BluetoothGattCharacteristic.PERMISSION_WRITE_ENCRYPTED_MITM, "WRITE_ENCRYPTED_MITM");
    	charPermissions.put(BluetoothGattCharacteristic.PERMISSION_WRITE_SIGNED, "WRITE_SIGNED");
    	charPermissions.put(BluetoothGattCharacteristic.PERMISSION_WRITE_SIGNED_MITM, "WRITE_SIGNED_MITM");	
    }
    
    public static String getCharPermission(int permission){
    	return getHashMapValue(charPermissions,permission);
    }
    //-------------------------------------------    
    private static HashMap<Integer, String> charProperties = new HashMap();
    static {
    	
    	charProperties.put(BluetoothGattCharacteristic.PROPERTY_BROADCAST, "BROADCAST");
    	charProperties.put(BluetoothGattCharacteristic.PROPERTY_EXTENDED_PROPS, "EXTENDED_PROPS");
    	charProperties.put(BluetoothGattCharacteristic.PROPERTY_INDICATE, "INDICATE");
    	charProperties.put(BluetoothGattCharacteristic.PROPERTY_NOTIFY, "NOTIFY");
    	charProperties.put(BluetoothGattCharacteristic.PROPERTY_READ, "READ");
    	charProperties.put(BluetoothGattCharacteristic.PROPERTY_SIGNED_WRITE, "SIGNED_WRITE");
    	charProperties.put(BluetoothGattCharacteristic.PROPERTY_WRITE, "WRITE");
    	charProperties.put(BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE, "WRITE_NO_RESPONSE");
    }
    
    public static String getCharPropertie(int property){
    	return getHashMapValue(charProperties,property);
    }
    
    //--------------------------------------------------------------------------
    private static HashMap<Integer, String> descPermissions = new HashMap();
    static {
    	descPermissions.put(0, "UNKNOW");
    	descPermissions.put(BluetoothGattDescriptor.PERMISSION_READ, "READ");
    	descPermissions.put(BluetoothGattDescriptor.PERMISSION_READ_ENCRYPTED, "READ_ENCRYPTED");
    	descPermissions.put(BluetoothGattDescriptor.PERMISSION_READ_ENCRYPTED_MITM, "READ_ENCRYPTED_MITM");
    	descPermissions.put(BluetoothGattDescriptor.PERMISSION_WRITE, "WRITE");
    	descPermissions.put(BluetoothGattDescriptor.PERMISSION_WRITE_ENCRYPTED, "WRITE_ENCRYPTED");
    	descPermissions.put(BluetoothGattDescriptor.PERMISSION_WRITE_ENCRYPTED_MITM, "WRITE_ENCRYPTED_MITM");
    	descPermissions.put(BluetoothGattDescriptor.PERMISSION_WRITE_SIGNED, "WRITE_SIGNED");
    	descPermissions.put(BluetoothGattDescriptor.PERMISSION_WRITE_SIGNED_MITM, "WRITE_SIGNED_MITM");
    }
    
    public static String getDescPermission(int property){
    	return getHashMapValue(descPermissions,property);
    }

    private static String getHashMapValue(HashMap<Integer, String> hashMap,int number){
    	String result =hashMap.get(number);
    	if(TextUtils.isEmpty(result)){
    		List<Integer> numbers = getElement(number);
    		result="";
    		for(int i=0;i<numbers.size();i++){
    			result+=hashMap.get(numbers.get(i))+"|";
    		}
    	}
    	return result;
    }

    /**
     * 位运算结果的反推函数10 -> 2 | 8;
     */
    static private List<Integer> getElement(int number){
    	List<Integer> result = new ArrayList<Integer>();
        for (int i = 0; i < 32; i++){
            int b = 1 << i;
            if ((number & b) > 0) 
            	result.add(b);
        }
        
        return result;
    }
    
    
    public static String bytesToHexString(byte[] src){  
        StringBuilder stringBuilder = new StringBuilder("");  
        if (src == null || src.length <= 0) {  
            return null;  
        }  
        for (int i = 0; i < src.length; i++) {  
            int v = src[i] & 0xFF;  
            String hv = Integer.toHexString(v);  
            if (hv.length() < 2) {  
                stringBuilder.append(0);  
            }  
            stringBuilder.append(hv);  
        }  
        return stringBuilder.toString();  
    }

    public static byte[] HexString2Bytes(String src) {
        byte[] ret = new byte[src.length() / 2];
        byte[] tmp = src.getBytes();
        for (int i = 0; i < tmp.length / 2; i++) {
            ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
        }
        return ret;
    }
    public static byte uniteBytes(byte src0, byte src1) {
        byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 }))
                .byteValue();
        _b0 = (byte) (_b0 << 4);
        byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 }))
                .byteValue();
        byte ret = (byte) (_b0 ^ _b1);
        return ret;
    }
    private static String hexString = "0123456789ABCDEF";
    /*
	 * 将字符串编码成16进制数字,适用于所有字符（包括中文）
	 */
    public static String encode(String str) {
        // 根据默认编码获取字节数组
        byte[] bytes = str.getBytes();
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        // 将字节数组中每个字节拆解成2位16进制整数
        for (int i = 0; i < bytes.length; i++) {
            sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
            sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
        }
        return sb.toString();
    }
    public static String str2HexStr(String str)
    {

        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;

        for (int i = 0; i < bs.length; i++)
        {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
            sb.append(' ');
        }
        return sb.toString().trim();
    }

    /**
     * 16进制字符串转为10进制int
     * @param str
     * @return
     */
    public static int str16to10int(String str){
        return Integer.parseInt(str, 16);
    }

    /**
     * 8位字符串的高低位数转换，用于手环协议
     * @param str
     * @return
     */
    public static String strConvert(String str){
        String s="";
        String a=str.substring(6,8);
        String b=str.substring(4,6);
        String c=str.substring(2,4);
        String d=str.substring(0,2);
        s=a+b+c+d;

        return s;
    }

	public static String strConvert4(String str){
		String s="";
		String c=str.substring(2,4);
		String d=str.substring(0,2);
		s=c+d;

		return s;
	}

    //用来处理日期月份
    public static String convertDate(String s){
        String [] s1=s.split("/");
        String year=s1[0];
        String month=s1[1];
        String day=s1[2];
        if (month.substring(0,1).equals("0")){
            month=month.substring(1,2);
        }
        String date=year+"/"+month+"/"+day;
        return date;
    }

    public static String convertDateMonth(String s) {
        String [] s1=s.split("/");
        String year=s1[0];
        String month=s1[1];

        if (month.substring(0,1).equals("0")){
            month=month.substring(1,2);
        }
        String date=year+"/"+month;
        return date;
    }
    //去掉日期前面的年份，为了精简
    public static String RemoveYearOfDate(String s){
        String []ss=s.split("/");
        String month=ss[1];
        String day=ss[2];
        return month+"/"+day;
    }
}
