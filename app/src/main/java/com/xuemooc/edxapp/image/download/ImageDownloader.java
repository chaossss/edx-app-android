package com.xuemooc.edxapp.image.download;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

/**
 * Created by chaossss on 2015/9/24.
 */
public interface ImageDownloader {
    InputStream getStream(String imageUri) throws IOException;

    enum Scheme{
        HTTP("http"), FILE("file"), CONTENT("content"), ASSETS("assets"), DRAWABLE("drawable"), UNKNOWN("");

        private String scheme;
        private String uriPrefix;

        Scheme(String scheme){
            this.scheme = scheme;
            uriPrefix = scheme + "://";
        }

        public static Scheme ofUri(String uri){
            if(uri != null){
                for(Scheme s : values()){
                    if(s.belongsTo(uri)){
                        return s;
                    }
                }
            }

            return UNKNOWN;
        }

        private boolean belongsTo(String uri){
            return uri.toLowerCase(Locale.US).startsWith(uriPrefix);
        }

        public String wrap(String path){
            return uriPrefix + path;
        }

        public String crop(String uri){
            if(!belongsTo(uri)){
                throw new IllegalArgumentException(String.format("URI doesn't have expected scheme", uri, scheme));
            }

            return uri.substring(uriPrefix.length());
        }
    }
}
