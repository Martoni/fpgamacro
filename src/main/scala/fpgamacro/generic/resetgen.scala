package fpgamacro.generic

import chisel3._
import chisel3.util._

class ResetGenEna(delayClk: Int) extends BlackBox with HasBlackBoxInline {
  val io = IO(new Bundle {
    val clk = Input(Clock())
    val ena = Input(Bool())
    val rst = Output(Bool())
  })

  val cptWidth = log2Ceil(delayClk)
  val cptMsb = cptWidth - 1
  setInline("ResetGenEna.v",
    f"""
    |module ResetGenEna(
    |    input clk,
    |    input ena,
    |    output rst
    |);
    |
    |reg reset_reg = 0;
    |reg [$cptMsb%d:0] reset_dly_cnt = 0;
    |always@(posedge clk, negedge ena) begin
    |  if(!ena)
    |  begin
    |    reset_dly_cnt <= $cptWidth%d'd0;
    |    reset_reg <= 0;
    |  end
    |  else
    |  begin
    |    if(reset_dly_cnt < $cptWidth%d'd$delayClk%d)
    |      reset_dly_cnt <= reset_dly_cnt + 1'b1;
    |    else
    |      reset_reg <= 1;
    |  end
    |end
    |
    |assign rst = !reset_reg;
    |endmodule
    """.stripMargin)
}

class ResetGen(delayClk: Int = 0xFF) extends RawModule {
  val io = IO(new Bundle {
    val clk = Input(Clock())
    val rst = Output(Bool())
  })

  val resetGen = Module(new ResetGenEna(delayClk))
  resetGen.io.clk <> io.clk
  resetGen.io.ena := true.B
  resetGen.io.rst := io.rst
}
