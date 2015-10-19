package org.codefamily.libva.http.user;

import org.codefamily.libva.http.ActionResponse;

/**
 * // TODO docs
 *
 * @author zhuchunlai
 * @version $Id: UserService.java, v1.0 2015/10/19 22:09 $
 */
public interface UserService {

    ActionResponse add(User user);

    User findByName(String userName);

}
