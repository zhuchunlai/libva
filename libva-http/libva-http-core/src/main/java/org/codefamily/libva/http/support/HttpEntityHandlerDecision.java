package org.codefamily.libva.http.support;

/**
 * 响应实体解析决策者
 *
 * @author zhuchunlai
 * @version 1.0.0
 * @since 2015-12-18
 */
public interface HttpEntityHandlerDecision {

    String decide(EntityHandlerDecisionContext context);

    public static class EntityHandlerDecisionContext {

    }

}
