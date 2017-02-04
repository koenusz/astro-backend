package orient.type.converter;

import java.util.HashMap;
import java.util.Map;
public class Request {
    private Map<String,String> requestMap = new HashMap<String,String>();
    public Request add(String key,String value)
    {
        requestMap.put(key, value);
        return this;
    }
    public Request clear()
    {
        requestMap.clear();
        return this;
    }
    public String getParameter(String key)
    {
        return requestMap.get(key);
    }
    @Override
    public String toString() {
        return "Request [requestMap=" + requestMap + "]";
    }
}