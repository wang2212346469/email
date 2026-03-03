package api.v1.paas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 服务器选择条件，自动模式下由 PaaS 按此条件实时选择并原子预留。
 * Server selection criteria used in automatic mode where PaaS selects
 * and atomically reserves servers matching these criteria.
 *
 * <p>Proto definition:
 * <pre>
 * message SelectionCriteria {
 *   int32 count = 1;
 *   repeated string preferred_node_ids = 2;
 *   repeated string preferred_node_codes = 3;
 *   repeated string preferred_data_center_codes = 4;
 *   repeated string preferred_model_codes = 5;
 *   repeated string excluded_server_ids = 6;
 * }
 * </pre>
 */
public final class SelectionCriteria {

    private final int count;
    private final List<String> preferredNodeIds;
    private final List<String> preferredNodeCodes;
    private final List<String> preferredDataCenterCodes;
    private final List<String> preferredModelCodes;
    private final List<String> excludedServerIds;

    private SelectionCriteria(Builder builder) {
        this.count = builder.count;
        this.preferredNodeIds = Collections.unmodifiableList(new ArrayList<>(builder.preferredNodeIds));
        this.preferredNodeCodes = Collections.unmodifiableList(new ArrayList<>(builder.preferredNodeCodes));
        this.preferredDataCenterCodes = Collections.unmodifiableList(new ArrayList<>(builder.preferredDataCenterCodes));
        this.preferredModelCodes = Collections.unmodifiableList(new ArrayList<>(builder.preferredModelCodes));
        this.excludedServerIds = Collections.unmodifiableList(new ArrayList<>(builder.excludedServerIds));
    }

    /** 返回需要选择的服务器数量（必填）。 */
    public int getCount() {
        return count;
    }

    /** 返回优先选择的节点 ID 列表。 */
    public List<String> getPreferredNodeIds() {
        return preferredNodeIds;
    }

    /** 返回优先选择的节点编码列表。 */
    public List<String> getPreferredNodeCodes() {
        return preferredNodeCodes;
    }

    /** 返回优先选择的机房编码列表。 */
    public List<String> getPreferredDataCenterCodes() {
        return preferredDataCenterCodes;
    }

    /** 返回优先选择的机型编码列表。 */
    public List<String> getPreferredModelCodes() {
        return preferredModelCodes;
    }

    /** 返回排除的服务器 ID 列表（如刚失败的）。 */
    public List<String> getExcludedServerIds() {
        return excludedServerIds;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SelectionCriteria)) return false;
        SelectionCriteria that = (SelectionCriteria) o;
        return count == that.count
                && Objects.equals(preferredNodeIds, that.preferredNodeIds)
                && Objects.equals(preferredNodeCodes, that.preferredNodeCodes)
                && Objects.equals(preferredDataCenterCodes, that.preferredDataCenterCodes)
                && Objects.equals(preferredModelCodes, that.preferredModelCodes)
                && Objects.equals(excludedServerIds, that.excludedServerIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(count, preferredNodeIds, preferredNodeCodes,
                preferredDataCenterCodes, preferredModelCodes, excludedServerIds);
    }

    @Override
    public String toString() {
        return "SelectionCriteria{"
                + "count=" + count
                + ", preferredNodeIds=" + preferredNodeIds
                + ", preferredNodeCodes=" + preferredNodeCodes
                + ", preferredDataCenterCodes=" + preferredDataCenterCodes
                + ", preferredModelCodes=" + preferredModelCodes
                + ", excludedServerIds=" + excludedServerIds
                + '}';
    }

    /** Builder for {@link SelectionCriteria}. */
    public static final class Builder {
        private int count;
        private List<String> preferredNodeIds = new ArrayList<>();
        private List<String> preferredNodeCodes = new ArrayList<>();
        private List<String> preferredDataCenterCodes = new ArrayList<>();
        private List<String> preferredModelCodes = new ArrayList<>();
        private List<String> excludedServerIds = new ArrayList<>();

        private Builder() {}

        public Builder setCount(int count) {
            this.count = count;
            return this;
        }

        public Builder addPreferredNodeIds(String nodeId) {
            this.preferredNodeIds.add(nodeId);
            return this;
        }

        public Builder addAllPreferredNodeIds(List<String> nodeIds) {
            this.preferredNodeIds.addAll(nodeIds);
            return this;
        }

        public Builder addPreferredNodeCodes(String nodeCode) {
            this.preferredNodeCodes.add(nodeCode);
            return this;
        }

        public Builder addAllPreferredNodeCodes(List<String> nodeCodes) {
            this.preferredNodeCodes.addAll(nodeCodes);
            return this;
        }

        public Builder addPreferredDataCenterCodes(String dataCenterCode) {
            this.preferredDataCenterCodes.add(dataCenterCode);
            return this;
        }

        public Builder addAllPreferredDataCenterCodes(List<String> dataCenterCodes) {
            this.preferredDataCenterCodes.addAll(dataCenterCodes);
            return this;
        }

        public Builder addPreferredModelCodes(String modelCode) {
            this.preferredModelCodes.add(modelCode);
            return this;
        }

        public Builder addAllPreferredModelCodes(List<String> modelCodes) {
            this.preferredModelCodes.addAll(modelCodes);
            return this;
        }

        public Builder addExcludedServerIds(String serverId) {
            this.excludedServerIds.add(serverId);
            return this;
        }

        public Builder addAllExcludedServerIds(List<String> serverIds) {
            this.excludedServerIds.addAll(serverIds);
            return this;
        }

        public SelectionCriteria build() {
            return new SelectionCriteria(this);
        }
    }
}
