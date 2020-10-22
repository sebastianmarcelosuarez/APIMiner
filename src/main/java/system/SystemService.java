package system;

import firebase.FireBaseService;
import com.google.firebase.database.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Board;
import model.Box;
import model.BoxStatus;
import model.UserDB;
import org.json.simple.JSONArray;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class SystemService {

    final static FireBaseService fireBaseService = new FireBaseService();

    Board dbBoard = null;
    Map<String, UserDB> usersDB = null;
    String JsonUsersDB = null;
    Gson gson = new Gson();
    Boolean reading = Boolean.FALSE;

    public Board newGame(String userName) throws InterruptedException {
        reading = Boolean.TRUE;
        readData();

        while (reading) {
            System.out.println("loading this.JsonDBInfo for new game, please wait");
            TimeUnit.SECONDS.sleep(2);
        }

        Board board = new Board(3);
        dbBoard = board;
        saveGame(userName,board);
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
        userDB.setBoxes(board.getGameBoardAsList());

        if (this.usersDB.containsKey(userName)) {
            this.usersDB.replace(userName,userDB);
        }else{
            this.usersDB.put(userName,userDB);
        }

        usersRef.setValueAsync(this.usersDB);
    }

    /**
     * Loads game from firebase
     */
    public Board loadGame(String userName) throws InterruptedException {
        System.out.println("Loading .........");
        readData();
        JsonUsersDB = null;
        updateBoard(userName);
        return this.dbBoard;
    }

    private Box toBox(Map map) {
        Box box = new Box();
        box.setMine((Boolean) map.get("mine"));
        box.setPosi(((Long)map.get("posi")).intValue() );
        box.setPosj(  ((Long)map.get("posj")).intValue() );
        box.setHidden((Boolean) map.get("hidden"));
        box.setMinesAround(((Long) map.get("minesAround")).intValue());

        BoxStatus boxStatus;

        if ( ((String) map.get("status")).equalsIgnoreCase(BoxStatus.NONE.toString())) {
            boxStatus = BoxStatus.NONE;
        }else{
            boxStatus= BoxStatus.FLAG;
        }

        box.setStatus(boxStatus );
        return box;
    }


    private void updateBoard(String userName) throws InterruptedException {
        System.out.println("updateBoard ............................");
        Board board = new Board(3);
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        List<UserDB> users;

        this.reading = Boolean.TRUE;
        while (reading) {
            System.out.println("loading data ...   please wait");
            TimeUnit.SECONDS.sleep(2);
        }


        if (this.usersDB.containsKey(userName)) {
            System.out.println("user data found , loading game");
            List<Map> boxList = new ArrayList<Map>();
            Map userMap = (Map) this.usersDB.get(userName);

            boxList = (ArrayList<Map>) userMap.get("boxes");

            boxList.forEach((box) ->toBox(box));

        }

        dbBoard = board;
    }

    private void readData( ){
        System.out.println("reading data");
        FirebaseDatabase firebase = fireBaseService.getDb();
        DatabaseReference ref = firebase.getReference("save/user");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                reading = Boolean.TRUE;

                Map<String, UserDB> map  = (Map) dataSnapshot.getValue();
                System.out.println("map");
                System.out.println(map);
                setUsersDB(map);
                reading = Boolean.FALSE;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public Board getDbBoard() {
        return dbBoard;
    }

    public void setDbBoard(Board dbBoard) {
        this.dbBoard = dbBoard;
    }


    public String getJsonUsersDB() {
        return JsonUsersDB;
    }

    public void setJsonUsersDB(String jsonUsersDB) {
        JsonUsersDB = jsonUsersDB;
    }

    public Map<String, UserDB> getUsersDB() {
        return usersDB;
    }

    public void setUsersDB(Map<String, UserDB> usersDB) {
        this.usersDB = usersDB;
    }
}
