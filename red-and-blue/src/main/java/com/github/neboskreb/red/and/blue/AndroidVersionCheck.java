package com.github.neboskreb.red.and.blue;

import java.lang.reflect.Field;
import java.util.Optional;

public class AndroidVersionCheck {
    private static final int ERROR = -1;

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private static Optional<Integer> result;

    public static void assertSDK(int requiredSDK) throws AssertionError {
        Optional<Integer> available = getAndroidSDK();

        if (available.isEmpty()) {
            // Not an Android environment. We're good.
            return;
        }

        int sdk = available.get();
        if (sdk == ERROR) {
            throw new AssertionError("Unable to detect available Android SDK");
        } else if (sdk < requiredSDK) {
            throw new AssertionError("Android SDK required: " + requiredSDK + ", available: " + sdk);
        }
    }

    @SuppressWarnings("OptionalAssignedToNull")
    synchronized private static Optional<Integer> getAndroidSDK() {
        if (result == null) {
            result = detectAvailableSDK();
        }

        return result;
    }

    private static Optional<Integer> detectAvailableSDK() {
        try {
            Class<?> version = Class.forName("android.os.Build$VERSION");
            return Optional.of(extractIntSdk(version));

        } catch (ClassNotFoundException e) {
            // Not an Android environment
            return Optional.empty();
        }
    }

    private static int extractIntSdk(Class<?> version) {
        try {
            Field field = version.getField("SDK_INT");
            Object value = field.get(null);
            try {
                return (int) value;

            } catch (NullPointerException | ClassCastException e) {
                return ERROR;
            }

        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            return ERROR;
        }
    }
}
