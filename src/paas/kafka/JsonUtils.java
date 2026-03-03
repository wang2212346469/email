package paas.kafka;

/**
 * JSON 字段提取工具类（无外部依赖），支持 camelCase 和 snake_case 两种字段命名风格。
 * JSON field extraction utility (no external dependencies), supporting both camelCase
 * and snake_case field naming styles.
 *
 * <p>本类被 PaaS 集成模块中所有需要解析 PaaS 原始 JSON 的消费者和转换器共用，
 * 避免重复实现相同的解析逻辑。</p>
 *
 * <p>Used by all consumers and converters in the PaaS integration module that need
 * to parse raw PaaS JSON, eliminating duplicated parsing logic.</p>
 */
public final class JsonUtils {

    private JsonUtils() {}

    /**
     * 从 JSON 字符串中提取字段值，同时尝试 camelCase 和 snake_case 两种键名。
     * Extracts a field value from a JSON string, trying both camelCase and snake_case keys.
     *
     * @param json      原始 JSON 字符串 / raw JSON string
     * @param camelKey  驼峰命名键名 / camelCase key name
     * @param snakeKey  下划线命名键名 / snake_case key name
     * @return 字段值，未找到时返回 null / field value, null if not found
     */
    public static String extractField(String json, String camelKey, String snakeKey) {
        String val = extractByKey(json, camelKey);
        return val != null ? val : extractByKey(json, snakeKey);
    }

    /**
     * 从 JSON 字符串中按单一键名提取字段值（不支持嵌套对象）。
     * Extracts a field value from a JSON string by a single key name (no nested objects).
     *
     * @param json  原始 JSON 字符串 / raw JSON string
     * @param key   键名 / key name
     * @return 字段值，未找到时返回 null / field value, null if not found
     */
    public static String extractByKey(String json, String key) {
        if (json == null || key == null) return null;
        String pattern = "\"" + key + "\"";
        int idx = json.indexOf(pattern);
        if (idx < 0) return null;
        int colonIdx = json.indexOf(':', idx + pattern.length());
        if (colonIdx < 0) return null;
        int start = colonIdx + 1;
        while (start < json.length() && Character.isWhitespace(json.charAt(start))) {
            start++;
        }
        if (start >= json.length()) return null;
        if (json.charAt(start) == '"') {
            int end = json.indexOf('"', start + 1);
            return end < 0 ? null : json.substring(start + 1, end);
        } else {
            int end = start;
            while (end < json.length() && json.charAt(end) != ',' && json.charAt(end) != '}') {
                end++;
            }
            return json.substring(start, end).trim();
        }
    }

    /**
     * 从 JSON 字符串中提取整型值，支持两种命名风格。
     * Extracts an integer value from a JSON string, supporting both naming styles.
     *
     * @param json        原始 JSON 字符串 / raw JSON string
     * @param camelKey    驼峰命名键名 / camelCase key name
     * @param snakeKey    下划线命名键名 / snake_case key name
     * @param defaultVal  解析失败时的默认值 / default value on parse failure
     * @return 整型字段值 / integer field value
     */
    public static int extractInt(String json, String camelKey, String snakeKey, int defaultVal) {
        String val = extractField(json, camelKey, snakeKey);
        if (val == null || val.isEmpty()) return defaultVal;
        try { return Integer.parseInt(val.trim()); } catch (NumberFormatException e) { return defaultVal; }
    }

    /**
     * 从 JSON 字符串中提取长整型值，支持两种命名风格。
     * Extracts a long value from a JSON string, supporting both naming styles.
     *
     * @param json        原始 JSON 字符串 / raw JSON string
     * @param camelKey    驼峰命名键名 / camelCase key name
     * @param snakeKey    下划线命名键名 / snake_case key name
     * @param defaultVal  解析失败时的默认值 / default value on parse failure
     * @return 长整型字段值 / long field value
     */
    public static long extractLong(String json, String camelKey, String snakeKey, long defaultVal) {
        String val = extractField(json, camelKey, snakeKey);
        if (val == null || val.isEmpty()) return defaultVal;
        try { return Long.parseLong(val.trim()); } catch (NumberFormatException e) { return defaultVal; }
    }

    /**
     * 从 JSON 字符串中提取双精度浮点值，支持两种命名风格。
     * Extracts a double value from a JSON string, supporting both naming styles.
     *
     * @param json        原始 JSON 字符串 / raw JSON string
     * @param camelKey    驼峰命名键名 / camelCase key name
     * @param snakeKey    下划线命名键名 / snake_case key name
     * @param defaultVal  解析失败时的默认值 / default value on parse failure
     * @return 双精度浮点字段值 / double field value
     */
    public static double extractDouble(String json, String camelKey, String snakeKey, double defaultVal) {
        String val = extractField(json, camelKey, snakeKey);
        if (val == null || val.isEmpty()) return defaultVal;
        try { return Double.parseDouble(val.trim()); } catch (NumberFormatException e) { return defaultVal; }
    }
}
