package paas.kafka;

/**
 * Kafka 生产者适配器抽象基类，提供主题管理和序列化骨架。
 * Abstract base class for Kafka producer adapters providing topic management and serialization skeleton.
 */
public abstract class KafkaProducerAdapter {

    /**
     * 返回本生产者发布消息的目标主题名称。
     * Returns the target topic name this producer publishes messages to.
     *
     * @return 主题名称 / topic name
     */
    protected abstract String getTopic();

    /**
     * 根据消息对象计算 Kafka 分区键。
     * Computes the Kafka partition key for the given message.
     *
     * @param message 消息对象 / message object
     * @return 分区键字符串 / partition key string
     */
    public abstract String getPartitionKey(Object message);

    /**
     * 将消息对象序列化为字符串（默认使用 toString()，生产环境应替换为 JSON 序列化）。
     * Serializes a message object to a string (defaults to toString(); replace with JSON in production).
     *
     * @param obj 待序列化的对象 / object to serialize
     * @return 序列化后的字符串 / serialized string
     */
    protected String serialize(Object obj) {
        return obj == null ? "null" : obj.toString();
    }
}
