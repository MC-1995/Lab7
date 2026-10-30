import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class Invoker {
    public static void main(String[] args) throws Exception {
        ExampleClass obj = new ExampleClass();

        for (Method method : ExampleClass.class.getDeclaredMethods()) {
            if (!method.isAnnotationPresent(Repeat.class)) {
                continue;
            }

            int modifiers = method.getModifiers();

            // Вызываем только защищённые и приватные аннотированные методы
            if (!Modifier.isProtected(modifiers) && !Modifier.isPrivate(modifiers)) {
                continue;
            }

            Repeat repeat = method.getAnnotation(Repeat.class);
            method.setAccessible(true);

            Object[] callArgs = buildArguments(method.getParameterTypes());

            for (int i = 0; i < repeat.times(); i++) {
                System.out.printf("Вызов %s #%d%n", method.getName(), i + 1);

                try {
                    Object result = method.invoke(obj, callArgs);

                    if (method.getReturnType() != void.class) {
                        System.out.println("Результат: " + result);
                    }
                } catch (InvocationTargetException e) {
                    System.out.println("Ошибка в методе: " + e.getCause());
                }
            }
        }
    }

    private static Object[] buildArguments(Class<?>[] parameterTypes) {
        Object[] args = new Object[parameterTypes.length];

        for (int i = 0; i < parameterTypes.length; i++) {
            Class<?> type = parameterTypes[i];

            if (type == String.class) {
                args[i] = "Java";
            } else if (type == int.class || type == Integer.class) {
                args[i] = 42;
            } else if (type == long.class || type == Long.class) {
                args[i] = 100L;
            } else if (type == double.class || type == Double.class) {
                args[i] = 3.14;
            } else if (type == boolean.class || type == Boolean.class) {
                args[i] = true;
            } else if (type == char.class || type == Character.class) {
                args[i] = 'A';
            } else {
                args[i] = null;
            }
        }

        return args;
    }
}