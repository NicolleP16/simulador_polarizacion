import Comete._
import Opinion._
import Benchmark._

//Valores de prueba
val pi_max = Vector(0.5, 0.0, 0.0, 0.0, 0.5)
val pi_min = Vector(0.0, 0.0, 1.0, 0.0, 0.0)
val pi_der = Vector(0.4, 0.0, 0.0, 0.0, 0.6)
val pi_izq = Vector(0.6, 0.0, 0.0, 0.0, 0.4)
val pi_int1 = Vector(0.0, 0.5, 0.0, 0.5, 0.0)
val pi_int2 = Vector(0.25, 0.0, 0.5, 0.0, 0.25)
val pi_int3 = Vector(0.25, 0.25, 0.0, 0.25, 0.25)
val pi_cons_centro = pi_min
val pi_cons_der = Vector(0.0, 0.0, 0.0, 0.0, 1.0)
val pi_cons_izq = Vector(1.0, 0.0, 0.0, 0.0, 0.0)
val likert5 = Vector(0.0, 0.25, 0.5, 0.75, 1.0)
/*
   * Resultados esperados con cmt1 = rhoCMTGen(1.2, 1.2):
   *   cmt1(pi_max,  likert5) ~= 0.379
   *   cmt1(pi_min,  likert5) ~= 0.0
   *   cmt1(pi_der,  likert5) ~= 0.327
   *   cmt1(pi_izq,  likert5) ~= 0.327
   *   cmt1(pi_int1, likert5) ~= 0.165
   *   cmt1(pi_int2, likert5) ~= 0.165
   *   cmt1(pi_int3, likert5) ~= 0.237
   *   cmt1(pi_cons_centro, likert5) ~= 0.0
   *   cmt1(pi_cons_der,    likert5) ~= 0.0
   *   cmt1(pi_cons_izq,    likert5) ~= 0.0
   */
val cmt1 = rhoCMTGen(1.2, 1.2)

cmt1((pi_max, likert5))
cmt1((pi_min, likert5))
cmt1((pi_der, likert5))
cmt1((pi_izq, likert5))
cmt1((pi_int1, likert5))
cmt1((pi_int2, likert5))
cmt1((pi_int3, likert5))
cmt1(pi_cons_centro, likert5)
cmt1(pi_cons_der, likert5)
cmt1(pi_cons_izq, likert5)

/*
   * Resultados esperados con val cmt1 norm = normalizar(cmt1):
   *   cmt1 norm(pi max, likert5) ~= 1.0
   *   cmt1 norm(pi min , likert5) ~= 0.0
   *   cmt1 norm(pi der , likert5) ~= 0.863
   *   cmt1 norm(pi izq , likert5) ~= 0.863
   *   cmt1 norm(pi int1 , likert5) ~= 0.435
   *   cmt1 norm(pi int2 , likert5) ~= 0.435
   *   cmt1 norm(pi int3 , likert5) ~= 0.625
   *   cmt1 norm(pi cons centro , likert5) ~= 0.0
   *   cmt1 norm(pi cons der , likert5) ~= 0.0
   *   cmt1 norm(pi cons izq , likert5) ~= 0.0
   */

val cmt1_norm = normalizar(cmt1)
cmt1_norm(pi_max, likert5)
cmt1_norm(pi_min, likert5)
cmt1_norm(pi_der, likert5)
cmt1_norm(pi_izq, likert5)
cmt1_norm(pi_int1, likert5)
cmt1_norm(pi_int2, likert5)
cmt1_norm(pi_int3, likert5)
cmt1_norm(pi_cons_centro , likert5)
cmt1_norm(pi_cons_der, likert5)
cmt1_norm(pi_cons_izq , likert5)

// Valores de prueba
val sb_ext = allExtremeBelief(100)
val sb_cons = consensusBelief(0.2)(100)
val sb_unif = uniformBelief(100)
val sb_triple = allTripleBelief(100)
val sb_midly = midlyBelief(100)

val rho1 = rho(1.2, 1.2)
val rho2 = rho(2.0, 1.0)

val dist1 = Vector(0.0, 0.25, 0.50, 0.75, 1.0)
val dist2 = Vector(0.0, 0.2, 0.4, 0.6, 0.8, 1.0)

rho1(sb_ext, dist1)
rho2(sb_ext, dist1)
rho1(sb_ext, dist2)
rho2(sb_ext, dist2)

rho1(sb_cons, dist1)
rho2(sb_cons, dist1)
rho1(sb_cons, dist2)
rho2(sb_cons, dist2)

rho1(sb_unif, dist1)
rho2(sb_unif, dist1)
rho1(sb_unif, dist2)
rho2(sb_unif, dist2)

rho1(sb_triple, dist1)
rho2(sb_triple, dist1)
rho1(sb_triple, dist2)
rho2(sb_triple, dist2)

rho1(sb_midly, dist1)
rho2(sb_midly, dist1)
rho1(sb_midly, dist2)
rho2(sb_midly, dist2)

val i1_10=i1(10)
val i2_10=i2(10)
val i1_20=i1(20)
val i2_20=i2(20)

showWeightedGraph(i1_10)
showWeightedGraph(i2_10)

val sbu_10 = uniformBelief(10)
val sbm_10 = midlyBelief(10)

// Verificar que la creencia inicial es correcta
// sbu_10 = Vector(0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0)
sbu_10

// Aplicar un paso de confBiasUpdate con i1_10
// Resultado esperado:
// Vector(0.1, 0.155, 0.243, 0.34, 0.44, 0.541, 0.644, 0.747, 0.851, 0.955)
val cbu_sbu_i1 = confBiasUpdate(sbu_10, i1_10)
cbu_sbu_i1

// Polarización antes y después del primer paso (debe bajar levemente)
// Esperado: 0.383 -> 0.380
rho1(sbu_10, dist1)
rho1(cbu_sbu_i1, dist1)

// sbm_10 = Vector(0.21, 0.22, 0.23, 0.24, 0.25, 0.75, 0.76, 0.77, 0.78, 0.79)
sbm_10

// Aplicar un paso de confBiasUpdate con i1_10 sobre sbm_10
// Resultado esperado:
// Vector(0.21, 0.215, 0.223, 0.232, 0.242, 0.655, 0.707, 0.733, 0.752, 0.768)
val cbu_sbm_i1 = confBiasUpdate(sbm_10, i1_10)
cbu_sbm_i1

// Polarización antes y después (debe mantenerse similar)
// Esperado: 0.435 -> 0.435
rho1(sbm_10, dist1)
rho1(cbu_sbm_i1, dist1)


// simulate con uniformBelief(10) durante 2 pasos usando i1_10
// Devuelve IndexedSeq con 3 elementos: [b0, b1, b2]
// Resultado esperado con polarización:
//   (b0, 0.383), (b1, 0.380), (b2, 0.335)
val sim_sbu_i1 = simulate(confBiasUpdate, i1_10, sbu_10, 2)
for {
  b <- sim_sbu_i1
} yield (b, rho1(b, dist1))

// simulate con midlyBelief(10) durante 2 pasos usando i1_10
// Resultado esperado con polarización:
//   (b0, 0.435), (b1, 0.435), (b2, 0.377)
val sim_sbm_i1 = simulate(confBiasUpdate, i1_10, sbm_10, 2)
for {
  b <- sim_sbm_i1
} yield (b, rho1(b, dist1))

// Verificar que simulate devuelve t+1 elementos
// Esperado: 3
sim_sbu_i1.length

// Verificar que el primer elemento es siempre b0 sin modificar
// Esperado: true
sim_sbu_i1.head == sbu_10
sim_sbm_i1.head == sbm_10

val rho1Par = rhoPar(1.2, 1.2)
val rho2Par = rhoPar(2.0, 1.0)

rho1Par(sb_ext, dist1)
rho2Par(sb_ext, dist1)
rho1Par(sb_ext, dist2)
rho2Par(sb_ext, dist2)

rho1Par(sb_cons, dist1)
rho2Par(sb_cons, dist1)
rho1Par(sb_cons, dist2)
rho2Par(sb_cons, dist2)

rho1Par(sb_unif, dist1)
rho2Par(sb_unif, dist1)
rho1Par(sb_unif, dist2)
rho2Par(sb_unif, dist2)

rho1Par(sb_triple, dist1)
rho2Par(sb_triple, dist1)
rho1Par(sb_triple, dist2)
rho2Par(sb_triple, dist2)

rho1Par(sb_midly, dist1)
rho2Par(sb_midly, dist1)
rho1Par(sb_midly, dist2)
rho2Par(sb_midly, dist2)

// Secuencial vs paralelo - función rho

val sbms = for {
  n<-2 until 16
    nags = math.pow(2,n).toInt
} yield midlyBelief(nags)
val polSec = rho(1.2, 1.2)
val polPar = rhoPar(1.2, 1.2)

val cmp1 = compararMedidasPol(sbms, likert5 , polSec , polPar)

cmp1.map(t => t._6)

// Secuencial vs paralelo - función confBiasUpdate
val i1_32768=i1(32768)
val i2_32768=i2(32768)
compararFuncionesAct(sbms. take(sbms. length/2),
  i2_32768 ,confBiasUpdate , confBiasUpdatePar)

val sbes = for {
  n <- 2 until 16
  nags = math.pow(2, n).toInt
} yield allExtremeBelief(nags)

val sbts = for {
  n <- 2 until 16
  nags = math.pow(2, n).toInt
} yield allTripleBelief(nags)

val evolsSec = for {
  i <- 0 until sbms.length
} yield simEvolucion(Seq(sbms(i),sbes(i),sbts(i)),
  i2_32768,10, polSec, confBiasUpdate, likert5,
  "Simulacion_Secuencial_" ++ i.toString ++ "_"
    ++ sbms(i).length.toString)