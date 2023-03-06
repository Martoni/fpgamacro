package fpgamacro.generic

import chisel3._
import chisel3.util._
import chisel3.experimental.Analog

class TriStateBuffer(width: Int = 1) extends BlackBox with HasBlackBoxInline {
  val io = IO(new Bundle {
    val pinout = Analog(width.W)
    val inp  = Input(UInt(width.W))
    val outp = Output(UInt(width.W))
    val dir = Input(Bool()) // 1 for output, 0 for input
  })
    val msb = width - 1
    setInline(f"TriStateBuffer$width%d.v",
    f"""
    |module TriStateBuffer$width%d(
    |    inout   [$msb%d:0] pinout,
    |    input   [$msb%d:0] inp,
    |    output  [$msb%d:0] outp,
    |    input   dir
    |);
    |
    |generate
    | genvar i;
    | for(i=0; i<$width%d; i=i+1)
    | begin: gen1
    |   assign pinout[i] = dir ? inp[i] : 1'bZ;
    | end
    |endgenerate
    |
    |assign outp = pinout;
    |
    |endmodule
    """.stripMargin)
}

class TriStateBufferAtomic(width: Int) extends BlackBox with HasBlackBoxInline {
  val io = IO(new Bundle {
    val pinout = Analog(width.W)
    val inp  = Input(UInt(width.W))
    val outp = Output(UInt(width.W))
    val dir  = Input(UInt(width.W)) // 1 for output, 0 for input
  })
    val msb = width - 1
    setInline(f"TriStateBufferAtomic$width%d.v",
    f"""
    |module TriStateBufferAtomic$width%d(
    |    inout   [$msb%d:0] pinout,
    |    input   [$msb%d:0] inp,
    |    output  [$msb%d:0] outp,
    |    input   [$msb%d:0] dir
    |);
    |
    |generate
    | genvar i;
    | for(i=0; i<$width%d; i=i+1)
    | begin: gen1
    |   assign pinout[i] = dir[i] ? inp[i] : 1'bZ;
    | end
    |endgenerate
    |
    |assign outp = pinout;
    |
    |endmodule
    """.stripMargin)
}

// XXX Legacy (deprecated)
class TriStateBuffer16 extends TriStateBufferAtomic(16)
