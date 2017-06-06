package com.herokuapp.polimiboardgamemanager.client;

import com.herokuapp.polimiboardgamemanager.client.view.ClientView;

public class App 
{

	
    public static void main( String[] args )
    {
        ClientView clientView = ClientView.getCliInstance();
        
        clientView.run();
    } 
       
}