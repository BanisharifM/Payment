package ir.rastech.analytics.Base;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;

/**
 * Created by arsalan on 10/10/15.
 */
public class JacksonJsonCreator {

    public String getJson(Object object) throws IOException {

        ObjectMapper objMapper = new ObjectMapper();
        objMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        return objMapper.writeValueAsString(object);

    }
}
