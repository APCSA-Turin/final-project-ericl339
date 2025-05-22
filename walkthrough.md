# 1. Project Overview - Game Guesser
This Java project generates 7 random games, alongside their genre, type, engine, company, platform, name, and summary, from the [IDGB API](https://www.igdb.com/api). The game allows the user to choose either completely random games or specified genres. It then chooses a correct game from that list and prompts the user to guess for it using hints that the game outputs.
# 2. Code Breakdown
```java
getBearer()
```
- Gets the bearer authorization code from a call to the API
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
- Connects to the main api with a post request
- Calls on the API with a specified command alongside the authorization code and the client id
- Returns the output of the API as a string
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
# 3. Features Implemented (Rubric Aligned)
dsf
# 4. Output Example
fsd
# 5. What I Learned
sfd