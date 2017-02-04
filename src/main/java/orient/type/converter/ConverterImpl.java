package orient.type.converter;

import java.beans.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;




public class Converter {

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
}