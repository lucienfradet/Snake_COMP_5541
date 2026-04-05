### The Snake

A simple snake game in java.
Using Threads and Java Swing to display the game.
As well as SQLite with a Login system to keep track of past games.

### How it looks:
![Snake Game Screenshot](./data/screenshot.png)

### How to run the project:

#### Requirements:
* Java runtime installed

#### How to play the game:
* Just download the SnakeGame.jar file
* Run it
* Start playing with the arrows keys or `WASD`

### Developper Notes:

#### using sqlite
```
project/
├── lib/
│   └── sqlite-jdbc-3.45.0.0.jar  ← Put the downloaded JAR here
├── db/
│   └── UserDB.java
└── Main.java
```
Compile:
```bash
javac -cp "lib/sqlite-jdbc-3.45.0.0.jar" db/UserDB.java Main.java
```
Run:
```bash
java -cp ".:lib/sqlite-jdbc-3.45.0.0.jar" Main
```
on Windows:
```bash
java -cp ".;lib/sqlite-jdbc-3.45.0.0.jar" Main
```

#### Using Maeven Test
- Run all tests:
```bash
mvn test
```
- Run only one test class:
```bash
mvn test -Dtest=UserDataTest
mvn test -Dtest=UserDBTest
mvn test -Dtest=UserDBIntegrationTest
```

- Run a single test method:
```bash
mvn test -Dtest="UserDBIntegrationTest#newUser_thenLogin_sameUserReturnedFromRealDB"
```
