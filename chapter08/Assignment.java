package chapter08;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.lang.reflect.Field;

public class Assignment implements ActionListener {
    private Object target;
    private Field field;
    private Object value;
    public Assignment(Object target, Field field, Object value) {
        this.target = target;
        this.field = field;
        this.value = value;
    }
    public void assign() throws IllegalArgumentException, IllegalAccessException {
        field.set(target, value);
    }
    public void actionPerformed(ActionEvent e) {
        try {
            assign();
        }
        catch (IllegalArgumentException | IllegalAccessException ex) {
            System.err.println("Assign: " + ex.toString());
        }
    }
    public static Assignment parse(Object target, String text) throws IOException {
        String fieldName;
        Object fieldValue;
        StreamTokenizer t = new StreamTokenizer(new StringReader(text));
        int token = t.nextToken();
        if (token != StreamTokenizer.TT_WORD) {
            throw new IOException("There isn't any field name!");
        }
        fieldName = t.sval;
        token = t.nextToken();
        if (token !=  '=') {
            throw new IOException("The assign symbol is absent!");
        }
        token = t.nextToken();
        if (token == StreamTokenizer.TT_WORD) {
            if (t.sval.equals("true")) {
                fieldValue = Boolean.TRUE;
            }
            else if (t.sval.equals("false")) {
                fieldValue = Boolean.FALSE;
            }
            else {
                fieldValue = t.sval;
            }
        }
        else if (token == '"') {
            fieldValue = t.sval;
        }
        else if (token == StreamTokenizer.TT_NUMBER) {
            int i = (int) t.nval;
            if (i == t.nval) {
                fieldValue = i;
            }
            else {
                fieldValue = t.nval;
            }
        }
        else {
            throw new IOException("Unknown token "  + t.sval + " in assigning value " + fieldName + " =");
        }
        token = t.nextToken();
        if (token != ';') {
            t.pushBack();
        }
        Field f;
        try {
            f = target.getClass().getField(fieldName);
        }
        catch (NoSuchFieldException e) {
            throw new IOException("There isn't such field " + fieldName);
        }
        return new Assignment(target, f, fieldValue);
    }
}
