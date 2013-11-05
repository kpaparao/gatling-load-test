package performance.img_srvc

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
import scala.concurrent.forkjoin.ThreadLocalRandom

class Img_perftest extends Simulation {

  val httpProtocol = http.baseURL("http://nick-intl.mtvnimages-d.mtvi.com")

  def getRand(i: Int) : String = ThreadLocalRandom.current.nextInt(i).toString

  val scn = scenario("Image Server Load test")
    .feed(csv("LIVE_Load_Testing.csv").circular)
    .exec(http("img_srvc").get("${url}")
    .queryParam("testParam", _ => getRand(100000))
    .check(status.is(200))
  )
  .pause(1, 2)

  setUp(scn
    .inject(
//        rampRate(1 usersPerSec) to (20 usersPerSec) during (30 seconds),
        constantRate(1 usersPerSec) during (10 seconds)//,
//        rampRate(20 usersPerSec) to (1 usersPerSec) during (30 seconds)
    )
  )
    .protocols(httpProtocol)
}
