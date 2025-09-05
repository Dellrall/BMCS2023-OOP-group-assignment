import hillclimmer.CustomerModule.Customer;

public class TestIC {
    public static void main(String[] args) {
        System.out.println("Testing isValidIC:");
        System.out.println("950101-14-5678: " + Customer.isValidIC("950101-14-5678"));
        System.out.println("invalid: " + Customer.isValidIC("invalid"));
        System.out.println("123456-78-9012: " + Customer.isValidIC("123456-78-9012"));
        System.out.println("950101-99-5678: " + Customer.isValidIC("950101-99-5678"));
    }
}
