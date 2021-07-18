package com.zhang.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * Author: Distance
 * Date: 2021/05/15/15:56
 */
public class OssUtil {

    private static String accessKeyId = "LTAI4G9mTsbTkpMhcjHbSeaF";

    private static String accessKeySecret = "WGA0yVmPO36uqLxTICbg1CcvJVwmL9";

    private static String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";




    public static OSS getOssClient(){
        return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }
}
