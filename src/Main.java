/**
 * 演示邮件地址解析与校验功能。
 * Demonstrates email address parsing and validation.
 */
public class Main {

    public static void main(String[] args) {
        EmailValidator validator = new EmailValidator();
        EmailChecker checker = new EmailChecker();

        String[] testAddresses = {
                "user@example.com",
                "invalid-address",
                "another@domain.org",
                "bad@",
                "2212346469@qq.com"
        };

        System.out.println("====== 邮件地址解析与校验 ======");
        for (String addr : testAddresses) {
            System.out.println("\n地址: " + addr);
            if (validator.isValid(addr)) {
                Email email = new Email(addr);
                System.out.println("  解析结果: " + email);
                System.out.println("  用户名: " + email.getUsername());
                System.out.println("  域名: " + email.getDomain());
                boolean exists = checker.domainExists(addr);
                System.out.println("  域名 MX 记录存在: " + exists);
            } else {
                System.out.println("  格式校验: 不合法");
            }
        }
    }
}
