package performance

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
import bootstrap._

class LoadTest_nc_true extends Simulation {

  val httpProtocol = http.baseURL("http://od.fep-d.mtvi.com/od/feed")

  val chainTrue = feed(csv("urls_no_host.csv").random)
  exec(
    http("nocache_true")
      .get("${url}")
      .queryParam("nocache", "true")
      .check(status.is(200)))

  val chainFalse = feed(csv("urls_no_host.csv").random)
  exec(
    http("nocache_false")
      .get("${url}")
      .queryParam("nocache", "false")
      .check(status.is(200)))

  val scn = scenario("FEP OD Load test")
  exec(randomSwitch(
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
//    .protocols(httpProtocol)
}









