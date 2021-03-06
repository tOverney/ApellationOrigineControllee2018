package ch.overney.aoc2018.day11

object Part1 extends App {
  val gridSerialNumber = Input.Data(2)
  val patchSize = 3
  val gridSize = 300
  val upperboundForSearch = gridSize - patchSize + 1

  def getCellValue(x: Int, y: Int, gsn: Int) = {
    val rackID = x + 10
    val plStart = rackID * y
    val newPl = plStart + gsn
    val updatedPl = newPl * rackID
    val hundredDigit = updatedPl.toString.dropRight(2).lastOption.map(c => ("" + c).toInt).getOrElse(0)

    //println(rackID + "->" + plStart + "->" + newPl + "->" + updatedPl + "->" + hundredDigit)

    hundredDigit - 5
  }

  val t1 = getCellValue(3, 5, 8)
  assert(t1 == 4, "1. " + t1)

  val t2 = getCellValue(122, 79, 57)
  assert(t2 == -5, "2. " + t2)

  val t3 = getCellValue(217, 196, 39)
  assert(t3 == 0, "3. " + t3)

  val t4 = getCellValue(101, 153, 71)
  assert(t4 == 4, "4. " + t4)


  val terrain = (for {
    x <- 1 to 300
    y <- 1 to 300
  } yield {
    ((x, y), getCellValue(x, y, gridSerialNumber))
  }).toMap

  val cornerOfMax = terrain.flatMap {
    case (coord @ (x, y), _) =>
      if (x > upperboundForSearch || y > upperboundForSearch) {
        None
      } else {
        val sum = (for {
          dx <- 0 until patchSize
          dy <- 0 until patchSize
        } yield {
          terrain((x + dx, y + dy))
        }).sum

        Some((coord, sum))
      }
  }.maxBy(_._2)

  // Tried: (235,85)
  println(cornerOfMax._1 + "   with value: " + cornerOfMax._2)
}
