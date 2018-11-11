package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder.toActionBuilder


class HttpSimulation1 extends Simulation {
  before {
    println("***** My simulation is about to begin! *****")
  }

  after {
    println("***** My simulation has ended! ******")
  }


  val theHttpProtocolBuilder = http
    .baseURL("http://localhost:8080")

  val theScenarioBuilder = scenario("Gail: Get all categories of meals")
    .exec(
      http("Meal")
        .get("/catalog/rest/catalog/meals")
    )

  setUp(
    theScenarioBuilder.inject(atOnceUsers(10000))
  ).protocols(theHttpProtocolBuilder)
}