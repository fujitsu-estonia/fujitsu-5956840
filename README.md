# Hotel application
## Hosting the application locally
### English
- Download the projcet as a zip file and extract it to a fitting location
    - alternatively clone the repo to your computer via git
- Download Docker (https://docs.docker.com/get-docker/)
- Open Powershell (Windows) or Terminal (Mac/Linux)
- Navigate to the project's distribution folder via command line (eg. `cd .../fujitsu-5956840/distribution`)
- Run `docker compose up` (may take several minutes for initial boot-up to complete)
- Open http://localhost:8081/ from your browser
  - API specifications (Swagger) are accessible from http://localhost:8080/swagger-ui/index.html
- CTRL + C in Terminal/Powershell to close the application (run `docker compose up` to start the app again)

### Eesti
- Lae alla projekt zip failina ning paki ta lahti kuhugi sobivasse kohta
    - alternatiivselt klooni repo omale arvutisse, kasutades git-i
- Lae alla Docker (https://docs.docker.com/get-docker/)
- Ava Powershell (Windows) või Terminal (Mac/Linux)
- Navigeeri projekti "distribution" kausta käsurealt (eg. `cd .../fujitsu-5956840/distribution`)
- Jooksuta käsku `docker compose up` (võib võtta mitu minutit esmasel käivitusel)
- Ava http://localhost:8081/ oma brauseris
    - API spetsifikatsioonid (Swagger) on kättesaadaval aadressilt http://localhost:8080/swagger-ui/index.html
- CTRL + C Terminali või Powershell aknas katkestab rakenduse (rakenduse uuesti käivitamiseks jooksuta `docker compose up` käsureal)

## Live application
If hosting the application locally is not possible then a live application can be seen here:
**http://20.82.137.137:8081/**

## User Interface (UI)
The UI of this app is within the "frontend" folder. There is a README in there, explaining the general work ethics of developing the UI. Additionally, the unit tests' line coverage is in the "coverage" folder.
The unit tests' line coverage can also be seen in the frontend folder's README file.

## Java web server + DB
The Java Spring Boot app (with the H2 Memory DB) is within the "backend" folder. The README in there shows useful monitoring and testing links when after running the application.
