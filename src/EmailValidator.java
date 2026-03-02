import java.util.regex.Pattern;

/**
 * 提供邮件地址格式验证功能。
 * Provides email address format validation.
 */
public class EmailValidator {

    /**
     * 符合 RFC 5322 的邮件地址正则表达式（简化版）。
     * 禁止用户名以点号开头/结尾或包含连续点号。
     */
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9_%+\\-]+(\\.[a-zA-Z0-9_%+\\-]+)*@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}$"
    );

    /**
     * 验证给定字符串是否是合法的邮件地址格式。
     *
     * @param address 待验证的邮件地址
     * @return 如果格式合法则返回 true，否则返回 false
     */
    public boolean isValid(String address) {
        if (address == null) {
            return false;
        }
        return EMAIL_PATTERN.matcher(address).matches();
    }
}
