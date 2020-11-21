import io.gatling.core.Predef._
import io.gatling.http.Predef._

class EmployeeSimulation extends Simulation {

  val httpconf = http.baseUrl("https://localhost:8000")
    .header("Content-Type", "application/json")

  val getAllTodos = scenario("Get all employees")
    .exec(http("get all employees")
      .get("/")
      .check(status.is(200)))

  setUp(
    getAllTodos.inject(rampUsers(10) during (3))
  ).assertions(
    global.successfulRequests.percent.gt(95)
  ).protocols(httpconf)
}