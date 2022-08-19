package fpgamacro.gowin

import chisel3._
import chisel3.util._

/* clk div */
class CLKDIV extends BlackBox(Map("DIV_MODE" -> "5")) {
    val io = IO(new Bundle{
        val RESETN = Input(Bool())
        val HCLKIN = Input(Clock())
        val CLKOUT = Output(Clock())
        val CALIB = Input(Bool())
    })
}

/* clk div 2 */
class CLKDIV2 extends BlackBox() {
    val io = IO(new Bundle{
        val RESETN = Input(Bool())
        val HCLKIN = Input(Clock())
        val CLKOUT = Output(Clock())
    })
}

/* OSER10 : serializer 10:1*/
class OSER10 extends BlackBox(
  Map("GSREN"->"false", "LSREN" -> "true")
){
  val io = IO(new Bundle {
  val Q = Output(Bool()) // OSER10 data output signal
  val D0 = Input(Bool())
  val D1 = Input(Bool())
  val D2 = Input(Bool())
  val D3 = Input(Bool())
  val D4 = Input(Bool())
  val D5 = Input(Bool())
  val D6 = Input(Bool())
  val D7 = Input(Bool())
  val D8 = Input(Bool())
  val D9 = Input(Bool()) //  OSER10 data input signal
  val PCLK = Input(Clock()) // Primary clock input signal
  val FCLK = Input(Clock()) // High speed clock input signal
  val RESET = Input(Reset()) // Asynchronous reset input signal,
                            //active-high.
  })
}

class Oser10Module extends Module {
  val io = IO(new Bundle {
    val q = Output(Bool())
    val data = Input(UInt(10.W))
    val fclk = Input(Clock()) // Fast clock
  })

  val osr10 = Module(new OSER10())
  io.q := osr10.io.Q
  osr10.io.D0 := io.data(0)
  osr10.io.D1 := io.data(1)
  osr10.io.D2 := io.data(2)
  osr10.io.D3 := io.data(3)
  osr10.io.D4 := io.data(4)
  osr10.io.D5 := io.data(5)
  osr10.io.D6 := io.data(6)
  osr10.io.D7 := io.data(7)
  osr10.io.D8 := io.data(8)
  osr10.io.D9 := io.data(9)
  osr10.io.PCLK := clock
  osr10.io.FCLK := io.fclk
  osr10.io.RESET:= reset
}

/* OSER10 : serializer 10:1*/
class IDES10 extends BlackBox(
  Map("GSREN"->"false", "LSREN" -> "true")
){
  val io = IO(new Bundle {
  val D = Input(Bool()) // IDES10 data input signal
  val Q0 = Output(Bool())
  val Q1 = Output(Bool())
  val Q2 = Output(Bool())
  val Q3 = Output(Bool())
  val Q4 = Output(Bool())
  val Q5 = Output(Bool())
  val Q6 = Output(Bool())
  val Q7 = Output(Bool())
  val Q8 = Output(Bool())
  val Q9 = Output(Bool()) //  IDES10 data output signal
  val PCLK = Input(Clock()) // Primary clock input signal
  val FCLK = Input(Clock()) // High speed clock input signal
  val CALIB = Input(Bool()) // IDES10 Calib signal
  val RESET = Input(Reset()) // Asynchronous reset input signal,
                            //active-high.
  })
}

class Ides10Module extends Module {
  val io = IO(new Bundle {
    val data = Input(Bool())
    val q = Output(UInt(10.W))
    val fclk = Input(Clock()) // Fast clock
    val calib = Input(Bool())
  })

  val ides10 = Module(new IDES10())
  ides10.io.D := io.data
  io.q(0) := ides10.io.Q0
  io.q(1) := ides10.io.Q1
  io.q(2) := ides10.io.Q2
  io.q(3) := ides10.io.Q3
  io.q(4) := ides10.io.Q4
  io.q(5) := ides10.io.Q5
  io.q(6) := ides10.io.Q6
  io.q(7) := ides10.io.Q7
  io.q(8) := ides10.io.Q8
  io.q(9) := ides10.io.Q9
  ides10.io.PCLK := clock
  ides10.io.FCLK := io.fclk
  ides10.io.CALIB:= io.calib
  ides10.io.RESET:= reset
}

/* lvds output */
class LVDS_OBUF extends BlackBox {
  val io = IO(new Bundle {
    val O = Output(Bool())
    val OB = Output(Bool())
    val I = Input(Bool())
  })
}

class TLVDS_OBUF extends LVDS_OBUF {
}

class ELVDS_OBUF extends LVDS_OBUF {
}
