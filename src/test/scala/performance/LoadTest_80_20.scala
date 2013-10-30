package performance

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
import bootstrap._

class LoadTest_80_20 extends Simulation {

  val httpProtocol = http.baseURL("http://od.fep-d.mtvi.com/od/feed")

  val chainTrue = exec(
    http("nocache_true")
      .get("${url}")
      .queryParam("nocache", "true")
      .check(status.is(200)))

  val chainFalse = exec(
    http("nocache_false")
      .get("${url}")
      .queryParam("nocache", "false")
      .check(status.is(200)))

  val scn = scenario("FEP OD Load test")
  feed(csv("urls_20k.csv").random)
  .exec(randomSwitch(
    80 -> chainFalse,
    20 -> chainTrue
  ))
    .pause(1, 2)

  setUp(scn
    .inject(
    ramp(5 users) over (10 seconds),
    constantRate(5 usersPerSec) during (10 seconds),
    rampRate(5 usersPerSec) to (1 usersPerSec) during (10 seconds)
  )
  )
    .protocols(httpProtocol)
}









