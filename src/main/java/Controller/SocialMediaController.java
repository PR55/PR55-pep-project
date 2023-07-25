package Controller;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import Service.SocialMediaAccount;
import Model.Account; 

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController { 
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("/register", this::registerHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */

    private void registerHandler(Context context){
        ObjectMapper om = new ObjectMapper();
        SocialMediaAccount socialMediaAccount = new SocialMediaAccount();
        try{
            System.out.println("\n" + "Hello" + "\n");
            Account toAdd = om.readValue(context.body(), Account.class);
            if(!socialMediaAccount.AccountExists(toAdd))
            {
                Account addedAccount = socialMediaAccount.AddAccount(toAdd);
                if(addedAccount != null)
                {
                    context.json(om.writeValueAsString(addedAccount));
                    context.status(200);
                }
                else
                {
                    context.status(400);
                }
                
            }
            else
            {
                context.status(400);
            }
            
        }
        catch(JacksonException e)
        {
            System.out.println(e.getMessage());
        }
        
        
    }


}