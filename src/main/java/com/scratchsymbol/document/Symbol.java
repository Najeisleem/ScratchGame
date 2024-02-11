package com.scratchsymbol.document;

import com.google.gson.annotations.SerializedName;

public class Symbol {

	 @SerializedName("reward_multiplier")
    private Double rewardMultiplier;
    private String type;
    private String impact;
    private int extra;
    
	public Double getRewardMultiplier() {
		return rewardMultiplier;
	}
	public void setRewardMultiplier(Double rewardMultiplier) {
		this.rewardMultiplier = rewardMultiplier;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getImpact() {
		return impact;
	}
	public void setImpact(String impact) {
		this.impact = impact;
	}
	public int getExtra() {
		return extra;
	}
	public void setExtra(int extra) {
		this.extra = extra;
	}
    
     
}
