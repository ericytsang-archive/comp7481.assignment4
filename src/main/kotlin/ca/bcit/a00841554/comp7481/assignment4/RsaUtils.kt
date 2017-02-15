package ca.bcit.a00841554.comp7481.assignment4

import java.util.ArrayList
import java.util.TreeSet
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.RejectedExecutionException
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class RsaUtils
{
    fun computePrimeFactors(product:Long):Set<Pair<Long,Long>>
    {
        // compute all primes up to the product
        val primes = computePrimesUpTo(product)

        // try to divide product by prime numbers...integer results are coprime
        return primes
            .filter {product%it == 0L}
            .map {listOf(product/it,it)}
            .filter {it.first() in primes}
            .map {it.sorted()}
            .map {it[0] to it[1]}
            .toSet()
    }

    fun computeCoprimes(product:Long):Set<Pair<Long,Long>>
    {
        return computePrimeFactors(product)
            .filter {isCoprime(it.first,it.second)}
            .toSet()
    }

    fun computePrimesUpTo(maxNumber:Long):Set<Long>
    {
        return computePrimesUpTo.computePrimesUpTo(maxNumber)
    }

    private val computePrimesUpTo = object
    {
        private val primes = TreeSet<Long>()

        private var maxTentativePrimeChecked:Long = 1

        private val mutex = ReentrantLock()

        fun computePrimesUpTo(n:Long):Set<Long> = mutex.withLock()
        {
            val threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())
            while (n > maxTentativePrimeChecked)
            {
                val tentativePrime = ++maxTentativePrimeChecked
                threadPool.execute()
                {
                    if ((2..tentativePrime-1).all {tentativePrime%it != 0L})
                    {
                        synchronized(primes) {primes += tentativePrime}
                    }
                }
            }
            threadPool.shutdown()
            threadPool.awaitTermination(Long.MAX_VALUE,TimeUnit.DAYS)
            return primes.takeWhile {it <= n}.toSet()
        }
    }

    data class Euclid(val dividend:Long,val divisor:Long,val remainder:Long)

    data class EuclidExt(val x:Long,val y:Long,val gcd:Long)

    fun computeEuclids(_a:Long,_b:Long):List<Euclid>
    {
        val result = ArrayList<Euclid>()
        var a = Math.max(_a,_b)
        var b = Math.min(_a,_b)
        var c:Long
        while (true)
        {
            c = a%b
            result += Euclid(a,b,c)
            if (c == 0L) break
            a = b
            b = c
        }
        return result
    }

    fun computeEuclidExts(a:Long,b:Long):List<EuclidExt>
    {
        val euclids = computeEuclids(a,b)
        val gcd = euclids.last().divisor
        return euclids.dropLast(1).foldRight(emptyList<EuclidExt>())
        {
            curr,accumulator ->
            if (accumulator.isEmpty())
            {
                listOf(EuclidExt(1,-(curr.dividend/curr.divisor),gcd))
            }
            else
            {
                val y = (gcd-curr.dividend*accumulator.last().y)/curr.divisor
                val x = (gcd-y*curr.divisor)/curr.dividend
                accumulator+listOf(EuclidExt(x,y,gcd))
            }
        }
    }

    fun computeEuclid(a:Long,b:Long):Euclid
    {
        return computeEuclids(a,b).last()
    }

    fun isCoprime(a:Long,b:Long):Boolean
    {
        return computeEuclid(a,b).divisor == 1L
    }

    fun computeD(e:Long,n:Long):List<DComputation>
    {
        require(isCoprime(e,n))
        val tentativeTotients = computeCoprimes(n)
            .filter {Math.max(it.first,it.second) < e}
            .filter {(it.first-1)*(it.second-1) > e}
            .filter {isCoprime((it.first-1)*(it.second-1),e)}

        return tentativeTotients.map()
        {
            val p = it.first
            val q = it.second
            val totient = (it.first-1)*(it.second-1)
            val d = computeEuclidExts(e,totient).last().y
                .let {d -> if (d < 0) d+totient else d}
            DComputation(p,q,totient,d)
        }
    }

    data class DComputation(val p:Long,val q:Long,val totient:Long,val d:Long)
}
