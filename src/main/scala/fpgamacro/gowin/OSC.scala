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

/* OSCH */
class OSCH(val pm: Map[String, Param]) extends BlackBox(pm){
    val io = IO(new Bundle{
        val OSCOUT = Output(Clock())
    })
}

/* Gowin OSCH (GW1N-1) */
class Gowin_OSCH(FREQ_DIV: Byte = 10) extends RawModule {
    val io = IO(new Bundle{
        val oscout = Output(Clock())
    })

  val om: Map[String, Param] = Map(
  "FREQ_DIV" -> FREQ_DIV,
  )

  val osc_inst = Module(new OSCH(om))

  io.oscout := osc_inst.io.OSCOUT
}

/* OSCZ */
class OSCZ(val pm: Map[String, Param]) extends BlackBox(pm){
    val io = IO(new Bundle{
        val OSCOUT = Output(Clock())
        val OSCEN = Input(Bool())
    })
}

/* Gowin OSCZ (GW1NZ-1 and GW1NSR-4C) */
class Gowin_OSCZ(FREQ_DIV: Byte = 10) extends RawModule {
    val io = IO(new Bundle{
        val oscout = Output(Clock())
        val oscen = Input(Bool())
    })

  val om: Map[String, Param] = Map(
  "FREQ_DIV" -> FREQ_DIV,
  )

  val osc_inst = Module(new OSCZ(om))

  io.oscout := osc_inst.io.OSCOUT
  osc_inst.io.OSCEN := io.oscen
}
