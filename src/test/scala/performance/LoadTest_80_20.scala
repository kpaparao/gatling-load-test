package performance

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class LoadTest_80_20 extends Simulation {

  val scn = scenario("FEP OD Load test")
    .feed(csv("urls_80_20.csv").circular)
    .exec(http("nocache_80_20").get("${url}")
      .check(status.is(200)))
      .pause(1, 2)

  setUp(scn
    .inject(
     ramp(20 users) over (20 seconds),
     constantRate(20 usersPerSec) during (10 minutes),
     rampRate(20 usersPerSec) to (1 usersPerSec) during (20 seconds)
    )
  )
}









