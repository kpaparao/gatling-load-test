package performance.fep_od

import scala.concurrent.duration._
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class LoadTest_nc_true extends Simulation {

  val httpProtocol = http.baseURL("http://platform-feeds-005.811.mtvi.com:8080")

  val scn = scenario("FEP OD Load test")
    .feed(csv("urls-100.csv").circular)
    .exec(http("nocache_true").get("${url}")
    .queryParam("nocache", "true")
    .check(status.is(200))
    .check(bodyString.transform(_.map(_.size)).matchWith(io.gatling.core.check.Matchers.greaterThan, 100))
  )
    .pause(1, 2)

  setUp(scn
    .inject(
    rampRate(1 usersPerSec) to (20 usersPerSec) during (30 seconds),
    constantRate(20 usersPerSec) during (5 minutes),
    rampRate(20 usersPerSec) to (1 usersPerSec) during (30 seconds)
  )
  )
    .protocols(httpProtocol)
}









