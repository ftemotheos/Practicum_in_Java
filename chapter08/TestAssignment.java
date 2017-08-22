package chapter08;

import java.io.IOException;

public class TestAssignment {
    public boolean rawBoolean = false;
    public Boolean boxedBoolean = Boolean.FALSE;
    public int rawInteger = 0;
    public Integer boxedInteger = 0;
    public double rawDouble = 0.0;
    public Double boxedDouble = 0.0;
    public String testString = "";
    public String testStringByReference = null;
    public void printTestAssignment() {
        System.out.printf(" rawBoolean = %b%n boxedBoolean = %b%n rawInteger = %d%n boxedInteger = %d%n rawDouble = %f%n boxedDouble = %f%n testString = %s%n testStringByReference = %s%n%n",
                rawBoolean, boxedBoolean, rawInteger, boxedInteger, rawDouble, boxedDouble, testString, testStringByReference);
    }
    public static void main(String[] args) throws IOException {
        TestAssignment testObject = new TestAssignment();
        System.out.println("TestAssignment fields before reflection change:");
        testObject.printTestAssignment();
        Assignment.parse(testObject, "rawBoolean=true;").actionPerformed(null);
        Assignment.parse(testObject, "boxedBoolean=true;").actionPerformed(null);
        Assignment.parse(testObject, "rawInteger=4;").actionPerformed(null);
        Assignment.parse(testObject, "boxedInteger=4;").actionPerformed(null);
        Assignment.parse(testObject, "rawDouble=3.17;").actionPerformed(null);
        Assignment.parse(testObject, "boxedDouble=3.17;").actionPerformed(null);
        Assignment.parse(testObject, "testString=\"Hello world!\";").actionPerformed(null);
        Assignment.parse(testObject, "testStringByReference=testString;").actionPerformed(null);
        System.out.println("TestAssignment fields after reflection change:");
        testObject.printTestAssignment();
    }
}
