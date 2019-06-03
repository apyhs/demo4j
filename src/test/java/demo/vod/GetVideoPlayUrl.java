package demo.vod;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import org.junit.Test;

import java.util.List;

public class GetVideoPlayUrl {
    private static String accessKeyId = "accessKeyId";
    private static String accessKeySecret = "accessKeySecret";

    @Test
    public void test1() {
        DefaultProfile profile = DefaultProfile.getProfile("cn-shanghai", accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        GetPlayInfoResponse response = getPlayInfo(client);

        String requestId = response.getRequestId();
        System.out.println(requestId);

        GetPlayInfoResponse.VideoBase videoBase = response.getVideoBase();
        System.out.println(JSON.toJSONString(videoBase, true));

        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        System.out.println(JSON.toJSONString(playInfoList, true));
    }

    GetPlayInfoResponse getPlayInfo(DefaultAcsClient client) {
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        // 准备播放的视频ID
        request.setVideoId("02b8c91577544f318a7ba87d73f63bde");
        request.setFormats("mp4,m3u8");
        request.setAuthTimeout(1800L);
        GetPlayInfoResponse response = null;
        try {
            response = client.getAcsResponse(request);
        } catch (ServerException e) {
            throw new RuntimeException("GetPlayInfoRequest Server failed");
        } catch (ClientException e) {
            e.printStackTrace();
            throw new RuntimeException("GetPlayInfoRequest Client failed");
        }
        response.getVideoBase();              // 视频基本信息
        response.getPlayInfoList();           // 视频播放信息（流信息列表）
        return response;
    }
}
