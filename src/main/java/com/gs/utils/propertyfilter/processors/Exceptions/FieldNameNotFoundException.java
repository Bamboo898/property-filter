package com.gs.utils.propertyfilter.processors.Exceptions;

import java.beans.Expression;

public class FieldNameNotFoundException extends ExpressionException {

    public FieldNameNotFoundException() {
    }

    public FieldNameNotFoundException(String arg0) {
        super(arg0);
    }

    public FieldNameNotFoundException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public FieldNameNotFoundException(Throwable arg0) {
        super(arg0);
    }
}
