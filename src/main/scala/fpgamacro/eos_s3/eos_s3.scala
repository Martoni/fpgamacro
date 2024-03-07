package fpgamacro.eos_s3

import chisel3._
import circt.stage.ChiselStage
import chisel3.util._
import chisel3.experimental.Analog

/* qlal4s3b_cell_macro */
class qlal4s3b_cell_macro() extends BlackBox() {
  val io = IO(new Bundle{
    val WBs_ADR      = Output(UInt(17.W)) // output [16:0] | Address Bus       to FPGA
    val WBs_CYC      = Output(Bool())     // output        | Cycle Chip Select to FPGA
    val WBs_BYTE_STB = Output(UInt(4.W))  // output  [3:0] | Byte Select       to FPGA
    val WBs_WE       = Output(Bool())     // output        | Write Enable      to FPGA
    val WBs_RD       = Output(Bool())     // output        | Read  Enable      to FPGA
    val WBs_STB      = Output(Bool())     // output        | Strobe Signal     to FPGA
    val WBs_WR_DAT   = Output(UInt(32.W)) // output [31:0] | Write Data Bus    to FPGA
    val WB_CLK       = Input(Clock())     // input         | FPGA Clock                 from FPGA
    val WB_RST       = Output(Bool())     // output        | FPGA Reset                 to   FPGA
    val WBs_RD_DAT   = Input(UInt(32.W))  // input  [31:0] | Read Data Bus              from FPGA
    val WBs_ACK      = Input(Bool())      // input         | Transfer Cycle Acknowledge from FPGA
    // SDMA Signals
    val SDMA_Req     = Input(UInt(4.W))  // input   [3:0]
    val SDMA_Sreq    = Input(UInt(4.W))  // input   [3:0]
    val SDMA_Done    = Output(UInt(4.W)) // output  [3:0]
    val SDMA_Active  = Output(UInt(4.W)) // output  [3:0]
    // FB Interrupts
    val FB_msg_out   = Input(UInt(4.W)) // input   [3:0]
    val FB_Int_Clr   = Input(UInt(8.W)) // input   [7:0]
    val FB_Start     = Output(Bool())   // output
    val FB_Busy      = Input(Bool())    // input
    // FB Clocks
    val Sys_Clk0     = Output(Clock()) // output
    val Sys_Clk0_Rst = Output(Bool()) // output
    val Sys_Clk1     = Output(Clock()) // output
    val Sys_Clk1_Rst = Output(Bool()) // output
    // Packet FIFO
    val Sys_PKfb_Clk    = Input(Clock())  // input
    val Sys_PKfb_Rst    = Output(Bool()) // output
    val FB_PKfbData     = Input(UInt(32.W)) // input  [31:0]
    val FB_PKfbPush     = Input(UInt(4.W))  // input   [3:0]
    val FB_PKfbSOF      = Input(Bool())  // input
    val FB_PKfbEOF      = Input(Bool())  // input
    val FB_PKfbOverflow = Output(Bool()) // output
    // Sensor Interface
    val Sensor_Int = Output(UInt(8.W))  // output  [7:0]
    val TimeStamp  = Output(UInt(24.W)) // output [23:0]
    // SPI Master APB Bus
    val Sys_Pclk      = Output(Clock()) // output
    val Sys_Pclk_Rst  = Output(Bool()) // output      <-- Fixed to add "_Rst"
    val Sys_PSel      = Input(Bool()) // input
    val SPIm_Paddr    = Input(UInt(16.W)) // input  [15:0]
    val SPIm_PEnable  = Input(Bool()) // input
    val SPIm_PWrite   = Input(Bool()) // input
    val SPIm_PWdata   = Input(UInt(32.W))  // input  [31:0]
    val SPIm_Prdata   = Output(UInt(32.W)) // output [31:0]
    val SPIm_PReady   = Output(Bool()) // output
    val SPIm_PSlvErr  = Output(Bool()) // output
    // Misc
    val Device_ID  = Input(UInt(16.W)) // input  [15:0]
    // FBIO Signals
    val FBIO_In      = Output(UInt(14.W)) // output [13:0] <-- Do Not make any connections; Use Constraint manager in SpDE to sFBIO
    val FBIO_In_En   = Input(UInt(14.W)) // input  [13:0] <-- Do Not make any connections; Use Constraint manager in SpDE to sFBIO
    val FBIO_Out     = Input(UInt(14.W)) // input  [13:0] <-- Do Not make any connections; Use Constraint manager in SpDE to sFBIO
    val FBIO_Out_En  = Input(UInt(14.W)) // input  [13:0] <-- Do Not make any connections; Use Constraint manager in SpDE to sFBIO
    // ???
    val SFBIO            = Analog(14.W)  // inout  [13:0]
    val Device_ID_6S     = Input(Bool()) // input
    val Device_ID_4S     = Input(Bool()) // input
    val SPIm_PWdata_26S  = Input(Bool()) // input
    val SPIm_PWdata_24S  = Input(Bool()) // input
    val SPIm_PWdata_14S  = Input(Bool()) // input
    val SPIm_PWdata_11S  = Input(Bool()) // input
    val SPIm_PWdata_0S   = Input(Bool()) // input
    val SPIm_Paddr_8S    = Input(Bool()) // input
    val SPIm_Paddr_6S    = Input(Bool()) // input
    val FB_PKfbPush_1S   = Input(Bool()) // input
    val FB_PKfbData_31S  = Input(Bool()) // input
    val FB_PKfbData_21S  = Input(Bool()) // input
    val FB_PKfbData_19S  = Input(Bool()) // input
    val FB_PKfbData_9S   = Input(Bool()) // input
    val FB_PKfbData_6S   = Input(Bool()) // input
    val Sys_PKfb_ClkS    = Input(Bool()) // input
    val FB_BusyS         = Input(Bool()) // input
    val WB_CLKS          = Input(Clock()) // input
  })
}

class SysClk extends RawModule {
  val io = IO(new Bundle {
    val sys_clk_0 = Output(Clock())
  })
  val u_qlal4s3b_cell_macro = Module(new qlal4s3b_cell_macro())
  io.sys_clk_0 := u_qlal4s3b_cell_macro.io.Sys_Clk0

  /* DontCare */
  u_qlal4s3b_cell_macro.io.WB_CLK := DontCare 
  u_qlal4s3b_cell_macro.io.WBs_RD_DAT := DontCare 
  u_qlal4s3b_cell_macro.io.WBs_ACK := DontCare 
  u_qlal4s3b_cell_macro.io.SDMA_Req := DontCare 
  u_qlal4s3b_cell_macro.io.SDMA_Sreq := DontCare 
  u_qlal4s3b_cell_macro.io.FB_msg_out := DontCare 
  u_qlal4s3b_cell_macro.io.FB_Int_Clr := DontCare 
  u_qlal4s3b_cell_macro.io.FB_Busy := DontCare 
  u_qlal4s3b_cell_macro.io.Sys_PKfb_Clk := DontCare 
  u_qlal4s3b_cell_macro.io.FB_PKfbData := DontCare 
  u_qlal4s3b_cell_macro.io.FB_PKfbPush := DontCare 
  u_qlal4s3b_cell_macro.io.FB_PKfbSOF := DontCare 
  u_qlal4s3b_cell_macro.io.FB_PKfbEOF := DontCare 
  u_qlal4s3b_cell_macro.io.Sys_PSel := DontCare 
  u_qlal4s3b_cell_macro.io.SPIm_Paddr := DontCare 
  u_qlal4s3b_cell_macro.io.SPIm_PEnable := DontCare 
  u_qlal4s3b_cell_macro.io.SPIm_PWrite := DontCare 
  u_qlal4s3b_cell_macro.io.SPIm_PWdata := DontCare 
  u_qlal4s3b_cell_macro.io.Device_ID := DontCare 
  u_qlal4s3b_cell_macro.io.FBIO_In_En := DontCare 
  u_qlal4s3b_cell_macro.io.FBIO_Out := DontCare 
  u_qlal4s3b_cell_macro.io.FBIO_Out_En := DontCare 
  u_qlal4s3b_cell_macro.io.Device_ID_6S := DontCare 
  u_qlal4s3b_cell_macro.io.Device_ID_4S := DontCare 
  u_qlal4s3b_cell_macro.io.SPIm_PWdata_26S := DontCare 
  u_qlal4s3b_cell_macro.io.SPIm_PWdata_24S := DontCare 
  u_qlal4s3b_cell_macro.io.SPIm_PWdata_14S := DontCare 
  u_qlal4s3b_cell_macro.io.SPIm_PWdata_11S := DontCare 
  u_qlal4s3b_cell_macro.io.SPIm_PWdata_0S := DontCare 
  u_qlal4s3b_cell_macro.io.SPIm_Paddr_8S := DontCare 
  u_qlal4s3b_cell_macro.io.SPIm_Paddr_6S := DontCare 
  u_qlal4s3b_cell_macro.io.FB_PKfbPush_1S := DontCare 
  u_qlal4s3b_cell_macro.io.FB_PKfbData_31S := DontCare 
  u_qlal4s3b_cell_macro.io.FB_PKfbData_21S := DontCare 
  u_qlal4s3b_cell_macro.io.FB_PKfbData_19S := DontCare 
  u_qlal4s3b_cell_macro.io.FB_PKfbData_9S := DontCare 
  u_qlal4s3b_cell_macro.io.FB_PKfbData_6S := DontCare 
  u_qlal4s3b_cell_macro.io.Sys_PKfb_ClkS := DontCare 
  u_qlal4s3b_cell_macro.io.FB_BusyS := DontCare 
  u_qlal4s3b_cell_macro.io.WB_CLKS := DontCare 
}

class SysClkAndRst extends RawModule {
  val io = IO(new Bundle {
    val sys_clk_0 = Output(Clock())
    val sys_clk_0_rst = Output(Bool())
  })
  val u_qlal4s3b_cell_macro = Module(new qlal4s3b_cell_macro())
  io.sys_clk_0 := u_qlal4s3b_cell_macro.io.Sys_Clk0
  io.sys_clk_0_rst := u_qlal4s3b_cell_macro.io.Sys_Clk0_Rst

  /* DontCare */
  u_qlal4s3b_cell_macro.io.WB_CLK := DontCare 
  u_qlal4s3b_cell_macro.io.WBs_RD_DAT := DontCare 
  u_qlal4s3b_cell_macro.io.WBs_ACK := DontCare 
  u_qlal4s3b_cell_macro.io.SDMA_Req := DontCare 
  u_qlal4s3b_cell_macro.io.SDMA_Sreq := DontCare 
  u_qlal4s3b_cell_macro.io.FB_msg_out := DontCare 
  u_qlal4s3b_cell_macro.io.FB_Int_Clr := DontCare 
  u_qlal4s3b_cell_macro.io.FB_Busy := DontCare 
  u_qlal4s3b_cell_macro.io.Sys_PKfb_Clk := DontCare 
  u_qlal4s3b_cell_macro.io.FB_PKfbData := DontCare 
  u_qlal4s3b_cell_macro.io.FB_PKfbPush := DontCare 
  u_qlal4s3b_cell_macro.io.FB_PKfbSOF := DontCare 
  u_qlal4s3b_cell_macro.io.FB_PKfbEOF := DontCare 
  u_qlal4s3b_cell_macro.io.Sys_PSel := DontCare 
  u_qlal4s3b_cell_macro.io.SPIm_Paddr := DontCare 
  u_qlal4s3b_cell_macro.io.SPIm_PEnable := DontCare 
  u_qlal4s3b_cell_macro.io.SPIm_PWrite := DontCare 
  u_qlal4s3b_cell_macro.io.SPIm_PWdata := DontCare 
  u_qlal4s3b_cell_macro.io.Device_ID := DontCare 
  u_qlal4s3b_cell_macro.io.FBIO_In_En := DontCare 
  u_qlal4s3b_cell_macro.io.FBIO_Out := DontCare 
  u_qlal4s3b_cell_macro.io.FBIO_Out_En := DontCare 
  u_qlal4s3b_cell_macro.io.Device_ID_6S := DontCare 
  u_qlal4s3b_cell_macro.io.Device_ID_4S := DontCare 
  u_qlal4s3b_cell_macro.io.SPIm_PWdata_26S := DontCare 
  u_qlal4s3b_cell_macro.io.SPIm_PWdata_24S := DontCare 
  u_qlal4s3b_cell_macro.io.SPIm_PWdata_14S := DontCare 
  u_qlal4s3b_cell_macro.io.SPIm_PWdata_11S := DontCare 
  u_qlal4s3b_cell_macro.io.SPIm_PWdata_0S := DontCare 
  u_qlal4s3b_cell_macro.io.SPIm_Paddr_8S := DontCare 
  u_qlal4s3b_cell_macro.io.SPIm_Paddr_6S := DontCare 
  u_qlal4s3b_cell_macro.io.FB_PKfbPush_1S := DontCare 
  u_qlal4s3b_cell_macro.io.FB_PKfbData_31S := DontCare 
  u_qlal4s3b_cell_macro.io.FB_PKfbData_21S := DontCare 
  u_qlal4s3b_cell_macro.io.FB_PKfbData_19S := DontCare 
  u_qlal4s3b_cell_macro.io.FB_PKfbData_9S := DontCare 
  u_qlal4s3b_cell_macro.io.FB_PKfbData_6S := DontCare 
  u_qlal4s3b_cell_macro.io.Sys_PKfb_ClkS := DontCare 
  u_qlal4s3b_cell_macro.io.FB_BusyS := DontCare 
  u_qlal4s3b_cell_macro.io.WB_CLKS := DontCare 
}

object SysClkAndRst extends App {
  val verilog_src = ChiselStage.emitSystemVerilog(
      new SysClkAndRst(),
      firtoolOpts = Array("-disable-all-randomization",
                          "-strip-debug-info"))
  val fverilog = os.pwd / "SysClkAndRst.v"
  if(os.exists(fverilog))
    os.remove(fverilog)
  os.write(fverilog, verilog_src)
}

// TODO: integrate this macro in chisel
//
//  qlal4s3b_cell_macro 
//    u_qlal4s3b_cell_macro 
//    (
//        // AHB-To-FPGA Bridge
//        .WBs_ADR                   ( WBs_ADR                        ), // output [16:0] | Address Bus                   to   FPGA
//        .WBs_CYC                   ( WBs_CYC                        ), // output        | Cycle Chip Select             to   FPGA
//        .WBs_BYTE_STB              ( WBs_BYTE_STB                   ), // output  [3:0] | Byte Select                   to   FPGA
//        .WBs_WE                    ( WBs_WE                         ), // output        | Write Enable                  to   FPGA
//        .WBs_RD                    ( WBs_RD                         ), // output        | Read  Enable                  to   FPGA
//        .WBs_STB                   ( WBs_STB                        ), // output        | Strobe Signal                 to   FPGA
//        .WBs_WR_DAT                ( WBs_WR_DAT                     ), // output [31:0] | Write Data Bus                to   FPGA
//        .WB_CLK                    ( WB_CLK                         ), // input         | FPGA Clock                    from FPGA
//        .WB_RST                    ( WB_RST                         ), // output        | FPGA Reset                    to   FPGA
//        .WBs_RD_DAT                ( WBs_RD_DAT                     ), // input  [31:0] | Read Data Bus                 from FPGA
//        .WBs_ACK                   ( WBs_ACK                        ), // input         | Transfer Cycle Acknowledge    from FPGA
//
//        // SDMA Signals
//        .SDMA_Req                  ( {3'b000, 1'b0}                 ), // input   [3:0]
//        .SDMA_Sreq                 ( 4'b0000                        ), // input   [3:0]
//        .SDMA_Done                 (                                ), // output  [3:0]
//        .SDMA_Active               (                                ), // output  [3:0]
//
//        // FB Interrupts
//        .FB_msg_out                ( {1'b0, 1'b0, 1'b0, FPGA_INTR[0]}), // input   [3:0]
//        .FB_Int_Clr                ( 8'h0                           ), // input   [7:0]
//        .FB_Start                  (                                ), // output
//        .FB_Busy                   ( 1'b0                           ), // input
//
//        // FB Clocks
//        .Sys_Clk0                  ( Sys_Clk0                       ), // output
//        .Sys_Clk0_Rst              ( Sys_Clk0_Rst                   ), // output
//        .Sys_Clk1                  ( Sys_Clk1                       ), // output
//        .Sys_Clk1_Rst              ( Sys_Clk1_Rst                   ), // output
//
//        // Packet FIFO
//        .Sys_PKfb_Clk              (  1'b0                          ), // input
//        .Sys_PKfb_Rst              (                                ), // output
//        .FB_PKfbData               ( 32'h0                          ), // input  [31:0]
//        .FB_PKfbPush               (  4'h0                          ), // input   [3:0]
//        .FB_PKfbSOF                (  1'b0                          ), // input
//        .FB_PKfbEOF                (  1'b0                          ), // input
//        .FB_PKfbOverflow           (                                ), // output
//
//        // Sensor Interface
//        .Sensor_Int                (                                ), // output  [7:0]
//        .TimeStamp                 (                                ), // output [23:0]
//
//        // SPI Master APB Bus
//        .Sys_Pclk                  (                                ), // output
//        .Sys_Pclk_Rst              (                                ), // output      <-- Fixed to add "_Rst"
//        .Sys_PSel                  (  1'b0                          ), // input
//        .SPIm_Paddr                ( 16'h0                          ), // input  [15:0]
//        .SPIm_PEnable              (  1'b0                          ), // input
//        .SPIm_PWrite               (  1'b0                          ), // input
//        .SPIm_PWdata               ( 32'h0                          ), // input  [31:0]
//        .SPIm_Prdata               (                                ), // output [31:0]
//        .SPIm_PReady               (                                ), // output
//        .SPIm_PSlvErr              (                                ), // output
//
//        // Misc
//        .Device_ID                 ( Device_ID                      ), // input  [15:0]
//
//        // FBIO Signals
//        .FBIO_In                   (                                ), // output [13:0] <-- Do Not make any connections; Use Constraint manager in SpDE to sFBIO
//        .FBIO_In_En                (                                ), // input  [13:0] <-- Do Not make any connections; Use Constraint manager in SpDE to sFBIO
//        .FBIO_Out                  (                                ), // input  [13:0] <-- Do Not make any connections; Use Constraint manager in SpDE to sFBIO
//        .FBIO_Out_En               (                                ), // input  [13:0] <-- Do Not make any connections; Use Constraint manager in SpDE to sFBIO
//
//        // ???
//        .SFBIO                     (                                ), // inout  [13:0]
//        .Device_ID_6S              ( 1'b0                           ), // input
//        .Device_ID_4S              ( 1'b0                           ), // input
//        .SPIm_PWdata_26S           ( 1'b0                           ), // input
//        .SPIm_PWdata_24S           ( 1'b0                           ), // input
//        .SPIm_PWdata_14S           ( 1'b0                           ), // input
//        .SPIm_PWdata_11S           ( 1'b0                           ), // input
//        .SPIm_PWdata_0S            ( 1'b0                           ), // input
//        .SPIm_Paddr_8S             ( 1'b0                           ), // input
//        .SPIm_Paddr_6S             ( 1'b0                           ), // input
//        .FB_PKfbPush_1S            ( 1'b0                           ), // input
//        .FB_PKfbData_31S           ( 1'b0                           ), // input
//        .FB_PKfbData_21S           ( 1'b0                           ), // input
//        .FB_PKfbData_19S           ( 1'b0                           ), // input
//        .FB_PKfbData_9S            ( 1'b0                           ), // input
//        .FB_PKfbData_6S            ( 1'b0                           ), // input
//        .Sys_PKfb_ClkS             ( 1'b0                           ), // input
//        .FB_BusyS                  ( 1'b0                           ), // input
//        .WB_CLKS                   ( 1'b0                           )  // input
//
//    );
