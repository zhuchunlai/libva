package org.codefamily.libva.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import org.codefamily.libva.http.core.HttpMethod;
import org.codefamily.libva.http.core.HttpRequest;
import org.codefamily.libva.http.core.HttpResponse;

/**
 * // TODO doc
 *
 * @author zhuchunlai
 * @version 1.0.0
 * @since 2015-12-18
 */
public class HttpExecutorTest {

    public static void main(String[] args) {
        BuildImageRequest model = new BuildImageRequest();
        model.from = "jd_new_base_img_6";
        model.entrypoint = "/etc/init.d/crond restart && /etc/init.d/sshd restart && sleep 9999999d";
        model.name = "wxg";
        model.run = new String[]{"mkdir -p /export/ && touch /export/22222222 && echo 213 > /export/22222222"};
        model.tag = "v1";
        model.passwd = new String[]{"root:1qaz2wsx"};

        HttpRequest<BuildImageResponse> request = HttpRequest.builder(BuildImageResponse.class)
                .endpoint("http://192.168.212.31:16380")
                .path("/v1/wangxinggang/build")
                .method(HttpMethod.POST)
                .entity(JSON.toJSONString(model))
                .build();

        HttpResponse<BuildImageResponse> response = HttpExecutor.create().execute(request);
        BuildImageResponse imageResponse = response.readEntity();
        System.out.println("1");
    }

    private static class BuildImageRequest {
        private static final String[] EMPTY_ARRAY = new String[0];

        private String from;
        private String entrypoint;
        private String workdir = "";
        private String add = "";
        private String name;
        private String env = "";
        private String[] run;
        private String[] volume = EMPTY_ARRAY;
        private String[] expose = EMPTY_ARRAY;
        private String tag;
        private String[] passwd;

        public String getFrom() {
            return from;
        }

        public String getEntrypoint() {
            return entrypoint;
        }

        public String getWorkdir() {
            return workdir;
        }

        public String getAdd() {
            return add;
        }

        public String getName() {
            return name;
        }

        public String getEnv() {
            return env;
        }

        public String[] getRun() {
            return run;
        }

        public String[] getVolume() {
            return volume;
        }

        public String[] getExpose() {
            return expose;
        }

        public String getTag() {
            return tag;
        }

        public String[] getPasswd() {
            return passwd;
        }
    }

    @SuppressWarnings("unused")
    private static class BuildImageResponse {
        private static final int SUCCESS = 200;

        // 状态码
        private int code;
        // 错误描述
        @JSONField(name = "msg")
        private String message;
        @JSONField(name = "image_uuid")
        private String uuid;

        public void setCode(int code) {
            this.code = code;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }
    }

}
