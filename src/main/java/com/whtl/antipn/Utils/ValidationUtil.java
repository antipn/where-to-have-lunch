package com.whtl.antipn.Utils;

import com.whtl.antipn.exception.NotFoundException;

public class ValidationUtil {

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id = " + id);
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }
}
