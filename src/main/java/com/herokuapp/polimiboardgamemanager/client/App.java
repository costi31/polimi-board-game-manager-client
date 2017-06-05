package com.herokuapp.polimiboardgamemanager.client;

import com.beust.jcommander.JCommander;

public class App 
{

	
    public static void main( String[] args )
    {
//        ClientView clientView = ClientView.getCliInstance();
//        
//        clientView.run();
    	AbbaCommand abba = new AbbaCommand();
    	JCommander jc = JCommander.newBuilder()
    	  .addCommand("abba", abba)
    	  .build();
    	jc.parse("abba", "-a", "ciao");
    	jc.usage();
    	
    	System.out.println(abba.a);
    } 
       
}