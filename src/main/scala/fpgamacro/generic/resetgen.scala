package fpgamacro.generic

import chisel3._
import chisel3.util._

class ResetGen extends BlackBox with HasBlackBoxInline {
  val io = IO(new Bundle {
    val clk = Input(Clock())
    val rst = Output(Bool())
  })

  setInline("ResetGen.v",
    s"""
    |module ResetGen(
    |    input clk,
    |    output rst
    |);
    |
    |reg reset_reg = 0;
    |reg [7:0] reset_dly_cnt = 0;
    |always@(posedge clk) begin
    |  if(reset_dly_cnt < 8'hff)
    |      reset_dly_cnt <= reset_dly_cnt + 1'b1;
    |  else
    |      reset_reg <= 1;
    |end
    |
    |assign rst = !reset_reg;
    |endmodule
    """.stripMargin)
}
