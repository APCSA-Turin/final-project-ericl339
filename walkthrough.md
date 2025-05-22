# 1. Project Overview - Game Guesser
This Java project generates 7 random games, alongside their genre, type, engine, company, platform, name, and summary, from the [IDGB API](https://www.igdb.com/api). The game allows the user to choose either completely random games or specified genres. It then chooses a correct game from that list and prompts the user to guess for it using hints that the game outputs.
# 2. Code Breakdown
```java
getBearer()
```
- Makes an HTTP POST request to the API to get the bearer code
- Reads the full JSON response into a StringBuilder
- Returns the edited JSON string to return a usable bearer code format
```java
getClientId()
```
- Returns the client id
```java
getClientSecret()
```
- Returns the client secret
```java
String getData(String link, String bearer, String clientId, String work)
```
- Makes an HTTP POST request to the main API
- Calls on the API with a specified command alongside the authorization code and the client id
- Reads the full JSON response into a StringBuilder
- Returns the edited JSON string to return a usable bearer code format
```java
GameInfo(ArrayList<String> genreName, String gameType, ArrayList<String> gameEngineName, ArrayList<String> companyName, ArrayList<String> platformName, String name, String summary)
```
- Creates an object that contains all the information from a game
```java
getGenreName()
```
- Returns the genre name
```java
getGameType()
```
- Returns the game type
```java
getGameEngineName()
```
- Returns the game engine
```java
getCompanyName()
```
- Returns the company name of the game
```java
platformName()
```
- Returns the platform name
```java
getName()
```
- Returns the name of the game
```java
getSummary()
```
- Returns the summary of the game
```java
main(String[] args)
```
- The main logic of the game
- Starts off with a call on getBearer() to get the bearer authorization
- Introduces the game and prompting the user to choose their guessing mode
- Calls on String getData(String link, String bearer, String clientId, String work) to get the total game count depending on the user's mode and then creating a random offset to randomize the games
- Calls on java String getData(String link, String bearer, String clientId, String work) to generate 7 random games
- Iterates through each JSONObject of the JSONArray and adds the game information to a GameInfo ArrayList
- If the user inputs a specific franchise, checks to see if it is actually a franchise by calling .size() on the ArrayList
- Picks out a correct game from the ArrayList and displays the whole ArrayList to the user
- Displays hints for the user through game info
- Prompts the user to guess the game using numbers
- Depending on the user's guess, checks off if a certain genre or engine matches to the correct game and displays it
- After user gets it correct, add their round points to total points
- Displays a loading section to prevent error 429
- Prompts the user to continue playing or end the game
- Displays the user's total points, total rounds, and their average points per round
# 3. Features Implemented (Rubric Aligned)
✔ Base Project (88%)
- Uses an external API (IGDB API)
- Uses 4 Java classes
- Parses JSON response using basic string matching
- Displays more than 3 meaningful information

✔ Basic Statistics or Machine Learning (6%)
- Performs an average using local data

✔ Filter/Sort Data (+2%)
- Game franchise is filtered
- Genre, game type, game engine, game company, game platform, game name, and game summary are sorted

Total = 96%
# 4. Output Example
sfd![Screenshot 2025-05-22 185231](https://github.com/user-attachments/assets/6d3f97d3-ec27-4795-b75f-42acd6ab4eb8)
# 5. What I Learned