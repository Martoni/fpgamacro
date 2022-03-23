package fpgamacro.gowin

import chisel3._
import chisel3.util.Cat

case class PLLParams(
  val IDIV_SEL: Byte,
  val FBDIV_SEL: Byte,
  val ODIV_SEL: Byte,
  val DYN_SDIV_SEL: Byte,
)

class Video_PLL(pp: PLLParams = PLLParams(IDIV_SEL = 3, FBDIV_SEL = 54, ODIV_SEL = 2, DYN_SDIV_SEL = 30)) extends RawModule {
    val io = IO(new Bundle{
        val clkout = Output(Clock())
        val lock = Output(Bool())
        val clkoutd = Output(Clock())
        val clkin = Input(Clock())
    })
}
