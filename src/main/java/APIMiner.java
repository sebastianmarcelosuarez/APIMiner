import com.google.gson.Gson;
import model.Board;
import model.Box;
import model.api.APIRequest;
import model.api.APIResponse;
import model.api.Status;
import system.SystemService;

import javax.ws.rs.*;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Path("/miner")
public class APIMiner {

    SystemService  SystemService = new SystemService();

    @POST
    @Path("/newgame")
    @Consumes("application/json")
    @Produces("application/json")
    public String newGame(InputStream inputStream) {

        Gson gson = new Gson();
        String text = null;
        try (Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name())) {
            text = scanner.useDelimiter("\\A").next();
        }
        APIRequest apiRequest = gson.fromJson(text,APIRequest.class);

        Board board = SystemService.newGame(apiRequest.getUserName());

        APIResponse apiResponse = new APIResponse();
        if (board == null) {
            apiResponse.setStatus(Status.ERROR.toString());
        }else{
            apiResponse.setStatus(Status.OK.toString());
            apiResponse.setBoxList(board.getGameBoardAsList());
            apiResponse.setLose(Boolean.FALSE);
            apiResponse.setWin(Boolean.FALSE);
        }

        apiResponse.setUserName(apiRequest.getUserName());

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

        String json = gson.toJson(apiResponse);
        return json;
    }
}