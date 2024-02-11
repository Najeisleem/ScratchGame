package com.scratchsymbol.document;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class WinCombination {
	
		@SerializedName("reward_multiplier")
	    private double rewardMultiplier;
	    private String when;
	    private int count;
	    private String group;
	    @SerializedName("covered_areas")
	    private List<List<String>> coveredAreas;
	    
	    
		public double getRewardMultiplier() {
			return rewardMultiplier;
		}
		public void setRewardMultiplier(double rewardMultiplier) {
			this.rewardMultiplier = rewardMultiplier;
		}
		public String getWhen() {
			return when;
		}
		public void setWhen(String when) {
			this.when = when;
		}
		public int getCount() {
			return count;
		}
		public void setCount(int count) {
			this.count = count;
		}
		public String getGroup() {
			return group;
		}
		public void setGroup(String group) {
			this.group = group;
		}
		public List<List<String>> getCoveredAreas() {
			return coveredAreas;
		}
		public void setCoveredAreas(List<List<String>> coveredAreas) {
			this.coveredAreas = coveredAreas;
		}
	    
	    
}
