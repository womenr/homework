package wj.pojo;

public class AgentBasicInfo extends AgentBasicInfoKey {
    private String agentName;

    private Integer agentAge;

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName == null ? null : agentName.trim();
    }

    public Integer getAgentAge() {
        return agentAge;
    }

    public void setAgentAge(Integer agentAge) {
        this.agentAge = agentAge;
    }
}