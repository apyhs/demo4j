package demo.common.poi;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.converter.core.IURIResolver;

public class BasicURIResolverDemo implements IURIResolver {
    private static final String WORD_MEDIA = "word/media/";

    private static final String SLASH = "/";

    private final String baseURL;

    public BasicURIResolverDemo( String baseURL )
    {
        if ( !baseURL.endsWith( SLASH ) )
        {
            baseURL += SLASH;
        }
        this.baseURL = baseURL;
    }

    public String resolve( String uri )
    {
        // 是因为在 XHTMLMapper 中会默认追加 WORD_MEDIA = "word/media/"
        System.out.println(uri);
        if (uri.startsWith(WORD_MEDIA)) {
            uri = StringUtils.replace(uri, WORD_MEDIA, "");
        }

        if ( uri.startsWith( SLASH ) )
        {
            return baseURL + uri.substring( 1, uri.length() );
        }
        return baseURL + uri;
    }

}
