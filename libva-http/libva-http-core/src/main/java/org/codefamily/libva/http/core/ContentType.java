package org.codefamily.libva.http.core;

import java.nio.charset.Charset;

/**
 * // TODO doc
 *
 * @author zhuchunlai
 * @version 1.0.0
 * @since 2015-12-18
 */
public enum ContentType {

    APPLICATION_JSON("application/json", Constants.UTF_8, Constants.ENTITY_HANDLER_JSON),

    TEXT_XML("text/xml", Constants.UTF_8, Constants.ENTITY_HANDLER_TEXT),

    APPLICATION_FORM_URLENCODED("application/x-www-form-urlencoded", Constants.UTF_8, Constants.ENTITY_HANDLER_JSON),

    MULTIPART_FORM_DATA("multipart/form-data", Constants.ISO_8859_1, Constants.ENTITY_HANDLER_TEXT);

    private final String mimeType;
    private final Charset charset;
    private final String defaultEntityHandlerName;

    private ContentType(final String mimeType, final Charset charset, final String defaultEntityHandlerName) {
        this.mimeType = mimeType;
        this.charset = charset;
        this.defaultEntityHandlerName = defaultEntityHandlerName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public Charset getCharset() {
        return charset;
    }

    public String getDefaultEntityHandlerName() {
        return defaultEntityHandlerName;
    }

    public static ContentType fromMimeType(String mimeType) {
        for (ContentType contentType : values()) {
            if (contentType.mimeType.equals(mimeType)) {
                return contentType;
            }
        }
        return null;
    }

}
