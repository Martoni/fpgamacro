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
