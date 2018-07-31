package com.myluffy.controller;

import com.myluffy.dao.ShortRepository;
import com.myluffy.entity.ShortUrl;
import com.myluffy.util.CMyEncrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ShortController {

    @Autowired
    private ShortRepository repository;

    private static final String address = "http://localhost:8080/";

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String view() {
        return "generate_short_url";
    }

    @RequestMapping("/{key}")
    public String find(@PathVariable String key) {
        if (StringUtils.isEmpty(key)) {
            return "error";
        }
        ShortUrl byShortKey = repository.getByShortKey(key);
        if (byShortKey == null) {
            return "error";
        }

        return "redirect:" + byShortKey.getOriginalUrl();
    }

    @RequestMapping("/generate")
    @ResponseBody
    public String generateShortUrl(HttpServletRequest request) {
        String originalUrl = request.getParameter("originalUrl");
        if (StringUtils.isEmpty(originalUrl)) {
            return "";
        }
        ShortUrl shortUrl= repository.getByOriginalUrl(originalUrl);
        if (shortUrl == null) {
            String shortKey = shortUrl(originalUrl);
            shortUrl = new ShortUrl();
            shortUrl.setShortKey(shortKey);
            shortUrl.setOriginalUrl(originalUrl);
            ShortUrl save = repository.save(shortUrl);
            if (StringUtils.isEmpty(save.getId())) {
                return "";
            }
            // 返回生成的短域名链接
            return address + shortKey;
        } else {
            return address + shortUrl.getShortKey();
        }

    }

    public static String shortUrl(String url) {
        // 可以自定义生成 MD5 加密字符传前的混合 KEY
        String key = "mengdelong";
        // 要使用生成 URL 的字符
        String[] chars = new String[]{"a", "b", "c", "d", "e", "f", "g", "h",
                "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
                "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
                "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
                "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
                "U", "V", "W", "X", "Y", "Z"

        };
        // 对传入网址进行 MD5 加密
        String sMD5EncryptResult = (new CMyEncrypt()).md5(key + url);
        String hex = sMD5EncryptResult;
        // 把加密字符按照 8 位一组 16 进制与 0x3FFFFFFF 进行位与运算
        String sTempSubString = hex.substring(1 * 8, 1 * 8 + 8);
        // 这里需要使用 long 型来转换，因为 Inteper .parseInt() 只能处理 31 位 , 首位为符号位 , 如果不用 long ，则会越界
        long lHexLong = 0x3FFFFFFF & Long.parseLong(sTempSubString, 16);
        String outChars = "";
        for (int j = 0; j < 6; j++) {
            // 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引
            long index = 0x0000003D & lHexLong;
            // 把取得的字符相加
            outChars += chars[(int) index];
            // 每次循环按位右移 5 位
            lHexLong = lHexLong >> 5;
        }
        return outChars;
    }

}
