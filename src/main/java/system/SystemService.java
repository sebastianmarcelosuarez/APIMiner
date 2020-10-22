package system;

import firebase.FireBaseService;
import com.google.firebase.database.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Board;
import model.Box;
import model.UserDB;
import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class SystemService {

    final static FireBaseService fireBaseService = new FireBaseService();

    Board dbBoard = null;
    String UsersDB = null;

    public Board newGame(String userName){
        Board board = new Board(3);
        return board;
    }

    /**
     * Saves the game on firebase
     */
    public void saveGame(String userName, Board board) {

        FirebaseDatabase firebase = fireBaseService.getDb();

        DatabaseReference ref = firebase.getReference("save");
        DatabaseReference usersRef = ref.child("user");

        UserDB userDB = new UserDB();
        userDB.setUserName(userName);
        List <Box> boxes = new ArrayList<Box>();
        for (int i = 0; i < board.getGameBoard().length; i++) {
            for (int j = 0; j <  board.getGameBoard().length; j++) {
                Box box = (Box) board.getGameBoard()[i][j];
                boxes.add(box);
            }
        }

        userDB.setBoxes(boxes);
        Gson gson = new Gson();
        String json = gson.toJson(userDB);
        usersRef.setValueAsync(json);
    }

    /**
     * Loads game from firebase
     */
    public void loadGame(String userName) throws InterruptedException {
        System.out.println("Loading .........");
        readData();
        UsersDB = null;
        updateBoard(userName);
    }


    private void updateBoard(String userName) throws InterruptedException {
        System.out.println("updateBoard ............................");
        //todo check user!
        Board board = new Board();
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        List<UserDB> users;
        while (this.UsersDB == null) {
            System.out.println("loading this.JsonDBInfo, please wait");
         //   System.out.println("this.JsonDBInfo is " + this.UsersDB);
            TimeUnit.SECONDS.sleep(2);
        }

        users = Arrays.asList(gson.fromJson( this.UsersDB, UserDB.class));
        System.out.println("users ");
        int  size = 0;
        size = (users.get(0).getBoxes().size())/3;
        System.out.println("size "+ size);
        board.setGameBoard(new Object[size][size]);



        UserDB  user = users.stream().filter(userDB -> userDB.getUserName().equals(userName)).findFirst().orElse(null);

        if (user != null ) {
            System.out.println("User found!, loading latest game");
              user.getBoxes()
                      .forEach(box ->
                        board.getGameBoard()[box.getPosi()][box.getPosj()] = box);
        }else{
            System.out.println("User not found, initializing game");
            dbBoard = new Board(3);
        }


     //   users.forEach( user -> user.getBoxes().stream().
       //       forEach(box ->
         //       board.getGameBoard()[box.getPosi()][box.getPosj()] = box));

        dbBoard = board;

    }

    private void readData( ){
        System.out.println("reading data");
        FirebaseDatabase firebase = fireBaseService.getDb();
        DatabaseReference ref = firebase.getReference("save/user");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                String JsonUsers = dataSnapshot.getValue(String.class);
                System.out.println(JsonUsers);
                setUsersDB(JsonUsers);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void updateBoard(Map<String, JSONArray> dBboxes) {
        System.out.println("boxes update inside");
        JSONArray boxesList = dBboxes.get("peplo");

        System.out.println("updateBoard ............................");
        //todo check user!
        Board board = new Board();
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        List<Box> boxes = new ArrayList<Box>();
        boxes = Arrays.asList(gson.fromJson(String.valueOf(boxesList), Box[].class));
        boxes.forEach( box -> board.getGameBoard()[box.getPosi()][box.getPosj()] = box);

        dbBoard = board;

        System.out.println("boxes for each ending");
    };

    public Board getDbBoard() {
        return dbBoard;
    }

    public void setDbBoard(Board dbBoard) {
        this.dbBoard = dbBoard;
    }

    public String getUsersDB() {
        return UsersDB;
    }

    public void setUsersDB(String usersDB) {
        UsersDB = usersDB;
    }
}
