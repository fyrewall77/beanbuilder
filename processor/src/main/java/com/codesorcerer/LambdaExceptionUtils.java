package com.codesorcerer;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by dphillips on 5/25/17.
 */
public class LambdaExceptionUtils {

    @FunctionalInterface
    public interface Consumer_WithExceptions<T> {
        void accept(T t) throws Exception;
    }

    @FunctionalInterface
    public interface BiConsumer_WithExceptions<T, U> {
        void accept(T t, U u) throws Exception;
    }

    @FunctionalInterface
    public interface Function_WithExceptions<T, R> {
        R apply(T t) throws Exception;
    }

    @FunctionalInterface
    public interface Supplier_WithExceptions<T> {
        T get() throws Exception;
    }

    @FunctionalInterface
    public interface Runnable_WithExceptions {
        void run() throws Exception;
    }

    /**
     * .forEach(rethrowConsumer(name -> System.out.println(Class.forName(name)))); or .forEach(rethrowConsumer(ClassNameUtil::println));
     */
    public static <T> Consumer<T> rethrowConsumer(Consumer_WithExceptions<T> consumer) {
        return t -> {
            try {
                consumer.accept(t);
            } catch (Exception exception) {
                throw new RuntimeException("", exception);
            }
        };
    }

    public static <T, U> BiConsumer<T, U> rethrowBiConsumer(BiConsumer_WithExceptions<T, U> biConsumer) {
        return (t, u) -> {
            try {
                biConsumer.accept(t, u);
            } catch (Exception exception) {
                throw new RuntimeException("", exception);
            }
        };
    }

    /**
     * .map(rethrowFunction(name -> Class.forName(name))) or .map(rethrowFunction(Class::forName))
     */
    public static <T, R> Function<T, R> rethrowFunction(Function_WithExceptions<T, R> function) {
        return t -> {
            try {
                return function.apply(t);
            } catch (Exception exception) {
                throw new RuntimeException("", exception);
            }
        };
    }

    /**
     * rethrowSupplier(() -> new StringJoiner(new String(new byte[]{77, 97, 114, 107}, "UTF-8"))),
     */
    public static <T> Supplier<T> rethrowSupplier(Supplier_WithExceptions<T> function) {
        return () -> {
            try {
                return function.get();
            } catch (Exception exception) {
                throw new RuntimeException("", exception);
            }
        };
    }

    /**
     * uncheck(() -> Class.forName("xxx"));
     */
    public static void uncheck(Runnable_WithExceptions t) {
        try {
            t.run();
        } catch (Exception exception) {
            throw new RuntimeException("", exception);
        }
    }

    /**
     * uncheck(() -> Class.forName("xxx"));
     */
    public static <R> R uncheck(Supplier_WithExceptions<R> supplier) {
        try {
            return supplier.get();
        } catch (Exception exception) {
            throw new RuntimeException("", exception);
        }
    }

    /**
     * uncheck(Class::forName, "xxx");
     */
    public static <T, R> R uncheck(Function_WithExceptions<T, R> function, T t) {
        try {
            return function.apply(t);
        } catch (Exception exception) {
            throw new RuntimeException("", exception);
        }
    }

}
