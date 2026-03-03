package paas.kafka;

/**
 * Kafka 消息处理器接口，所有消费者适配器均实现此接口。
 * Kafka message handler interface implemented by all consumer adapters.
 */
public interface KafkaMessageHandler {

    /**
     * 处理一条 Kafka 消息。
     * Handles a single Kafka message.
     *
     * @param topic   消息来源主题 / source topic
     * @param key     消息键（分区键）/ message key (partition key)
     * @param payload 消息体（JSON 字符串）/ message payload (JSON string)
     * @throws Exception 处理异常 / processing exception
     */
    void handle(String topic, String key, String payload) throws Exception;
}
