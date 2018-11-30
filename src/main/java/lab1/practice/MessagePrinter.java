package lab1.practice;


import java.text.DecimalFormat;

public class MessagePrinter implements Printer {

    @InjectRandom(type = String.class, numberOfCharacters = 3)
    private String message;

    @InjectRandom(type = double.class, doubleRangeTo = 0.05D)
    private Double aDouble;

    // создать аннотацию и аннотировать поле так, что бы при поднятии контекста
    // в поле инжектилось случайное число от 0 до числа заданого при помощи аннотации
    @InjectRandom(type = int.class, intRangeTo = 5)
    private int count;

    public void print() {
        System.out.println("Injected random count value: " + count + ".");
        for (int i = 0; i < count; i++) {
            DecimalFormat df = new DecimalFormat("#.##");
            System.out.println("Random string: " + message + ", Random double: " + df.format(aDouble) + ".");
        }
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
