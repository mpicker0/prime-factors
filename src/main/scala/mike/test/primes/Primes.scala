package mike.test.primes

import mike.test.primes.Primes.PrimeCache

trait Primes {
  private def candidates(from: Long, to: Long): Stream[Long] =
    if(from > to) Stream.empty else from #:: candidates(from + 1L, to)

  /** Recursive prime factorization, kicked off by getPrimes */
  private def getPrimesRec(i: Long, list: Seq[Long]): Seq[Long] = {
    val optNextFactor = candidates(2L, i).find(p => i % p == 0)
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
  def getPrimes(i: Long, cache: PrimeCache): (Seq[Long], PrimeCache) =
    if(cache.contains(i)) (cache(i), cache)
    else {
      val primes = getPrimesRec(i, Nil)
      (primes, cache.updated(i, primes))
    }
}

object Primes extends Primes {
  type PrimeCache = Map[Long, Seq[Long]]
  val emptyCache: PrimeCache = Map.empty
}