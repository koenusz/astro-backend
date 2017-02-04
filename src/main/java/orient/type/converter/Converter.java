package orient.type.converter;

import com.orientechnologies.orient.core.record.impl.ODocument;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

public interface Converter<T> {


    ODocument convert(T t) throws IllegalAccessException, InstantiationException, IntrospectionException, InvocationTargetException, NoSuchMethodException;

    T convert(ODocument doc);

}
