package com.scratchsymbol.game;
import java.io.File;

public class App 
{
    public static void main( String[] args ){
    	
    	  String configFile = "";
          int bettingAmount = 0;

          for (int i = 0; i < args.length; i++) {
              if (args[i].equals("--config") && i + 1 < args.length) {
                  configFile = args[i + 1];
                  i++;
              } else if (args[i].equals("--betting-amount") && i + 1 < args.length) {
                  try {
                      bettingAmount = Integer.parseInt(args[i + 1]);
                  } catch (NumberFormatException e) {
                      System.err.println("Invalid betting amount. Please provide a valid integer.");
                      System.exit(1);
                  }
                  i++;
              }
          }

          if (configFile.isEmpty()) {
              System.err.println("Config file not provided.");
              System.exit(1);
          }

          File file = new File(configFile);
          if (!file.exists()) {
              System.err.println("Config file does not exist: " + configFile);
              System.exit(1);
          }
	        
	        System.out.println("Config File: " + configFile);
	        System.out.println("Betting Amount: " + bettingAmount);
	        
	    	new ScratchGame(new File(configFile),bettingAmount);
	    	
	    }
	    
      
    
}
