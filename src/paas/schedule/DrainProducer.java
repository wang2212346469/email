package paas.schedule;

import paas.kafka.DrainRequest;
import paas.kafka.KafkaProducerAdapter;
import paas.kafka.KafkaTopics;

/**
 * 资源腾挪请求生产者，向 gpuscheduler-paas-drain 主题发布腾挪消息。
 * Drain request producer publishing drain messages to the gpuscheduler-paas-drain topic.
 */
public class DrainProducer extends KafkaProducerAdapter {

    @Override
    protected String getTopic() {
        return KafkaTopics.GPUSCHEDULER_PAAS_DRAIN;
    }

    /**
     * 以 taskId 作为 Kafka 分区键。
     * Uses taskId as the Kafka partition key.
     *
     * @param message 消息对象（需为 DrainRequest）/ message object (expected DrainRequest)
     * @return 分区键字符串 / partition key string
     */
    @Override
    public String getPartitionKey(Object message) {
        if (message instanceof DrainRequest) {
            return ((DrainRequest) message).getTaskId();
        }
        return message == null ? "" : message.toString();
    }

    /**
     * 发送资源腾挪请求（存根实现，打印到控制台）。
     * Sends a resource drain request (stub: prints to console).
     *
     * @param req 腾挪请求 / drain request
     */
    public void sendDrainRequest(DrainRequest req) {
        System.out.println("[DrainProducer] Sending to topic=" + getTopic()
                + " key=" + getPartitionKey(req)
                + " payload=" + serialize(req));
    }
}
