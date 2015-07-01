package ua.kulku.onlooker;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;

/**
 * Created by andrii.lavrinenko on 15.03.2015.
 */
public class Jackson {
    public static ObjectMapper sObjectMapper = new ObjectMapper()
                       .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    static {
        sObjectMapper.setVisibilityChecker(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
    }
}
