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

      def countInterval(entities:SpecificBelief,tuple:(Double,Double)): Int = {
          entities.count(x => tuple._1 <= x && x < tuple._2 )
      }




      def rhoAux(p: Double): Double = pi.zip(y).map { case (piI, yI) => math.pow(piI, alpha) * math.pow(math.abs(yI - p), beta)}.sum
      val pOptimo = min_p(rhoAux, 0.0, 1.0, 1e-6)
      rhoAux(pOptimo)
    }
  }

}