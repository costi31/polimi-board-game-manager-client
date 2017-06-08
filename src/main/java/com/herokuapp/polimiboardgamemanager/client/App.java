package com.herokuapp.polimiboardgamemanager.client;

import com.herokuapp.polimiboardgamemanager.client.view.ClientView;

/**
 * The Class App.
 */
public class App 
{

	
    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main( String[] args )
    {
        ClientView clientView = ClientView.getCliInstance();
        
        clientView.run();
    } 
       
}