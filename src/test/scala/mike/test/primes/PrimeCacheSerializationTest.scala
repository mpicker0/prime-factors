package mike.test.primes

import org.scalatest.{FunSpecLike, Matchers}
import io.circe.syntax._
import io.circe.parser._
import mike.test.primes.Primes._

class PrimeCacheSerializationTest extends FunSpecLike with Matchers {
  val primeCache: PrimeCache = Map(
    BigInt(2) -> Seq(BigInt(2)),
    BigInt(4) -> Seq(BigInt(2), BigInt(2))
  )
  val primeJson = """{"2":[2],"4":[2,2]}"""

  describe("conversion") {
    it("should write a PrimeCache as JSON") {
      primeCache.asJson.noSpaces shouldBe primeJson
    }
    it("parse JSON into a PrimeCache") {
      decode[Map[BigInt, Seq[BigInt]]](primeJson).right.get shouldBe primeCache
    }
  }

}
