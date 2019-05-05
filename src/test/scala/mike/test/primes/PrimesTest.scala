package mike.test.primes

import org.scalatest.{FunSpecLike, Matchers}
import mike.test.primes.Primes.emptyCache

class PrimesTest extends FunSpecLike with Matchers {

  describe("getPrimes") {
    it("should find the prime factors of a number and add to the cache") {
      val (primes, cache) = Primes.getPrimes(4, emptyCache)
      primes shouldBe Seq(2, 2)
      cache shouldBe Map(4 -> Seq(2, 2))
    }

    it("should add a new entry to the cache passed in") {
      val (_, cache1) = Primes.getPrimes(4, Map.empty)
      val (primes, cache2) = Primes.getPrimes(18, cache1)

      primes shouldBe Seq(2, 3, 3)
      cache2 shouldBe Map(
        4 -> Seq(2, 2),
        18 -> Seq(2, 3, 3)
      )
    }

    it("should work with a prime number") {
      val (primes, cache) = Primes.getPrimes(17, emptyCache)
      primes shouldBe Seq(17)
      cache shouldBe Map(17 -> Seq(17))
    }

    it("should work with numbers bigger than Int.MaxValue") {
      val bigNum = Math.pow(2, 32).toLong
      val expectedFactors = Seq.fill(32)(2)
      val (primes, cache) = Primes.getPrimes(bigNum, emptyCache)
      primes shouldBe expectedFactors
      cache shouldBe Map(bigNum -> expectedFactors)
    }
  }

}
