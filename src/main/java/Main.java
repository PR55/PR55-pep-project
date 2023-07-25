import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import Controller.SocialMediaController;
import Model.Account;
import Util.ConnectionUtil;
import io.javalin.Javalin;

/**
 * This class is provided with a main method to allow you to manually run and test your application. This class will not
 * affect your program in any way and you may write whatever code you like here.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException, IOException{
        ConnectionUtil.resetTestDatabase();
        SocialMediaController socialMediaController = new SocialMediaController();
        Javalin app = socialMediaController.startAPI();
        HttpClient webClient = HttpClient.newHttpClient();
        ObjectMapper objectMapper = new ObjectMapper();
        app.start(8080);
        HttpRequest postRequest = HttpRequest.newBuilder()
        .uri(URI.create("http://localhost:8080/register"))
        .POST(HttpRequest.BodyPublishers.ofString("{" +
                "\"username\": \"user\", " +
                "\"password\": \"password\" }"))
        .header("Content-Type", "application/json")
        .build();
        HttpResponse response = webClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        //Account added = objectMapper.readValue(response.body().toString(), Account.class);
        System.out.println(status);
        System.out.println(response.body().toString());
        app.close();
        app.stop();

    }
}
