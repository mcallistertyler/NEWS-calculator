# NEWS-score

This is a documentation page for the Case Assignment for the NEWS Calculation service's backend and frontend components.

The complete service is represented by two microservices, a Java Spring Boot Server that hosts the API and a Vite Express Server written in React
that interacts with the Java API. Both services are separated in this way to represent how I would have created the service in a `production` environment. 

Both services would be standalone microservices running within a kubernetes cluster on a docker image. The frontend component would communicate over kubernetes, calling it via its pod name within the cluster.

The Java back-end service is written in Java 21 and built with Gradle 8.13. A gradle wrapper is included to improve simplicity of running the service.

The React front-end service is written in React 18 and was created via the installation setup from [Vite Express Server](https://github.com/szymmis/vite-express?tab=readme-ov-file)


## Running Locally

### Prerequisites

The following should be installed to run these services locally:

    - npm version: `10.9.2`
    - node version: `v22.13.0`
    - java version: `21.0.2`



The frontend can be run locally by running:
```bash
npm run dev
```
Within the `NEWS-score-calculator-webapp` directory. It will run on http://localhost:3000 by default.

And the backend can be run locally by running:
```bash
./gradlew bootRun
```
Within the `NEWS-score-calculator-server` directory. It will run on http://localhost:8080 by default. This is the assumed address the front-end will look for when making requests.

To test the entirety of the required user-flow both services must be run simultaneously.


## API

A basic swagger page for the API can be found at:
http://localhost:8080/swagger-ui/index.html#/api-controller/calculateNewsValue


## Current Limitations and Next Steps

Some improvements could be made both technically and conceptually, such as:

- The frontend component does not handle error handling as elegantly as I would like. Currently it receives an error response but is not given enough context to programmatically give the user a more detailed error response.
- The frontend component is also using default HTML form logic for reset and validation functionality. This works for simple forms but it would perhaps be better to write custom logic if the design wants to prioritize giving the user a more specialised experience. It also wastes an API call since the user won't know a value is outside of a range until they submit the form. 
- More accessibility features could be added, such as keyboard navigation which I haven't tested very thoroughly or screen readers which I didn't test at all.
- The backend swagger documentation is missing error responses so any potential integrator wouldn't be able to handle those without either asking me or diving into the codebase themselves.

Next steps for such a service before it would be production ready are as follows:

- Observability metrics for API calls would be beneficial in both detecting errors and measuring the number of requests and latency of responses.
- Health endpoints for determining the status of both services while they are deployed
