package chapter02;

public class PostAddress {
    private static int counter = 0;
    private final int codeZIP = ++counter;
    private String perName;
    private String perAddress;
    private String city;
    private String state;
    PostAddress(String perName, String perAddress, String city, String state) {
        this.perName = perName;
        this.perAddress = perAddress;
        this.city = city;
        this.state = state;
    }
    public void setName(String perName) {
        this.perName = perName;
    }
    public void setAddress(String perAddress) {
        this.perAddress = perAddress;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getName() {
        return perName;
    }
    public String getAddress() {
        return perAddress;
    }
    public String getCity() {
        return city;
    }
    public String getState() {
        return state;
    }
    public int getCode() {
        return codeZIP;
    }
    public String toString() {
        StringBuffer expression = new StringBuffer("\nZIP-code is:\t");
        expression.append(codeZIP);
        if (perName.isEmpty()) expression.append("\nA person named:\tUnknown");
        else {
            expression.append("\nA person named:\t");
            expression.append(perName);
        }
        expression.append("\nWho lives in");
        if (perAddress.isEmpty()) expression.append("\nan address:\tUnknown");
        else {
            expression.append("\nan address:\t");
            expression.append(perAddress);
        }
        if (city.isEmpty()) expression.append("\nin the city:\tUnknown");
        else {
            expression.append("\nin the city:\t");
            expression.append(city);
        }
        if (state.isEmpty()) expression.append("\nin the state\tUnknown");
        else {
            expression.append("\nin the state:\t");
            expression.append(state);
        }
        return  new String(expression);
    }
    public static void main(String[] args) {
        PostAddress per1 = new PostAddress("John Walkley", "Haven Street, 57/16", "Chicago", "Illinois"),
                per2 = new PostAddress("", "", "", "");
        System.out.println(per1);
        System.out.println(per2);
        per2.setName("Sandra Brown");
        per2.setAddress("Smooth avenue");
        per2.setCity("Dallas");
        per2.setState("Texas");
        System.out.println(per2);
    }
}