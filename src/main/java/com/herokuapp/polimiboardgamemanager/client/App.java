package com.herokuapp.polimiboardgamemanager.client;

import java.net.URI;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import com.herokuapp.polimiboardgamemanager.client.view.cli.CommandLine;
import com.herokuapp.polimiboardgamemanager.model.User;

public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Polimi Board Game Manager - Client" );
        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);
//        WebTarget service = client.target(getBaseURI());
//        List<User> allUsers = service.path("/users").request(MediaType.APPLICATION_JSON).get(new GenericType<List<User>>() {});
//        
//        for (User user: allUsers) {            
//            System.out.println(user);
//        }    
        
        CommandLine.getInstance().run();
    }
    
    private static URI getBaseURI() {
        return UriBuilder.fromUri("https://polimi-board-game-manager.herokuapp.com/").build();
    }   
       
}
