package s4.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by carlos on 12/18/17.
 */

public class ResponseResult {
    private Map<String, Object> results = new HashMap<>();

    public Map<String, Object> getResults() {
        return results;
    }

    public void setResults(Map<String, Object> results) {
        this.results = results;
    }

    public ResponseResult returnValue(String fieldName, Object fieldValue) {
        results.put(fieldName, fieldValue);

        return this;
    }

}
