package mike.test.primes

import mike.test.primes.Primes.PrimeCache

trait Primes {
  /** Recursive prime factorization, kicked off by getPrimes */
  private def getPrimesRec(i: Int, list: Seq[Int]): Seq[Int] = {
    val optNextFactor = (2 to i).find(p => i % p == 0)
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
  def getPrimes(i: Int, cache: PrimeCache): (Seq[Int], PrimeCache) =
    if(cache.contains(i)) (cache(i), cache)
    else {
      val primes = getPrimesRec(i, Nil)
      (primes, cache.updated(i, primes))
    }
}

object Primes extends Primes {
  type PrimeCache = Map[Int, Seq[Int]]
  val emptyCache: PrimeCache = Map.empty
}