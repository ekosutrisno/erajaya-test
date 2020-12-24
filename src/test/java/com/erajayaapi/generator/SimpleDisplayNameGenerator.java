package com.erajayaapi.generator;

import org.junit.jupiter.api.DisplayNameGenerator;

import java.lang.reflect.Method;

/**
 * @author Eko Sutrisno
 * @create 13/11/2020 16:48
 */
public class SimpleDisplayNameGenerator implements DisplayNameGenerator {
    @Override
    public String generateDisplayNameForClass(Class<?> tesClass) {
        return "Test " + tesClass.getSimpleName();
    }

    @Override
    public String generateDisplayNameForMethod(Class<?> tesClass, Method method) {
        return "Test " + tesClass.getSimpleName() + "." + method.getName();
    }

    @Override
    public String generateDisplayNameForNestedClass(Class<?> tesClass) {
        return null;
    }
}
