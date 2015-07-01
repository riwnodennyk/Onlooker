package ua.kulku.onlooker;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;

import java.io.IOException;
import java.util.ArrayList;

import ua.kulku.onlooker.model.Question;

/**
 * Created by andrii.lavrinenko on 15.03.2015.
 */
public class JacksonUtils {
    public static ObjectMapper sObjectMapper = new ObjectMapper()
                       .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    static {
        sObjectMapper.setVisibilityChecker(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
    }

    public static ArrayList<Question> asQuestions(String questionsJson) throws IOException {
        return sObjectMapper.readValue(questionsJson,
                            sObjectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Question.class));
    }

    public static String toString(ArrayList<Question> questions) throws JsonProcessingException {
        return sObjectMapper.writeValueAsString(questions);
    }
}
