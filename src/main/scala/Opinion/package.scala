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


  def rho (alpha : Double , beta : Double ) : AgentsPolMeasure= {
    (agents: SpecificBelief , y:DistributionValues) => {

      def buildBounds(dv:DistributionValues): Vector[(Double,Double)] = {
        val k = dv.length
        val mids = (0 until k - 1).map { i =>
          (dv(i) + dv(i + 1)) / 2.0
        }.toVector
        val bounds = Vector(0.0) ++ mids ++ Vector(1.0)
        bounds.zip(bounds.tail)
      }

      def countInterval(body:SpecificBelief,tuple:(Double,Double)): Int = {
          body.count(x =>
            if (tuple._2 == 1.0)
              tuple._1 <= x && x <= tuple._2
            else
              tuple._1 <= x && x < tuple._2
          )
      }

      def pi_b(entities:SpecificBelief,distribution:DistributionValues):Vector[Double] = {

        val bounds = buildBounds(distribution)

        val count = bounds.map(interval=>countInterval(entities,interval))

        val totalAgents = entities.length.toDouble

        count.map(_/totalAgents)

      }

      val pi = pi_b(agents,y)

      def rhoAux(p: Double): Double = {
        pi.zip(y).map { case (piI, yI) => math.pow(piI, alpha) * math.pow(math.abs(yI - p), beta)}.sum
      }
      val pOptimo = min_p(rhoAux, 0.0, 1.0, 1e-6)
      rhoAux(pOptimo)

    }
  }

}