package performance

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
import bootstrap._

class LoadTest_nc_false extends Simulation {

  val httpProtocol = http.baseURL("http://od.fep-d.mtvi.com/od/feed")

  val scn = scenario("FEP OD Load test")
    .feed(csv("urls_20k.csv").random)
    .exec(http("nocache_false").get("${url}")
    .check(status.is(200)))
    .pause(1, 2)

  setUp(scn
    .inject(
    ramp(20 users) over (20 seconds),
    constantRate(20 usersPerSec) during (5 minutes),
    rampRate(20 usersPerSec) to (1 usersPerSec) during (20 seconds)
  )
  )
    .protocols(httpProtocol)
}









