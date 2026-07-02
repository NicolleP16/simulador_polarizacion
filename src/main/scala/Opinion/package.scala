import Comete._
import common._

import scala.collection.parallel.CollectionConverters._

package object Opinion {

  type SpecificBelief = Vector[Double]
  type GenericBeliefConf = Int => SpecificBelief
  type AgentsPolMeasure = (SpecificBelief, DistributionValues) => Double

  def rho(alpha: Double, beta: Double): AgentsPolMeasure = {

    def buildBounds(dv: DistributionValues): Vector[(Double, Double)] = {
      val k = dv.length
      val mids = (0 until k - 1).map { i => (dv(i) + dv(i + 1)) / 2.0 }.toVector

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

  type WeightedGraph = (Int, Int) => Double
  type SpecificWeightedGraph = (WeightedGraph, Int)
  type GenericWeightedGraph = Int => SpecificWeightedGraph

  def showWeightedGraph(swg: SpecificWeightedGraph): IndexedSeq[IndexedSeq[Double]] = {
    val (g, nags) = swg

    IndexedSeq.tabulate(nags) { i => IndexedSeq.tabulate(nags) { j => g(i, j) } }
  }

  // ── FunctionUpdate ────────────────────────────────────────
  type FunctionUpdate =
    (SpecificBelief, SpecificWeightedGraph) => SpecificBelief

  def confBiasUpdate(sb: SpecificBelief, swg: SpecificWeightedGraph): SpecificBelief = {
    val (influence, _) = swg

    val n = sb.length

    Vector.tabulate(n) { i =>
      val bi = sb(i)
      val neighbors =
        (0 until n).filter(j => influence(j, i) > 0.0)
      if (neighbors.isEmpty) bi
      else {
        val numerador =
          neighbors.map { j =>
            val bj = sb(j)
            val beta = 1.0 - math.abs(bj - bi)
            beta * influence(j, i) * (bj - bi)
          }.sum
        math.max(0.0, math.min(1.0, bi + numerador / neighbors.size))
      }
    }
  }

  def simulate(fu: FunctionUpdate,
               swg: SpecificWeightedGraph,
               b0: SpecificBelief,
               t: Int): IndexedSeq[SpecificBelief] = {
    (0 until t).scanLeft(b0) { (bActual, _) =>
      fu(bActual, swg)
    }
  }

  def rhoPar(alpha: Double, beta: Double): AgentsPolMeasure = {

    def buildBoundsPar(dv: DistributionValues): Vector[(Double, Double)] = {
      val k = dv.length / 2
      val (mid1, mid2) = parallel(
        (0 until k - 1).map { i => (dv(i) + dv(i + 1)) / 2.0 }.toVector,
        (k - 1 until dv.length - 1).map { i => (dv(i) + dv(i + 1)) / 2.0 }.toVector
      )
      val bounds = Vector(0.0) ++ mid1 ++ mid2 ++ Vector(1.0)
      bounds.zip(bounds.tail)
    }


    def countIntervalPar(body: SpecificBelief, interval: (Double, Double)): Int = {

      val m = body.length / 2

      val body1 = body.take(m)
      val body2 = body.drop(m)

      val (count1, count2) = parallel(
        body1.count(x => if (interval._2 == 1.0) interval._1 <= x && x <= interval._2 else interval._1 <= x && x < interval._2),
        body2.count(x => if (interval._2 == 1.0) interval._1 <= x && x <= interval._2 else interval._1 <= x && x < interval._2)
      )

      count1 + count2
    }

    def pi_b(entities: SpecificBelief, distribution: DistributionValues): Vector[Double] = {
      val bounds = buildBoundsPar(distribution)
      val counts = bounds.par.map(interval => countIntervalPar(entities, interval)).toVector
      val totalAgents = entities.length.toDouble
      counts.map(_.toDouble / totalAgents)
    }

    val medidaNorm = normalizar(rhoCMTGen(alpha, beta))

    (agents: SpecificBelief, y: DistributionValues) => {
      val pi = pi_b(agents, y)
      medidaNorm((pi, y))
    }
  }

  def confBiasUpdatePar(sb: SpecificBelief, swg: SpecificWeightedGraph): SpecificBelief = {
    val (influence, _) = swg
    val n = sb.length
    (0 until n).par.map { i =>
      val bi = sb(i)
      val neighbors =
        (0 until n).filter(j => influence(j, i) > 0.0)
      if (neighbors.isEmpty) bi
      else {
        val numerador =
          neighbors.par.map { j =>
            val bj = sb(j)
            val beta = 1.0 - math.abs(bj - bi)
            beta * influence(j, i) * (bj - bi)
          }.sum
        math.max(0.0, math.min(1.0, bi + numerador / neighbors.size))
      }
    }.toVector
  }
}