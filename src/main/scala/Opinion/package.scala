import Comete._
import common._
import scala.collection.parallel.CollectionConverters._

package object Opinion {

  type SpecificBelief = Vector[Double]
  type GenericBeliefConf = Int => SpecificBelief
  type AgentsPolMeasure = (SpecificBelief, DistributionValues) => Double

  def uniformBelief(nags:Int) :SpecificBelief={
    Vector.tabulate(nags) ( ( i :Int) =>
      (i+1).toDouble/nags.toDouble )
  }

  def midlyBelief(nags:Int): SpecificBelief = {
    val middle = nags /2
    Vector.tabulate(nags) ( ( i : Int ) =>
      if ( i < middle)math.max (0.25-0.01*( middle-i-1),0)
      else math.min(0.75-0.01*(middle-i),1))
  }

  def allExtremeBelief(nags:Int):SpecificBelief = {
    val middle = nags /2
    Vector.tabulate(nags) ( ( i :Int ) =>
      if ( i < middle ) 0.0 else 1.0)
  }

  def allTripleBelief(nags: Int): SpecificBelief= {
    val oneThird= nags/3
    val twoThird = (nags/3) * 2
    Vector.tabulate(nags)((i : Int) =>
      if (i < oneThird) 0.0
      else if (i >= twoThird) 1.0
        else 0.5)
  }

  def consensusBelief(b:Double)(nags: Int): SpecificBelief= {
    Vector.tabulate(nags)(( i:Int) => b)
  }

  def rho(alpha: Double, beta: Double): AgentsPolMeasure = {

    def buildBounds(dv: DistributionValues): Vector[(Double, Double)] = {
      val k = dv.length
      val mids = (0 until k - 1).map { i => (dv(i) + dv(i + 1)) / 2.0}.toVector

      val bounds = Vector(0.0) ++ mids ++ Vector(1.0)
      bounds.zip(bounds.tail)
    }

    def countInterval(body: SpecificBelief, interval: (Double, Double)): Int = {
      body.count(x =>
        if (interval._2 == 1.0) interval._1 <= x && x <= interval._2
        else interval._1 <= x && x < interval._2
      )
    }

    def pi_b(entities: SpecificBelief, distribution: DistributionValues): Vector[Double] = {
      val bounds = buildBounds(distribution)
      val counts = bounds.map(interval => countInterval(entities, interval))
      val totalAgents = entities.length.toDouble

      counts.map(_.toDouble / totalAgents)
    }

    val medidaNorm = normalizar(rhoCMTGen(alpha, beta))

    (agents: SpecificBelief, y: DistributionValues) => {
      val pi = pi_b(agents, y)
      medidaNorm((pi, y))
    }
  }
}