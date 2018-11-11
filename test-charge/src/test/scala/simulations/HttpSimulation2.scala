package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder.toActionBuilder


class HttpSimulation2 extends Simulation {
  before {
    println("***** My simulation is about to begin! *****")
  }

  after {
    println("***** My simulation has ended! ******")
  }


  val theHttpProtocolBuilder = http
    .baseURL("http://localhost:8083")

  val theScenarioBuilder = scenario("Jordan: I access to the order list of my restaurant: the soup restaurant")
    .exec(
      http("Order")
        .get("/orders/restaurants/soup%20restaurant/orders")
    )

  setUp(
    theScenarioBuilder.inject(atOnceUsers(100))
  ).protocols(theHttpProtocolBuilder)
}