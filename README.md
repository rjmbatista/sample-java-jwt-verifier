
# Java JWT Verification Sample

### Java Application
Set the client values in the `src/main/webapp/WEB-INF/web.xml` file.

```xml
<context-param>
  <param-name>com.auth0.domain</param-name>
  <param-value>https://{YOUR_AUTH0_DOMAIN}/</param-value>
</context-param>
<context-param>
  <param-name>com.auth0.clientId</param-name>
  <param-value>{YOUR_AUTH0_CLIEND_ID}</param-value>
</context-param>
<context-param>
  <param-name>com.auth0.clientSecret</param-name>
  <param-value>{YOUR_AUTH0_CLIENT_SECRET}</param-value>
</context-param>
<context-param>
  <param-name>com.auth0.audience</param-name>
  <param-value>{YOUR_AUTH0_AUDIENCE}</param-value>
</context-param>
```

### Running the sample

Open a terminal, go to the project root directory and run the following command:

```bash
./gradlew clean appRun
```

The server will be accessible on https://localhost:8080/portal/home
