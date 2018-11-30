package lab1.practice;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface InjectRandom {
    Class<?> type();
    double doubleRangeTo() default 1.95D;
    int numberOfCharacters() default 10;
    int intRangeTo() default 10;
}
