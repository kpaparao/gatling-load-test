package performance

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class LoadTest_nc_true_repeat extends Simulation {

  val httpProtocol = http.baseURL("http://od.fep-d.mtvi.com")
  //http://platform-feeds-008.1515.mtvi.com:8080

  val scn = scenario("FEP OD Load test")
    .feed(csv("urls-100.csv").random)
    .exec(http("nocache_true").get("${url}")
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









