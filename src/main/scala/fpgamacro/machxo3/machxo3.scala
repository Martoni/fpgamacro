package fpgamacro.machxo3

import chisel3._
import chisel3.util._
import chisel3.experimental.Analog

class ReverseClock() extends BlackBox with HasBlackBoxInline {
  val io = IO(new Bundle {
    val CLKI = Input(Clock())
    val CLKOP = Output(Clock())
    val CLKOS = Output(Clock())
  })

  setInline("machxo3pll.v",
  s"""
  |/* Verilog netlist generated by SCUBA Diamond (64-bit) 3.10.0.111.2 */
  |/* Module Version: 5.7 */
  |/* /usr/local/diamond/3.10_x64/ispfpga/bin/lin64/scuba -w -n ReverseClock -lang verilog -synth lse -arch xo3c00f -type pll -fin 50.0 -fclkop 50 -fclkop_tol 0.0 -fclkos 50 -fclkos_tol 0.0 -trimp 0 -phasep 0 -trimp_r -trims 0 -phases 180 -trims_r -phase_cntl STATIC -fb_mode 5 -fracn 0  */
  |/* Thu Nov 14 11:25:05 2019 */
  |
  |
  |`timescale 1 ns / 1 ps
  |`ifdef COCOTB_SIM
  |module ReverseClock (CLKI, CLKOP, CLKOS);
  |    input wire CLKI;
  |    output wire CLKOP;
  |    output wire CLKOS;
  | assign CLKOP = !CLKI;
  | assign CLKOS = !CLKI;
  |endmodule
  |`else
  |module ReverseClock (CLKI, CLKOP, CLKOS)/* synthesis NGD_DRC_MASK=1 */;
  |    input wire CLKI;
  |    output wire CLKOP;
  |    output wire CLKOS;
  |
  |    wire LOCK;
  |    wire CLKOS_t;
  |    wire CLKOP_t;
  |    wire CLKFB_t;
  |    wire scuba_vlo;
  |
  |    VLO scuba_vlo_inst (.Z(scuba_vlo));
  |
  |    defparam PLLInst_0.DDRST_ENA = "DISABLED" ;
  |    defparam PLLInst_0.DCRST_ENA = "DISABLED" ;
  |    defparam PLLInst_0.MRST_ENA = "DISABLED" ;
  |    defparam PLLInst_0.PLLRST_ENA = "DISABLED" ;
  |    defparam PLLInst_0.INTFB_WAKE = "DISABLED" ;
  |    defparam PLLInst_0.STDBY_ENABLE = "DISABLED" ;
  |    defparam PLLInst_0.DPHASE_SOURCE = "DISABLED" ;
  |    defparam PLLInst_0.PLL_USE_WB = "DISABLED" ;
  |    defparam PLLInst_0.CLKOS3_FPHASE = 0 ;
  |    defparam PLLInst_0.CLKOS3_CPHASE = 0 ;
  |    defparam PLLInst_0.CLKOS2_FPHASE = 0 ;
  |    defparam PLLInst_0.CLKOS2_CPHASE = 0 ;
  |    defparam PLLInst_0.CLKOS_FPHASE = 0 ;
  |    defparam PLLInst_0.CLKOS_CPHASE = 14 ;
  |    defparam PLLInst_0.CLKOP_FPHASE = 0 ;
  |    defparam PLLInst_0.CLKOP_CPHASE = 9 ;
  |    defparam PLLInst_0.PLL_LOCK_MODE = 0 ;
  |    defparam PLLInst_0.CLKOS_TRIM_DELAY = 0 ;
  |    defparam PLLInst_0.CLKOS_TRIM_POL = "RISING" ;
  |    defparam PLLInst_0.CLKOP_TRIM_DELAY = 0 ;
  |    defparam PLLInst_0.CLKOP_TRIM_POL = "RISING" ;
  |    defparam PLLInst_0.FRACN_DIV = 0 ;
  |    defparam PLLInst_0.FRACN_ENABLE = "ENABLED" ;
  |    defparam PLLInst_0.OUTDIVIDER_MUXD2 = "DIVD" ;
  |    defparam PLLInst_0.PREDIVIDER_MUXD1 = 0 ;
  |    defparam PLLInst_0.VCO_BYPASS_D0 = "DISABLED" ;
  |    defparam PLLInst_0.CLKOS3_ENABLE = "DISABLED" ;
  |    defparam PLLInst_0.OUTDIVIDER_MUXC2 = "DIVC" ;
  |    defparam PLLInst_0.PREDIVIDER_MUXC1 = 0 ;
  |    defparam PLLInst_0.VCO_BYPASS_C0 = "DISABLED" ;
  |    defparam PLLInst_0.CLKOS2_ENABLE = "DISABLED" ;
  |    defparam PLLInst_0.OUTDIVIDER_MUXB2 = "DIVB" ;
  |    defparam PLLInst_0.PREDIVIDER_MUXB1 = 0 ;
  |    defparam PLLInst_0.VCO_BYPASS_B0 = "DISABLED" ;
  |    defparam PLLInst_0.CLKOS_ENABLE = "ENABLED" ;
  |    defparam PLLInst_0.OUTDIVIDER_MUXA2 = "DIVA" ;
  |    defparam PLLInst_0.PREDIVIDER_MUXA1 = 0 ;
  |    defparam PLLInst_0.VCO_BYPASS_A0 = "DISABLED" ;
  |    defparam PLLInst_0.CLKOP_ENABLE = "ENABLED" ;
  |    defparam PLLInst_0.CLKOS3_DIV = 1 ;
  |    defparam PLLInst_0.CLKOS2_DIV = 1 ;
  |    defparam PLLInst_0.CLKOS_DIV = 10 ;
  |    defparam PLLInst_0.CLKOP_DIV = 10 ;
  |    defparam PLLInst_0.CLKFB_DIV = 1 ;
  |    defparam PLLInst_0.CLKI_DIV = 1 ;
  |    defparam PLLInst_0.FEEDBK_PATH = "INT_DIVA" ;
  |    EHXPLLJ PLLInst_0 (.CLKI(CLKI), .CLKFB(CLKFB_t), .PHASESEL1(scuba_vlo), 
  |        .PHASESEL0(scuba_vlo), .PHASEDIR(scuba_vlo), .PHASESTEP(scuba_vlo), 
  |        .LOADREG(scuba_vlo), .STDBY(scuba_vlo), .PLLWAKESYNC(scuba_vlo), 
  |        .RST(scuba_vlo), .RESETM(scuba_vlo), .RESETC(scuba_vlo), .RESETD(scuba_vlo), 
  |        .ENCLKOP(scuba_vlo), .ENCLKOS(scuba_vlo), .ENCLKOS2(scuba_vlo), 
  |        .ENCLKOS3(scuba_vlo), .PLLCLK(scuba_vlo), .PLLRST(scuba_vlo), .PLLSTB(scuba_vlo), 
  |        .PLLWE(scuba_vlo), .PLLADDR4(scuba_vlo), .PLLADDR3(scuba_vlo), .PLLADDR2(scuba_vlo), 
  |        .PLLADDR1(scuba_vlo), .PLLADDR0(scuba_vlo), .PLLDATI7(scuba_vlo), 
  |        .PLLDATI6(scuba_vlo), .PLLDATI5(scuba_vlo), .PLLDATI4(scuba_vlo), 
  |        .PLLDATI3(scuba_vlo), .PLLDATI2(scuba_vlo), .PLLDATI1(scuba_vlo), 
  |        .PLLDATI0(scuba_vlo), .CLKOP(CLKOP_t), .CLKOS(CLKOS_t), .CLKOS2(), 
  |        .CLKOS3(), .LOCK(LOCK), .INTLOCK(), .REFCLK(), .CLKINTFB(CLKFB_t), 
  |        .DPHSRC(), .PLLACK(), .PLLDATO7(), .PLLDATO6(), .PLLDATO5(), .PLLDATO4(), 
  |        .PLLDATO3(), .PLLDATO2(), .PLLDATO1(), .PLLDATO0())
  |             /* synthesis FREQUENCY_PIN_CLKOS="50.000000" */
  |             /* synthesis FREQUENCY_PIN_CLKOP="50.000000" */
  |             /* synthesis FREQUENCY_PIN_CLKI="50.000000" */
  |             /* synthesis ICP_CURRENT="9" */
  |             /* synthesis LPF_RESISTOR="72" */;
  |
  |    assign CLKOS = CLKOS_t;
  |    assign CLKOP = CLKOP_t;
  |
  |
  |    // exemplar begin
  |    // exemplar attribute PLLInst_0 FREQUENCY_PIN_CLKOS 50.000000
  |    // exemplar attribute PLLInst_0 FREQUENCY_PIN_CLKOP 50.000000
  |    // exemplar attribute PLLInst_0 FREQUENCY_PIN_CLKI 50.000000
  |    // exemplar attribute PLLInst_0 ICP_CURRENT 9
  |    // exemplar attribute PLLInst_0 LPF_RESISTOR 72
  |    // exemplar end
  |
  |endmodule
  |`endif
  """.stripMargin)
}
