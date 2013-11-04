package performance.img_srvc

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
import scala.util.Random

class Img_Srvc_Perftest extends Simulation {

  val httpProtocol = http.baseURL("http://nick-intl.mtvnimages-d.mtvi.com")
  val rand = new Random(System.currentTimeMillis())

  val scn = scenario("Image Server Load test")
    .feed(csv("LIVE_Load_Testing.csv").random)
    .exec(http("img_srvc").get("${url}")
    .queryParam("testParam", rand.nextInt(1000).toString)
    .check(status.is(200))
    //     .check(bodyString.transform(_.map(_.size)).matchWith(io.gatling.core.check.Matchers.greaterThan, 100))
  )
    .pause(1, 2)

  setUp(scn
    .inject(
//    rampRate(1 usersPerSec) to (20 usersPerSec) during (30 seconds),
    constantRate(5 usersPerSec) during (20 seconds)//,
//    rampRate(20 usersPerSec) to (1 usersPerSec) during (30 seconds)
  )
  )
    .protocols(httpProtocol)
}









