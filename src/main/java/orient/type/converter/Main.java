package orient.type.converter;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

public class Main {
    public void testConversion() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
        Request req = new Request();
        req.add("name", "Shamik").add("age", "33").add("salary", "10000").add("gender", "M");
        OldCOnverter converter = new OldCOnverter();
        Employee emp = converter.convert(Employee.class, req);
        System.out.println("After Conversion " + emp);
    }

    public static void main(String[] args) {
        Main main = new Main();
        try {
            main.testConversion();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IntrospectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}