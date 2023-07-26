package Controller;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import Service.SocialMediaAccount;
import Service.SocialMediaMessages;
import Model.Account;
import Model.Message; 

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
        app.post("/register", this::registerHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages", this::newMessageHandler);

        app.delete("/messages/{messageID}", this::deleteMessageIDHandler);

        app.patch("/messages/{messageID}", this::messageUpdateHandler);

        app.get("/messages", this::messageHandler);
        app.get("accounts/{userID}/messages", this::userMessageHandler);
        app.get("/messages/{messageID}", this::messageIDHandler);

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
            Account toAdd = om.readValue(context.body(), Account.class);
            if(socialMediaAccount.AccountExists(toAdd) == false)
            {
                Account addedAccount = socialMediaAccount.AddAccount(toAdd);
                if(addedAccount != null)
                {
                    context.status(200);
                    context.json(om.writeValueAsString(addedAccount));
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

    private void loginHandler(Context context)
    {
        ObjectMapper om = new ObjectMapper();
        SocialMediaAccount socialMediaAccount = new SocialMediaAccount();
        try{
            Account loginAccount = om.readValue(context.body(), Account.class);
            if(socialMediaAccount.loginTest(loginAccount))
            {
                context.status(200);
                Account loginReference = socialMediaAccount.getReference(loginAccount);
                context.json(om.writeValueAsString(loginReference));
            }
            else
            {
                context.status(401);
            }
        }
        catch(JacksonException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private void newMessageHandler(Context context)
    {
        ObjectMapper om = new ObjectMapper();
        SocialMediaMessages socialMediaMessages= new SocialMediaMessages();
        SocialMediaAccount socialMediaAccount = new SocialMediaAccount();
        try
        {
            Message newMessage = om.readValue(context.body(), Message.class);
            if(newMessage.message_text != "" && newMessage.message_text.length() < 255
            && socialMediaAccount.AccountByID(newMessage.posted_by) != null)
            {
                Message generatedMessage = socialMediaMessages.NewMessage(newMessage);
                if(generatedMessage != null)
                {
                    context.status(200);
                    context.json(om.writeValueAsString(generatedMessage));
                }
                else
                {
                    context.status(400);
                    context.json("");
                }
            }
            else
            {
                context.status(400);
                context.json("");
            }

        }catch(JacksonException e)
        {
            System.out.println(e.getMessage());
        }
        

    }

    private void messageHandler(Context context)
    {
        ObjectMapper om = new ObjectMapper();
        SocialMediaMessages socialMediaMessages= new SocialMediaMessages();
        try{
            context.status(200);
            context.json(om.writeValueAsString(socialMediaMessages.GetAllMessages()));
        } catch(JacksonException e)
        {
            System.out.println(e.getMessage());
        }
        
    }

    private void userMessageHandler(Context context)
    {
        ObjectMapper om = new ObjectMapper();
        SocialMediaMessages socialMediaMessages= new SocialMediaMessages();
        try{
            int userID = Integer.parseInt(context.pathParam("userID"));
            context.status(200);
            context.json(om.writeValueAsString(socialMediaMessages.GetUserMessages(userID)));
        } catch(JacksonException e)
        {
            System.out.println(e.getMessage());
        }
        
    }

    private void messageIDHandler(Context context)
    {
        ObjectMapper om = new ObjectMapper();
        SocialMediaMessages socialMediaMessages= new SocialMediaMessages();
        try{
            int userID = Integer.parseInt(context.pathParam("messageID"));
            context.status(200);
            Message retMess = socialMediaMessages.GetMessageByID(userID);
            if(retMess == null)
                context.json("");
            else
                context.json(om.writeValueAsString(retMess));
            
        } catch(JacksonException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private void deleteMessageIDHandler(Context context)
    {
        ObjectMapper om = new ObjectMapper();
        SocialMediaMessages socialMediaMessages= new SocialMediaMessages();
        try{
            int userID = Integer.parseInt(context.pathParam("messageID"));
            context.status(200);
            Message retMess = socialMediaMessages.DeleteMessageByID(userID);
            if(retMess == null)
                context.json("");
            else
                context.json(om.writeValueAsString(retMess));
            
        } catch(JacksonException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private void messageUpdateHandler(Context context)
    {
        ObjectMapper om = new ObjectMapper();
        SocialMediaMessages socialMediaMessages= new SocialMediaMessages();

        try{
            Message oldMessage = socialMediaMessages.GetMessageByID(Integer.parseInt(context.pathParam("messageID")));
            

            if(oldMessage != null)
            {
                Message newMessage = socialMediaMessages.UpdateMessage(Integer.parseInt(context.pathParam("messageID")),
                 om.readValue(context.body(), Message.class).message_text);
                if(newMessage != null)
                {
                    
                    context.status(200);
                    context.json(om.writeValueAsString(newMessage));
                }   
                else
                {
                    context.status(400);
                    context.json("");
                }
            }
            else
            {
                context.status(400);
                context.json("");
            }

        }catch(JacksonException e)
        {
            System.out.println(e.getMessage());
        }
    }


}