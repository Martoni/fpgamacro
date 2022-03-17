package fpgamacro.gowin

import chisel3._
import chisel3.experimental.Param

/* OSC */
class OSC(val pm: Map[String, Param]) extends BlackBox(pm){
    val io = IO(new Bundle{
        val OSCOUT = Output(Clock())
    })
}

/* Gowin OSC */
class Gowin_OSC(FREQ_DIV: Byte = 10) extends RawModule {
    val io = IO(new Bundle{
        val oscout = Output(Clock())
    })

  val om: Map[String, Param] = Map(
  "FREQ_DIV" -> FREQ_DIV,
  )

  val osc_inst = Module(new OSC(om))

  io.oscout := osc_inst.io.OSCOUT
}
