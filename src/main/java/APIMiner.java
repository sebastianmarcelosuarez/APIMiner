import com.google.gson.Gson;
import model.Board;
import model.api.APIRequest;
import model.api.APIResponse;
import model.api.Action;
import model.api.Status;
import system.SystemService;

import javax.ws.rs.*;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@Path("/miner")
public class APIMiner {

    SystemService systemService = new SystemService();
    Gson gson = new Gson();


    /**
     * Reads Input stream to ApiRequest
     * @return APIRequest
     */
    private APIRequest readInputStream (InputStream inputStream) {
        String text;
        try (Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name())) {
            text = scanner.useDelimiter("\\A").next();
        }
        return gson.fromJson(text,APIRequest.class);
    }

    @POST
    @Path("/newgame")
    @Consumes("application/json")
    @Produces("application/json")
    public String newGame(InputStream inputStream) throws InterruptedException {

        APIRequest apiRequest = readInputStream(inputStream);

        Board board = systemService.newGame(apiRequest.getUserName());

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

        return  gson.toJson(apiResponse);
    }

    @PUT
    @Path("/action")
    @Consumes("application/json")
    @Produces("application/json")
    public String action(InputStream inputStream) throws InterruptedException {
        APIResponse apiResponse = new APIResponse();

        APIRequest apiRequest = readInputStream(inputStream);
        Board board = systemService.loadGame(apiRequest.getUserName());
        apiResponse.setStatus(Status.OK.toString());

        if (apiRequest.getAction().equalsIgnoreCase(Action.LEFTCLICK.toString())){
            if (board.leftclick(apiRequest.getUserName(),apiRequest.getPosi(),apiRequest.getPosj())){
                //Mine found
                apiResponse.setLose(Boolean.TRUE);
            }else{
                //Mine not found
                if (board.checkWin()) {
                    apiResponse.setWin(Boolean.TRUE);
                    //erase user data
                    systemService.saveGame(apiRequest.getUserName(), new Board(3));
                }
                apiResponse.setWin(Boolean.FALSE);
                systemService.saveGame(apiRequest.getUserName(), board);
            }


        }else{
            //right click action
             board = systemService.loadGame(apiRequest.getUserName());
             board.rightclick(apiRequest.getPosi(), apiRequest.getPosj());
             systemService.saveGame(apiRequest.getUserName(), board);

        }

      apiResponse.setBoxList(board.getGameBoardAsList());

        return gson.toJson(apiResponse);
    }
}