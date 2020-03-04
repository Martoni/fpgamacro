# FpgaMacro
A Chisel3 package to describe verilog FPGA template and macro for hardened FPGA modules

To install it, clone it then publish local:
```bash
$ git clone https://github.com/Martoni/fpgamacro.git
$ cd fpgamacro
$ sbt publishLocal
```
Then add these lines in your `build.sbt` project :
```scala
//publish local https://github.com/Martoni/fpgamacro.git
libraryDependencies ++= Seq("org.armadeus" %% "fpgamacro" % "0.1")
```

## Generic

### ResetGen

Generating a reset pulse with initial 0 state.

### TriStateBuffer

Instanciation of 1 bit and 16bits tri-state buffers.

## MachXO3

### ReverseClock

A PLL instanciation of MachXO3 to reverse clock (180° phase).
