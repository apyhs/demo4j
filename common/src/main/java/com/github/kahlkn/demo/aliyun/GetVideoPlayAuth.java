package com.github.kahlkn.demo.aliyun;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import org.junit.Test;

public class GetVideoPlayAuth {
    private static String accessKeyId = "accessKeyId";
    private static String accessKeySecret = "accessKeySecret";

    @Test
    public void test1() {
        DefaultProfile profile = DefaultProfile.getProfile("cn-shanghai", accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        GetVideoPlayAuthResponse response = getVideoPlayAuth(client);
        //播放凭证
        String playAuth = response.getPlayAuth();
        System.out.println(playAuth);
        //视频Meta信息
        GetVideoPlayAuthResponse.VideoMeta videoMeta = response.getVideoMeta();
        System.out.println(JSON.toJSONString(videoMeta));
    }

    GetVideoPlayAuthResponse getVideoPlayAuth(DefaultAcsClient client) {
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        // 视频ID
        request.setVideoId("2a611fd8d21d4aac8cb2891510353b37");
        GetVideoPlayAuthResponse response = null;
        try {
            response = client.getAcsResponse(request);
        } catch (ServerException e) {
            throw new RuntimeException("GetVideoPlayAuthRequest Server failed");
        } catch (ClientException e) {
            e.printStackTrace();
            throw new RuntimeException("GetVideoPlayAuthRequest Client failed");
        }
        return response;
    }

}
