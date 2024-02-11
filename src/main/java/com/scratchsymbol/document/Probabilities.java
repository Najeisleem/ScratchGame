package com.scratchsymbol.document;


import com.google.gson.annotations.SerializedName;

public class Probabilities {

	 @SerializedName("bonus_symbols")
	 private BonusSymbols bonusSymbols;
	 
	 
	public BonusSymbols getBonusSymbols() {
		return bonusSymbols;
	}
	public void setBonusSymbols(BonusSymbols bonusSymbols) {
		this.bonusSymbols = bonusSymbols;
	}
	 
	 
}
