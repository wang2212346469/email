/**
 * 表示一个电子邮件地址，并提供解析功能。
 * Represents an email address and provides parsing functionality.
 */
public class Email {

    private final String address;
    private final String username;
    private final String domain;

    /**
     * 通过完整的邮件地址字符串构造 Email 对象。
     *
     * @param address 完整的邮件地址，例如 "user@example.com"
     * @throws IllegalArgumentException 如果地址格式不合法
     */
    public Email(String address) {
        if (address == null || !address.contains("@")) {
            throw new IllegalArgumentException("无效的邮件地址: " + address);
        }
        int atIndex = address.lastIndexOf('@');
        String user = address.substring(0, atIndex);
        String dom = address.substring(atIndex + 1);
        if (user.isEmpty() || dom.isEmpty() || !dom.contains(".")) {
            throw new IllegalArgumentException("无效的邮件地址: " + address);
        }
        this.address = address;
        this.username = user;
        this.domain = dom;
    }

    /** 返回完整的邮件地址。 */
    public String getAddress() {
        return address;
    }

    /** 返回邮件地址中 @ 符号前的用户名部分。 */
    public String getUsername() {
        return username;
    }

    /** 返回邮件地址中 @ 符号后的域名部分。 */
    public String getDomain() {
        return domain;
    }

    @Override
    public String toString() {
        return "Email{address='" + address + "', username='" + username + "', domain='" + domain + "'}";
    }
}
