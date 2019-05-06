package mike.test

import java.io.{BufferedWriter, File, FileWriter}
import java.nio.file.Files.isRegularFile

import mike.test.primes.Primes
import mike.test.primes.Primes._
import io.circe.syntax._
import io.circe.parser._

import scala.io.Source
import scala.util.Try

object Main {
  val defaultFilename = "/tmp/mike-test-primes-cache.json"

  def loadInitialCache(filename: String): PrimeCache = {
    if(isRegularFile(java.nio.file.Paths.get(filename))) {
      val infile = Source.fromFile(filename)
      val contents = infile.getLines.mkString
      infile.close()
      decode[PrimeCache](contents).getOrElse(Primes.emptyCache)
    }
    else Primes.emptyCache
  }

  def main(args: Array[String]) {
    // validate the arguments
    if(args.length < 1) {
      println("Please specify number to factor")
      System.exit(1)
    }

    val numberToFactorOpt = Try(BigInt(args(0))).toOption
    if(numberToFactorOpt.isEmpty || numberToFactorOpt.get <= 1) {
      println(s"Please enter a number greater than 1")
      System.exit(1)
    }
    val numberToFactor = numberToFactorOpt.get

    val filename = Try(args(1)).toOption.getOrElse(defaultFilename)

    val initialCache = loadInitialCache(filename)

    println(s"Prime factors of $numberToFactor:")
    val (primes, newCache) = Primes.getPrimes(numberToFactor, initialCache)
    println(primes)

    // update the cache for next time
    val writer = new BufferedWriter(new FileWriter(new File(filename)))
    writer.write(newCache.asJson.spaces2)
    writer.close()
  }
}