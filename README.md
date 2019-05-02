# Prime factorization

This program finds and prints the prime factors of a given number.  It also
maintains a cache of previously factored numbers which it uses to make repeated
lookups faster.

## Requirements

You must have [sbt](https://www.scala-sbt.org/) installed and in your path.

## Building and running

To compile and run the tests:

    sbt test

To run the program and determine the prime factors of a number, use the
syntax

    sbt "run <number> [cache-file]"

where _number_ is the number you want to factor, and _cache-file_ is a file
that will cache the results between runs.  _cache-file_ defaults to
`/tmp/mike-test-primes-cache.json` if not specified.  If the file does not
exist it will be created; if it is invalid JSON it will be rewritten.

An example:

    sbt "run 18"

### Easier interaction

Because `sbt` is slow to start, it is easier to run interactively if you want
to run many times in a row.  To do this, first start `sbt`:

    sbt

Then, at the `sbt>` prompt, enter the arguments:

    18

After the program runs, you will still be at the `sbt>` prompt and can enter
additional numbers.

### Viewing the cache

Look at the contents of the cache file (by default,
`/tmp/mike-test-primes-cache.json`) to see new numbers and their prime factors
get added each time you run the program. 

## Design

The design is very basic in the spirit of not overengineering the program.
The prime factorization algorithm is using trial division and is not very
efficient, but this can be mitigated by the cache.

The cache is just a `Map[Int, Seq[Int]]` and maintained as JSON on the
filesystem so it survives between runs of the program.  I wanted a simple way
to handle persistence, so a file made sense, and
[Circe](https://github.com/circe/circe) was used to handle converting to/from
JSON.  A real application would use a more robust persistence method.

Tests are provided for the prime factorization as well as JSON conversion.

## Future enhancements

There are currently several limitations but these can be overcome given more
time:

* Integer range:  the program is limited to values in the `Int` range, which
  has a maximum of 2^32 - 1.  I considered using `BigInt` but this was not
  straightforward to encode/decode with Circe.  I also considered `Long` but
  Scala's range type (`2 to i`) is limited by the size of `Int`.  A possible
  solution would be to use a `Stream` instead of a range.
* The algorithm is not efficient.  It would be more efficient to divide the
  number to be factored by prime numbers, rather than trying everything from 2
  to the number.  However, it performs well enough given the other
  limitations.
* The user interface could be improved.   
     