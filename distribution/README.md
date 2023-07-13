## Hosting the application

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
