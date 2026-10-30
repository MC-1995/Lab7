public class ExampleClass {

    // Публичные методы
    public void publicMethod1() {
        System.out.println("publicMethod1()");
    }

    public void publicMethod2(String text) {
        System.out.println("publicMethod2(" + text + ")");
    }

    public int publicMethod3(int value) {
        System.out.println("publicMethod3(" + value + ")");
        return value * 2;
    }

    // Защищённые методы
    @Repeat(times = 2)
    protected void protectedMethod1(String text) {
        System.out.println("protectedMethod1(" + text + ")");
    }

    @Repeat(times = 3)
    protected int protectedMethod2(int value) {
        System.out.println("protectedMethod2(" + value + ")");
        return value + 10;
    }

    protected void protectedMethod3() {
        System.out.println("protectedMethod3()");
    }

    // Приватные методы с параметрами
    @Repeat(times = 2)
    private void privateMethod1(String text, int value) {
        System.out.println("privateMethod1(" + text + ", " + value + ")");
    }

    @Repeat(times = 1)
    private int privateMethod2(int value) {
        System.out.println("privateMethod2(" + value + ")");
        return value * value;
    }

    private void privateMethod3(boolean flag) {
        System.out.println("privateMethod3(" + flag + ")");
    }
}