# Hotel application
## Hosting the application locally
- Download Docker Desktop (https://www.docker.com/products/docker-desktop/)
- Open Powershell (Windows) or Terminal (Mac/Linux)
- Navigate to the project's distribution file via command line (eg. `cd .../fujitsu-5956840/distribution`
- Run `docker compose up` (may take several for initial boot-up to complete)
- open http://localhost:8081/ from your browser
- CTRL + C in Terminal/Powershell to close the application (run `docker compose up` to start the app again)

## Live application
If hosting the application locally is not possible then a live application can be seen here:
**http://20.82.137.137:8081/**

## User Interface (UI)
The UI of this app is within the "frontend" folder. There is a README in there, explaining the general work ethics of developing the UI. Additionally, the unit tests' line coverage is in the "coverage" folder.
The unit tests' line coverage can also be seen in the frontend folder's README file.

## Java web server + DB
The Java Spring Boot app (with the H2 Memory DB) is within the "backend" folder. The README in there shows useful monitoring and testing links when after running the application.
