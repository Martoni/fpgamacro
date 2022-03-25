package fpgamacro.gowin

import chisel3._
import chisel3.util.Cat
import chisel3.experimental.Param

/* DCS */
class DCS(val pm: Map[String, Param]) extends BlackBox(pm){
    val io = IO(new Bundle{
        val CLKOUT = Output(Clock())
        val CLKSEL = Input(UInt(4.W))
        val CLK0 = Input(Clock())
        val CLK1 = Input(Clock())
        val CLK2 = Input(Clock())
        val CLK3 = Input(Clock())
        val SELFORCE = Input(UInt(1.W))
    })
}

/* GATED_CLK (GW1N-1, GW1NZ-1, GW1NSR-4C and GW1NR-9) */
class GATED_CLK() extends RawModule {
  val clkin = IO(Input(Clock()))
  val clkout = IO(Output(Clock()))
  val clken = IO(Input(Bool()))

  val pm: Map[String, Param] = Map(
  "DCS_MODE" -> "RISING"
  )

  val gw_gnd = Wire(Bool())

  gw_gnd := false.B

  val dcs_inst = Module(new DCS(pm))

  dcs_inst.io.CLK0 := clkin
  dcs_inst.io.CLK1 := gw_gnd.asTypeOf(dcs_inst.io.CLK1)
  dcs_inst.io.CLK2 := gw_gnd.asTypeOf(dcs_inst.io.CLK2)
  dcs_inst.io.CLK3 := gw_gnd.asTypeOf(dcs_inst.io.CLK3)
  dcs_inst.io.CLKSEL := Cat("b0".U(3.W), clken)
  dcs_inst.io.SELFORCE := gw_gnd
  clkout := dcs_inst.io.CLKOUT
}
