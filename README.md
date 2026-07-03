# Simulador de Polarización en Redes Sociales

**Fundamentos de Programación Funcional y Concurrente**
Universidad del Valle — 2026

## Descripción

Este proyecto implementa, en Scala y bajo el paradigma de programación funcional, un simulador de polarización de opiniones en redes sociales, basado en un modelo de sesgo de confirmación (*confirmation bias*).

El proyecto se organiza en tres paquetes principales:

- **`Comete`**
  Implementa la medida de polarización *comete* (`rhoCMTGen`), su normalización (`normalizar`) y la búsqueda del punto óptimo mediante búsqueda ternaria (`min_p`).

- **`Opinion`**
  Extiende la medida de polarización a una red de agentes (`rho`), define el mecanismo de actualización de creencias bajo sesgo de confirmación (`confBiasUpdate`) y su simulación en el tiempo (`simulate`). Incluye además versiones paralelas de las funciones más costosas (`rhoPar`, `confBiasUpdatePar`).

- **`Benchmark`**
  Contiene las funciones auxiliares usadas para comparar el desempeño de las versiones secuenciales y paralelas (`compararMedidasPol`, `compararFuncionesAct`, `simEvolucion`), así como generadores de datos de prueba (grafos de influencia, distribuciones de creencias iniciales, etc.).

## Estructura del proyecto

```
build.sbt
build.properties
src/
  main/
    scala/
      Benchmark/
        package.scala   -> Funciones de comparación de desempeño
                            (compararMedidasPol, compararFuncionesAct,
                            simEvolucion) y generadores de datos de prueba.
      Comete/           -> Medida de polarización comete
                            (rhoCMTGen, normalizar, min_p).
      common/           -> Tipos y utilidades compartidas.
      Opinion/
        package.scala   -> Medida de polarización sobre agentes (rho, rhoPar),
                            función de actualización (confBiasUpdate,
                            confBiasUpdatePar) y simulación (simulate).
  test/
    scala/
      Pruebas.sc        -> Worksheet principal de pruebas; desde aquí se
                            ejecutan y verifican todas las funciones del
                            proyecto.
graficas/                -> Archivos HTML generados por simEvolucion con la
                            evolución de la polarización en cada simulación.
```

## Requisitos

- JDK 8 o superior
- sbt (Scala Build Tool)

## Cómo ejecutar

1. Clonar el repositorio:

   ```bash
   git clone git@github.com:NicolleP16/simulador_polarizacion.git
   ```

   Y abrirlo en IntelliJ como proyecto sbt (o ubicarse en la raíz del proyecto, donde está `build.sbt`, si se usa la terminal).

2. Compilar el proyecto con sbt. Desde la terminal, en la raíz del proyecto:

   ```bash
   sbt compile
   ```

3. Para probar las funciones, abrir el worksheet principal:

   ```
   src/test/scala/Pruebas.sc
   ```

   Este archivo ya importa los paquetes necesarios (`Comete`, `Opinion`, `Benchmark`) y contiene las pruebas de verificación de todas las funciones del proyecto, así como los benchmarks de comparación secuencial vs. paralela.

    - Abrir `Pruebas.sc` en IntelliJ.
    - Presionar el botón de **Run (▶)** del worksheet para evaluarlo; los resultados aparecen en el panel derecho junto a cada línea.

   > **Nota:** el worksheet debe evaluarse con el proyecto ya compilado (paso 2); si se modifican las funciones en `src/main/scala`, conviene recompilar antes de volver a correr `Pruebas.sc`.

4. Las simulaciones ejecutadas mediante `simEvolucion` (dentro de `Benchmark`) generan archivos HTML con la evolución de la polarización en el tiempo, guardados automáticamente en la carpeta:

   ```
   graficas/
   ```

   Estos archivos pueden abrirse directamente en el navegador para visualizar los resultados de cada simulación.

## Autores

- May Barreto, William Rooselbelt
- Patiño Rodríguez, Sergio Ernesto
- Paz Molineros, Nicolle Andrea

**Curso:** Fundamentos de Programación Funcional y Concurrente
**Profesor:** Juan Francisco Díaz Frías
**Monitoras:** Emily Núñez, Salomé Acosta