package org.codefamily.libva.http.user.internal;

import com.alibaba.fastjson.JSON;
import org.codefamily.libva.http.HttpExecutor;
import org.codefamily.libva.http.core.*;
import org.codefamily.libva.http.user.User;
import org.codefamily.libva.http.user.UserService;

/**
 * // TODO docs
 *
 * @author zhuchunlai
 * @version $Id: UserServiceImpl.java, v1.0 2015/10/19 22:11 $
 */
public final class UserServiceImpl implements UserService {

    @Override
    public User add(User user) {
        HttpRequest<User> request = HttpRequest.builder(User.class)
                .endpoint("http://localhost:8080")
                .path("/user")
                .contentType(ContentType.APPLICATION_JSON)
                .config(HttpConfig.DEFAULT)
                .entity(JSON.toJSONString(user))
                .method(HttpMethod.POST)
                .build();

        HttpResponse<User> response = HttpExecutor.create().execute(request);
        switch (response.getStatus()) {
            case 200:
                return response.readEntity();
            default:
                throw new RuntimeException(String.format("http status error, code %d, error %s",
                        response.getStatus(), response.getStatusMessage()));
        }
    }

    @Override
    public User findById(long id) {
        HttpRequest<User> request = HttpRequest.builder(User.class)
                .endpoint("www.family.org")
                .path(String.format("/user/%s", String.valueOf(id)))
                .config(HttpConfig.DEFAULT)
                .method(HttpMethod.GET)
                .build();

        HttpResponse<User> response = HttpExecutor.create().execute(request);
        switch (response.getStatus()) {
            case 200:
                return response.readEntity();
            default:
                throw new RuntimeException(String.format("http status error, code %d, error %s",
                        response.getStatus(), response.getStatusMessage()));
        }
    }

    @Override
    public User findByName(String name) {
        HttpRequest<User> request = HttpRequest.builder(User.class)
                .config(HttpConfig.DEFAULT)
                .addParameter("name", name)
                .build();

        HttpResponse<User> response = HttpExecutor.create().execute(request);
        switch (response.getStatus()) {
            case 200:
                return response.readEntity();
            default:
                throw new RuntimeException(String.format("http status error, code %d, error %s",
                        response.getStatus(), response.getStatusMessage()));
        }
    }

}
