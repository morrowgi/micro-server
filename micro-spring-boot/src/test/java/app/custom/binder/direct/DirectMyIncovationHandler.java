package app.custom.binder.direct;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DirectMyIncovationHandler implements InvocationHandler {

	public static volatile boolean captured= false;
    @Override
    public Object invoke(Object obj, Method method, Object[] args)
            throws Throwable {
       System.out.println("Captured!");
       captured= true;
        Object result = method.invoke(obj, args);
        return result;
    }
}
