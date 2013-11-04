package performance.fep_od

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class LoadTest_nc_true extends Simulation {

  val httpProtocol = http.baseURL("http://od.fep-d.mtvi.com/od/feed")

  val scn = scenario("FEP OD Load test")
    .feed(csv("urls_20k.csv").random)
    .exec(http("nocache_true").get("${url}")
    .queryParam("nocache", "true")
    .check(status.is(200))
    .check(bodyString.transform(_.map(_.size)).matchWith(io.gatling.core.check.Matchers.greaterThan, 100))
  )
    .pause(1, 2)

  setUp(scn
    .inject(
    rampRate(1 usersPerSec) to (20 usersPerSec) during (30 seconds),
    constantRate(20 usersPerSec) during (20 seconds),
    rampRate(20 usersPerSec) to (1 usersPerSec) during (30 seconds)
  )
  )
    .protocols(httpProtocol)
}








