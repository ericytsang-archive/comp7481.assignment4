package ca.bcit.a00841554.comp7481.assignment4.test

import ca.bcit.a00841554.comp7481.assignment4.RsaUtils
import org.junit.Test
import kotlin.system.measureTimeMillis

class Assignment
{
    @Test
    fun _1a()
    {
        compute1AInstance(187)
        compute1AInstance(2173)
        compute1AInstance(4)
        compute1AInstance(85273)
        compute1AInstance(32559)
    }

    private fun compute1AInstance(product:Long)
    {
        val primeFactors = RsaUtils().computePrimeFactors(product)
        println("($product): $primeFactors")
    }

    @Test
    fun _1b()
    {
        compute1BInstance(5,221)
        compute1BInstance(17,3937)
        compute1BInstance(3,437)
        compute1BInstance(101,34524)
        compute1BInstance(107,89565)
    }

    private fun compute1BInstance(e:Long,totient:Long)
    {
        var d = RsaUtils().computeEuclidExts(5,221).last().y
        if (d < 0) d += totient
        println("($e, $totient): $d")
    }

    @Test
    fun _1c()
    {
        measureTimeMillis {RsaUtils().computeD(623,2173)}
        measureTimeMillis {RsaUtils().computeD(623,2173)}
        measureTimeMillis {RsaUtils().computeD(623,2173)}
        compute1CInstance(623,2173)
        compute1CInstance(17,55)
        compute1CInstance(3,55)
        compute1CInstance(70619,85273)
        compute1CInstance(14683,32559)
    }

    private fun compute1CInstance(e:Long,n:Long)
    {
        val computationResult:List<RsaUtils.DComputation> = RsaUtils().computeD(e,n)
        val timeElapsed = (1..10)
            .map {measureTimeMillis {RsaUtils().computeD(e,n)}}
            .average()
        println("($e, $n): $computationResult in $timeElapsed ms")
    }

    @Test
    fun _2()
    {
        println(RsaUtils().computeEuclids(7,160).joinToString("\n"))
        println(RsaUtils().computeEuclidExts(7,160).joinToString("\n"))
    }
}
