## EOS-S3 \label{eos_s3}

EOS-S3 is a microcontroller (Cortex-M4F core) with an eFPGA included. All tools
are opensource and available on a [git repository](https://github.com/QuickLogic-Corp/quick-feather-dev-board).

All communication with MCU core is done by a big macrocell named
`qlal4s3b_cell_macro`.

This package declare some symplified macros mainly for clock and reset:

- `qlal4s3b_cell_macro`: the big macrocell exporting all signal to and from
  core. 

- `Sys_Clk`: An only one output module that export clock `sys_clk_0` comming
  from core

- `SysClkAndRst`: export clock `sys_clk_0` and reset `sys_clk_0_rst`

