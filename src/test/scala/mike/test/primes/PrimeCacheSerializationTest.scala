package mike.test.primes

import org.scalatest.{FunSpecLike, Matchers}
import io.circe.syntax._
import io.circe.parser._
import mike.test.primes.Primes.PrimeCache

class PrimeCacheSerializationTest extends FunSpecLike with Matchers {
  val primeCache = Map(2 -> Seq(2), 4 -> Seq(2, 2))
  val primeJson = """{"2":[2],"4":[2,2]}"""

  describe("conversion") {
    it("should write a PrimeCache as JSON") {
      primeCache.asJson.noSpaces shouldBe primeJson
    }
    it("parse JSON into a PrimeCache") {
      decode[PrimeCache](primeJson).right.get shouldBe primeCache
    }
  }

}
