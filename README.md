gatling-load-test
=================

Gatling load tests 

Running tests with Maven
========================

to run test one should run the following command from projects root folder:

mvn gatling:execute -Dgatling.simulationClass=[package.testClass]

for example -- mvn gatling:execute -Dgatling.simulationClass=performance.fep_od.LoadTest_nc_true
will run test with nocache parameter set to "true"

Test results
============

Test results generated in 'target/gatling/results' folder.
There a new folder named [TestName_timestamp] will be created.
Just open index.html file in any browser to see detales and graphs.

for example -- [projectRoot]/target/gatling/results/loadtest_nc_true_[timestamp]/index.html

Also after test is finished -- the path to test results will be available in terminal under the
test statistics table. So it can be copied from terminal.

Test setup
==========

One can change test setup by editing test script file located in src/test/scala folder.
Test scripts are combined in packages. There is one main package which contains load tests - 
it's called 'performance'. Tests for FEP OD located in main packaege in 'fep_od' folder.

All scripts have the scenario setup and performance setup.

To change server url one should change the value of 'httpProtocol' variable.
Server url should NOT end with "/".

Data for test is collected from feeders - a csv files wich described by
feed(csv("file_name.csv") construction. The file itself is located in [projectRoot]/src/test/resources/data
folder. First line in csv file should contain name of the variable which will be used later in test script.
For example in this tests variable name is set to 'url' in csv file and then called in script as "${url}".

Any additional query parameters are added using queryParam("param", "value") attribute in scenario.

Test setup is configured in 'setUp' section. 

By default test configured to run with the following setup:

setUp(scn
    .inject(
         rampRate(1 usersPerSec) to (20 usersPerSec) during (30 seconds), // this sets ramp up period. Gatling will ramp from 1 to 20 users per second for 30 seconds
         constantRate(20 usersPerSec) during (5 minutes), // here Gatling will start hitting the server with constant rate of 20 users per second for 5 minutes
         rampRate(20 usersPerSec) to (1 usersPerSec) during (30 seconds) // and here Gatling will ramp down from 20 to 1 user per second for 30 seconds. (Minimun users count should be 1 or greater)
    )
)

More info on Gatling-tool can be found at https://github.com/excilys/gatling/wiki/Gatling-2
