package paas.schedule;

import paas.kafka.BatchCancelReserveRequest;
import paas.kafka.KafkaProducerAdapter;
import paas.kafka.KafkaTopics;
import paas.kafka.ReserveRequest;

/**
 * 资源预留请求生产者，向 gpuscheduler-paas-reserve 主题发布预留和取消预留消息。
 * Reserve request producer publishing reserve and cancel-reserve messages
 * to the gpuscheduler-paas-reserve topic.
 */
public class ReserveProducer extends KafkaProducerAdapter {

    @Override
    protected String getTopic() {
        return KafkaTopics.GPUSCHEDULER_PAAS_RESERVE;
    }

    /**
     * 以 taskId 作为 Kafka 分区键。
     * Uses taskId as the Kafka partition key.
     *
     * @param message 消息对象，需为 ReserveRequest 或 BatchCancelReserveRequest / message object
     * @return 分区键字符串 / partition key string
     */
    @Override
    public String getPartitionKey(Object message) {
        if (message instanceof ReserveRequest) {
            return ((ReserveRequest) message).getTaskId();
        }
        if (message instanceof BatchCancelReserveRequest) {
            return ((BatchCancelReserveRequest) message).getTaskId();
        }
        return message == null ? "" : message.toString();
    }

    /**
     * 发送资源预留请求（存根实现，打印到控制台）。
     * Sends a resource reserve request (stub: prints to console).
     *
     * @param req 预留请求 / reserve request
     */
    public void sendReserveRequest(ReserveRequest req) {
        System.out.println("[ReserveProducer] Sending to topic=" + getTopic()
                + " key=" + getPartitionKey(req)
                + " payload=" + serialize(req));
    }

    /**
     * 发送批量取消预留请求（存根实现，打印到控制台）。
     * Sends a batch cancel-reserve request (stub: prints to console).
     *
     * @param req 取消预留请求 / cancel reserve request
     */
    public void sendCancelReserveRequest(BatchCancelReserveRequest req) {
        System.out.println("[ReserveProducer] Sending cancel to topic=" + getTopic()
                + " key=" + getPartitionKey(req)
                + " payload=" + serialize(req));
    }
}
