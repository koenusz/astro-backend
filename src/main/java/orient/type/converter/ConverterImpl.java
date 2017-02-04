package orient.type.converter;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.TypeResolver;
import com.orientechnologies.orient.core.record.impl.ODocument;
import org.apache.commons.beanutils.PropertyUtils;

import java.beans.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;


public class ConverterImpl<T> implements Converter<T> {

    public <T> T convert(Class<T> clazz, Request req) throws InstantiationException,
            IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, IntrospectionException {
        T instance = clazz.newInstance();
        BeanInfo info = Introspector.getBeanInfo(clazz);
        PropertyDescriptor[] props = info.getPropertyDescriptors();
        for (PropertyDescriptor prop : props) {
            System.out.println(prop.getName() + ":"
                    + prop.getPropertyType().getName());
            PropertyEditor editor = PropertyEditorManager.findEditor(prop
                    .getPropertyType());
            System.out.println(editor);
            if (editor == null) {
                continue;
            }
            String value = req.getParameter(prop.getName());
            editor.setAsText(value);
            Method setter = prop.getWriteMethod();
            setter.invoke(instance, new Object[]{editor.getValue()});
        }
        return instance;
    }

    @Override
    public ODocument convert(T t) throws IllegalAccessException, InstantiationException, IntrospectionException, InvocationTargetException, NoSuchMethodException {

        ODocument doc = new ODocument();

        Class<T> clazz = getType();

        PropertyDescriptor[] props = PropertyUtils.getPropertyDescriptors(clazz);
        for (PropertyDescriptor prop : props) {
            System.out.println(prop.getName() + ":"
                    + prop.getPropertyType().getName());

//            PropertyEditor editor = PropertyEditorManager.findEditor(prop
//                    .getPropertyType());
//            System.out.println(editor);
//            if (editor == null) {
//                continue;
//            }

            Object value = PropertyUtils.getProperty(t,prop.getName());
            doc.field(prop.getName(), value);
        }

        return doc;
    }

    @Override
    public T convert(ODocument doc) {
        return null;
    }

    @SuppressWarnings("unchecked")
    private Class<T> getType() {
        TypeResolver typeResolver = new TypeResolver();
        ResolvedType thisType = typeResolver.resolve(this.getClass());
        return (Class<T>) thisType.getErasedType();
    }
}