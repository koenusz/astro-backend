package netty;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class ResponseSerializer extends StdSerializer<Response> {

    public ResponseSerializer() {
        this(null);
    }

    protected ResponseSerializer(Class<Response> t) {
        super(t);
    }

    @Override
    public void serialize(Response value, JsonGenerator gen, SerializerProvider provider) throws IOException {

    }
}
