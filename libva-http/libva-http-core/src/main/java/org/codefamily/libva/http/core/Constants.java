package org.codefamily.libva.http.core;

import java.nio.charset.Charset;

/**
 * 常量池
 *
 * @author zhuchunlai
 * @version 1.0.0
 * @since 2015-10-19
 */
public class Constants {

    public static final String EMPTY_STRING = "";

    public static final String USER_AGENT = "Libva-Agent";

    public static final Charset UTF_8 = Charset.forName("UTF-8");
    public static final Charset ASCII = Charset.forName("US-ASCII");
    public static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");

    public static final String ENTITY_HANDLER_JSON = "JSON-Entity-Handler";
    public static final String ENTITY_HANDLER_TEXT = "Text-Entity-Handler";

    public static final String CONTENT_TYPE = "Content-Type";
    public static final String SEMICOLON = ";";

    public static final String URI_SEP = "/";


}
