package org.codefamily.libva.http.user.internal;

import com.alibaba.fastjson.JSON;
import org.codefamily.libva.http.ActionResponse;
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
    public ActionResponse add(User user) {
        HttpRequest<ActionResponse> request = HttpRequest.builder(ActionResponse.class)
                .endpoint("www.family.org")
                .path("user")
                .contentType(ClientConstants.CONTENT_TYPE_JSON)
                .config(HttpConfig.DEFAULT)
                .entity(JSON.toJSONString(user))
                .method(HttpMethod.POST)
                .build();
        HttpResponse<ActionResponse> response = HttpExecutor.newInstance().execute(request);
        switch (response.getStatus()) {
            case 200:
                return ActionResponse.SUCCESS;
            default:
                return new ActionResponse(false, response.getStatusMessage());
        }
    }

    @Override
    public User findByName(String userName) {
        HttpRequest<User> request = HttpRequest.builder(User.class)
                .endpoint("www.family.org")
                .path("user")
                .contentType(ClientConstants.CONTENT_TYPE_JSON)
                .config(HttpConfig.DEFAULT)
                .addParameter("name", userName)
                .method(HttpMethod.GET)
                .build();

        HttpResponse<User> response = HttpExecutor.newInstance().execute(request);
        switch (response.getStatus()) {
            case 200:
                return response.readEntity(User.class);
            default:
                throw new RuntimeException(response.getStatusMessage());
        }
    }
}
