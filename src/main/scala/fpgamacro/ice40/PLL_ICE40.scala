package fpgamacro.ice40

import chisel3._
import circt.stage.ChiselStage
import chisel3.util._
import chisel3.experimental.Param

/*
verilog blackbox from 
https://github.com/juanmard/examples/blob/main/Blinkies/SB_PLL40_CORE.v
module SB_PLL40_CORE (
  input   REFERENCECLK,
  output  PLLOUTCORE,
  output  PLLOUTGLOBAL,
  input   EXTFEEDBACK,
  input   [7:0] DYNAMICDELAY,
  output  LOCK,
  input   BYPASS,
  input   RESETB,
  input   LATCHINPUTVALUE,
  output  SDO,
  input   SDI,
  input   SCLK
);
  parameter FEEDBACK_PATH = "SIMPLE";
  parameter DELAY_ADJUSTMENT_MODE_FEEDBACK = "FIXED";
  parameter DELAY_ADJUSTMENT_MODE_RELATIVE = "FIXED";
  parameter SHIFTREG_DIV_MODE = 1'b0;
  parameter FDA_FEEDBACK = 4'b0000;
  parameter FDA_RELATIVE = 4'b0000;
  parameter PLLOUT_SELECT = "GENCLK";
  parameter DIVR = 4'b0000;
  parameter DIVF = 7'b0000000;
  parameter DIVQ = 3'b000;
  parameter FILTER_RANGE = 3'b000;
  parameter ENABLE_ICEGATE = 1'b0;
  parameter TEST_MODE = 1'b0;
  parameter EXTERNAL_DIVIDE_FACTOR = 1;
endmodule
*/

class SB_PLL40_CORE(val pm: Map[String, Param]) extends BlackBox(pm){
  val io = IO(new Bundle{
    val REFERENCECLK = Input(Clock())
    val PLLOUTCORE = Output(Clock())
    val PLLOUTGLOBAL = Output(Clock())
    val EXTFEEDBACK = Input(Clock())
    val DYNAMICDELAY = Input(UInt(8.W))
    val LOCK = Output(Bool())
    val BYPASS = Input(Bool())
    val RESETB = Input(Bool())
    val LATCHINPUTVALUE = Input(Bool())
    val SDO = Output(Bool())
    val SDI = Input(Bool())
    val SCLK = Input(Clock())
  })
}

/* top module to test pll connexion */
class TopSB_PLL40_CORE extends RawModule {
  val io = IO(new Bundle{
    val clock_i = Input(Clock())
    val clock_o = Output(Clock())
  })

  val pm: Map[String, Param] = Map(
    "FEEDBACK_PATH" -> "SIMPLE",
    "DELAY_ADJUSTMENT_MODE_FEEDBACK" -> "FIXED",
    "DELAY_ADJUSTMENT_MODE_RELATIVE" -> "FIXED",
    "SHIFTREG_DIV_MODE" -> 0,
    "FDA_FEEDBACK" -> 0,
    "FDA_RELATIVE" -> 0,
    "PLLOUT_SELECT" -> "GENCLK",
    "DIVR" -> 0,
    "DIVF" -> 0,
    "DIVQ" -> 0,
    "FILTER_RANGE" -> 0,
    "ENABLE_ICEGATE" -> 0,
    "TEST_MODE" -> 0,
    "EXTERNAL_DIVIDE_FACTOR" -> 1)

  val gm_pll = Module(new SB_PLL40_CORE(pm))
  gm_pll.io.REFERENCECLK := io.clock_i
  io.clock_o := gm_pll.io.PLLOUTCORE
  gm_pll.io.RESETB := true.B
  gm_pll.io.BYPASS := false.B

  gm_pll.io.EXTFEEDBACK := DontCare

  gm_pll.io.DYNAMICDELAY := DontCare
  gm_pll.io.LATCHINPUTVALUE := DontCare
  gm_pll.io.SDI := DontCare
  gm_pll.io.SCLK := DontCare
}

object TopSB_PLL40_CORE extends App {
  val verilog_src = ChiselStage.emitSystemVerilog(
      new TopSB_PLL40_CORE(),
      firtoolOpts = Array("-disable-all-randomization",
                          "-strip-debug-info"))
  val fverilog = os.pwd / "TopSB_PLL40_CORE.v"
  if(os.exists(fverilog))
    os.remove(fverilog)
  os.write(fverilog, verilog_src)
}

