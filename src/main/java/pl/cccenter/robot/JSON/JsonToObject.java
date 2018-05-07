package pl.cccenter.robot.JSON;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by Squier on 2016-10-15.
 */
public class JsonToObject<T> {

    private ObjectMapper mapper;

    public JsonToObject(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public T toObject(String JSON, Class<T> objectClass) throws IOException {
        return mapper.readValue(JSON, objectClass);
    }

}
