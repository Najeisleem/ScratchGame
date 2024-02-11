package com.scratchsymbol.game;

import com.google.gson.Gson;
import com.scratchsymbol.document.Config;
import com.scratchsymbol.document.Symbol;
import com.scratchsymbol.document.WinCombination;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class ScratchGame {
	
	    private int rows;
	    private int columns;
	    private int betAmount;
	    private int bonusSymbolCount;
	    private List<String> standardSymbols;
		private Map<String, Symbol> symbols;
	    private Map<String, WinCombination> winCombinations;
	    private Map<String, Integer> symbolsCount = new HashMap<>();
		private Map<String,String[]> appliedWinnincgombinations = new HashMap();
        private double totalReward = 0.0;
        private String bonus = "";
        private String[][] matrix;
        private Random random;
	    private Config config;
	    
	    
	    public ScratchGame(File confFile, int betAmount) {
	    	parseConfig(confFile);
	        this.betAmount = betAmount;
			this.random = new Random();
	        this.standardSymbols = symbols.entrySet().stream()
	                .filter(entry -> "standard".equals(entry.getValue().getType()))
	                .map(Map.Entry::getKey)
	                .collect(Collectors.toList());
	        generateMatrix();
	        calculateRewardAndGenerateOutput();
	    }
	
	    private boolean parseConfig(File confFile) {
	        try (Reader reader = new FileReader(confFile)) {
	            Gson gson = new Gson();
	            this.config = gson.fromJson(reader, Config.class);
	            this.rows = config.getRows() != 0 ? config.getRows() : 3;
		        this.columns = config.getColumns() != 0 ? config.getColumns() : 3;
		        this.symbols = config.getSymbols();
		        this.winCombinations = config.getWinCombinations().entrySet().stream()
		                .sorted(Comparator.comparingInt(entry -> entry.getValue().getCount()))
		                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return true;
	    }
	  
	  
	    private String[][] generateMatrix() {
            matrix = new String[rows][columns];
	        for (int i = 0; i < rows; i++) {
	            for (int j = 0; j < columns; j++) {
	                String symbol = getRandomSymbol();
	    	        symbolsCount.put(symbol, symbolsCount.getOrDefault(symbol, 0) + 1);
	                matrix[i][j] = symbol;
	            }
	        }
	        return matrix;
	    }
	    
	    private String getRandomSymbol() {

	        Object[] keys = symbols.keySet().toArray();
	        int randomIndex = random.nextInt(keys.length);
	        String symbol = (String) keys[randomIndex];
	        Symbol symbolData = symbols.get(symbol);

	        if ("bonus".equals(symbolData.getType())) {
	            if (bonusSymbolCount == 0) {
	                bonusSymbolCount++;
	                return symbol;
	            } else {
	                randomIndex = random.nextInt(standardSymbols.size());
	     	        return standardSymbols.get(randomIndex);
	            }
	        } else {
	            return symbol;
	        }
	    }
	    
	    private void calculateRewardAndGenerateOutput() {
	    	Map<String, Object> output = new HashMap();
	    	
	        for (Map.Entry<String, WinCombination> entry :  winCombinations.entrySet()) {
	            WinCombination combination = entry.getValue();
	            winCombinationSymbol(combination);
	        }
	        if(totalReward != 0 && bonus.length() > 0) {
	        	if(bonus.startsWith("+")) {
	        		totalReward += Integer.valueOf(bonus.replace("+", ""));
	        	} else {
	        		totalReward *= Integer.valueOf(bonus.replace("x", ""));
	        	}
	        }
	        output.put("matrix",matrix);
	        output.put("reward",totalReward);
	        output.put("applied_winning_combinations",appliedWinnincgombinations);
	        output.put("applied_bonus_symbol",bonus);
	        Gson gson = new Gson();
	        String jsonOutput = gson.toJson(output);
       	 	System.out.println(jsonOutput);
	    }


		private Map<String, String[]> winCombinationSymbol(WinCombination winCombination) {
			for (Map.Entry<String, Integer> entry : symbolsCount.entrySet()) {
			    String symbol = entry.getKey();
			    int count = entry.getValue();
			    if(winCombination.getCount() == count) {
				    double combinationRewardMultiplier = winCombination.getRewardMultiplier();
	            	 Double symbolReward = 0.0;
	            	 Symbol symbolData = symbols.get(symbol);
	            	 if(symbolData.getRewardMultiplier() != null) {
	            		 symbolReward = symbolData.getRewardMultiplier();
	            	 } else if(symbolData.getExtra() != 0) {
	            		 symbolReward = Double.valueOf(symbolData.getExtra());
	            	 }
	            	 totalReward += ((betAmount * symbolReward) * combinationRewardMultiplier);
	            	 String[] array = { "same_symbol_" + count + "_times" };
	            	 appliedWinnincgombinations.put(symbol, array);
			    } else if(symbol.matches(".*\\d+.*")) {
			    	bonus = symbol;
			    }
			}	
			return appliedWinnincgombinations;
		}

			
	 
}
