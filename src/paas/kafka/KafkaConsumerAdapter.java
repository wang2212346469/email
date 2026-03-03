package paas.kafka;

/**
 * Kafka 消费者适配器抽象基类，负责主题匹配和消息分发。
 * Abstract base class for Kafka consumer adapters, handling topic matching and message dispatch.
 */
public abstract class KafkaConsumerAdapter implements KafkaMessageHandler {

    /**
     * 返回本消费者订阅的主题名称。
     * Returns the topic name this consumer subscribes to.
     *
     * @return 主题名称 / topic name
     */
    protected abstract String getTopic();

    /**
     * 处理已匹配主题的消息，由子类实现具体业务逻辑。
     * Processes a message after topic matching; subclasses implement business logic.
     *
     * @param key     消息键 / message key
     * @param payload 消息体 / message payload
     * @throws Exception 处理异常 / processing exception
     */
    protected abstract void processMessage(String key, String payload) throws Exception;

    /**
     * 接收 Kafka 消息，仅当主题匹配时转发给 {@link #processMessage}。
     * Receives a Kafka message and forwards to {@link #processMessage} only if the topic matches.
     *
     * @param topic   消息来源主题 / source topic
     * @param key     消息键 / message key
     * @param payload 消息体 / message payload
     */
    @Override
    public void handle(String topic, String key, String payload) throws Exception {
        if (getTopic().equals(topic)) {
            processMessage(key, payload);
        }
    }
}
