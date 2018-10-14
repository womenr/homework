package wj.pojo;

public class AgentBasicInfo{

    private String agentId;
	
	private String agentName;

    private Integer agentAge;

    private String shopNumber;


	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public Integer getAgentAge() {
		return agentAge;
	}

	public void setAgentAge(Integer agentAge) {
		this.agentAge = agentAge;
	}

	public String getShopNumber() {
		return shopNumber;
	}

	public void setShopNumber(String shopNumber) {
		this.shopNumber = shopNumber;
	}

	@Override
	public String toString() {
		return "AgentBasicInfo [agentId=" + agentId + ", agentName=" + agentName + ", agentAge=" + agentAge
				+ ", shopNumber=" + shopNumber + "]";
	}

	public String toCsvString() {
		return agentId + "," + agentName + "," + agentAge + "," + shopNumber;
	}
}