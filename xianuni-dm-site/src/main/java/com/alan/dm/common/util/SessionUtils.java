package com.alan.dm.common.util;

import com.alan.dm.entity.CookieInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * COOKIE的生成以及校验器
 * Created by zhangbinalan on 15/11/15.
 */

public class SessionUtils {
    private static Logger LOG= LoggerFactory.getLogger(SessionUtils.class);

    private static final String priKey = "30820276020100300d06092a864886f70d0101010500048202603082025c02010002818100901b902f06e2a12188055e6da3fe0667a7a6818fd5ef5dac981e680efa477f22d7c9d57644d65a6c8a031fc28180aa35bd610f34065f534610b33df4169ddb57d8770a808a6946aa1c1ab7d3d026ee39b04dcd69c895b3b0e14d9104ff3d559d110097d81f603a09354c33e121a171c20589eaaedb99820c3f6d3605ff6482ab02030100010281807c7201124b5cdc8f0b5aaf8ff9257e3c772fb8051157734a7fd70a3f9cecaf22d4bc6c8584aea349c5e92abd1a7e94fe717b66d8a38420728a2b6f9de106130ab668c8e7bf67288e68730e69af20ea625dd21b1d62c898a3b51043187b2e37d0741b407f514aeb5e8d70667f411f0642ec5430bb1881f7e7ce31094fa30a46d9024100efccd0b7ba05f7809534fd8c7146884b9c1e635572316328a490020bcd74fbbbd78f4e96c02b752279e13fb20e3cf96a3be388b63db9530decdc45ea77445bed02410099d7cf9075e3ab44dc93b01daf71ae90a131cd9195e09d64518c4fd6607964263964e3af619347038ad3f6d0435edd28f4608cf938c4e4689de05db12f8ef5f7024100b50b361d3015e729adfca6dc8c7d0ad3c2d7727985506223c232734903e2295845438dbae4f52a67500b4e7298ab5246f15634d61a3dbbc5cca8f52432cd6a7502402bccf0f0bd4fd3bc685cb820cb89bf5cb83838c2017d10f2b5f10fee064ba982e4865ff8d7fc1994dd9ad9c393dad776c89510ae0846ae8d469dd1af17ce492302401d33c7672ad881daf99b6160592a153b8bb173d9f13ad30c65edbe88d75f2099f7014ef86a654f7fcdd958cce8fed8578aaf8c3efae328bdcc13dcb8a20ecc85";
    private static final String pubKey = "30819f300d06092a864886f70d010101050003818d0030818902818100901b902f06e2a12188055e6da3fe0667a7a6818fd5ef5dac981e680efa477f22d7c9d57644d65a6c8a031fc28180aa35bd610f34065f534610b33df4169ddb57d8770a808a6946aa1c1ab7d3d026ee39b04dcd69c895b3b0e14d9104ff3d559d110097d81f603a09354c33e121a171c20589eaaedb99820c3f6d3605ff6482ab0203010001";
    private static final String SEP = "#";
    /**
     * 默认的cookie有效期
     */
    private static final long TIMEVAL = 43200000L;

    /**
     * 根据相应的登录信息产生COOKIE
     * @return
     */
    public static String generateCookie(Integer userId,String userIp,Long loginTime,Long timeInterval) {
        StringBuffer sb = new StringBuffer(SEP);
        sb.append(userId).append(SEP);
        sb.append(userIp).append(SEP);
        sb.append(loginTime==null?System.currentTimeMillis():loginTime).append(SEP);
        sb.append(timeInterval == null ? TIMEVAL : timeInterval);
        String cookie=RSA.encode(sb.toString());
        return cookie;
    }

    /**
     * 解析cookie
     * @param cookie
     * @return
     */
    public static CookieInfo parseCookie(String cookie) {
        String src = RSA.decode(cookie);
        try {
            String[] token = src.split(SEP);
            int userId = Integer.parseInt(token[1]);
            String userIp = token[2];
            long loginTime = Long.parseLong(token[3]);
            long timeInterval = Long.parseLong(token[4]);
            CookieInfo cookieInfo=new CookieInfo();
            cookieInfo.setCookie(cookie);
            cookieInfo.setUserId(userId);
            cookieInfo.setUserIp(userIp);
            cookieInfo.setLoginTime(loginTime);
            cookieInfo.setTimeValidate(timeInterval);
            return cookieInfo;
        } catch (Exception e) {
            LOG.error("parse cookie fail,cookie={}",cookie,e);
            return null;
        }
    }

    /**
     * 检查会话信息
     * 如果传入了userId/userIp/currentTime则进行相应的属性的检查
     * @param cookieInfo
     * @param userId
     * @param userIp
     * @param timeInvalid
     * @return
     */
    public static int validateCookie(CookieInfo cookieInfo,Integer userId,String userIp,Long timeInvalid) {
        if(userId!=null&&cookieInfo.getUserId()!=userId){
            return 1;
        }
        if(!StringUtils.isEmpty(userIp)&&!userIp.equals(cookieInfo.getUserIp())){
            return 2;
        }
        if(timeInvalid!=null&&(cookieInfo.getLoginTime()+timeInvalid<System.currentTimeMillis())){
            return 3;
        }
        return 0;
    }

    private static class Base {
        private static final byte[] encodingTable = {
                46, 95,
                48, 49, 50, 51, 52,
                53, 54, 55, 56, 57,
                97, 98, 99, 100, 101,
                102, 103, 104, 105, 106,
                107, 108, 109, 110, 111,
                112, 113, 114, 115, 116,
                117, 118, 119, 120, 121,
                122,
                65, 66, 67, 68, 69,
                70, 71, 72, 73, 74,
                75, 76, 77, 78, 79,
                80, 81, 82, 83, 84,
                85, 86, 87, 88, 89,
                90 };

        private static final byte[] decodingTable = new byte[128];

        static {
            for (int i = 0; i < 128; i++) {
                decodingTable[i] = -1;
            }
            for (int i = 65; i <= 90; i++) {
                decodingTable[i] = ((byte)(i - 65 + 38));
            }
            for (int i = 97; i <= 122; i++) {
                decodingTable[i] = ((byte)(i - 97 + 12));
            }
            for (int i = 48; i <= 57; i++) {
                decodingTable[i] = ((byte)(i - 48 + 2));
            }
            decodingTable[46] = 0;
            decodingTable[95] = 1;
        }

        public static byte[] encode(byte[] data) {
            int mod = data.length % 3;
            byte[] bytes;
            if (mod == 0){
                bytes = new byte[4 * data.length / 3];
            } else {
                bytes = new byte[4 * data.length / 3 + 1];
            }
            int dataLength = data.length - mod;
            int i = 0;
            for (int j = 0; i < dataLength; ) {
                int a1 = data[(i++)] & 0xFF;
                int a2 = data[(i++)] & 0xFF;
                int a3 = data[(i++)] & 0xFF;
                bytes[(j++)] = encodingTable[(a1 >>> 2 & 0x3F)];
                bytes[(j++)] = encodingTable[((a1 << 4 | a2 >>> 4) & 0x3F)];
                bytes[(j++)] = encodingTable[((a2 << 2 | a3 >>> 6) & 0x3F)];
                bytes[(j++)] = encodingTable[(a3 & 0x3F)];
            }

            switch (mod) {
                case 0:
                    break;
                case 1:
                    int d1 = data[(data.length - 1)] & 0xFF;
                    int b1 = d1 >>> 2 & 0x3F;
                    int b2 = d1 << 4 & 0x3F;
                    bytes[(bytes.length - 2)] = encodingTable[b1];
                    bytes[(bytes.length - 1)] = encodingTable[b2];
                    break;
                case 2:
                    int d12 = data[(data.length - 2)] & 0xFF;
                    int d22 = data[(data.length - 1)] & 0xFF;
                    int b12 = d12 >>> 2 & 0x3F;
                    int b22 = (d12 << 4 | d22 >>> 4) & 0x3F;
                    int b32 = d22 << 2 & 0x3F;
                    bytes[(bytes.length - 3)] = encodingTable[b12];
                    bytes[(bytes.length - 2)] = encodingTable[b22];
                    bytes[(bytes.length - 1)] = encodingTable[b32];
            }
            return bytes;
        }

        public static byte[] decode(byte[] data) {
            data = discardNonBase64Bytes(data);

            int mod = data.length % 4;
            byte[] bytes = new byte[data.length * 3 / 4];

            int i = 0;
            for (int j = 0; i < data.length - mod; ) {
                byte b1 = decodingTable[data[(i++)]];
                byte b2 = decodingTable[data[(i++)]];
                byte b3 = decodingTable[data[(i++)]];
                byte b4 = decodingTable[data[(i++)]];
                bytes[(j++)] = ((byte)(b1 << 2 | b2 >> 4));
                bytes[(j++)] = ((byte)(b2 << 4 | b3 >> 2));
                bytes[(j++)] = ((byte)(b3 << 6 | b4));
            }

            if (2 == mod) {
                byte b1 = decodingTable[data[(data.length - 2)]];
                byte b2 = decodingTable[data[(data.length - 1)]];
                bytes[(bytes.length - 1)] = ((byte)(b1 << 2 | b2 >> 4));
            } else if (3 == mod) {
                byte b1 = decodingTable[data[(data.length - 3)]];
                byte b2 = decodingTable[data[(data.length - 2)]];
                byte b3 = decodingTable[data[(data.length - 1)]];
                bytes[(bytes.length - 2)] = ((byte)(b1 << 2 | b2 >> 4));
                bytes[(bytes.length - 1)] = ((byte)(b2 << 4 | b3 >> 2));
            }
            return bytes;
        }

        public static byte[] decode(String data) {
            data = discardNonBase64Chars(data);

            int mod = data.length() % 4;
            byte[] bytes = new byte[data.length() * 3 / 4];

            int i = 0;
            for (int j = 0; i < data.length() - mod; ) {
                byte b1 = decodingTable[data.charAt(i++)];
                byte b2 = decodingTable[data.charAt(i++)];
                byte b3 = decodingTable[data.charAt(i++)];
                byte b4 = decodingTable[data.charAt(i++)];
                bytes[(j++)] = ((byte)(b1 << 2 | b2 >> 4));
                bytes[(j++)] = ((byte)(b2 << 4 | b3 >> 2));
                bytes[(j++)] = ((byte)(b3 << 6 | b4));
            }
            switch (mod) {
                case 2:
                    byte b1 = decodingTable[data.charAt(data.length() - 2)];
                    byte b2 = decodingTable[data.charAt(data.length() - 1)];
                    bytes[(bytes.length - 1)] = ((byte)(b1 << 2 | b2 >> 4));
                    break;
                case 3:
                    byte b12 = decodingTable[data.charAt(data.length() - 3)];
                    byte b22 = decodingTable[data.charAt(data.length() - 2)];
                    byte b32 = decodingTable[data.charAt(data.length() - 1)];
                    bytes[(bytes.length - 2)] = ((byte)(b12 << 2 | b22 >> 4));
                    bytes[(bytes.length - 1)] = ((byte)(b22 << 4 | b32 >> 2));
            }
            return bytes;
        }
        private static byte[] discardNonBase64Bytes(byte[] data) {
            byte[] temp = new byte[data.length];
            int bytesCopied = 0;
            for (int i = 0; i < data.length; i++) {
                if (isValidBase64Byte(data[i])) {
                    temp[(bytesCopied++)] = data[i];
                }
            }
            byte[] newData = new byte[bytesCopied];
            System.arraycopy(temp, 0, newData, 0, bytesCopied);
            return newData;
        }

        private static String discardNonBase64Chars(String data) {
            StringBuffer sb = new StringBuffer();
            int length = data.length();
            for (int i = 0; i < length; i++) {
                if (isValidBase64Byte((byte)data.charAt(i))) {
                    sb.append(data.charAt(i));
                }
            }
            return sb.toString();
        }

        private static boolean isValidBase64Byte(byte b) {
            if (b == 45)
                return false;
            if ((b < 0) || (b >= 128))
                return false;
            if (decodingTable[b] == -1) {
                return false;
            }
            return true;
        }
    }

    private static class RSA {
        public static String encode(String src) {
            String encStr = "";
            try {
                byte[] pribyte = hexStrToBytes(priKey);
                PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pribyte);
                KeyFactory fac = KeyFactory.getInstance("RSA");
                RSAPrivateKey privateKey = (RSAPrivateKey)fac.generatePrivate(keySpec);
                Cipher cipher = Cipher.getInstance("RSA");
                cipher.init(1, privateKey);

                while (src.length() > 0) {
                    String toEnc = src.substring(0, src.length() > 117 ? 117 : src.length());
                    src = src.substring(toEnc.length());

                    byte[] bytes = cipher.doFinal(toEnc.getBytes());
                    encStr = encStr + new String(Base.encode(bytes));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return encStr;
        }

        public static String decode(String encStr) {
            String src = null;
            try {
                byte[] pubbyte = hexStrToBytes(pubKey);
                X509EncodedKeySpec keySpec = new X509EncodedKeySpec(pubbyte);
                KeyFactory fac = KeyFactory.getInstance("RSA");
                RSAPublicKey pubKey = (RSAPublicKey)fac.generatePublic(keySpec);
                Cipher cipher = Cipher.getInstance("RSA");
                cipher.init(2, pubKey);

                while (encStr.length() > 0) {
                    String toDec = encStr.substring(0, 171);
                    encStr = encStr.substring(171);

                    byte[] bytes = cipher.doFinal(Base.decode(toDec));
                    src = src + new String(bytes);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return src;
        }

        public static final byte[] hexStrToBytes(String s) {
            byte[] bytes = new byte[s.length() / 2];
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = ((byte)Integer.parseInt(s.substring(2 * i, 2 * i + 2),16));
            }
            return bytes;
        }
    }
}

