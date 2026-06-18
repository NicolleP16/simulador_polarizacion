package object Comete {
  type DistributionValues = Vector[Double]
  type Frequency = Vector[Double]
  type Distribution = (Frequency, DistributionValues)
  type PolMeasure = Distribution => Double

  /**
   * @param f funcion convexa a minimizar
   * @param min limite inferior del intervalo de busqueda
   * @param max limite superior del intervalo de busqueda
   * @param prec precision deseada
   * @return el punto p en [min,max] donde f(p) es minimo
   */
  def min_p(f: Double=>Double, min: Double, max: Double, prec: Double): Double = {
    //Caso base, intervalo suficientemente pequeño
    if (max - min < prec) (min + max) / 2.0
    else {
      val m1 = min + (max - min) / 3.0
      val m2 = max - (max - min) / 3.0
      if(f(m1) > f(m2))
        min_p(f,m1,max,prec)
      else min_p(f,min,m2,prec)
    }
  }
  def rhoCMTGen(alpha: Double, beta: Double): PolMeasure = {
    (dist: Distribution) => {
      val(pi, y) = dist
      def rhoAux(p: Double): Double = pi.zip(y).map { case (piI, yI) => math.pow(piI, alpha) * math.pow(math.abs(yI - p), beta)}.sum
      val pOptimo = min_p(rhoAux, 0.0, 1.0, 1e-6)
      rhoAux(pOptimo)
    }
  }
}