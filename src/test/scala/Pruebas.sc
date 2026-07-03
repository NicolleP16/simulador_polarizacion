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

//Pruebas propias

//Pruebas min_p

// Caso 1: función cuadrática, mínimo en x=0.5
// f(x) = (x - 0.5)^2, mínimo en 0.5
val f1 = (x: Double) => math.pow(x - 0.5, 2)
val minP1 = min_p(f1, 0.0, 1.0, 1e-6)
// Esperado: ~0.5
assert(math.abs(minP1 - 0.5) < 1e-4, s"min_p caso 1 falló: $minP1")
minP1

// Caso 2: función cuadrática, mínimo en x=0.0 (extremo izquierdo)
val f2 = (x: Double) => math.pow(x, 2)
val minP2 = min_p(f2, 0.0, 1.0, 1e-6)
// Esperado: ~0.0
assert(math.abs(minP2) < 1e-4, s"min_p caso 2 falló: $minP2")
minP2

// Caso 3: función cuadrática, mínimo en x=1.0 (extremo derecho)
val f3 = (x: Double) => math.pow(x - 1.0, 2)
val minP3 = min_p(f3, 0.0, 1.0, 1e-6)
// Esperado: ~1.0
assert(math.abs(minP3 - 1.0) < 1e-4, s"min_p caso 3 falló: $minP3")
minP3

// Caso 4: función cuadrática, mínimo en x=0.25
val f4 = (x: Double) => math.pow(x - 0.25, 2)
val minP4 = min_p(f4, 0.0, 1.0, 1e-6)
// Esperado: ~0.25
assert(math.abs(minP4 - 0.25) < 1e-4, s"min_p caso 4 falló: $minP4")
minP4

// Caso 5: función cuadrática, mínimo en x=0.75
val f5 = (x: Double) => math.pow(x - 0.75, 2)
val minP5 = min_p(f5, 0.0, 1.0, 1e-6)
// Esperado: ~0.75
assert(math.abs(minP5 - 0.75) < 1e-4, s"min_p caso 5 falló: $minP5")
minP5

// Caso 6: función lineal (convexa), mínimo en el extremo inferior
val f6 = (x: Double) => 2.0 * x + 1.0
val minP6 = min_p(f6, 0.0, 1.0, 1e-6)
// f es creciente, mínimo en 0.0
assert(minP6 < 0.01, s"min_p caso 6 falló: $minP6")
minP6

// Pruebas rhoCMTGen

val cmt12 = rhoCMTGen(1.2, 1.2)
val cmt20 = rhoCMTGen(2.0, 1.0)
val likert3 = Vector(0.0, 0.5, 1.0)

// Caso 1: distribución extrema pi_max con likert5 — máxima polarización
// Esperado: ~0.379
val rCmt1 = cmt12((pi_max, likert5))
assert(math.abs(rCmt1 - 0.379) < 0.01, s"rhoCMTGen caso 1 falló: $rCmt1")
rCmt1

// Caso 2: distribución de consenso en centro — polarización mínima
// Esperado: 0.0
val rCmt2 = cmt12((pi_min, likert5))
assert(math.abs(rCmt2) < 1e-6, s"rhoCMTGen caso 2 falló: $rCmt2")
rCmt2

// Caso 3: distribución intermedia pi_int1 — polarización moderada
// Esperado: ~0.165
val rCmt3 = cmt12((pi_int1, likert5))
assert(math.abs(rCmt3 - 0.165) < 0.01, s"rhoCMTGen caso 3 falló: $rCmt3")
rCmt3

// Caso 4: distribución asimétrica pi_der — polarización asimétrica
// Esperado: ~0.327
val rCmt4 = cmt12((pi_der, likert5))
assert(math.abs(rCmt4 - 0.327) < 0.01, s"rhoCMTGen caso 4 falló: $rCmt4")
rCmt4

// Caso 5: escala Likert3 con consenso en extremo izquierdo
// pi = (1.0, 0.0, 0.0), todos en 0.0, polarización = 0.0
val piConsIzq3 = Vector(1.0, 0.0, 0.0)
val rCmt5 = cmt12((piConsIzq3, likert3))
assert(math.abs(rCmt5) < 1e-6, s"rhoCMTGen caso 5 falló: $rCmt5")
rCmt5

// Caso 6: alpha=2.0, beta=1.0 sobre pi_max — diferente parametrización
// Esperado: ~0.25 (distinto de alpha=1.2)
val rCmt6 = cmt20((pi_max, likert5))
rCmt6

// Pruebas normalizar

val cmt1NormTest = normalizar(rhoCMTGen(1.2, 1.2))

// Caso 1: peor caso da exactamente 1.0
val rNorm1 = cmt1NormTest((pi_max, likert5))
assert(math.abs(rNorm1 - 1.0) < 1e-6, s"normalizar caso 1 falló: $rNorm1")
rNorm1

// Caso 2: consenso total da exactamente 0.0
val rNorm2 = cmt1NormTest((pi_min, likert5))
assert(math.abs(rNorm2) < 1e-6, s"normalizar caso 2 falló: $rNorm2")
rNorm2

// Caso 3: consenso en extremo derecho — también 0.0
val rNorm3 = cmt1NormTest((pi_cons_der, likert5))
assert(math.abs(rNorm3) < 1e-6, s"normalizar caso 3 falló: $rNorm3")
rNorm3

// Caso 4: pi_der normalizado — debe estar en (0,1)
val rNorm4 = cmt1NormTest((pi_der, likert5))
assert(rNorm4 > 0.0 && rNorm4 < 1.0, s"normalizar caso 4 falló: $rNorm4")
// Esperado: ~0.864
assert(math.abs(rNorm4 - 0.864) < 0.01, s"normalizar caso 4 valor falló: $rNorm4")
rNorm4

// Caso 5: pi_int3 normalizado — polarización moderada
// Esperado: ~0.625
val rNorm5 = cmt1NormTest((pi_int3, likert5))
assert(math.abs(rNorm5 - 0.625) < 0.01, s"normalizar caso 5 falló: $rNorm5")
rNorm5

// Caso 6: resultado normalizado siempre en [0,1] para pi_int1
val rNorm6 = cmt1NormTest((pi_int1, likert5))
assert(rNorm6 >= 0.0 && rNorm6 <= 1.0, s"normalizar caso 6 fuera de rango: $rNorm6")
rNorm6

// PRUEBAS rho

// Caso 1: allExtremeBelief — máxima polarización esperada = 1.0
val rRho1 = rho1(sb_ext, dist1)
assert(math.abs(rRho1 - 1.0) < 1e-6, s"rho caso 1 falló: $rRho1")
rRho1

// Caso 2: consensusBelief — polarización mínima = 0.0
val rRho2 = rho1(sb_cons, dist1)
assert(math.abs(rRho2) < 1e-6, s"rho caso 2 falló: $rRho2")
rRho2

// Caso 3: uniformBelief(100) con dist1
// Esperado: ~0.380 (distribución uniforme, polarización baja-media)
val rRho3 = rho1(sb_unif, dist1)
assert(rRho3 > 0.0 && rRho3 < 1.0, s"rho caso 3 fuera de rango: $rRho3")
rRho3

// Caso 4: allTripleBelief — tres grupos, polarización media-alta
// Esperado: ~0.617
val rRho4 = rho1(sb_triple, dist1)
assert(math.abs(rRho4 - 0.617) < 0.01, s"rho caso 4 falló: $rRho4")
rRho4

// Caso 5: midlyBelief — levemente polarizado
// Esperado: ~0.784
val rRho5 = rho1(sb_midly, dist1)
assert(rRho5 > 0.0 && rRho5 < 1.0, s"rho caso 5 fuera de rango: $rRho5")
rRho5

// Caso 6: rho con alpha=2.0, beta=1.0 sobre allExtremeBelief
// También debe dar 1.0 para el caso extremo
val rRho6 = rho2(sb_ext, dist1)
assert(math.abs(rRho6 - 1.0) < 1e-6, s"rho caso 6 falló: $rRho6")
rRho6

//Pruebas confBiasUpdate

// Caso 1: creencia uniforme con i1_10 — convergencia gradual
// Vector(0.1, 0.155, 0.243, 0.34, ...)
val cbuCase1 = confBiasUpdate(sbu_10, i1_10)
assert(math.abs(cbuCase1(0) - 0.1) < 1e-6, s"confBiasUpdate caso 1(0) falló: ${cbuCase1(0)}")
assert(math.abs(cbuCase1(1) - 0.155) < 1e-3, s"confBiasUpdate caso 1(1) falló: ${cbuCase1(1)}")
cbuCase1

// Caso 2: creencia midly con i1_10
val cbuCase2 = confBiasUpdate(sbm_10, i1_10)
assert(math.abs(cbuCase2(0) - 0.21) < 1e-6, s"confBiasUpdate caso 2(0) falló: ${cbuCase2(0)}")
cbuCase2

// Caso 3: creencia extrema (0s y 1s) — los extremos no cambian con i1
// con i1 el agente 0 tiene I(j,0)=0 para j>0, así que no tiene vecinos
// b(0) = 0.0, sin vecinos → permanece 0.0
val sbe_10 = allExtremeBelief(10)
val cbuCase3 = confBiasUpdate(sbe_10, i1_10)
assert(math.abs(cbuCase3(0) - 0.0) < 1e-6, s"confBiasUpdate caso 3 falló: ${cbuCase3(0)}")
cbuCase3

// Caso 4: creencia de consenso — no cambia (todos iguales, diferencia = 0)
val sbCons10 = consensusBelief(0.5)(10)
val cbuCase4 = confBiasUpdate(sbCons10, i1_10)
assert(cbuCase4.forall(x => math.abs(x - 0.5) < 1e-9),
  s"confBiasUpdate caso 4 falló: $cbuCase4")
cbuCase4

// Caso 5: resultado siempre en [0,1]
val cbuCase5 = confBiasUpdate(sbu_10, i2_10)
assert(cbuCase5.forall(x => x >= 0.0 && x <= 1.0),
  s"confBiasUpdate caso 5 fuera de rango: $cbuCase5")
cbuCase5

// Caso 6: polarización disminuye o se mantiene tras un paso con i1
val rhoBefore = rho1(sbu_10, dist1)
val rhoAfter  = rho1(confBiasUpdate(sbu_10, i1_10), dist1)
assert(rhoAfter <= rhoBefore + 1e-6,
  s"confBiasUpdate caso 6: polarización aumentó de $rhoBefore a $rhoAfter")
(rhoBefore, rhoAfter)

// Pruebas simulate

// Caso 1: t=0 devuelve solo el estado inicial (longitud 1)
val simCase1 = simulate(confBiasUpdate, i1_10, sbu_10, 0)
assert(simCase1.length == 1, s"simulate caso 1 longitud: ${simCase1.length}")
assert(simCase1.head == sbu_10, "simulate caso 1 estado inicial")
simCase1.length

// Caso 2: t=1 devuelve [b0, b1] (longitud 2)
val simCase2 = simulate(confBiasUpdate, i1_10, sbu_10, 1)
assert(simCase2.length == 2, s"simulate caso 2 longitud: ${simCase2.length}")
assert(simCase2.head == sbu_10, "simulate caso 2 estado inicial")
simCase2.length

// Caso 3: t=2 polarizacion decrece: [0.383, 0.380, 0.335]
val simCase3 = simulate(confBiasUpdate, i1_10, sbu_10, 2)
val pols3 = simCase3.map(b => rho1(b, dist1))
assert(pols3(0) >= pols3(2), s"simulate caso 3: polarización no decreció: $pols3")
pols3

// Caso 4: consenso es estado absorbente — polarización siempre 0.0
val sbCons5 = consensusBelief(0.3)(10)
val simCase4 = simulate(confBiasUpdate, i1_10, sbCons5, 5)
val pols4 = simCase4.map(b => rho1(b, dist1))
assert(pols4.forall(_ < 1e-6), s"simulate caso 4: consenso no absorbente: $pols4")
simCase4.length

// Caso 5: primer elemento siempre es b0 (invariante de scanLeft)
val simCase5 = simulate(confBiasUpdate, i2_10, sbm_10, 3)
assert(simCase5.head == sbm_10, "simulate caso 5: primer elemento no es b0")
assert(simCase5.length == 4, s"simulate caso 5 longitud: ${simCase5.length}")
simCase5.length

// Caso 6: t=5, longitud = 6
val simCase6 = simulate(confBiasUpdate, i1_10, sbu_10, 5)
assert(simCase6.length == 6, s"simulate caso 6 longitud: ${simCase6.length}")
simCase6.length

//Pruebas rhoPar (equivalencia con rho)

// Caso 1: allExtremeBelief — rhoPar == rho
val rParCase1 = rho1Par(sb_ext, dist1)
assert(math.abs(rParCase1 - rho1(sb_ext, dist1)) < 1e-6,
  s"rhoPar caso 1 difiere: $rParCase1 vs ${rho1(sb_ext, dist1)}")
rParCase1

// Caso 2: consensusBelief — rhoPar == rho == 0.0
val rParCase2 = rho1Par(sb_cons, dist1)
assert(math.abs(rParCase2) < 1e-6, s"rhoPar caso 2 falló: $rParCase2")
rParCase2

// Caso 3: uniformBelief — rhoPar == rho
val rParCase3 = rho1Par(sb_unif, dist1)
assert(math.abs(rParCase3 - rho1(sb_unif, dist1)) < 1e-6,
  s"rhoPar caso 3 difiere: $rParCase3 vs ${rho1(sb_unif, dist1)}")
rParCase3

// Caso 4: allTripleBelief con dist2 — rhoPar == rho
val rParCase4 = rho1Par(sb_triple, dist2)
assert(math.abs(rParCase4 - rho1(sb_triple, dist2)) < 1e-6,
  s"rhoPar caso 4 difiere: $rParCase4 vs ${rho1(sb_triple, dist2)}")
rParCase4

// Caso 5: midlyBelief con rho2Par == rho2
val rParCase5 = rho2Par(sb_midly, dist1)
assert(math.abs(rParCase5 - rho2(sb_midly, dist1)) < 1e-6,
  s"rhoPar caso 5 difiere: $rParCase5 vs ${rho2(sb_midly, dist1)}")
rParCase5

// Caso 6: red grande (1000 agentes) — rhoPar == rho
val sbLarge = uniformBelief(1000)
val rParCase6Sec = rho1(sbLarge, dist1)
val rParCase6Par = rho1Par(sbLarge, dist1)
assert(math.abs(rParCase6Sec - rParCase6Par) < 1e-6,
  s"rhoPar caso 6 difiere: $rParCase6Par vs $rParCase6Sec")
(rParCase6Sec, rParCase6Par)

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