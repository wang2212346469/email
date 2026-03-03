package paas.schedule;

import paas.kafka.KafkaProducerAdapter;
import paas.kafka.KafkaTopics;
import paas.kafka.ScheduleEventNotification;

/**
 * 调度事件通知生产者，向 gpuscheduler-schedule-event 主题发布 LEND/RECLAIM/TRANSFER 事件。
 * Schedule event notification producer publishing LEND/RECLAIM/TRANSFER events
 * to the gpuscheduler-schedule-event topic.
 */
public class ScheduleEventProducer extends KafkaProducerAdapter {

    @Override
    protected String getTopic() {
        return KafkaTopics.GPUSCHEDULER_SCHEDULE_EVENT;
    }

    /**
     * 以 serverId 作为 Kafka 分区键，保证同一服务器的事件有序。
     * Uses serverId as the Kafka partition key to ensure ordering for the same server.
     *
     * @param message 消息对象（需为 ScheduleEventNotification）/ message object
     * @return 分区键字符串 / partition key string
     */
    @Override
    public String getPartitionKey(Object message) {
        if (message instanceof ScheduleEventNotification) {
            return ((ScheduleEventNotification) message).getServerId();
        }
        return message == null ? "" : message.toString();
    }

    /**
     * 发送调度事件通知（存根实现，打印到控制台）。
     * Sends a schedule event notification (stub: prints to console).
     *
     * @param event 调度事件 / schedule event notification
     */
    public void sendEvent(ScheduleEventNotification event) {
        System.out.println("[ScheduleEventProducer] Sending to topic=" + getTopic()
                + " key=" + getPartitionKey(event)
                + " payload=" + serialize(event));
    }
}
