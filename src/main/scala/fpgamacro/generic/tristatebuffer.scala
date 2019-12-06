package fpgamacro.generic

import chisel3._
import chisel3.util._
import chisel3.experimental.Analog
import chisel3.Driver

class TriStateBuffer extends BlackBox with HasBlackBoxInline {
  val io = IO(new Bundle {
    val pinout = Analog(1.W)
    val inp  = Input(Bool())
    val outp = Output(Bool())
    val dir = Input(Bool())
  })

    setInline("TriStateBuffer.v",
    s"""
      |module TriStateBuffer(
      |    inout   pinout,
      |    input   inp,
      |    output  outp,
      |    input   dir
      |);
      | assign pinout = dir ? inp : 1'bZ;
      | assign outp = pinout;
      |
      |endmodule
    """.stripMargin)
}
