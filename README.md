# Spring Boot WebSocket Chat

## Description
A real-time chat application using Spring Boot on the backend and a static JavaScript frontend. The backend exposes a SockJS/STOMP WebSocket endpoint and a REST endpoint to fetch message history.

## Technologies
- Java (Spring Boot)
- JavaScript (frontend: `src/main/resources/static/chat.js`)
- Maven

## Requirements
- Java 11 or newer
- Maven
- IntelliJ IDEA (Windows)

## Run (Windows / IntelliJ / terminal)
From the project root:
- `mvn clean package`
- `mvn spring-boot:run`
- Or: `java -jar target/your-app-name.jar`

Open `http://localhost:8080/` in a browser (adjust path if frontend is served from a different location).

## Key Implementation Details
- WebSocket (SockJS) endpoint: `\`/ws\``
- Client sends messages to: `\`/app/chat\``
- Client subscriptions:
  - `\`/user/queue/messages\`` — user-specific queue for private messages
  - `\`/topic/public\`` — topic for public messages
- REST endpoint for history: `GET /messages?with={username}` — returns JSON message history
- Frontend JavaScript: `\`src/main/resources/static/chat.js\`` handles STOMP connection, subscribes, sends messages, and loads history when a recipient is set

## Testing
- Open two browsers or devices and connect with different usernames.
- Send public and private messages and verify messages arrive on:
  - `\`/topic/public\`` for public messages
  - `\`/user/queue/messages\`` for private messages
- Verify REST history via `\`/messages?with={user}\``

## References
- Official Apache Maven documentation: `https://maven.apache.org/guides/index.html`
- Spring Boot Maven Plugin: `https://docs.spring.io/spring-boot/3.5.7/maven-plugin`
- WebSocket guide: `https://spring.io/guides/gs/messaging-stomp-websocket/`
- Spring Boot WebSocket reference: `https://docs.spring.io/spring-boot/3.5.7/reference/messaging/websockets.html`