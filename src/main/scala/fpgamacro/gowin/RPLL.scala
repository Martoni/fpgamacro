package fpgamacro.gowin

import chisel3._
import chisel3.util._
import chisel3.experimental.Param

/* rPLL */
class rPLL(val pm: Map[String, Param]) extends BlackBox(pm){
    val io = IO(new Bundle{
        val CLKOUT = Output(Clock())
        val LOCK = Output(UInt(1.W))
        val CLKOUTP = Output(Clock())
        val CLKOUTD = Output(Clock())
        val CLKOUTD3 = Output(Clock())
        val RESET = Input(UInt(1.W))
        val RESET_P = Input(UInt(1.W))
        val CLKIN = Input(Clock())
        val CLKFB = Input(Bool())
        val FBDSEL = Input(UInt(6.W))
        val IDSEL = Input(UInt(6.W))
        val ODSEL = Input(UInt(6.W))
        val PSDA = Input(UInt(4.W))
        val DUTYDA = Input(UInt(4.W))
        val FDLY = Input(UInt(4.W))
    })
}

/* Gowin rPLL (GW1NR-9) */
class Gowin_rPLL(pp: PLLParams = PLLParams(IDIV_SEL = 3, FBDIV_SEL = 54, ODIV_SEL = 2, DYN_SDIV_SEL = 2)) extends Video_PLL {
  val pm: Map[String, Param] = Map(
  "FCLKIN" -> "27",
  "DYN_IDIV_SEL" -> "false",
  "IDIV_SEL" -> pp.IDIV_SEL,
  "DYN_FBDIV_SEL" -> "false",
  "FBDIV_SEL" -> pp.FBDIV_SEL,
  "DYN_ODIV_SEL" -> "false",
  "ODIV_SEL" -> pp.ODIV_SEL,
  "PSDA_SEL" -> "0000",
  "DYN_DA_EN" -> "true",
  "DUTYDA_SEL" -> "1000",
  "CLKOUT_FT_DIR" -> "1'b1",
  "CLKOUTP_FT_DIR" -> "1'b1",
  "CLKOUT_DLY_STEP" -> 0,
  "CLKOUTP_DLY_STEP" -> 0,
  "CLKFB_SEL" -> "internal",
  "CLKOUT_BYPASS" -> "false",
  "CLKOUTP_BYPASS" -> "false",
  "CLKOUTD_BYPASS" -> "false",
  "DYN_SDIV_SEL" -> pp.DYN_SDIV_SEL,
  "CLKOUTD_SRC" -> "CLKOUT",
  "CLKOUTD3_SRC" -> "CLKOUT",
  )

  val clkoutp_o = Wire(Clock())
  val clkoutd3_o = Wire(Clock())
  val gw_gnd = Wire(UInt(1.W))

  gw_gnd := 0.U(1.W)

  val rpll_inst = Module(new rPLL(pm))

  io.clkout := rpll_inst.io.CLKOUT
  io.lock := rpll_inst.io.LOCK
  clkoutp_o := rpll_inst.io.CLKOUTP
  io.clkoutd := rpll_inst.io.CLKOUTD
  clkoutd3_o := rpll_inst.io.CLKOUTD3

  rpll_inst.io.RESET := gw_gnd
  rpll_inst.io.RESET_P := gw_gnd
  rpll_inst.io.CLKIN := io.clkin
  rpll_inst.io.CLKFB := gw_gnd
  rpll_inst.io.FBDSEL := Cat(gw_gnd,gw_gnd,gw_gnd,gw_gnd,gw_gnd,gw_gnd)
  rpll_inst.io.IDSEL := Cat(gw_gnd,gw_gnd,gw_gnd,gw_gnd,gw_gnd,gw_gnd)
  rpll_inst.io.ODSEL := Cat(gw_gnd,gw_gnd,gw_gnd,gw_gnd,gw_gnd,gw_gnd)
  rpll_inst.io.PSDA := Cat(gw_gnd,gw_gnd,gw_gnd,gw_gnd)
  rpll_inst.io.DUTYDA := Cat(gw_gnd,gw_gnd,gw_gnd,gw_gnd)
  rpll_inst.io.FDLY := Cat(gw_gnd,gw_gnd,gw_gnd,gw_gnd)
}
