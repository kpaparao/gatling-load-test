package performance.fep_od

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class LoadTest_nc_false_repeat extends Simulation {

  val httpProtocol = http.baseURL("http://platform-feeds-005.811.mtvi.com")
//http://od.fep-d.mtvi.com
//http://platform-feeds-008.1515.mtvi.com:8080

  val scn = scenario("FEP OD Load test")
    .feed(csv("urls-100.csv").circular)
    .exec(http("nocache_false").get("${url}")
    .queryParam("nocache", "false")
    .check(status.is(200)))
    .pause(1, 2)

  setUp(scn
    .inject(
    ramp(20 users) over (30 seconds),
    constantRate(20 usersPerSec) during (5 minutes),
    rampRate(20 usersPerSec) to (1 usersPerSec) during (30 seconds)
  )
  )
    .protocols(httpProtocol)
}
