package ua.kulku.onlooker.model;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static ua.kulku.onlooker.Jackson.sObjectMapper;
import static ua.kulku.onlooker.MyApplication.getSharedPreferences;

/**
 * Created by aindrias on 18.03.2015.
 */
public final class Data {
    private static final String SHARED_PREF_KEY = "questions in storage";
    private static ArrayList<Question> sQuestions;

    public static List<Question> getAllQuestions() {
        restoreIfNeeded();
        return Collections.unmodifiableList(sQuestions);
    }

    public static void add(Question question) {
        restoreIfNeeded();
        sQuestions.add(question);
        Data.save();
    }

    public static void remove(Question question) {
        restoreIfNeeded();
        sQuestions.remove(question);
        Data.save();
    }

    private static void restoreIfNeeded() {
        if (sQuestions == null) {
            try {
                String questionsJson = getSharedPreferences().getString(SHARED_PREF_KEY, "[]");
//                String questionsJson = getSharedPreferences().getString(SHARED_PREF_KEY, "[{\"id\":\"281262ae-3134-428f-812b-1276a1ea6306\",\"name\":\"мова паперових книжок\",\"possibleAnswers\":[{\"inputs\":[{\"createDate\":1428423485542,\"gender\":\"FEMALE\",\"age\":55},{\"createDate\":1429172595909,\"gender\":\"FEMALE\",\"age\":40},{\"createDate\":1429352701754,\"gender\":\"FEMALE\",\"age\":22},{\"createDate\":1429637626939,\"gender\":\"FEMALE\",\"age\":24},{\"createDate\":1429814322753,\"gender\":\"FEMALE\",\"age\":20},{\"createDate\":1430071876781,\"gender\":\"FEMALE\",\"age\":20}],\"name\":\"укр\"},{\"inputs\":[{\"createDate\":null,\"gender\":\"FEMALE\",\"age\":20},{\"createDate\":null,\"gender\":\"FEMALE\",\"age\":53},{\"createDate\":null,\"gender\":\"FEMALE\",\"age\":20},{\"createDate\":null,\"gender\":\"FEMALE\",\"age\":30},{\"createDate\":null,\"gender\":\"MALE\",\"age\":32},{\"createDate\":1428423471101,\"gender\":\"MALE\",\"age\":60},{\"createDate\":1428596386985,\"gender\":\"FEMALE\",\"age\":29},{\"createDate\":1428745348849,\"gender\":\"FEMALE\",\"age\":40},{\"createDate\":1429002973058,\"gender\":\"FEMALE\",\"age\":29},{\"createDate\":1429205375200,\"gender\":\"MALE\",\"age\":48},{\"createDate\":1429354172610,\"gender\":\"FEMALE\",\"age\":21},{\"createDate\":1429435271461,\"gender\":\"FEMALE\",\"age\":40},{\"createDate\":1429551121587,\"gender\":\"MALE\",\"age\":50},{\"createDate\":1429608168712,\"gender\":\"MALE\",\"age\":22},{\"createDate\":1429869030907,\"gender\":\"FEMALE\",\"age\":25},{\"createDate\":1429892428953,\"gender\":\"FEMALE\",\"age\":24},{\"createDate\":1429957260584,\"gender\":\"FEMALE\",\"age\":80},{\"createDate\":1430072320400,\"gender\":\"FEMALE\",\"age\":45},{\"createDate\":1430156406661,\"gender\":\"MALE\",\"age\":40},{\"createDate\":1430236344228,\"gender\":\"FEMALE\",\"age\":27},{\"createDate\":1430295330319,\"gender\":\"MALE\",\"age\":50}],\"name\":\"рос в укр\"},{\"inputs\":[{\"createDate\":null,\"gender\":\"FEMALE\",\"age\":29}],\"name\":\"deutsch\"},{\"inputs\":[{\"createDate\":null,\"gender\":\"FEMALE\",\"age\":40},{\"createDate\":1428596817909,\"gender\":\"MALE\",\"age\":21}],\"name\":\"english\"},{\"inputs\":[{\"createDate\":1428745329035,\"gender\":\"MALE\",\"age\":71}],\"name\":\"церковнослов'янська\"}],\"answerStats\":\"укр        19%\\nрос в укр        67%\\ndeutsch        3%\\nenglish        6%\\nцерковнослов'янська        3%\\n\",\"inputsCount\":31},{\"id\":\"c670aafd-fc63-4951-ad60-7dd46b367b05\",\"name\":\"навколо мова київ\",\"possibleAnswers\":[{\"inputs\":[],\"name\":\"українська\"},{\"inputs\":[{\"createDate\":1430386089271,\"gender\":\"MALE\",\"age\":48},{\"createDate\":1430386277556,\"gender\":\"FEMALE\",\"age\":42}],\"name\":\"ватна\"}],\"answerStats\":\"українська        0%\\nватна        100%\\n\",\"inputsCount\":2}]");
                sQuestions = sObjectMapper.readValue(questionsJson,
                        sObjectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Question.class));
            } catch (IOException e) {
                e.printStackTrace();
                sQuestions = new ArrayList<>();
            }
        }
    }

    static void save() {
        try {
            String questionsJson = sObjectMapper.writeValueAsString(sQuestions);
            getSharedPreferences().edit()
                    .putString(SHARED_PREF_KEY, questionsJson)
                    .commit();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public static Question getQuestionById(UUID id) {
        for (Question question : getAllQuestions()) {
            if (question.getId().equals(id))
                return question;
        }
        return null;
    }
}
