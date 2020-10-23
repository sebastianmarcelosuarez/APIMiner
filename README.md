# APIMiner
This is a backend API miner.

It uses Jersey for API configuration.

The process was build in two faces: 
1) the game itself, located in : https://github.com/sebastianmarcelosuarez/minesweepbeta, this is a standalone game for one user on local machine
2) API-miner: minesweepbeta transformed to API.

For server, i tried Glassfish first, but i couldnt make it work on my machine, so i switched to 
Apache Tomcat and worked on first try

To save information, Firebase DB was used:
https://console.firebase.google.com/project/minesweeper-920a1/database/minesweeper-920a1/data

I had to modify my logic to work with this DB: this is an asyncronous db and the code needs to work 
with that concept.

For API Documentation Postman was used:
https://web.postman.co/collections/5519184-a01af009-e60f-4b7c-9e20-0ac689898278?version=latest&workspace=14af425f-9cd9-4f5e-8207-1c9499cf5410

I tried to use ui-swagger but faced two mayor issues 
1) Library conflict (solved, had to clean pom.xml)
2) CORS issue. Could not solved that one after applying different solutions. Next time I would use Spring and not Jersey

Requeriments:
1)Design and implement a documented RESTful API for the game (think of a mobile app for your API)
    Created, firebase used for that mobile concept 
2)Implement an API client library for the API designed above. Ideally, in a different language, of your preference, to the one used for the API
    API created. I used java
3)When a cell with no adjacent mines is revealed, all adjacent squares will be revealed (and repeat)
    Algorithm created:   Board.unHiddenBoxes(int, int);
4)Ability to 'flag' a cell with a question mark or red flag
    Ability created (right click) although i forgot the question mark on the enum. Easy to add
5)Detect when game is over
    Algorithm created: Board.checkWin()
6)Persistence
    firebase used
7)Time tracking
    Not implemented. I forgot this one. Although i only need one more field on user to save the time when 
    a game is created and then calculate the difference when the game finish (win or lose)
8)Ability to start a new game and preserve/resume the old ones
    A user may only have one game active
9) Ability to select the game parameters: number of rows, columns, and mines
    Due time constraints, i only generated a 3x3 field, but all this things are easily to modify 
    when the user generates the game
9) Ability to support multiple users/accounts
    Multiple accounts via username key