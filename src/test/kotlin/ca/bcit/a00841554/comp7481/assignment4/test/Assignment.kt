package ca.bcit.a00841554.comp7481.assignment4.test

import ca.bcit.a00841554.comp7481.assignment4.RsaUtils
import org.junit.Test
import kotlin.system.measureTimeMillis

class Assignment
{
    @Test
    fun _1a()
    {
        println(RsaUtils().computePrimeFactors(187))
        println(RsaUtils().computePrimeFactors(2173))
        println(RsaUtils().computePrimeFactors(4))
        println(RsaUtils().computePrimeFactors(85273))
        println(RsaUtils().computePrimeFactors(32559))
    }

    @Test
    fun _1b()
    {
        println(RsaUtils().computeEuclidExts(5,221).last().y)
        println(RsaUtils().computeEuclidExts(17,3937).last().y)
        println(RsaUtils().computeEuclidExts(3,437).last().y)
        println(RsaUtils().computeEuclidExts(101,34524).last().y)
        println(RsaUtils().computeEuclidExts(107,89565).last().y)
    }

    @Test
    fun _1c()
    {
        measureTimeMillis {RsaUtils().computeD(623,2173)}
        measureTimeMillis {RsaUtils().computeD(623,2173)}
        measureTimeMillis {RsaUtils().computeD(623,2173)}
        computeD(623,2173)
        computeD(17,55)
        computeD(3,55)
        computeD(70619,85273)
        computeD(14683,32559)
    }

    private fun computeD(e:Long,n:Long)
    {
        var computationResult:List<RsaUtils.DComputation>? = null
        val timeElapsed = measureTimeMillis {
            computationResult = RsaUtils().computeD(e,n)
        }
        println("($e, $n): $computationResult in $timeElapsed ms")
    }

    @Test
    fun _2()
    {
        println(RsaUtils().computeEuclidExts(7,160).last())
    }
}
