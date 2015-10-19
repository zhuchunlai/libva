package org.codefamily.libva.http;

/**
 * // TODO docs
 *
 * @author zhuchunlai
 * @version $Id: ActionResponse.java, v1.0 2015/10/19 22:09 $
 */
public class ActionResponse {

    public static final ActionResponse SUCCESS = new ActionResponse(true, null);

    protected final boolean success;

    protected final String error;

    public ActionResponse(boolean success, String error) {
        this.success = success;
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getError() {
        return error;
    }

}
