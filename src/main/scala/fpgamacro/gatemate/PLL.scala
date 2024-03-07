package fpgamacro.gatemate

import chisel3._
import circt.stage.ChiselStage
import chisel3.util.Cat
import chisel3.experimental.Param

/* Gatemate PLL */
//	CC_PLL #(
//		.REF_CLK("10.0"),    // reference input in MHz
//		.OUT_CLK("100.0"),   // pll output frequency in MHz
//		.PERF_MD("ECONOMY"), // LOWPOWER, ECONOMY, SPEED
//		.LOW_JITTER(1),      // 0: disable, 1: enable low jitter mode
//		.CI_FILTER_CONST(2), // optional CI filter constant
//		.CP_FILTER_CONST(4)  // optional CP filter constant
//	) pll_inst (
//		.CLK_REF(clk),
//		.CLK_FEEDBACK(1'b0),
//		.USR_CLK_REF(1'b0),
//		.USR_LOCKED_STDY_RST(1'b0),
//		.USR_PLL_LOCKED_STDY(usr_pll_lock_stdy),
//		.USR_PLL_LOCKED(usr_pll_lock),
//		.CLK270(clk270),
//		.CLK180(clk180),
//		.CLK90(clk90),
//		.CLK0(clk0),
//		.CLK_REF_OUT(usr_ref_out)
//	);
class CC_PLL(val pm: Map[String, Param]) extends BlackBox(pm){
  val io = IO(new Bundle{
    val CLK_REF = Input(Clock())
    val CLK_FEEDBACK = Input(Clock())
    val USR_CLK_REF = Input(Clock())
    val USR_LOCKED_STDY_RST = Input(Bool())
    val USR_PLL_LOCKED_STDY = Output(Bool())
    val USR_PLL_LOCKED = Output(Bool())
    val CLK270     = Output(Clock())
    val CLK180     = Output(Clock())
    val CLK90      = Output(Clock())
    val CLK0       = Output(Clock())
    val CLK_REF_OUT= Output(Clock())
  })
}

/* top module to test pll connexion */
class TopCC_PLL extends RawModule {
  val io = IO(new Bundle{
    val clock_i = Input(Clock())
    val clock_o = Output(Clock())
  })

  val pm: Map[String, Param] = Map(
    "REF_CLK"         -> "10.0",
    "OUT_CLK"         -> "100.0",
    "PERF_MD"         -> "ECONOMY",
    "LOW_JITTER"      -> 1,
    "CI_FILTER_CONST" -> 2,
    "CP_FILTER_CONST" -> 4)
  val gm_pll = Module(new CC_PLL(pm))
  gm_pll.io.CLK_REF := io.clock_i
  gm_pll.io.CLK_FEEDBACK := (false.B).asClock
  gm_pll.io.USR_CLK_REF := (false.B).asClock
  gm_pll.io.USR_LOCKED_STDY_RST := false.B
//  gm_pll.io.USR_PLL_LOCKED_STDY
//  gm_pll.io.USR_PLL_LOCKED 
//  gm_pll.io.CLK270
//  gm_pll.io.CLK180
//  gm_pll.io.CLK90
//  gm_pll.io.CLK0
  io.clock_o := gm_pll.io.CLK_REF_OUT

}

object TopCC_PLL extends App {
  val verilog_src = ChiselStage.emitSystemVerilog(
      new TopCC_PLL(),
      firtoolOpts = Array("-disable-all-randomization",
                          "-strip-debug-info"))
  val fverilog = os.pwd / "TopCC_PLL.v"
  if(os.exists(fverilog))
    os.remove(fverilog)
  os.write(fverilog, verilog_src)
}
