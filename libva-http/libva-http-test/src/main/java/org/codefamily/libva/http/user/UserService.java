package org.codefamily.libva.http.user;

/**
 * // TODO docs
 *
 * @author zhuchunlai
 * @version $Id: UserService.java, v1.0 2015/10/19 22:09 $
 */
public interface UserService {

    User add(User user);

    User findById(long id);

    User findByName(String name);

}
