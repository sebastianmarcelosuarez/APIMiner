import com.google.gson.Gson;
import model.APIResponse;

import javax.ws.rs.*;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@Path("/miner")
public class APIMiner {

    @POST
    @Path("/newgame")
    @Consumes("application/json")
    @Produces("application/json")
    public String newGame(InputStream inputStream) {

        Gson gson = new Gson();

        System.out.println("json request!!");
        System.out.println(inputStream.toString());

       // InputStream inputStream = new ByteArrayInputStream(inputStream.getBytes());

        String text = null;
        try (Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name())) {
            text = scanner.useDelimiter("\\A").next();
        }

        System.out.println("text");
        System.out.println(text);


        String request = gson.toJson(text);
        APIResponse apiResponse = new APIResponse();

        apiResponse.setStatus("OK");
        apiResponse.setResult("newgame");

        String json = gson.toJson(apiResponse);
        return json;
    }

    @GET
    @Path("/load")
    @Produces("application/json")
    public String loadGame() {
        Gson gson = new Gson();

        APIResponse apiResponse = new APIResponse();

        apiResponse.setStatus("OK");
        apiResponse.setResult("load");

        String json = gson.toJson(apiResponse);
        return json;
    }

    @PUT
    @Path("/rightclick")
    @Produces("application/json")
    public String rightClick() {
        Gson gson = new Gson();

        APIResponse apiResponse = new APIResponse();

        apiResponse.setStatus("OK");
        apiResponse.setResult("rightclick");

        String json = gson.toJson(apiResponse);
        return json;
    }

    @PUT
    @Path("/leftclick")
    @Produces("application/json")
    public String leftClick() {
        Gson gson = new Gson();

        APIResponse apiResponse = new APIResponse();

        apiResponse.setStatus("OK");
        apiResponse.setResult("leftclick");

        String json = gson.toJson(apiResponse);
        return json;
    }
}