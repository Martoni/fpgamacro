package fpgamacro.gowin

import chisel3._
import chisel3.util.Cat
import chisel3.experimental.Param

/* DQCE */
class DQCE(val pm: Map[String, Param]) extends BlackBox(pm){
    val io = IO(new Bundle{
        val CLKOUT = Output(Clock())
        val CLKIN = Input(Clock())
        val CE = Input(Bool())
    })
}

/* Gowin_DQCE (GW1N-1, GW1NZ-1, GW1NSR-4C and GW1NR-9) */
class Gowin_DQCE() extends RawModule {
    val io = IO(new Bundle{
        val clkin = Input(Clock())
        val clkout = Output(Clock())
        val ce = Input(Bool())
    })

  val pm: Map[String, Param] = Map(
  )

  val dqce_inst = Module(new DQCE(pm))

  dqce_inst.io.CLKIN := io.clkin
  dqce_inst.io.CE := io.ce
  io.clkout := dqce_inst.io.CLKOUT
}
