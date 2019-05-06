package mike.test.primes

import io.circe.{KeyDecoder, KeyEncoder}
import mike.test.primes.Primes.PrimeCache

trait Primes {
  private def candidates(from: BigInt, to: BigInt): Stream[BigInt] =
    if(from > to) Stream.empty else from #:: candidates(from + 1, to)

  /** Recursive prime factorization, kicked off by getPrimes */
  private def getPrimesRec(i: BigInt, list: Seq[BigInt]): Seq[BigInt] = {
    val optNextFactor = candidates(2, i).find(p => i % p == 0)
    optNextFactor.map { nextFactor =>
      getPrimesRec(i / nextFactor, list :+ nextFactor)
    }.getOrElse(list)
  }

  /**
    * Get a number's prime factors
    * @param i the number to factor
    * @param cache a cache with previously factored primes
    * @return a tuple containing the prime factors, and an updated cache for
    *         use in future calls
    */
  def getPrimes(i: BigInt, cache: PrimeCache): (Seq[BigInt], PrimeCache) =
    if(cache.contains(i)) (cache(i), cache)
    else {
      val primes = getPrimesRec(i, Nil)
      (primes, cache.updated(i, primes))
    }
}

object Primes extends Primes {
  type PrimeCache = Map[BigInt, Seq[BigInt]]
  val emptyCache: PrimeCache = Map.empty
  implicit val primeCacheKeyDecoder: KeyDecoder[BigInt] = (key: String) => Some(BigInt(key))
  implicit val primeCacheKeyEncoder: KeyEncoder[BigInt] = (bi: BigInt) => bi.toString(10)
}