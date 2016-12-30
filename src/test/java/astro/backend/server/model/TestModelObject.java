package astro.backend.server.model;

import com.orientechnologies.orient.core.id.ORID;
import com.sun.istack.internal.Nullable;
import org.immutables.value.Value;

@Value.Immutable
public interface TestModelObject {

        @Nullable
        ORID getId();

        boolean isBool();

        String getString();

        int getInt();

        double getDouble();

        float getFloat();

        long getLong();

        byte getByte();

        short getShort();

        char getChar();

}
